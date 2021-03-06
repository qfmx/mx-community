package cn.allms.project.blog.dao;

import cn.allms.project.blog.po.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author: 南迪叶先生:https://blog.csdn.net/weixin_44420143?type=blog
 * @Date: 2019/9/29
 * @Description: com.yrp.dao
 * @version: 1.0
 */
public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);


    /**
     * 查询type按照分页的方式
     *
     * @param pageable
     * @return
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);

}
