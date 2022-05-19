package com.dataoperation.autotable.bean;

import com.alibaba.fastjson.JSONObject;

public class ResultBean {
	private int id;
	private String name;
	private String type;
	private String wordValue;
	private String databaseValue;
	private String  error;
	private String tableName;
	
	
	
	
	
	
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getWordValue() {
		return wordValue;
	}
	public void setWordValue(String wordValue) {
		this.wordValue = wordValue;
	}
	public String getDatabaseValue() {
		return databaseValue;
	}
	public void setDatabaseValue(String databaseValue) {
		this.databaseValue = databaseValue;
	}
	
	
	@Override
    public String toString() {
        return JSONObject.toJSONString(this);

    }

}
