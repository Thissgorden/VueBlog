package com.GDLearn.service;

import com.GDLearn.dto.RegistryDto;
import com.GDLearn.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 作者名会被放到这里
 * @since 2021-10-14
 */

public interface UserService extends IService<User> {

    String getAuthority(User user);

    boolean register(User user,RegistryDto dto);
}
