package com.GDLearn.controller;



import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.GDLearn.entity.Blog;
import com.GDLearn.lang.Result;
import com.GDLearn.service.BlogService;
import com.GDLearn.shiro.AccountProfile;
import com.GDLearn.util.ShiroUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者名会被放到这里
 * @since 2021-10-14
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        //分页 mybatis-plus支持Page        pageSize:默认每页5条记录
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));


        return Result.sucess(pageData);
    }

    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable(name= "id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客不存在");

        return Result.sucess(blog);
    }

    @RequiresAuthentication
    @PostMapping ("/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp = null;

        if(blog.getId() != null){
            temp = blogService.getById(blog.getId());
            //只能编辑自己的文章
            Assert.isTrue(temp.getUserId().equals(ShiroUtils.getProfile().getId()),"没有编辑权限");
        }
        else {

            temp = new Blog();
            temp.setUserId(ShiroUtils.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);
        return Result.sucess(temp);
    }
}
