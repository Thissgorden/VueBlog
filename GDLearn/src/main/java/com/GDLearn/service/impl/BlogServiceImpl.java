package com.GDLearn.service.impl;

import com.GDLearn.entity.Blog;
import com.GDLearn.mapper.BlogMapper;
import com.GDLearn.service.BlogService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
