package com.itembankmanagement.dao;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Paper {
    private List<String> chapterIdList;//=new ArrayList<>();

    private Map<Long,Integer> titleTypeIntegerMap;//=new HashMap<Long, Integer>();

}
