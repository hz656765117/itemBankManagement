package com.itembankmanagement.service.serviceImpl;

import com.itembankmanagement.dao.TitleType;
import com.itembankmanagement.dao.TitleTypeRspository;
import com.itembankmanagement.model.TitleTypeVO;
import com.itembankmanagement.service.TypeService;
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
public class TypeServiceImlp implements TypeService {
    @Autowired
    TitleTypeRspository titleTypeRspository;


    @Override
    public Result add(String name) {
        if(name==null||name.equals("")){
            return Result.error(3040,"为输入题型名");
        }
        TitleType titleType=new TitleType();
        titleType.setName(name);
        titleTypeRspository.save(titleType);
        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        if(id==null){
            return Result.error(3040,"为输入题型编号");
        }
        titleTypeRspository.deleteById(id);
        return Result.success();
    }

    @Override
    public Result modification(Long id, String name) {
        if(id==null){
            return Result.error(3040,"为输入题型编号");
        }
        TitleType titleType=titleTypeRspository.findById(id).get();
        if(titleType==null){
            return Result.error(3040,"不存在该题型");
        }
        if(name!=null&&!name.equals("")){
            titleType.setName(name);
        }
        titleTypeRspository.save(titleType);
        return Result.success();
    }

    @Override
    public Result get(Long id) {
        if(id==null){
            return Result.error(3040,"为输入题型编号");
        }
        TitleType titleType=titleTypeRspository.findById(id).get();
        if(titleType==null){
            return Result.error(3040,"不存在该题型");
        }
        return Result.success(titleType);
    }

    @Override
    public Result getAll() {
        List<TitleType> titleTypeList=titleTypeRspository.findAll();
        List<TitleTypeVO> titleTypeVOList=new LinkedList<>();

        for(TitleType titleType:titleTypeList){
            TitleTypeVO titleTypeVO=new TitleTypeVO(titleType);
            titleTypeVOList.add(titleTypeVO);
        }

        return Result.success(titleTypeVOList);
    }
}
