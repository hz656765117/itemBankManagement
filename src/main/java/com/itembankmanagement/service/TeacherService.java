package com.itembankmanagement.service;

import com.itembankmanagement.util.Result;

public interface TeacherService {
    Result add(String name,Integer age,String sex,String address);

    Result delete(Long id);

    Result modification(Long id,String name,Integer age,String sex,String address);

    Result get(Long id);
}
