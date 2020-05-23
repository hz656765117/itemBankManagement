package com.itembankmanagement.model;

import com.itembankmanagement.dao.Title;
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
public class TitleVO {

    private Long id;
    private String content;
    private String answer;
    private Integer selectNum;
    private String type;

    private Date createDate;
    private String chapterName;

    public TitleVO(Title title){
        id=title.getId();
        content=title.getContent();
        answer=title.getAnswer();
        selectNum=title.getSelectNum();
        type=title.getTitleType().getName();
        chapterName=title.getChapter().getName();
        createDate=title.getCreateDate();
    }
}
