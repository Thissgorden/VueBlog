package com.GDLearn.util;

import com.GDLearn.entity.Gacha;
import com.GDLearn.mapper.GachaMapper;
import com.baomidou.mybatisplus.extension.api.R;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class GaChaUtils {

    //pickup角色的数据库id
    private final int pickup5starid = 1;
    private final int pickup4starid1 =3;
    private final int pickup4starid2 =9;
    private final int pickup4starid3 =11;

    @Autowired
    GachaMapper gachaMapper;

    Random rd = new Random();

    /**
     * @return 随机获取数据库中的一个非限定5星角色
     */
    public Gacha getCharUnlimit5stars(){
        //先从数据库中select出非限定的五星角色id集合
        List<Integer> ids = gachaMapper.getCharUnlimit5starsIds();
        int i = rd.nextInt(ids.size());
        return gachaMapper.getCharById(ids.get(i));
    }

    /**
     * @return 随机获取数据库中的一个*非限定*4星角色
     */
    public Gacha getCharUnlimit4stars(){
        List<Integer> ids = gachaMapper.getChar4starsIds();
        int i = rd.nextInt(ids.size());
        return gachaMapper.getCharById(ids.get(i));
    }

    /**
     * @return 获取当期up的5星角色
     */
    public Gacha getCharlimit5stars(){
        return gachaMapper.getCharById(pickup5starid);
    }

    /**
     * @return 随机获取一个当期up的4星角色
     */
    public Gacha getCharlimit4stars(){
        int[] ints = {pickup4starid1, pickup4starid2, pickup4starid3};
        int id = ints[rd.nextInt(3)];
        return gachaMapper.getCharById(id);
    }

    /**
     * @return 随机获取一个4星武器
     */
    public Gacha getWeapon4stars(){
        List<Integer> ids = gachaMapper.getWeapon4starsIds();
        int rdi = rd.nextInt(ids.size());
        return gachaMapper.getWeaponById(ids.get(rdi));
    }

    /**
     * @return 随机获取一个3星武器
     */
    public Gacha getWeapon3stars(){
        List<Integer> ids = gachaMapper.getWeapon3starsIds();
        int rdi = rd.nextInt(ids.size());
        return gachaMapper.getWeaponById(ids.get(rdi));
    }
}
