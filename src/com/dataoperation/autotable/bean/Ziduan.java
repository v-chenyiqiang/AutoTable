package com.dataoperation.autotable.bean;


import com.alibaba.fastjson.JSONObject;


public class Ziduan {
	//����
    private String name;
    //����
    private String type;

    //�Ƿ�Ϊ��
    private Object isEmtpy;
    //Ĭ��ֵ
    private String defaultValue;
    //�Ƿ�����
    private Object isKey;
    //С��
    private Object xiaoshu;
    
    //����
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
