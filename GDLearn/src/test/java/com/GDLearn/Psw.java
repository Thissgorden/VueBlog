package com.GDLearn;

import com.GDLearn.dto.RegistryDto;
import com.GDLearn.service.impl.MailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Psw {

    @Autowired
    MailServiceImpl mail;


    @Qualifier("stringRedisTemplate")
    @Autowired
    RedisTemplate tmp;

    @Test
    public void showPsw(){
        RegistryDto dto = new RegistryDto();
        dto.setEmail("1480027186@qq.com");
        dto.setUserId(4L);
        dto.setUsername("测试账号");
        mail.sendMail(dto);
    }

    @Test
    public void checkRedis(){
        Object res2 = tmp.opsForHash().keys("active");
        System.out.println(res2);
        Object res1 = tmp.opsForHash().get("active","f3b7bbac38604c529462aabaa3eb383f");
        System.out.println(res1.toString());
        Long l = Long.parseLong(res1.toString());
        System.out.println(l);
    }

}
