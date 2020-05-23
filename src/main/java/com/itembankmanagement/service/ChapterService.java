package com.itembankmanagement.service;

import com.itembankmanagement.util.Result;

public interface ChapterService {
    Result adminAdd(String name,String difficulty,String highlight,Long lessonId);

    Result adminDelete(Long id);

    Result adminGet(Long id);

    Result adminModification(Long id,String name,String difficulty,String highlight,Long lessonId);

    Result adminGetALl();

    Result adminGetByLesson(Long id);
}
