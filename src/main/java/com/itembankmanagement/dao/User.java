package com.itembankmanagement.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;//账号

    @Column(nullable = false,length = 32)
    private String name;//用户名

    @Column(nullable = false,length = 32)
    private String pw;//密码

    private Date createDate;//创建时间

    @Column(length = 32)
    private String identity;//身份

    @Column(length = 32)
    private String phone;//手机号

    private  Integer status;//状态


    @Column(nullable = false)
    private Integer age;//年龄

    @Column(nullable = false)
    private String sex;//性别

    private String address;//地址

    @ManyToMany
    @JoinTable(name = "user_lesson",
            joinColumns = {@JoinColumn(name = "uid",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "lid",referencedColumnName = "id")})
    private List<Lesson> lessonList;
}
