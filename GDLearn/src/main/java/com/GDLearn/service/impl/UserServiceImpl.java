package com.GDLearn.service.impl;

import com.GDLearn.entity.Role;
import com.GDLearn.entity.User;
import com.GDLearn.mapper.UserMapper;
import com.GDLearn.service.RoleService;
import com.GDLearn.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 作者名会被放到这里
 * @since 2021-10-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    RoleService roleService;

    @Override
    public String getAuthority(User user) {

        Role role = roleService.getById(user.getRoleid());

        return role.getCode();
    }
}
