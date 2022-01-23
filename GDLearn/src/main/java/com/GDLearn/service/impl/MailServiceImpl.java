package com.GDLearn.service.impl;

import com.GDLearn.dto.RegistryDto;
import com.GDLearn.lang.Result;
import com.GDLearn.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@ConfigurationProperties(prefix = "mymail.settings")
public class MailServiceImpl implements MailService {

    private String from;
    private String subject;
    private String text;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    public Result sendMail(RegistryDto dto) {
        //跳过检测 检测已经在Dto中用注解处理
        sendMimeMail(dto);
        return Result.sucess("");//因为是异步请求所以不需要返回值，
    }

    //寄出邮件
    private void sendMimeMail(RegistryDto dto) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(),true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
