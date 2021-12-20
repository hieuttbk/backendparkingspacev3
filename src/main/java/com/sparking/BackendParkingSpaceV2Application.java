package com.sparking;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import com.sparking.entities.data.*;
import com.sparking.entities.jsonResp.FieldAnalysis;
import com.sparking.getData.GetTime;
import com.sparking.getData.TagModule;
import com.sparking.repository.*;
import com.sparking.service.FieldService;
import com.sparking.service_impl.GoogleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.hibernate.internal.CoreLogging.logger;


@SpringBootApplication
@EnableSwagger2
public class BackendParkingSpaceV2Application implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(BackendParkingSpaceV2Application.class);

    @Autowired
    SlotRepo slotRepo;

    @Autowired
    FieldRepo fieldRepo;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    GoogleService googleService;

    @Autowired
    DataCamAndDetectorRepo dataCamAndDetectorRepo;

    @Autowired
    FieldService fieldService;

    @Autowired
    StatsFieldRepo statsFieldRepoRepo;


    List<String> rows = new ArrayList<>();

    List<Slot> slots = new ArrayList<>();


    public static void main(String[] args) {
        SpringApplication.run(BackendParkingSpaceV2Application.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.ant("/**")).build();
    }

    @Value("${pathCamStatus}")
    String pathDataCam;
    @Value("${pathDetectorStatus}")
    String pathDetectorStatus;
    @Value("${timeExpiredContract}")
    String timeExpiredContract;

    @Autowired
    com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        logger.info("SERVER STARTED");

       // System.out.println("******************** Start server ********************");
//        set timezone cho backend
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
//        set timezone cho controller
        objectMapper.setTimeZone(TimeZone.getDefault());

        //TODO
        // using Thread or sthg else !!!!!!!
        logger.info("DELETE EXPIRED CONTRACT");
     //   update();
//        GetDataDetector.main(args);

        logger.info("START TAG MODULE");
     //   TagModule.start();

        logger.info("UPDATE STATS FIELD");
        updateStatsField();

        logger.info("UPDATE STATS FIELD FREQ");
        updateStatsFieldFreq();
    }

    public void update() throws FileNotFoundException, InterruptedException, UnsupportedEncodingException {
        while (true){
            deleteExpiredContract();
            Thread.sleep(5000);
        }
    }

    public boolean getDataCam() throws FileNotFoundException {
        File file = new File (pathDataCam);
        if (!file.exists()) {
            return false;
        }

        Scanner myReader = new Scanner(file);
        List<String> newRows = new ArrayList<>();
        while (myReader.hasNextLine()) {
            String row = myReader.nextLine();
            newRows.add(row);
        }
        if(!rows.equals(newRows)) {
            System.out.println("Data cam has changed");
            rows.clear();
            rows.addAll(newRows);
            for (int i = 1; i< rows.size(); i++){
                boolean status = rows.get(i).split(" ")[1].equals("1");
//                fake field cho slot
                int fieldId = 1;
//                so thu tu sua slot trong field
                int stt = Integer.parseInt(rows.get(i).split(" ")[0]) - 1;
                Slot oldSlot = slotRepo.findAll().stream()
                        .filter(slot -> slot.getFieldId() == fieldId)
                        .collect(Collectors.toList())
                        .get(stt);
                oldSlot.setStatusCam(status);
                slotRepo.createAndUpdate(oldSlot);
                //            dataCamAndDetector
                dataCamAndDetectorRepo.createAndUpdate(DataCamAndDetector.builder()
                        .statusCam(status)
                        .slotId(stt)
                        .time(GetTime.getTime(rows.get(0)))
                        .build());
            }
            System.out.println("Data cam has updated successfully");
        }
        myReader.close();
        return true;
    }

    public void writeDataDetector() throws  FileNotFoundException, UnsupportedEncodingException {
        List<Slot> newSlots = slotRepo.findAll();
        if(!slots.equals(newSlots)){
            PrintWriter writer = new PrintWriter(pathDetectorStatus, "UTF-8");
            for (Slot slot : newSlots){
                writer.println(slot.getId() + " " + (slot.getStatusDetector() == null ? "2" : slot.getStatusDetector() ? "1": "0"));
            }
            writer.close();
            slots.clear();
            slots.addAll(newSlots);
        }
    }

    void deleteExpiredContract() {
        for(Contract contract: contractRepo.findAll()){

            if (contract.getTimeInBook()!=null) {
                if (new Timestamp(new Date().getTime()).getTime() - contract.getTimeInBook().getTime() >= Integer.parseInt(timeExpiredContract)
                        && contract.getTimeCarIn() == null) {
                    contractRepo.delete(contract.getId());
                }
            }
        }
    }

    // auto update statistic field
    void updateStatsField() throws ParseException {
        List<StatsField> s = statsFieldRepoRepo.getLatest();
        if (s==null){
            logger.info("UPDATE STATS FIELD");
            List<Contract> contracts=contractRepo.findAll();
            List<Field> fields = fieldRepo.findAll();
            Timestamp until = new Timestamp(new Date().getTime());
            Timestamp since = until;


            for(Contract contract: contracts){
                if (contract.getTimeInBook()!=null) {
                    if (contract.getTimeInBook().before(since))
                        since = contract.getTimeInBook();
                }

            }
            long millisInDay = 60 * 60 * 24 * 1000;
            //long currentTime = new Date().getTime();
            long sinceDateOnly = (since.getTime() / millisInDay) * millisInDay;
            long untilDateOnly = (until.getTime() / millisInDay) * millisInDay;
            //  Date clearDate = new Date(dateOnly);
           // logger.info("since " + sinceDateOnly );
           // logger.info("until " + untilDateOnly );

            for (Field f : fields) {
                List<FieldAnalysis> fieldAnalyses = fieldService.analysis(f.getId(), sinceDateOnly, untilDateOnly, "day");

                for(FieldAnalysis fa: fieldAnalyses) {
                   // logger.info("fieldAnalyses " + fa.getFreq());
                    statsFieldRepoRepo.createAndUpdate(StatsField.builder().
                            id(-1).
                            fieldId(f.getId()).
                            day(new Timestamp(fa.getTime())).
                            freq(fa.getFreq()).
                            cost(fa.getCost()).
                            build());
                }
            }
        }

    }

    void updateStatsFieldFreq() throws ParseException {
        List<StatsField> s = statsFieldRepoRepo.getLatest();

        List<Field> fields = fieldRepo.findAll();
        Timestamp until = new Timestamp(new Date().getTime());
        Timestamp since = s.get(s.size()-1).getDay();

        long millisInDay = 60 * 60 * 24 * 1000;
        //long currentTime = new Date().getTime();
        long sinceDateOnly = (since.getTime() / millisInDay) * millisInDay;
        long untilDateOnly = (until.getTime() / millisInDay) * millisInDay;
        //  Date clearDate = new Date(dateOnly);

        for (Field f : fields) {
            List<FieldAnalysis> fieldAnalyses = fieldService.analysis(f.getId(), since.getTime(), until.getTime(), "day");
            for(FieldAnalysis fa: fieldAnalyses) {
                statsFieldRepoRepo.createAndUpdate(StatsField.builder().
                        id(0).
                        fieldId(f.getId()).
                        day(new Timestamp(fa.getTime())).
                        freq(fa.getFreq()).
                        cost(fa.getCost()).
                        build());
            }
        }
    }

    public void updateStatsFieldFreqTime() throws FileNotFoundException, InterruptedException, UnsupportedEncodingException, ParseException {
        while (true){
            updateStatsFieldFreq();
            long millisInDay = 60 * 60 * 24 * 1000;
            Thread.sleep(millisInDay);
        }
    }

}
