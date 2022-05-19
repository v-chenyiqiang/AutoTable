package com.dataoperation.autotable.bean;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class Model {
	private String file;
	private long createTime;
	private long updateTime;
	private String name;
	private Map<String, String> sheetMap;
	private Map<String, String> selectSheetMap;
	
	
	
	
	
	public Map<String, String> getSelectSheetMap() {
		return selectSheetMap;
	}



	public void setSelectSheetMap(Map<String, String> selectSheetMap) {
		this.selectSheetMap = selectSheetMap;
	}



	public String getFile() {
		return file;
	}



	public void setFile(String file) {
		this.file = file;
	}


	public long getCreateTime() {
		return createTime;
	}


	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}


	public long getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}




	public Map<String, String> getSheetMap() {
		return sheetMap;
	}


	public void setSheetMap(Map<String, String> sheetMap) {
		this.sheetMap = sheetMap;
	}




	@Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
