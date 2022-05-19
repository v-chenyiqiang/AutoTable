package com.dataoperation.autotable.bean;


import com.alibaba.fastjson.JSONObject;


public class Ziduan {
	//名称
    private String name;
    //类型
    private String type;

    //是否为空
    private Object isEmtpy;
    //默认值
    private String defaultValue;
    //是否主键
    private Object isKey;
    //小数
    private Object xiaoshu;
    
    //长度
    private Object length;
    
    
    
    
    
    

	public Object getIsEmtpy() {
		return isEmtpy;
	}



	public void setIsEmtpy(Object isEmtpy) {
		this.isEmtpy = isEmtpy;
	}



	public Object getIsKey() {
		return isKey;
	}



	public void setIsKey(Object isKey) {
		this.isKey = isKey;
	}



	public Object getLength() {
		return length;
	}



	public void setLength(Object length) {
		this.length = length;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}





	public Object isEmtpy() {
		return isEmtpy;
	}



	public void setEmtpy(Object isEmtpy) {
		this.isEmtpy = isEmtpy;
	}



	public String getDefaultValue() {
		return defaultValue;
	}



	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}



	public Object isKey() {
		return isKey;
	}



	public void setKey(Object isKey) {
		this.isKey = isKey;
	}



	public Object getXiaoshu() {
		return xiaoshu;
	}



	public void setXiaoshu(Object xiaoshu) {
		this.xiaoshu = xiaoshu;
	}



	@Override
    public String toString() {
        return JSONObject.toJSONString(this);

    }
}
