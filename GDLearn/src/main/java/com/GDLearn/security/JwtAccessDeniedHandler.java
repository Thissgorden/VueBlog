package com.GDLearn.security;

import cn.hutool.json.JSONUtil;
import com.GDLearn.lang.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //格式类似JwtAuthenticationEntryPoint，详细注解见该类
        response.setContentType("application/json,charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Result result = Result.fail(accessDeniedException.getMessage());

        ServletOutputStream ops = response.getOutputStream();

        //@return
        ops.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        ops.flush();
        ops.close();
    }
}
