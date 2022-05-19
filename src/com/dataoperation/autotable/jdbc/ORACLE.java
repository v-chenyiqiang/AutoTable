package com.dataoperation.autotable.jdbc;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataoperation.autotable.bean.DatabaConnectBean;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ORACLE implements JDBC  {
    private String connectType;
    private String serverName;
    private String IP;
    private int PORT;
    private String USER;
    private String PASSWORD;
    private Connection conn;
    private Statement st;
    private String URL;
    private ResultSet rs;
    private DatabaseMetaData metaData;
    public ORACLE(DatabaConnectBean bean) {
    	this.serverName=bean.getSERVER();
    	this.connectType=bean.getCONNECTTYPE();
    	this.IP=bean.getIP();
    	this.PORT=bean.getPORT();
    	this.USER=bean.getUSER();
    	this.PASSWORD=bean.getPASS();
    }
   

    public Connection getConn() throws ClassNotFoundException, SQLException {
        String conType;
        
        if(connectType.equalsIgnoreCase("sid")){
            conType=":";
        }
        else{
            conType="/";
        }
        URL="jdbc:oracle:thin:@"+IP+":"+PORT+conType+serverName;
			Class.forName("oracle.jdbc.OracleDriver");
			  conn = DriverManager.getConnection(URL, USER, PASSWORD);
	
        //2.获得数据库链接
        System.out.println("连接成功");
        return conn;
    }


    public List<List<Object>> findColumnByRemark(String findDabaName,String findTableName,String str) {
        List<List<Object>> list=new LinkedList<List<Object>>();
        System.out.println("根据注释，搜索中...");
        System.out.println(String.format("搜索的信息：数据库:%s  表名:%s 注释:%s",findDabaName,findTableName,str));

        try {
            Object[] dabas;
            if (findDabaName==null){
                dabas=getDatabases();
            }
            else {
                dabas=new Object[]{findDabaName};
            }
            for (int i=0;i<dabas.length;i++){
                String dabaName=dabas[i].toString();
                if (findDabaName!=null){
                    if (!findDabaName.equalsIgnoreCase(dabaName)){
                        continue;
                    }
                }
                metaData = conn.getMetaData();
                Object[] tables;
                if (findTableName==null){
                    tables=getTables(dabaName);
                }
                else{
                    tables=new Object[]{findTableName};
                }
                for (Object tableName:tables){
                    if (findTableName!=null){
                        if (!findTableName.equalsIgnoreCase(tableName.toString())){
                            continue;
                        }
                    }
                    ResultSet colRet = metaData.getColumns(dabaName, "%", tableName.toString(), "%");
                    while (colRet.next()) {
                        String columnName = colRet.getString("COLUMN_NAME");
                        String remarks = colRet.getString("REMARKS");
                        if (remarks.indexOf(str)!=-1){
                            String   columnType = colRet.getString("TYPE_NAME");
                            int datasize = colRet.getInt("COLUMN_SIZE");
                            int digits = colRet.getInt("DECIMAL_DIGITS");
                            int nullable = colRet.getInt("NULLABLE");
                            String isNull=nullable>0?"必填":"非必填";
                            List<Object> data=new LinkedList<Object>();
                            data.add(dabaName);
                            data.add(tableName);
                            data.add(columnName);
                            data.add(columnType);
                            data.add(datasize);
                            data.add(digits);
                            data.add(remarks);
                            data.add(isNull);
                            list.add(data);
                            //list.add("数据库:"+dabaName+" 表名:"+tableName+" 字段:"+columnName+"  字段类型:"+columnType+"  字段大小"+datasize+"  小数位数:"+digits+"  注释:"+remarks+"  是否必填:"+isNull);
                        }
                    }
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public List<List<Object>> findColumnByColumn(String findDabaName,String findTableName,String str) {
        List<List<Object>> list=new LinkedList<List<Object>>();
        System.out.println("根据字段，搜索中...");
        System.out.println(String.format("搜索的信息：数据库:%s  表名:%s 字段名:%s",findDabaName,findTableName,str));
        try {
            Object[] dabas;
            if (findDabaName==null){
                dabas=getDatabases();
            }
            else {
                dabas=new Object[]{findDabaName};
            }
            for (int i=0;i<dabas.length;i++){
                String dabaName=dabas[i].toString();
                if (findDabaName!=null){
                    if (!findDabaName.equalsIgnoreCase(dabaName)){
                        continue;
                    }
                }
                metaData = conn.getMetaData();
                rs = metaData.getTables(dabaName, "%", "%", new String[]{"TABLE"});
                Object[] tables;
                if (findTableName==null){
                    tables=getTables(dabaName);
                }
                else{
                    tables=new Object[]{findTableName};
                }
                for (Object tableName:tables) {
                    if (findTableName != null) {
                        if (!findTableName.equalsIgnoreCase(tableName.toString())) {
                            continue;
                        }
                    }
                    ResultSet colRet = metaData.getColumns(dabaName, "%", tableName.toString(), "%");
                    while (colRet.next()) {
                        String columnName = colRet.getString("COLUMN_NAME");
                        String remarks = colRet.getString("REMARKS");
                        if (columnName.toLowerCase().indexOf(str.toLowerCase()) != -1) {
                            String columnType = colRet.getString("TYPE_NAME");
                            int datasize = colRet.getInt("COLUMN_SIZE");
                            int digits = colRet.getInt("DECIMAL_DIGITS");
                            int nullable = colRet.getInt("NULLABLE");
                            String isNull = nullable > 0 ? "必填" : "非必填";
                            List<Object> data=new LinkedList<Object>();
                            data.add(dabaName);
                            data.add(tableName);
                            data.add(columnName);
                            data.add(columnType);
                            data.add(datasize);
                            data.add(digits);
                            data.add(remarks);
                            data.add(isNull);
                            list.add(data);
                            //  list.add("数据库:" + dabaName + " 表名:" + tableName + " 字段:" + columnName + "  字段类型:" + columnType + "  字段大小" + datasize + "  小数位数:" + digits + "  注释:" + remarks + "  是否必填:" + isNull);
                        }
                    }
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }

    public int execute(String sql){
        int re=-1;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            st = conn.createStatement();
            re = st.executeUpdate(sql);
        }catch (Exception e){
            System.out.println("执行sql语句失败:"+e.toString());
        }
        return re;
    }


    public JSONObject select(String sql){
        ResultSet re=null;
        int column=0;
        int rows=0;
        JSONObject json = new JSONObject();
        try {
            //3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData metaData=null;
            metaData = rs.getMetaData();
            List<String> colomnStrings=new LinkedList<>();
            column=metaData.getColumnCount();
            json.put("column",column);
            JSONArray data=new JSONArray();
            while (rs.next()){
                JSONObject list=new JSONObject();
                for (int i=1;i<=column;i++){
                    list.put(i-1+"",rs.getObject(i));
                    if (json.get("colomnName")==null) {
                        colomnStrings.add(metaData.getColumnLabel(i));
                        if(i==column) {
                            json.put("colomnName", colomnStrings);
                        }
					}
                }
                rows++;
                data.add(list);
            }
            json.put("rows",rows);
            json.put("data",data);
            
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("执行sql语句失败:"+e.toString());
        }
       
  
        return json;
    }
    public Object[] getDatabases(){
        String sql="show databases";
        JSONObject bases=select(sql);
        JSONArray dabas=bases.getJSONArray("data");
        Object[] re=new Object[dabas.size()];
        for (int i=0;i<dabas.size();i++) {
            JSONObject dabaObject = (JSONObject) dabas.get(i);
            re[i]=dabaObject.get(0).toString();
        }
        return re;
    }
    public Object[] getTables(String dabaName){
        List<Object> list=new LinkedList<Object>();
        try {
            metaData = conn.getMetaData();
            rs = metaData.getTables(dabaName, "%", "%", new String[]{"TABLE"});
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                list.add(tableName);
            }
        } catch (SQLException throwables) {
            //System.out.println(throwables.toString());
        }
        Object[] re=list.toArray();
        return re;
    }

    public  void createTable(){
        StringBuilder builder=new StringBuilder();
        String tableName="CYQ4";
        builder.append("CREATE TABLE \"UIS\".\""+tableName+"\" (\n");
        builder.append("\"ID\" NUMBER,\n");
        for(int i=0;i<10;i++){
            builder.append("\"TEST"+i+"\" VARCHAR2(255),\n");
        }
        builder.append("\"TEST10"+"\" CLOB,\n");
        builder.append("\"TECH_UPDT_TIME"+"\" DATE,\n");
        builder.append("PRIMARY KEY (\"ID\")");
        builder.append(")");
        System.out.println(builder);
        System.out.println(execute(builder.toString()));
    }
    public void insertRows(){
        StringBuilder builder=new StringBuilder();

        for(int i=0;i<10;i++){

            //builder.append("INSERT INTO \"UIS\""+tableName+"VALUES ");
        }
    }

    public String getDataVersion(){
        String sql="select name from v$database";
        String v=select(sql).getJSONArray("data").getJSONObject(0).get(0).toString();
        return v;
    }
    public boolean isHasDatabase(String DatabaseName){
        String sql="show databases";
        JSONObject re=select(sql);
        int rows=(Integer)re.get("rows");
        if(rows==0){
            return false;
        }
        int column=(Integer) re.get("column");
        JSONArray data=re.getJSONArray("data");
        for (int i=0;i<rows;i++){
            JSONArray list= (JSONArray) data.get(i);
            for (int j=0;j<column;j++){
                if(DatabaseName.equals(list.getString(j))){
                    return true;
                }
            }
        }
        return false;
    }

	@Override
	public void close() {
		// TODO Auto-generated method stub
		   try {
	            if (rs != null)
	                rs.close();
	            if (st != null)
	                st.close();
	            if (conn != null)
	                conn.close();
	        }catch (Exception e){
	            System.out.println("关闭异常:"+e.toString());
	        }
	}


	@Override
	public Connection getConn(String DATABASE) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}