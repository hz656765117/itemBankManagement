package com.itembankmanagement.controller;

import com.itembankmanagement.service.TypeService;
import com.itembankmanagement.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/titleType")
@RestController
public class TitleTypeController {
    @Autowired
    TypeService typeService;

    @PostMapping("/add")
    Result typeAdd(String name){
        return typeService.add(name);
    }

    @GetMapping("/get")
    Result typeGet(Long id){
        return typeService.get(id);
    }

    @PostMapping("/delete")
    Result typeDelete(Long id){
        return typeService.delete(id);
    }

    @PostMapping("/modification")
    Result typeModification(Long id,String name){
        return typeService.modification(id,name);
    }

    @GetMapping("/getAll")
    Result typeGetAll(){
        return typeService.getAll();
    }

}
