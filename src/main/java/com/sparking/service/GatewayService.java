package com.sparking.service;

import com.sparking.entities.data.Gateway;
import com.sparking.entities.json.GatewayJson;

import java.util.List;

public interface GatewayService {

    GatewayJson createAndUpdate(Gateway gateway);

    boolean delete(int id);

    List<GatewayJson> findAll();

    List<Gateway> managerFind(String phone);

    Gateway managerUpdate(Gateway gateway, String phone);

    boolean managerDelete(int id, String phone);
}
