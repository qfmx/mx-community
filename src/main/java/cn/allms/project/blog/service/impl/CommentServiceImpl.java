package cn.allms.project.blog.service.impl;

import cn.allms.common.utils.CommentUtil;
import cn.allms.project.blog.dao.CommentRepository;
import cn.allms.project.blog.po.Comment;
import cn.allms.project.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author: 南迪叶先生:https://blog.csdn.net/weixin_44420143?type=blog
 * @Date: 2019/10/10
 * @Description: com.yrp.service.impl
 * @version: 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by("createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return CommentUtil.eachComment(comments);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment saveComment(Comment comment) {
        //回复: 如果有父级,需要将父级set进来,之后在保存
        Long parentCommentId = comment.getParentComment().getId();
        System.out.println(parentCommentId);
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        } else {
            //发布评论:
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

}
