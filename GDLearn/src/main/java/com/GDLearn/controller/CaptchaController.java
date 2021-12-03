package com.GDLearn.controller;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.map.MapUtil;
import com.GDLearn.lang.Result;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@RestController
public class CaptchaController {
    @Autowired
    Producer producer;
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/captcha")
    public Result getCaptcha() throws IOException {
        String key = UUID.randomUUID().toString();
        String code = producer.createText();
        key="55555";
        code="55555";

        BufferedImage bi = producer.createImage(code);
        //创建字节输出流
        ByteArrayOutputStream BAO = new ByteArrayOutputStream();
        ImageIO.write(bi,"jpeg",BAO);

        String str = "data:image/jpeg;base64,";
        //使用base64进行编码
        String base64Img = str + Base64Encoder.encode(BAO.toByteArray());
        redisTemplate.opsForHash().put("captcha",key,code);
        redisTemplate.expire("captcha", Duration.ofSeconds(120L));


        return Result.sucess(MapUtil.builder()
                .put("key",key)
                .put("base64Img",base64Img)
                .build()
        );
    }
}
