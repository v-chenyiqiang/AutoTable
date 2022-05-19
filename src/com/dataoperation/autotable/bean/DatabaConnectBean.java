package com.dataoperation.autotable.bean;

import com.alibaba.fastjson.JSON;


public class DatabaConnectBean {
	private String IP;
	private int PORT;
	private String USER;
	private String PASS;
	private String DATABASE;
	private String TYPE;
	private String SERVER;
	private String CONNECTTYPE;
	
	
	public String getCONNECTTYPE() {
		return CONNECTTYPE;
	}
	public void setCONNECTTYPE(String cONNECTTYPE) {
		CONNECTTYPE = cONNECTTYPE;
	}
	public String getDATABASE() {
		return DATABASE;
	}
	public void setDATABASE(String dATABASE) {
		DATABASE = dATABASE;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getSERVER() {
		return SERVER;
	}
	public void setSERVER(String sERVER) {
		SERVER = sERVER;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public int getPORT() {
		return PORT;
	}
	public void setPORT(int PORT) {
		this.PORT = PORT;
	}
	public String getUSER() {
		return USER;
	}
	public void setUSER(String uSER) {
		USER = uSER;
	}
	public String getPASS() {
		return PASS;
	}
	public void setPASS(String pASS) {
		PASS = pASS;
	}
	@Override
	public String toString() {
		return  JSON.toJSONString(this);
	}
	
	

}
