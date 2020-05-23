package com.itembankmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itembankmanagement.dao.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
    private Long id;//账号
    private String name;//用户名
    private String pw;//密码
    private Date createDate;//创建时间
    private String identity;//身份
    private String phone;//手机号
    private  Integer status;//状态

    public UserVO(User user){
        id=user.getId();
        name=user.getName();
        pw=user.getPw();
        identity=user.getIdentity();
        phone=user.getPhone();
        createDate=user.getCreateDate();

    }

}
