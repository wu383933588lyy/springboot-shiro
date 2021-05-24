package com.hello.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author WuRui
 * @ClassName QuickStart
 * @Date 2021/5/24 9:29
 * @Version 1.0
 * @Description //TODO
 **/
public class QuickStart {


    private static final transient Logger log = LoggerFactory.getLogger(QuickStart.class);


    public static void main(String[] args) {

        // 创建带有配置的 Shiro SecurityManager 的最简单方法
        // realms, users, roles and permissions 是使用简单的 INI 配置。
        //我们将使用可提取.ini文件的工厂来完成此操作，返回一个SecurityManager实例：
        //在类路径的根目录下使用shiro.ini文件（file：和url：前缀分别从文件和url加载）：
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        //对于这个简单的示例快速入门，请使 SecurityManager 作为JVM单例访问。 大多数应用程序都不会这样做，而是依靠其容器配置或web.xml进行 webapps。
        // 这超出了此简单快速入门的范围，因此我们只做最低限度的工作，所以您可以继续感受一下为了这些事。
        SecurityUtils.setSecurityManager(securityManager);

        // 获取当前用户
        Subject currentUser = SecurityUtils.getSubject();

        // Do some stuff with a Session (no need for a web or EJB container!!!)
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("检索正确的值！ [" + value + "]");
        }

        // 让我们登录当前用户，以便我们可以检查角色和权限：
        if (!currentUser.isAuthenticated()) {
            // 获取令牌
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            // 设置记住我
            token.setRememberMe(true);
            try {
                // 登录操作
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("没有用户名为 " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("账号密码不正确 " + token.getPrincipal() );
            } catch (LockedAccountException lae) {
                log.info("账户密码已锁定 " + token.getPrincipal() +
                            "请与您的管理员联系以将其解锁");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        // 判断用户角色
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        // 测试用户权限（粗粒度）
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        // 测试用户权限（细粒度）
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        // 注销
        currentUser.logout();

        System.exit(0);
    }
}
