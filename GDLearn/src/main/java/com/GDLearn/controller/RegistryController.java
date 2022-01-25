package com.GDLearn.controller;

import com.GDLearn.dto.RegistryDto;
import com.GDLearn.entity.User;
import com.GDLearn.lang.Result;
import com.GDLearn.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
public class RegistryController {
    @Autowired
    BCryptPasswordEncoder encoder;
    @Qualifier("stringRedisTemplate")
    @Autowired
    RedisTemplate rt;
    @Autowired
    UserService userService;

    @Value("${gdlearn.jwt.frontsite}")
    String frontSite;

    @GetMapping("/activateAccount")
    public String activateAccount(String cde, HttpServletResponse resp){
        try{
            //请求激活的账号id
            Long activeId = Long.parseLong(rt.opsForHash().get("active",cde).toString());
            rt.opsForHash().delete("active", cde);
            /*
            userService修改一下状态 然后直接redirect回前端 此处需要前端写一个激活的页面
            使用 const blogId = this.$route.params.blogId 接受参数
            然后这里传参告诉前端是激活成功了还是失败了
            */
            User byId = userService.getById(activeId);
            System.out.println(byId);
            byId.setStatus(0);
            userService.updateById(byId);

            //todo 给用户生成token放入header
            return "redirect:http://"+frontSite+"?status=1";
        }catch (Exception e) {
            //激活失败
            return "redirect:http://" + frontSite + "?status=0";
        }
    }

    @ResponseBody
    @PostMapping("/register")
    public Result registering(@RequestBody RegistryDto dto){
        User newAccount = new User();
        newAccount.setUsername(dto.getUsername());
        newAccount.setEmail(dto.getEmail());
        newAccount.setStatus(1);                                  // 1为未激活 0为可使用
        newAccount.setPassword(encoder.encode(dto.getPassword()));// 老实人 不保存用户的密码
        newAccount.setRoleid("2");// 2为普通用户
        //todo 暂时不考虑激活过期账户的清理，预计以后写一个程序扫一遍数据库把所有未激活并且现在时间-创建时间>30分钟的账号数据删除
        newAccount.setCreated(LocalDateTime.now());
//        userService.save(newAccount);
//调用发邮件方法
//        mailService.sendMail(dto);
        userService.register(newAccount,dto);
        return Result.sucess("操作成功",null);

    }

    @ResponseBody
    @GetMapping("/checkUsername")
    /**
     * 注册时检查数据库里用户名是否被使用
     */
    public Result checkUsername(String username){
        if(username == null){
            throw new IllegalArgumentException("用户名实体参数有误");
        }
        User usr = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if(usr != null){
            return Result.sucess(203,"该用户名已被使用",null);
        }else{
            return Result.sucess("该用户名可以使用");
        }

    }
    @ResponseBody
    @GetMapping("/checkMail")
    /**
     * 注册时检查数据库里用户名是否被使用
     */
    public Result checkMail(String email){
        if(email == null){
            throw new IllegalArgumentException("邮箱实体参数有误");
        }
        User usr = userService.getOne(new QueryWrapper<User>().eq("email", email));
        if(usr != null){
            return Result.sucess(203,"该邮箱已被使用",null);
        }else{
            return Result.sucess("该邮箱可以使用");
        }
    }
}
