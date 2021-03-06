package com.GDLearn.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code; //200是正常 非200为异常
    private String msg;
    private Object data;

    public static Result sucess(Object data){
        return Result.sucess(200,"操作成功",data);
    }

    public static Result sucess(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result sucess(String msg,Object data){
        Result r = new Result();
        r.setMsg(msg);
        r.setCode(200);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg){
        return fail(500,msg,null);
    }

    public static Result fail(String msg,Object data){
        return fail(500,msg,data);
    }

    public static Result fail(int code,String msg,Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
