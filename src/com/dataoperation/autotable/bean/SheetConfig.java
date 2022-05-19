package com.dataoperation.autotable.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class SheetConfig {
	private List<ZiduanConfig> ziduanConfig;
	private String name;
	private int tableLocation;
	private String tableName;
	private boolean isUseKey;
	private String keyName;
	private boolean isUseNull;
	private boolean isUseTable;
	private boolean isBegin;
	private String database;
	private String colomn;
	private String tableType;
	private String ziduanType;
	private String connectName;
	private Map<String, String> adapter;
	private Map<String, String> selectAdapte;
	private String tableNameUpdate;
	
	
	

	public Map<String, String> getSelectAdapte() {
		return selectAdapte;
	}

	public void setSelectAdapte(Map<String, String> selectAdapte) {
		this.selectAdapte = selectAdapte;
	}

	public String getTableNameUpdate() {
		return tableNameUpdate;
	}

	public void setTableNameUpdate(String tableNameUpdate) {
		this.tableNameUpdate = tableNameUpdate;
	}

	public SheetConfig() {
		
	}
	
	public SheetConfig(JSONObject config) {
		ziduanConfig=JSONObject.parseArray(config.getString("ziduanConfig"),ZiduanConfig.class);
		name=config.getString("name");
		tableName=config.getString("tableName");
		tableLocation=config.getIntValue("tableLocation");
		isUseKey=config.getBooleanValue("useKey");
		keyName=config.getString("keyName");
		isUseNull=config.getBooleanValue("useNull");
		isUseTable=config.getBooleanValue("useTable");
		database=config.getString("database");
		colomn=config.getString("colomn");
		tableType=config.getString("tableType");
		ziduanType=config.getString("ziduanType");
		connectName=config.getString("connectName");
		adapter=JSONObject.parseObject(config.getString("adapter"),LinkedHashMap.class);
		isBegin=config.getBooleanValue("begin");
		tableNameUpdate=config.getString("tableNameUpdate");
		selectAdapte=JSONObject.parseObject(config.getString("selectAdapte"),LinkedHashMap.class);

	}
	
	
	
	public boolean isBegin() {
		return isBegin;
	}

	public void setBegin(boolean isBegin) {
		this.isBegin = isBegin;
	}

	public Map<String, String> getAdapter() {
		return adapter;
	}
	public void setAdapter(Map<String, String> adapter) {
		this.adapter = adapter;
	}

	public String getConnectName() {
		return connectName;
	}
	public void setConnectName(String connectName) {
		this.connectName = connectName;
	}
	public String getZiduanType() {
		return ziduanType;
	}
	public void setZiduanType(String ziduanType) {
		this.ziduanType = ziduanType;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getColomn() {
		return colomn;
	}
	public void setColomn(String colomn) {
		this.colomn = colomn;
	}
	public int getTableLocation() {
		return tableLocation;
	}
	public void setTableLocation(int tableLocation) {
		this.tableLocation = tableLocation;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public boolean isUseKey() {
		return isUseKey;
	}
	public void setUseKey(boolean isUseKey) {
		this.isUseKey = isUseKey;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public boolean isUseNull() {
		return isUseNull;
	}
	public void setUseNull(boolean isUseNull) {
		this.isUseNull = isUseNull;
	}
	public boolean isUseTable() {
		return isUseTable;
	}
	public void setUseTable(boolean isUseTable) {
		this.isUseTable = isUseTable;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ZiduanConfig> getZiduanConfig() {
		return ziduanConfig;
	}
	public void setZiduanConfig(List<ZiduanConfig> ziduanConfig) {
		this.ziduanConfig = ziduanConfig;
	}


	@Override
    public String toString() {
        return JSONObject.toJSONString(this);

    }

}
