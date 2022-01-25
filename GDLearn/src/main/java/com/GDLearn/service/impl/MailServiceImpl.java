package com.GDLearn.service.impl;

import com.GDLearn.dto.RegistryDto;
import com.GDLearn.lang.Result;
import com.GDLearn.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;


@Service
@ConfigurationProperties(prefix = "mymail.settings")
public class MailServiceImpl implements MailService {
    @Value("${mymail.settings.from}")
    private String from;
    @Value("${mymail.settings.subject}")
    private String subject;
    private String text;
    @Value("${mymail.settings.siteaddress}")
    private String siteaddress;

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Qualifier("stringRedisTemplate")
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Async
    public Result sendMail(RegistryDto dto) {
        //激活码以及正文生成
        String ActiveCode = UUID.randomUUID().toString().replace("-","");
        String ActiveBtn = "<a href='http://"+siteaddress+"/activateAccount?cde="+ActiveCode+"'>这里</a>";
        this.text = "感谢注册 gd 的网站，<br>  请注意不要进行测试行为或者发布违法信息，有任何疑问或建议可以通过该发件邮箱联系我，祝您使用愉快！";
        this.text += "请点击"+ActiveBtn+"进行激活<br>";
        this.text += "请在30分钟内进行激活，逾期失效";

        redisTemplate.opsForHash().put("active",ActiveCode,dto.getUserId()+"");
        redisTemplate.expire("active", Duration.ofMinutes(30L));

        //跳过检测 检测已经在Dto中用注解处理
        sendMimeMail(dto);
        return Result.sucess("");//因为是异步请求所以不需要返回值，
    }

    //寄出邮件
    private void sendMimeMail(RegistryDto dto) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailSender.createMimeMessage(),true);
            helper.setFrom(from);        //发件人
            helper.setTo(dto.getEmail());//收信人
            helper.setSubject(dto.getUsername()+" 你好，"+subject);  //主题
            helper.setText(text);        //正文
            helper.setSentDate(new Date());//发送时间
            mailSender.send(helper.getMimeMessage());//发送
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
