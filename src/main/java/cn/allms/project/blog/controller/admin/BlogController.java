package cn.allms.project.blog.controller.admin;

import cn.allms.project.blog.controller.vo.BlogQuery;
import cn.allms.project.blog.po.Blog;
import cn.allms.project.blog.po.User;
import cn.allms.project.blog.service.BlogService;
import cn.allms.project.blog.service.TagService;
import cn.allms.project.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;

import static cn.allms.common.constants.SessionConstants.USER_SESSION_KEY;

/**
 * @author: 南迪叶先生:https://blog.csdn.net/weixin_44420143?type=blog
 * @Date: 2019/10/3
 * @Description: com.yrp.controller.admin
 * @version: 1.0
 */
@Controller
@RequestMapping("admin")
public class BlogController {


    /**
     * 博客发布页面url
     */
    private static final String INPUT = "admin/blogs-input";
    private static final String HEAD_URL = "https://api.rainss.cn/random.php?t=";
    /**
     * TODO 修改为配置
     */
    private static final String HEAD_URL_1 = "https://api.rainss.cn/acgimgurl/acgurl.php?t=";

    /**
     * 显示分页查询到的博客列表页面url
     */
    private static final String LIST = "admin/blogs";

    /**
     * 局部刷新表格查询到的数据url
     */
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogServiceImpl;

    @Autowired
    private TypeService typeServiceImpl;

    @Autowired
    private TagService tagServiceImpl;

    /**
     * 分页显示博客列表
     *
     * @param model
     * @param pageable
     * @param blog
     * @return
     */
    @GetMapping("/blogs")
    public String showBlogs(Model model, @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog) {
        model.addAttribute("page", blogServiceImpl.ListBlog(pageable, blog));
        model.addAttribute("types", typeServiceImpl.listType());
        return LIST;
    }

    /**
     * 联合查询博客列表
     *
     * @param model
     * @param pageable
     * @param blog
     * @return
     */
    @PostMapping("/blogs/search")
    public String searchBlogs(Model model, @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              BlogQuery blog) {
        model.addAttribute("page", blogServiceImpl.ListBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    /**
     * 新增博客页面
     *
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String inputBlog(Model model) {
        model.addAttribute("firstPicture", HEAD_URL_1 + System.currentTimeMillis());
        setTypeAadTag(model);
        return INPUT;
    }


    /**
     * 提交博客
     *
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute(USER_SESSION_KEY));
        blog.setType(typeServiceImpl.getType(blog.getType().getId()));
        blog.setTags(tagServiceImpl.ListTag(blog.getTagIds()));
        Blog b;
        Date date = new Date();
        //新增博客
        if (blog.getId() == null) {
            blog.setUpdateTime(date);
            blog.setCreateTime(date);
            blog.setViews(0);
            b = blogServiceImpl.saveBlog(blog);
        } else {//更新博客
            b = blogServiceImpl.updateBlog(blog.getId(), blog);
        }
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return REDIRECT_LIST;
    }

    /**
     * 删除博客
     *
     * @param id
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable("id") Long id) {
        blogServiceImpl.deleteBlog(id);
        return REDIRECT_LIST;
    }

    /**
     * 修改博客页面
     *
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable("id") Long id, Model model) {
        setTypeAadTag(model);
        Blog blog = blogServiceImpl.getBlog(id);
        //处理tagIds
        blog.init();
        model.addAttribute("firstPicture", HEAD_URL_1 + System.currentTimeMillis());
        model.addAttribute("blog", blog);
        return INPUT;
    }

    private void setTypeAadTag(Model model) {
        /*博客查询,为后面的编辑博客做准备*/
        model.addAttribute("blog", new Blog());
        /*分类查询*/
        model.addAttribute("types", typeServiceImpl.listType());
        /*标签查询*/
        model.addAttribute("tags", tagServiceImpl.ListTag());
    }
}
