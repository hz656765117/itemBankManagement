package com.itembankmanagement.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chapter")
public class Chapter {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;//章节号

    @Column(nullable = false)
    private String name;//章节名

    @Column(nullable = false)
    private String difficulty;//难度

    private String highlight;//重点

    @Transient
    private Long lessonId;//所属课程编号

    @ManyToOne(targetEntity = Lesson.class)
    @JoinColumn(name = "lessonId")
    private Lesson lesson;

    @OneToMany(mappedBy = "chapter")
    private List<Title> titleList;

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", highlight='" + highlight + '\'' +
                ", lessonId=" + lessonId +
                ", titleList=" + titleList +
                '}';
    }
}
