package com.GDLearn.controller;

import cn.hutool.core.map.MapUtil;
import com.GDLearn.lang.Result;
import com.GDLearn.service.UserService;
import com.GDLearn.util.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class AccountController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @PostMapping("/login/success")
    public Result loginSuccess(HttpServletResponse resp){
        // 登录成功后用户的认证信息 UserDetails会存在 安全上下文寄存器 SecurityContextHolder 中
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        com.GDLearn.entity.User Queryuser = new com.GDLearn.entity.User();
        //由于没有重写spring security的User类 这里绕了一圈去数据库查询用户获取ID等完整信息
        Queryuser.setUsername(principal.getUsername());
        com.GDLearn.entity.User realUser = userService.getOne(new QueryWrapper<com.GDLearn.entity.User>(Queryuser));

        String jwt = jwtUtils.generateToken(realUser.getUsername());
        resp.setHeader("Authorization",jwt);
        resp.setHeader("Access-control-Expose-Headers","Authorization");

        return Result.sucess(MapUtil.builder()
                        .put("id",realUser.getId())
                        .put("username",realUser.getUsername())
                        .put("avatar",realUser.getAvatar())
                        .put("email",realUser.getEmail())
                        .put("roleid",realUser.getRoleid())
                        .map()
                );
    }
    @PostMapping("/login/failure")
    public Result loginFailure(HttpServletRequest request){
        Exception e = (Exception)request.getAttribute("Exception");
        if(e!=null)return Result.fail(e.getMessage());
        else return Result.fail("用户名或密码错误");
    }

    @GetMapping("/userInfo")
    public Result getUserInfo(HttpServletRequest request,HttpServletResponse resp){
        try {
            if(request.getHeader("Authorization")!=null){
                Map<Object, Object> map;
                com.GDLearn.entity.User realUser = (com.GDLearn.entity.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                map = MapUtil.builder()
                        .put("id", realUser.getId())
                        .put("username", realUser.getUsername())
                        .put("avatar", realUser.getAvatar())
                        .put("email", realUser.getEmail())
                        .put("roleid", realUser.getRoleid())
                        .map();
                return Result.sucess(map);
            }
        } catch (Exception e) {
            throw new JwtException("token解析异常");
        }
        //设置响应401未授权，前端会清空未授权的jwt并跳转到登陆
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return Result.fail("token解析出错!");
    }


    @GetMapping("/visitor")
    public Result visitor(HttpServletResponse resp){
        String jwt = jwtUtils.generateToken("visitor");
        resp.setHeader("Authorization",jwt);
        resp.setHeader("Access-control-Expose-Headers","Authorization");
        return Result.sucess(MapUtil.builder()
                .put("id",0)
                .put("username","游客")
                .put("roleid",3)
                .map());
    }
}
