package com.GDLearn;

import com.GDLearn.entity.Gacha;
import com.GDLearn.util.GaChaUtils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class gachaTest {

    @Autowired
    GaChaUtils gaChaUtils;

    @Test
    public void Hikuone(){
            Gacha res = gaChaUtils.getWeapon3stars();
            System.out.println(res);
    }
}
