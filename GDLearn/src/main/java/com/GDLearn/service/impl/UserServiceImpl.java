package com.GDLearn.service.impl;

import com.GDLearn.entity.User;
import com.GDLearn.mapper.UserMapper;
import com.GDLearn.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
