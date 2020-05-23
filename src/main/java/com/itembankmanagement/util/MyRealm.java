package com.itembankmanagement.util;


import com.itembankmanagement.dao.User;
import com.itembankmanagement.dao.UserRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(MyRealm.class);

    private UserRepository userRepository;


    public MyRealm(UserRepository userRepository) {
        this.userRepository=userRepository;
    }


    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userId = JWTUtil.getOpenid(principals.toString());
        User oneUser = userRepository.findById(Long.valueOf(userId)).get();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 获取用户拥有的角色
        simpleAuthorizationInfo.addRole(oneUser.getIdentity());
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String userId = JWTUtil.getOpenid(token);
        if (userId == null) {
            throw new AuthenticationException("token invalid");
        }
        User oneUser = userRepository.findById(Long.valueOf(userId)).get();
        if (oneUser == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        if (! JWTUtil.verify(token, userId, oneUser.getPw())) {
            throw new AuthenticationException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, "my_realm");
    }
}
