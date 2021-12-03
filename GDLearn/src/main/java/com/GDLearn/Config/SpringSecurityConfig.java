package com.GDLearn.Config;

import com.GDLearn.controller.LogoutHandler;
import com.GDLearn.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    LogoutHandler logoutHandler;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    CaptchaFilter captchaFilter;

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/captcha",
            "/favicon.icon",
            "/test/**",
            "/visitor"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()//开启跨域和关闭csrf
                //表单登陆
                .formLogin()
                .successForwardUrl("/login/success")
                .failureForwardUrl("/login/failure")

                .and()//关闭session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()//登出
                .logout().logoutSuccessHandler(logoutHandler)

                .and()//配置拦截器
                //配置拦截器的主方法,后边都是子方法
                .authorizeRequests()
                .antMatchers(URL_WHITELIST).permitAll()//先放行白名单
                .anyRequest()
                //任意请求都需要授权
                .authenticated()

                .and()//异常处理
                .exceptionHandling()//异常处理主方法
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//JWT认证失败处理器
                .accessDeniedHandler(jwtAccessDeniedHandler)//JWT请求拒绝处理器

                .and()//配置自定义过滤器
                .addFilter(jwtAuthenticationFilter())//JWT认证过滤器
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)//验证码处理过滤器
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
