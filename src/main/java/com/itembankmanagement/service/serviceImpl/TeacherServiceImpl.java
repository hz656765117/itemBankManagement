package com.itembankmanagement.service.serviceImpl;

import com.itembankmanagement.dao.Teacher;
import com.itembankmanagement.dao.TeacherRepository;
import com.itembankmanagement.service.TeacherService;
import com.itembankmanagement.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;


    @Override
    public Result add(String name, Integer age, String sex, String address) {
        if(name==null||name.equals("")){
            return Result.error(3050,"未输入名称");
        }
        if(age==null){
            return Result.error(3050,"未输入年纪");
        }
        if(sex==null||sex.equals("")){
            return Result.error(3050,"未输入性别");
        }
        Teacher teacher=new Teacher();
        teacher.setAge(age);
        teacher.setAddress(address);
        teacher.setName(name);
        teacher.setSex(sex);
        teacher.setCreateDate(new Date());
        teacherRepository.save(teacher);
        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        if(id==null){
            return Result.error(3050,"为输入教师号");
        }
        teacherRepository.deleteById(id);
        return Result.success();
    }

    @Override
    public Result modification(Long id,String name, Integer age, String sex, String address) {
        if(id==null){
            return Result.error(3040,"为输入教师号");
        }
        Teacher teacher=teacherRepository.findById(id).get();
        if(teacher==null){
            return Result.error(3040,"不存在该教师");
        }
        if (age!=null&&age>0){
            teacher.setAge(age);
        }
        if(name!=null&&!name.equals("")){
            teacher.setName(name);
        }
        if(sex!=null&&!sex.equals("")){
            teacher.setSex(sex);
        }
        teacherRepository.save(teacher);
        return Result.success();
    }

    @Override
    public Result get(Long id) {
        if(id==null){
            return Result.error(3050,"为输入教师号");
        }
        Teacher teacher=teacherRepository.findById(id).get();
        if(teacher==null){
            return Result.error(3050,"没有该教师");
        }
        return Result.success(teacher);
    }
}
