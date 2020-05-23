package com.itembankmanagement.model;

import com.itembankmanagement.dao.Chapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterVO {

    private Long id;//章节号
    private String name;//章节名
    private String difficulty;//难度
    private String highlight;//重点
    private String lessonName;//所属课程编号

    public ChapterVO(Chapter chapter) {
        this.id = chapter.getId();
        this.name = chapter.getName();
        this.difficulty = chapter.getDifficulty();
        this.highlight = chapter.getHighlight();
        this.lessonName = chapter.getLesson().getName();
    }
}
