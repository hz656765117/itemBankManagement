package com.itembankmanagement.service.serviceImpl;

import com.itembankmanagement.dao.Chapter;
import com.itembankmanagement.dao.ChapterRspository;
import com.itembankmanagement.dao.Lesson;
import com.itembankmanagement.dao.LessonRepository;
import com.itembankmanagement.model.ChapterVO;
import com.itembankmanagement.model.LessonVO;
import com.itembankmanagement.service.ChapterService;
import com.itembankmanagement.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;


@Service
@Slf4j
@Transactional
public class ChapterServiceImlp implements ChapterService {

    @Autowired
    ChapterRspository chapterRspository;
    @Autowired
    LessonRepository lessonRepository;

    @Override
    public Result adminAdd(String name, String difficulty, String highlight, Long lessonId) {
        if(name==null||name.equals("")){
            Result.error(3020,"未输入章节名称");
        }
        if (difficulty==null||difficulty.equals("")){
            Result.error(3021,"未输入章节难度");
        }
        if(lessonId==null||lessonId.equals("")){
            Result.error(3022,"未输入章节所属课程");
        }
        Lesson lesson=lessonRepository.findById(lessonId).get();
        Chapter chapter=new Chapter();
        chapter.setName(name);
        chapter.setDifficulty(difficulty);
        chapter.setHighlight(highlight);
        chapter.setLessonId(lessonId);
        chapter.setLesson(lesson);
        chapterRspository.save(chapter);
        return Result.success();
    }

    @Override
    public Result adminDelete(Long id) {
        if(id==null||"".equals(id)){
            return Result.error(3023,"未输入章节号");
        }
        chapterRspository.deleteById(id);
        return Result.success();
    }

    @Override
    public Result adminGet(Long id) {
        if(id==null||"".equals(id)){
            return Result.error(3023,"未输入章节号");
        }
        Chapter chapter=chapterRspository.findById(id).get();
        if(chapter==null){
            return Result.error(3024,"没有该章节");
        }
        return Result.success(new ChapterVO(chapter));
    }

    @Override
    public Result adminModification(Long id, String name, String difficulty, String highlight, Long lessonId) {
        if(id==null||id.equals("")){
            return Result.error(3023,"未输入章节号");
        }
        Chapter chapter=chapterRspository.findById(id).get();
        if(name!=null&&!name.equals("")){
            chapter.setName(name);
        }
        if(difficulty!=null&&!difficulty.equals("")){
            chapter.setDifficulty(difficulty);
        }
        if(highlight!=null&&!highlight.equals("")){
            chapter.setHighlight(highlight);
        }
        if(lessonId!=null){
            chapter.setLessonId(lessonId);
        }
        chapterRspository.save(chapter);
        return Result.success();
    }

    @Override
    public Result adminGetALl() {
        List<Chapter> chapterList=chapterRspository.findAll();
        List<ChapterVO> chapterVOList=new LinkedList<>();
        for(Chapter chapter:chapterList){
            ChapterVO chapterVO=new ChapterVO(chapter);
            chapterVOList.add(chapterVO);
        }
        return Result.success(chapterVOList);
    }

    @Override
    public Result adminGetByLesson(Long id) {
        List<Chapter> chapterList=chapterRspository.findAllByLesson(lessonRepository.findById(id).get());
        List<ChapterVO> chapterVOList=new LinkedList<>();
        for(Chapter chapter:chapterList){
            ChapterVO chapterVO=new ChapterVO(chapter);
            chapterVOList.add(chapterVO);
        }
        return Result.success(chapterVOList);
    }
}
