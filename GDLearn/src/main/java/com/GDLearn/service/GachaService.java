package com.GDLearn.service;

import com.GDLearn.entity.Gacha;
import com.GDLearn.entity.GachaProtect;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;


public interface GachaService extends IService<Gacha> {
    Gacha roll(GachaProtect gachaProtect);
}
