package com.dataoperation.autotable.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.alibaba.fastjson.JSONObject;

public interface JDBC {
    public Connection getConn() throws SQLException, ClassNotFoundException;
    public Connection getConn(String DATABASE) throws ClassNotFoundException, SQLException;
	public int execute(String sql);

	public JSONObject select(String sql);

	public Object[] getDatabases();

	public Object[] getTables(String dabaName);

	public boolean isHasDatabase(String DatabaseName);
	
	public void close();

}
