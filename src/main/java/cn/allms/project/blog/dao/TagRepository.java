package cn.allms.project.blog.dao;

import cn.allms.project.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 标签
 * @author josxy
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);


    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);

}
