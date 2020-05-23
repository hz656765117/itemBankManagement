package com.itembankmanagement.service.serviceImpl;

import com.itembankmanagement.dao.User;
import com.itembankmanagement.dao.UserRepository;
import com.itembankmanagement.model.UserVO;
import com.itembankmanagement.service.UserService;
import com.itembankmanagement.util.JWTUtil;
import com.itembankmanagement.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public Result add(String name,String pw,String phone) {
        if(name==null){
            return Result.error(3001,"未输入姓名");
        }
        if (pw==null){
            return Result.error(3002,"未输入密码");
        }

        User user=new User();
        user.setName(name);
        user.setPw(pw);
        user.setPhone(phone);
        user.setCreateDate(new Date());
        user.setIdentity("教师");
        user.setAddress("无");
        user.setAge(15);
        user.setSex("");
        userRepository.save(user);
        log.info("成功添加用户----->"+user);
        return Result.success();
    }

    @Override
    public Result delete(Long id) {
        if(id==null){
            return Result.error(3001,"为输入姓名");
        }
        userRepository.deleteById(id);
        log.info("成功删除----"+id+"----用户");
        return Result.success();
    }

    @Override
    public Result login(HttpServletResponse response, Long id, String pw) {
        if(id==null||pw==null){
            return Result.error(3003,"账号或密码错误");
        }
        try{
              Optional<User> optionalUser=userRepository.findById(id);
              User user=optionalUser.get();
              if(pw.equals(user.getPw())){
                  String token= JWTUtil.sign(user.getId().toString(),user.getPw());
                  Cookie cookie=new Cookie("session",token);
                  cookie.setHttpOnly(true);
                  cookie.setMaxAge(60*60*12);
                  cookie.setPath("/");
                  response.addCookie(cookie);
                  Map<String,Object> data=new HashMap<>();
                  log.info("用户"+id+"成功登陆");
                  data.put("token",token);
                  return Result.success(data);
              }
              else {
                  log.info("用户"+id+"尝试登陆，密码错误");
                  return Result.error(3003,"账号或密码错误");
              }
          }catch (Exception e){
               return Result.error(3003,"账号或密码错误");
          }
    }

    @Override
    public Result login(Long id, String pw) {
        if(id==null||pw==null){
            return Result.error(3003,"账号或密码错误");
        }
        try{
            Optional<User> optionalUser=userRepository.findById(id);
            User user=optionalUser.get();
            if(pw.equals(user.getPw())){

                Map<String,Object> data=new HashMap<>();
                log.info("用户"+id+"成功登陆");
                return Result.success();
            }
            else {
                log.info("用户"+id+"尝试登陆，密码错误");
                return Result.error(3003,"账号或密码错误");
            }
        }catch (Exception e){
            return Result.error(3003,"账号或密码错误");
        }
    }

    @Override
    public Result logout(HttpServletResponse response) {
        Cookie cookie= new Cookie("session",null);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return Result.success();
    }

    @Override
    public Result getInfo(Long id) {
        if(id==null){
            return Result.error(3005,"未输入id");
        }
        try {
            User user =userRepository.findById(id).get();
            if (user == null) {
                return Result.error(3006, "未找到用户");
            }
            return Result.success(new UserVO(user));
        }catch (Exception e){
            return Result.error(10000,"服务器错误");
        }

    }


    @Override
    public Result getInfo() {
        String token= SecurityUtils.getSubject().getPrincipal().toString();
        Long userId=Long.valueOf(JWTUtil.getOpenid(token));
        User user=userRepository.findById(userId).get();
        if(user==null){
            return Result.error(3000,"没有登陆");
        }
        return Result.success(user);
    }

    @Override
    public Result getAll() {
        List<UserVO> userVOList=new ArrayList<>();
        for(User user:userRepository.findAll()){
            userVOList.add(new UserVO(user));
        }
        return Result.success(userVOList);
    }

    @Override
    public Result modification(Long id, String name, String phone, String pw) {
        if (id==null){
            return Result.error(3001,"为输入id");
        }
        User user=userRepository.findById(id).get();
        if(name!=null){
            user.setName(name);
        }
        if (phone!=null){
            user.setPhone(phone);
        }
        if(pw!=null){
            user.setPw(pw);
        }

        userRepository.save(user);
        return Result.success();
    }

    @Override
    public Result modif(String name, String phone) {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        Long id = Long.valueOf(JWTUtil.getOpenid(token));
        User user = userRepository.findById(id).get();
        if (user == null) {
            return Result.error(3005, "用户未登录");
        }
        if (name != null && !name.equals("")) {
            user.setName(name);
        }
        if (phone != null && !phone.equals("")) {
            user.setPhone(phone);
        }
        userRepository.save(user);
        return Result.success();
    }

    @Override
    public Result modifPw(String oldPw, String newPw) {
        String token=SecurityUtils.getSubject().getPrincipal().toString();
        Long id= Long.valueOf(JWTUtil.getOpenid(token));
        User user=userRepository.findById(id).get();
        if(user==null){
            return Result.error(3005, "用户未登录");
        }
        if (newPw==null||newPw.length()<6){
            return Result.error(3006,"新密码长度没超过6位");
        }
        if(user.getPw().equals(oldPw)){
            user.setPw(oldPw);
        }
        userRepository.save(user);
        return Result.success();
    }

}



/**
 * @Override
 *     public Result add() {
 *         UserVO userVO =new UserVO();
 *         userVO.setName("jj");
 *         userVO.setIdentity("teacher");
 *         userVO.setPhone("17605007309");
 *         userVO.setPw("123456");
 *         return Result.success(userVO);
 *     }
 *
 *     @Override
 *     public Result login(Long id,String pw) {
 *         //UserVO userVO=new UserVO();
 *        // userVO.setId(id);
 *         //userVO.setPw(pw);
 *         //User user=dozerMapper.map(userVO,User.class);
 *         try{
 *             Optional<User> optionalUser=userRepository.findById(id);
 *             if(pw.equals(optionalUser.get().getPw())){
 *                 System.out.println("ok");
 *                 return Result.success();
 *             }
 *             else {
 *                 System.out.println("ok2");
 *                 return Result.error(401,"密码错误");
 *             }
 *         }catch (Exception e){
 *             System.out.println("ok3");
 *             return Result.error(401,"账号不存在");
 *         }
 *     }
 */