package com.GDLearn.controller;




import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.GDLearn.entity.Blog;
import com.GDLearn.entity.User;
import com.GDLearn.lang.Result;
import com.GDLearn.service.BlogService;
import com.GDLearn.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者名会被放到这里
 * @since 2021-10-14
 */
@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    UserService userService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        //分页 mybatis-plus支持Page        pageSize:默认每页5条记录
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));

        return Result.sucess(pageData);
    }


    @GetMapping("/detail/{id}")
    @PreAuthorize("hasAnyAuthority('normal,admin,visitor')")
    public Result detail(@PathVariable(name= "id") Long id){
        Blog blog = blogService.getById(id);
        //去数据库拿一下作者名
        blog.setOwnerName(userService.getById(blog.getUserId()).getUsername());

        Assert.notNull(blog,"该博客不存在");

        return Result.sucess(blog);
    }

    @PostMapping ("/edit")
    @PreAuthorize("hasAnyAuthority('normal,admin')")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp = null;
        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(blog.getId() != null){
            temp = blogService.getById(blog.getId());

            //只能编辑自己的文章 或 权限ID为管理员
            Assert.isTrue(temp.getUserId().equals(currentUser.getId() ) || "2".equals(currentUser.getRoleid()),"没有编辑权限");
        }
        else {
            Long tempBlogid = blog.getUserId()==null?currentUser.getId():blog.getUserId();
            temp = new Blog();
            temp.setUserId(tempBlogid);
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        //log.error("-----------------修改功能暂不可用！");
        blogService.saveOrUpdate(temp);
        return Result.sucess(temp);
    }

    @GetMapping("/delete")
    @PreAuthorize("hasAnyAuthority('normal,admin')")
    public Result delete(Long blogId){

        User currentUser = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Blog temp = blogService.getById(blogId);

        //后端验证权限
        Assert.isTrue(temp.getUserId().equals(currentUser.getId() ) || "2".equals(currentUser.getRoleid()),"没有删除权限");

        blogService.removeById(blogId);
        return Result.sucess("删除成功");
    }
}
