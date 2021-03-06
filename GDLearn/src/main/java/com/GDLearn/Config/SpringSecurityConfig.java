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
    /*
    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/captcha",
            "/favicon.icon",
            "/test/**",
            "/visitor",
            "/activateAccount",
            "/register",
            "/blog/list"
    };*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()//?????????????????????csrf
                //????????????
                .formLogin()
                .successForwardUrl("/login/success")
                .failureForwardUrl("/login/failure")

                .and()//??????session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()//??????
                .logout().logoutSuccessHandler(logoutHandler)

                //.and()//???????????????
                //???????????????????????????,?????????????????????
                //.authorizeRequests()
                //.antMatchers(URL_WHITELIST).permitAll()//??????????????????
                //.anyRequest()
                //???????????????????????????
                //.authenticated()

                .and()//????????????
                .exceptionHandling()//?????????????????????
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)//JWT?????????????????????
                .accessDeniedHandler(jwtAccessDeniedHandler)//JWT?????????????????????

                .and()//????????????????????????
                .addFilter(jwtAuthenticationFilter())//JWT???????????????
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)//????????????????????????
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
