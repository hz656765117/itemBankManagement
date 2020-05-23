package com.itembankmanagement.service.serviceImpl;

import com.itembankmanagement.dao.Lesson;
import com.itembankmanagement.dao.LessonRepository;
import com.itembankmanagement.dao.User;
import com.itembankmanagement.dao.UserRepository;
import com.itembankmanagement.model.LessonVO;
import com.itembankmanagement.service.LessonService;
import com.itembankmanagement.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
public class LessonServiceImpl implements LessonService {

    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Result adminAdd(String name, String college, Integer sum) {
        if(name==null||"".equals(name)){
            return Result.error(3010,"未输入课程名");
        }
        if (college==null||"".equals(college)){
            return Result.error(3011,"未输入课程类别");
        }
        Lesson lesson=new Lesson();
        lesson.setName(name);
        lesson.setCollege(college);
        lesson.setSum(sum);
        lesson.setCreateDate(new Date());
        lessonRepository.save(lesson);
        return Result.success();
    }

    @Override
    public Result adminDelete(Long id) {
        if(id==null||id.equals("")){
            return Result.error(3012,"未输入课程编号");
        }
        lessonRepository.deleteById(id);
        return Result.success();
    }

    @Override
    public Result adminGetInfo(Long id) {
        System.out.println(id+" oooooooooooo");
        if(id==null||id.equals("")){
            return Result.error(3012,"未输入课程编号");
        }
        try {
            Lesson lesson = lessonRepository.findById(id).get();
            if(lesson==null){
                return Result.error(3013,"没有该课程");
            }
            return Result.success(new LessonVO(lesson));
        }catch (Exception e){
            return Result.error(3013,"没有该课程");
        }
    }

    @Override
    public Result adminModification(Long id, String name, String college, Integer sum) {
        if(id==null||id.equals("")){
            return Result.error(3012,"未输入课程编号");
        }
        try{
            Lesson lesson=lessonRepository.findById(id).get();
            if(lesson==null){
                return Result.error(3013,"没有该课程");
            }
            if(name!=null){
                lesson.setName(name);
            }
            if(college!=null){
                lesson.setCollege(college);
            }
            if (sum!=null){
                lesson.setSum(sum);
            }
            lessonRepository.save(lesson);
            return Result.success();
        }catch (Exception e){
            return Result.error(3013,"没有该课程");
        }

    }

    @Override
    public Result adminGetALl() {
        List<Lesson> lessonList=lessonRepository.findAll();
        List<LessonVO> lessonVOList=new LinkedList<>();
        for(Lesson lesson:lessonList){
            LessonVO lessonVO=new LessonVO(lesson);
            lessonVOList.add(lessonVO);
        }
        return Result.success(lessonVOList);

    }

    @Override
    public Result getByUserId(Long id) {
        List<Lesson> lessonList=userRepository.findById(id).get().getLessonList();
        List<LessonVO> lessonVOList=new ArrayList<>();
        for(Lesson lesson:lessonList){
            lessonVOList.add(new LessonVO(lesson));
        }
        return Result.success(userRepository.findById(id).get());
    }
}
