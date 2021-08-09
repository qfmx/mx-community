package cn.allms.project.blog.service.impl;

import cn.allms.common.utils.MD5Utils;
import cn.allms.project.blog.dao.UserRepository;
import cn.allms.project.blog.po.User;
import cn.allms.project.blog.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 南迪叶先生:https://blog.csdn.net/weixin_44420143?type=blog
 * @Date: 2019/9/28
 * @Description: com.yrp.service.serviceimpl
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements Userservice {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        System.out.println(MD5Utils.code(password));
        return user;
    }
}
