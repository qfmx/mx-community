package cn.allms.project.blog.controller.user;

import cn.allms.project.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: 南迪叶先生:https://blog.csdn.net/weixin_44420143?type=blog
 * @Date: 2019/10/15
 * @Description: com.yrp.controller.admin
 * @version: 1.0
 */
@Controller
public class ArchiveShowController {
    @Autowired
    private BlogService blogServiceImpl;


    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogServiceImpl.archiveBlog());
        model.addAttribute("blogCount", blogServiceImpl.countBlog());
        return "archives";
    }
}
