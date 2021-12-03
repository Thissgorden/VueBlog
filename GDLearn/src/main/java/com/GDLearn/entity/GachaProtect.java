package com.GDLearn.entity;

import lombok.Data;

@Data
public class GachaProtect {
    //保底后多少发
    private int Protectcount;

    //是否大保底
    boolean isProtect;

    //4星保底后多少发
    private int Protect4count;

    //是否四星保底
    boolean isProtect4;

    public GachaProtect ProtectcountPlus(){
        Protectcount++;
        return this;
    }

    public GachaProtect Protect4countPlus(){
        Protect4count++;
        return this;
    }
}
