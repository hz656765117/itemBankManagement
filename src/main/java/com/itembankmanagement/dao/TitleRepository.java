package com.itembankmanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleRepository extends JpaRepository<Title,Long> {
    List<Title> findByChapter(Chapter chapter);

}
