package com.itembankmanagement.service;

import com.itembankmanagement.dao.TitleType;
import com.itembankmanagement.util.Result;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TitleService {
    Result add(String content,String answer,Long typeId,Long chapterId);

    Result delete(Long id);

    Result modification(Long id,String content,String answer,Long typeId,Long chapterId);

    Result get(Long id);

    Result getAll();

    Result getByLesson(Long id);

    Result getTitleByChapters(List<String> chapters);

    ResponseEntity GeneratingTestPapers(List<Long> chapterIdList, Map<Long,Integer> titleTypeIntegerMap) throws Exception;

    Result GeneratingByWord(List<String> chapterList, Map<Long,Integer> titleTypeIntegerMap) throws Exception;

    Result GeneratingTestPapers2(List<String> chapterList, Map<Long,Integer> titleTypeIntegerMap) throws Exception;

    ResponseEntity downloadTest(String fileName) throws Exception;
}
