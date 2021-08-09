package cn.allms.project.blog.service;

import cn.allms.project.blog.po.User;

/**
 * @author: 南迪叶先生:https://blog.csdn.net/weixin_44420143?type=blog
 * @Date: 2019/9/28
 * @Description: com.yrp.service
 * @version: 1.0
 */
public interface Userservice {

    /**
     * 用户登录检测
     * @param username
     * @param password
     * @return
     */
    User checkUser(String username, String password);

}
