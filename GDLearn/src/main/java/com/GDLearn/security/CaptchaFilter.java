package com.GDLearn.security;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if("/login".equals(requestURI) && request.getMethod().equals("POST")){
            try {
                validate(request);
            } catch (CaptchaException e) {
                request.setAttribute("Exception",e);
                request.getRequestDispatcher("/login/failure").forward(request,response);
                return;
            }
        }
        doFilter(request,response,filterChain);

    }

    private void validate(HttpServletRequest request) {
        String code = request.getParameter("code");
        String key = request.getParameter("key");
        if(StrUtil.isBlank(code) || StrUtil.isBlank(key))throw new CaptchaException("验证码错误");
        Object captcha = redisTemplate.opsForHash().get("captcha", key);
        if(!code.equals(captcha))throw new CaptchaException("验证码错误");
        //销毁KEY 一次性使用
        redisTemplate.opsForHash().delete("captcha",key);
    }
}
