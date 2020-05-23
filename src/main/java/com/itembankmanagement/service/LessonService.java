package com.itembankmanagement.service;

import com.itembankmanagement.util.Result;

public interface LessonService {
    Result adminAdd(String name,String college,Integer sum);

    Result adminDelete(Long id);

    Result adminGetInfo(Long id);

    Result adminModification(Long id,String name,String college,Integer sum);

    Result adminGetALl();

    Result getByUserId(Long id);
}
