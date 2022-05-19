package com.dataoperation.autotable.jdbc;

import java.sql.SQLException;

import com.dataoperation.autotable.bean.DatabaConnectBean;

public class MYJDBC {
	
	public static JDBC getJDBC(DatabaConnectBean bean) {
		if(bean.getTYPE().equalsIgnoreCase("oracle")) {
			ORACLE oracle=new ORACLE(bean);
			return oracle;
		}
		else if(bean.getTYPE().equalsIgnoreCase("mysql")) {
			return new MYSQL(bean);
		}
		else if(bean.getTYPE().equalsIgnoreCase("hive")) {
		return new HIVE(bean);
		}
		else {
			/*
			 * ÔÝ²»Ö§³Ö
			 */
			return null;
		}
	}
}
