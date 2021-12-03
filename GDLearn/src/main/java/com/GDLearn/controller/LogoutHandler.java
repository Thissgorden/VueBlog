package com.GDLearn.controller;

import cn.hutool.json.JSONUtil;
import com.GDLearn.lang.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json,charset=utf-8");
        if(authentication!=null){
            //手动退出
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        ServletOutputStream outputStream = response.getOutputStream();

        response.setHeader("token"," ");
        Result res = Result.sucess("登出成功");

        //@return
        outputStream.write(JSONUtil.toJsonStr(res).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
