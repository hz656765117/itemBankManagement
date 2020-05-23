package com.itembankmanagement.service;

import com.itembankmanagement.util.Result;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    Result add(String name,String pw,String phone);

    Result delete(Long id);

    Result login(HttpServletResponse response,Long id, String pw);

    Result login(Long id, String pw);

    Result logout(HttpServletResponse response);

    Result getInfo(Long id);

    Result getInfo();

    Result getAll();

    Result modification(Long id,String name,String phone,String pw);

    Result modif(String name, String phone);

    Result modifPw(String oldPw,String newPw);
}
