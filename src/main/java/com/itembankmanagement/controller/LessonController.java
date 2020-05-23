package com.itembankmanagement.controller;

import com.itembankmanagement.service.LessonService;
import com.itembankmanagement.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @PostMapping("/add")
    Result lessonAdminAdd(String name,String college,Integer sum){
        return lessonService.adminAdd(name,college,sum);
    }

    @PostMapping("/delete")
    Result lessonAdminDelete(Long id){
        return lessonService.adminDelete(id);
    }

    @PostMapping("/modification")
    Result lessonAdminModification(Long id,String name,String college,Integer sum){
        System.out.println(id+name+college+sum);
        return lessonService.adminModification(id,name,college,sum);
    }

    @GetMapping("/get")
    Result lessonAdminGet(Long id){
        return lessonService.adminGetInfo(id);
    }

    @GetMapping("/getAll")
    Result lessonAdminGetAll(){
        return lessonService.adminGetALl();
    }

    @GetMapping("/getByUser")
    Result getByUserId(Long id){
        return lessonService.getByUserId(id);
    }

}
