package com.itembankmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChapterRspository extends JpaRepository<Chapter,Long> {
    List<Chapter> findAllByLesson(Lesson lesson);

    List<Chapter> findAllByName(String name);


}
