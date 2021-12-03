package com.GDLearn.controller;

import cn.hutool.core.map.MapUtil;
import com.GDLearn.entity.Gacha;
import com.GDLearn.entity.GachaProtect;
import com.GDLearn.lang.Result;
import com.GDLearn.service.GachaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class GachaController {

    @Autowired
    GachaService gachaService;

    /**
     * @param protect 保底情况 包含保底后多少发Protectcount 以及是否大保底isProtect
     * @Return gacha 抽卡结果 和 保底情况protect
     */
    @PostMapping("/Gacha")
    public Result Gacha(GachaProtect protect) {
        Gacha gacha = gachaService.roll(protect);

        return Result.sucess(MapUtil.builder()
                .put("protect",protect)
                .put("gacha",gacha).map()
        );
    }


    @PostMapping("/Gacha10")
    public Result Gacha10(GachaProtect protect){
        ArrayList<Gacha> gachas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            gachas.add(gachaService.roll(protect));
        }
        return Result.sucess(MapUtil.builder()
                .put("protect",protect)
                .put("gachas",gachas)
                .map()
        );
    }

}
