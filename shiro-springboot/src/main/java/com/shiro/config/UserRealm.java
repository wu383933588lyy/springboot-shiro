package com.shiro.config;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

//    @Autowired
//    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权======================>doGetAuthorizationInfo=");
        return null;
    }

    /**
     * @Author WuRui
     * @Date 16:45 2021/5/24
     * @Param AuthenticationToken
     * @Return AuthenticationInfo
     * @Description //TODO 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证======================>doGetAuthenticationInfo=");
        String name = "root";
        String password = "123456";
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (!token.getUsername().equals(name)){
            return null; // UnknownAccountException
        }
            return new SimpleAuthenticationInfo("",password,"");
    }
}