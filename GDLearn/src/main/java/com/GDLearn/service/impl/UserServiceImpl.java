package com.GDLearn.service.impl;

import cn.hutool.extra.mail.MailException;
import com.GDLearn.dto.RegistryDto;
import com.GDLearn.entity.Role;
import com.GDLearn.entity.User;
import com.GDLearn.mapper.UserMapper;
import com.GDLearn.service.MailService;
import com.GDLearn.service.RoleService;
import com.GDLearn.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    @Autowired
    MailService mailService;

    @Override
    public String getAuthority(User user) {

        Role role = roleService.getById(user.getRoleid());

        return role.getCode();
    }

    @Override
    @Async
    public boolean register(User usr,RegistryDto dto) {
        //加个事务 防止出BUG导致数据库出现垃圾数据占用用户信息
        save(usr);
        User username = getOne(new QueryWrapper<User>().eq("username", usr.getUsername()));
        dto.setUserId(username.getId());
        try {
            mailService.sendMail(dto);
        }catch (Exception e){
            removeById(username);
            throw e;
        }
        return true;
    }
}
