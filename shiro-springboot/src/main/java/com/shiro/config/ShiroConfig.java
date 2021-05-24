package com.shiro.config;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    // 创建 realm 对象
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联 UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(securityManager);
        // 添加 shiro 内置过滤器
        /*
        anon: 无需认证就可以访问
        authc: 必须认证才能访问
        user: 必须拥有记住我功能才能用
        perms: 拥有对某个资源的权限才能访问
        role: 拥有某个角色权限才能访问
         */
        // 设置拦截
        Map<String,String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/user/add","authc");
//        filterMap.put("/user/update","authc");
        filterMap.put("/user/*","authc");

        bean.setFilterChainDefinitionMap(filterMap);

        // 拦截后跳转等登录页面
        bean.setLoginUrl("/toLogin");

        return bean;
    }
}