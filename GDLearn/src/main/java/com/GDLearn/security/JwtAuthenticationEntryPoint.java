package com.GDLearn.security;


import cn.hutool.json.JSONUtil;
import com.GDLearn.lang.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        //设置response的格式
        response.setContentType("application/json,charset=utf-8");
        //从response获取返回的输出流
        ServletOutputStream ops = response.getOutputStream();
        //给response设置状态401 未认证错误
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Result res = Result.fail("请先登陆");

        //@return
        ops.write(JSONUtil.toJsonStr(res).getBytes(StandardCharsets.UTF_8));
        ops.flush();
        ops.close();
    }
}
