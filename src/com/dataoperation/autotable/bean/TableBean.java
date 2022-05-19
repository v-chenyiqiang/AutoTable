package com.dataoperation.autotable.bean;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TableBean {
	private String name;
	private List<Ziduan> ziduans;
	private String database;
	private Object[] colomn;
	private Object beizhu;
	
	
	
	
	
	public Object getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(Object beizhu) {
		this.beizhu = beizhu;
	}
	public Object[] getColomn() {
		return colomn;
	}
	public void setColomn(Object[] colomn) {
		this.colomn = colomn;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Ziduan> getZiduans() {
		return ziduans;
	}
	public void setZiduans(List<Ziduan> ziduans) {
		this.ziduans = ziduans;
	}

	@Override
    public String toString() {
        return JSONObject.toJSONString(this);

    }
}
