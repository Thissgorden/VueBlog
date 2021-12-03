package com.GDLearn.security;

import cn.hutool.core.util.StrUtil;
import com.GDLearn.entity.User;
import com.GDLearn.service.UserService;
import com.GDLearn.util.JwtUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserService userService;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    //重写构造方法
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


        //重写过滤的流程
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            String jwt = request.getHeader("Authorization");
            if(StrUtil.isBlankOrUndefined(jwt)){
                chain.doFilter(request,response);
                return;
            }
            Claims claim = jwtUtils.getClaimByToken(jwt);
            if(claim == null){
                throw new JwtException("token异常");
            }
            if(claim.getExpiration().before(new Date())){
                throw new JwtException("token过期");
            }
            //通过验证token有效
            String username = claim.getSubject();
            User sysUser = userService.getOne(new QueryWrapper<User>(new User().setUsername(username)));
            //这里可以设置获取用户权限信息，但是目前暂无权限信息表所以忽略
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(sysUser,null, userDetailsService.getAuthority(sysUser));
            //完成JWT校验后剩下的交由Security上下文自动完成登陆的过程
            SecurityContextHolder.getContext().setAuthentication(token);

            chain.doFilter(request, response);
        }
}
