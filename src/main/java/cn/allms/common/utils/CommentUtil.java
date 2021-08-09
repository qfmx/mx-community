package cn.allms.common.utils;

import cn.allms.project.blog.po.Comment;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论格式化工具
 *
 * @author allms
 */
public class CommentUtil {
    /**
     * 存放迭代找出的所有子代的集合
     */
    public static List<Comment> tempReplays = new ArrayList<>();

    /**
     * 循环每个顶级的评论节点
     *
     * @param comments
     * @return
     */
    public static List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    public static void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replays = comment.getReplyComments();
            for (Comment reply : replays) {
                //循环迭代，找出子代，存放在tempReplays中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplays);
            //清除临时存放区
            tempReplays = new ArrayList<>();
        }
    }

    /**
     * 递归迭代，剥洋葱
     *
     * @param comment 被迭代的对象
     * @return
     */
    public static void recursively(Comment comment) {
        //顶节点添加到临时存放集合
        tempReplays.add(comment);
        if (comment.getReplyComments().size() > 0) {
            List<Comment> replays = comment.getReplyComments();
            for (Comment reply : replays) {
                tempReplays.add(reply);
                if (reply.getReplyComments().size() > 0) {
                    recursively(reply);
                }
            }
        }
    }
}
