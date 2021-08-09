package cn.allms.project.blog.dao;

import cn.allms.project.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author: 南迪叶先生:https://blog.csdn.net/weixin_44420143?type=blog
 * @Date: 2019/9/28
 * @Description: com.yrp.dao
 * @version: 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 通过用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
}
