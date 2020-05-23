package com.itembankmanagement.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;//教师号

    @Column(nullable = false)
    private String name;//姓名

    private Date createDate;//创建时间

    @Column(nullable = false)
    private Integer age;//年龄

    @Column(nullable = false)
    private String sex;//性别

    private String address;//地址

    @ManyToMany
    @JoinTable(name = "teacher_lesson",
                joinColumns = {@JoinColumn(name = "tid",referencedColumnName = "id")},
                inverseJoinColumns = {@JoinColumn(name = "lid",referencedColumnName = "id")})
    private Set<Lesson> lessonSet;

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate=" + createDate +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", lessonSet=" + lessonSet +
                '}';
    }
}
