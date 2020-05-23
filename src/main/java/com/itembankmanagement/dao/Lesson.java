package com.itembankmanagement.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "lesson")
public class Lesson {
   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;//课程号

    @Column(nullable = false)
    private String name;//名称

    private Date createDate;//创建时间

    @Column(nullable = false)
    private String college;//所属类别

    private Integer sum;//课时

    @OneToMany(mappedBy = "lesson")
    private List<Chapter> chapterList;

    @ManyToMany(mappedBy = "lessonSet")
    private Set<Teacher> teacherSet;


}
