package com.dataoperation.autotable.bean;

import com.alibaba.fastjson.JSONObject;

public class ZiduanConfig {
	private int len;
	private String name;
	private String keyName;
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	@Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
