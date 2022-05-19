package com.dataoperation.autotable.bean;

import com.alibaba.fastjson.JSON;

public class Result {
    private int code;
    private String msg;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public static Result setSuccess(String msg){
        Result bean=new Result();
        bean.setCode(1);
        bean.setMsg(msg);
        return bean;
    }
    public static Result setType(String type,String msg){
        Result bean=new Result();
        bean.setCode(1);
        bean.setType(type);
        bean.setMsg(msg);
        return bean;
    }

    public static Result setError(String msg){
        Result bean=new Result();
        bean.setCode(2);
        bean.setMsg(msg);
        return bean;
    }
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
