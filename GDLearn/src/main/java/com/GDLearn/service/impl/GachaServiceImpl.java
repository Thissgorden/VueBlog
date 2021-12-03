package com.GDLearn.service.impl;

import com.GDLearn.entity.Gacha;
import com.GDLearn.entity.GachaProtect;
import com.GDLearn.mapper.GachaMapper;
import com.GDLearn.service.GachaService;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Random;

public class GachaServiceImpl extends ServiceImpl<GachaMapper, Gacha> implements GachaService {

    @Override
    public Gacha roll(GachaProtect gacha) {
        //如GachaProtect>=90 && isProtect == false 返回掷骰50%概率是否限定。

        //如GachaProtect>=90 && isProtect == true 返回限定五星

        //如Protect4count>=10 && isProtect == false 返回掷骰50%概率是否限定。

        //如Protect4count>=10 && isProtect == true 返回限定四星

        //若是出了5星限定角色两个isProtect都需要回归false并且ProtectCount回归为0

        //先随机掷筛 范围 [0,1000)

        //若大于等于993 则获取到5星角色再次 掷骰50%概率是否限定。

        //若大于等于942 并且小于993则获取到4星物品 再次掷骰子50%是否限定角色 若非限定角色再次掷骰子是否为武器

        //若是出了4星限定角色 isProtect4回归false;

        //若小于942则获取到3星武器并且 isProtect++;isProtect4++;

    }
}
