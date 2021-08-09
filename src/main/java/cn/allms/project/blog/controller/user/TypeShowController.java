package cn.allms.project.blog.controller.user;

import cn.allms.project.blog.controller.vo.BlogQuery;
import cn.allms.project.blog.po.Type;
import cn.allms.project.blog.service.BlogService;
import cn.allms.project.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author: 南迪叶先生:https://blog.csdn.net/weixin_44420143?type=blog
 * @Date: 2019/10/12
 * @Description: com.yrp.controller
 * @version: 1.0
 */
@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeServiceImpl;


    @Autowired
    private BlogService blogServiceImpl;


    @GetMapping("/types/{id}")
    public String types(Model model, @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
    @PathVariable Long id, Pageable pageable) {

        List<Type> types = typeServiceImpl.listTypeTop(10000);
        if (id == -1 && types.size() > 0) {
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogServiceImpl.ListBlog(pageable, blogQuery));
        model.addAttribute("avtiveTypeId", id);
        return "types";
    }

}
