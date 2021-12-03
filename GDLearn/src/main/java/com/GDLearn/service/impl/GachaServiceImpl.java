package com.GDLearn.service.impl;

import com.GDLearn.entity.Gacha;
import com.GDLearn.entity.GachaProtect;
import com.GDLearn.mapper.GachaMapper;
import com.GDLearn.service.GachaService;
import com.GDLearn.util.GaChaUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class GachaServiceImpl extends ServiceImpl<GachaMapper, Gacha> implements GachaService {
    
    @Autowired
    GaChaUtils gaChaUtils;
    Random rd = new Random();
    
    @Override
    public Gacha roll(GachaProtect protect) {
        //先判断GachaProtectCount>=90，根据isProtect来返回是否限定五星
        if(protect.getProtectcount()>=89){
            protect.setProtectcount(0);
            protect.setProtect4count(0);
            if(protect.isProtect()){
                //大保底
                protect.setProtect(false);
                protect.setProtect4(false);//五星保底了同样消耗四星的保底
                return gaChaUtils.getCharlimit5stars();
            }else{
                //非大保底
                if(rd.nextBoolean()){
                    //出了当期up
                    return gaChaUtils.getCharlimit5stars();
                }else{
                    //没出当期up
                    protect.setProtect(true);
                    return gaChaUtils.getCharUnlimit5stars();
                }
            }
        }
        //如Protect4count>=10 && isProtect == false 返回掷骰50%概率是否限定。
        //如Protect4count>=10 && isProtect == true 返回限定四星
        if(protect.getProtect4count()>=9){
            protect.ProtectcountPlus();
            protect.setProtect4count(0);
            if(protect.isProtect4()){
                //四星大保底
                protect.setProtect4(false);
                return gaChaUtils.getCharlimit4stars();
            }else{
                //非四星大保底
                if(rd.nextBoolean()){
                    //出了当期up
                    return gaChaUtils.getCharlimit4stars();
                }else{
                    //没出当期up
                    protect.setProtect4(true);
                    return gaChaUtils.getCharUnlimit4stars();
                }
            }
        }
        //若是出了5星限定角色两个isProtect都需要回归false并且ProtectCount回归为0


        //保底情况已处理，接下来开始随机掷筛 范围 [0,1000)
        int rdnum = rd.nextInt(1000);

        //若大于等于993 则获取到5星角色再次 掷骰50%概率是否限定。
        if(rdnum>=993){
            //抽到了五星
            if(rd.nextBoolean() || protect.isProtect()){
                //没歪
                protect.setProtect(false);
                protect.setProtect4count(0);
                protect.setProtectcount(0);
                return gaChaUtils.getCharlimit5stars();
            }else{
                //歪了
                protect.setProtect(true);
                protect.setProtect4count(0);
                protect.setProtectcount(0);
                return gaChaUtils.getCharUnlimit5stars();
            }
        }
        //若大于等于942 并且小于993则获取到4星物品 再次掷骰子50%是否限定角色 若非限定角色再次掷骰子50%概率是否为武器
        if(rdnum >= 942){
            protect.ProtectcountPlus();
            protect.setProtect4(false);
            protect.setProtect4count(0);
            //抽到了四星
            if(rd.nextBoolean() || protect.isProtect4()){
                //没歪
                return gaChaUtils.getCharlimit4stars();
            }else{
                //歪了
                if(rd.nextBoolean()){
                    return gaChaUtils.getCharUnlimit4stars();
                }else{
                    return gaChaUtils.getWeapon4stars();
                }
            }
        }
        //若是出了4星限定角色 isProtect4回归false;

        //若小于942则获取到3星武器并且 isProtect++;isProtect4++;
        else{
            //在942和999的范围之外,抽到3星
            //四星和五星的保底计数+1
            protect.ProtectcountPlus();
            protect.Protect4countPlus();
            return gaChaUtils.getWeapon3stars();
        }
    }
}
