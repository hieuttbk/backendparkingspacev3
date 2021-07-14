package com.hongdatchy.repository;

import com.hongdatchy.entities.data.BlackList;

import java.util.List;

public interface BlackListRepo {

    BlackList create(String token);

    List<BlackList> findByToken(String token);

}
