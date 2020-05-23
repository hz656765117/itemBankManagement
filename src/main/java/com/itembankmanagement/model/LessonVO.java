package com.itembankmanagement.model;

import com.itembankmanagement.dao.Lesson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonVO {

    private Long id;//课程号
    private String name;//名称
    private Date createDate;//创建时间
    private String college;//所属类别
    private Integer sum;//课时

    public LessonVO(Lesson lesson){
        id=lesson.getId();
        name=lesson.getName();
        createDate=lesson.getCreateDate();
        college=lesson.getCollege();
        sum=lesson.getSum();
    }
}
