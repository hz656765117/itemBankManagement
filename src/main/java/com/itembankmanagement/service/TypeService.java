package com.itembankmanagement.service;

import com.itembankmanagement.util.Result;

public interface TypeService {
    Result add(String name);

    Result delete(Long id);

    Result modification(Long id,String name);

    Result get(Long id);

    Result getAll();
}
