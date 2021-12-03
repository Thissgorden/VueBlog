package com.GDLearn.Config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha producer(){
        Properties prop = new Properties();
        //创建Properties并设置参数
        prop.put("kapcha.border","no");
        prop.put("kaptcha.textproducer.font.color", "black");
        prop.put("kaptcha.textproducer.char.space", "4");
        prop.put("kaptcha.image.height", "60");
        prop.put("kaptcha.image.width", "160");
        prop.put("kaptcha.textproducer.font.size", "30");
        //填入Kaptcha的工具类Config
        Config config = new Config(prop);

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        //注入到defaultKaptcha并返回
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;


    }
}
