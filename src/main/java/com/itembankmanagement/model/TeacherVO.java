package com.itembankmanagement.model;

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
public class TeacherVO {

    private Long id;//教师号
    private String name;//姓名
    private Date createDate;//创建时间
    private Integer age;//年龄
    private String sex;//性别
    private String address;//地址

}
