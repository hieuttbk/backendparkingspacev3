package com.sparking;

import com.sparking.entities.data.Field;
import com.sparking.entities.data.Gateway;
import com.sparking.entities.data.Slot;
import com.sparking.repository.FieldRepo;
import com.sparking.repository.GatewayRepo;
import com.sparking.repository.PackageRepo;
import com.sparking.repository.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Service
@Transactional
public class InitData {

    @Autowired
    private FieldRepo fieldRepo;

    @Autowired
    private GatewayRepo gatewayRepo;

    @Autowired
    SlotRepo slotRepo;

    @Autowired
    PackageRepo packageRepo;

    @PostConstruct
    public void init(){
//        if(slotRepo.findAll().size() < 100){
//            System.out.println("Init 100 slot");
//            fieldRepo.createAndUpdate(new Field(1,"C9", "20.960377427559497", "105.79658800934396", "Đại học BKHN", "", 50000.0, "O", new BigDecimal("50.0"), "Bãi đồ xe"));
//            fieldRepo.createAndUpdate(new Field(2,"C3", "21.0066272", "105.8416806", "Đại học BKHN", "", 50000.0, "O", new BigDecimal("40.0"), "Bãi đồ xe"));
//            gatewayRepo.createAndUpdate(new Gateway(1,1,"1.1.1.1"));
//            gatewayRepo.createAndUpdate(new Gateway(2,2,"2.2.2.2"));
//            for (int i = 0; i < 50; i++) {
//                slotRepo.createAndUpdate(new Slot(i+1, 1, false, false));
//            }
//            for (int i = 0; i < 50; i++) {
//                slotRepo.createAndUpdate(new Slot(i+51, 2, false, false));
//            }
//            System.out.println("Init data end");
//        }
    }
}