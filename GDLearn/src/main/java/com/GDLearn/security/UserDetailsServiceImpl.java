package com.GDLearn.security;

import com.GDLearn.entity.User;
import com.GDLearn.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User sysUser = userService.getOne(new QueryWrapper<User>(new User().setUsername(s)));
        if(sysUser == null){
            throw new UsernameNotFoundException("用户名或密码不正确");
        }
        return new org.springframework.security.core.userdetails.User(
                sysUser.getUsername(),
                sysUser.getPassword(),
                //todo  权限表
                getAuthority(sysUser)
                );
    }

    public List<GrantedAuthority> getAuthority(User user){
        String authority = userService.getAuthority(user);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
