package com.sparking.service_impl;

import com.sparking.entities.data.District;
import com.sparking.entities.data.Manager;
import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.repository.DistrictRepo;
import com.sparking.repository.ManagerRepo;
import com.sparking.security.JWTService;
import com.sparking.service.DistrictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictService_Impl implements DistrictService {
    private static final Logger logger = LoggerFactory.getLogger(DistrictService_Impl.class);

    @Autowired
    private DistrictRepo districtRepo;

    @Autowired
    private ManagerRepo managerRepo;

    @Override
    public List<District> getAllDistrict() {
        return districtRepo.getAllDistrict();
    }

    @Override
    public MyResponse mnGetAllDistricts(String decode) {
        try {
            Manager manager = managerRepo.findByEmail(decode);
            List<District> districts = districtRepo.mnGetAllDistricts(manager);
            return MyResponse.success(districts);
        } catch (Exception e) {
            this.logger.error("mnGetAllDistricts wrong: " + e.toString());
            return MyResponse.fail(e.getStackTrace());
        }
    }
}
