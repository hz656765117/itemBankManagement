package com.itembankmanagement.controller;

import com.itembankmanagement.service.ChapterService;
import com.itembankmanagement.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @PostMapping("/add")
    Result adminAdd(String name,String difficulty,String highlight,Long lessonId){
        return chapterService.adminAdd(name,difficulty,highlight,lessonId);
    }

    @PostMapping("/delete")
    Result adminDelete(Long id){
        return chapterService.adminDelete(id);
    }

    @PostMapping("/modification")
    Result adminModification(Long id,String name,String difficulty,String highlight,Long lessonId){
        return chapterService.adminModification(id,name,difficulty,highlight,lessonId);
    }

    @GetMapping("/get")
    Result adminGet(Long id){
        return chapterService.adminGet(id);
    }

    @GetMapping("/getAll")
    Result lessonAdminGetAll(){
        return chapterService.adminGetALl();
    }

    @GetMapping("/getByLesson")
    Result admineGetByLesson(Long id){
        return chapterService.adminGetByLesson(id);
    }
}
