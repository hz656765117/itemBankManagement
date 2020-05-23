package com.itembankmanagement.controller;

import com.itembankmanagement.service.TeacherService;
import com.itembankmanagement.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @PostMapping("/add")
    Result teacherAdd(String name,Integer age,String sex,String address){
        return teacherService.add(name,age,sex,address);
    }

    @PostMapping("/modification")
    Result teacherModification(Long id,String name,Integer age,String sex,String address){
        return teacherService.modification(id,name,age,sex,address);
    }

    @PostMapping("/delete")
    Result teacherDelete(Long id){
        return teacherService.delete(id);
    }

    @GetMapping("/get")
    Result teacherGet(Long id){
        return teacherService.get(id);
    }
}
