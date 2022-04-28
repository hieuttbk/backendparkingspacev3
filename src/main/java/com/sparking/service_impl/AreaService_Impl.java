package com.sparking.service_impl;

import com.sparking.entities.data.Area;
import com.sparking.entities.data.Manager;
import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.repository.AreaRepo;
import com.sparking.repository.ManagerRepo;
import com.sparking.security.JWTService;
import com.sparking.service.AreaService;
import com.sparking.service.FieldService;
import com.sparking.service.ManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService_Impl implements AreaService {
    private final Logger logger = LoggerFactory.getLogger(AreaService_Impl.class);

    @Autowired
    private AreaRepo areaRepo;

    @Autowired
    private ManagerRepo managerRepo;


    @Override
    public List<Area> getAllAreas() {
        return areaRepo.getAllAreas();
    }

    @Override
    public MyResponse mnGetAllAreas(String decode) {
        try {
            Manager manager = managerRepo.findByEmail(decode);
            if (manager == null) {
                this.logger.info("Manager Not Found");
                return null;
            }
            List<Area> areas = areaRepo.mnGetAllAreas(manager);
            return MyResponse.success(areas);
        } catch (Exception e) {
            this.logger.error("Manager GetAll Areas wrong: " + e.toString());
            return MyResponse.fail(e.getStackTrace());
        }
    }

}
