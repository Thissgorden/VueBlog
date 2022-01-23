package com.GDLearn.controller;

import com.GDLearn.lang.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistryController {

    @Qualifier("redisTemplate")
    @Autowired
    RedisTemplate rt;

    @GetMapping("/activateAccount")
    public String activateAccount(String cde){
        //请求激活的账号id
        Long activeId = (Long)rt.opsForHash().get("active", cde);

        if (activeId!=null){
            rt.opsForHash().delete("active", cde);
            /*
            userService修改一下状态 然后直接redirect回前端 此处需要前端写一个激活的页面
            使用 const blogId = this.$route.params.blogId 接受参数
            然后这里传参告诉前端是激活成功了还是失败了
            */
            return "redirect: ";
        }
        //激活失败
        return "redirect: ";
    }
}
