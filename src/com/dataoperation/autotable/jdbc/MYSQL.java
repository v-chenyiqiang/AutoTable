package com.dataoperation.autotable.jdbc;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataoperation.autotable.bean.DatabaConnectBean;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MYSQL implements JDBC {
    private String IP;
    private int PORT;
    private String USER;
    private String PASSWORD;
    private Connection conn;
    private Statement st;
    private String URL;
    private ResultSet rs;
    private DatabaseMetaData metaData;
    public MYSQL(DatabaConnectBean bean) {
    	this.IP=bean.getIP();
    	this.PORT=bean.getPORT();
    	this.USER=bean.getUSER();
    	this.PASSWORD=bean.getPASS();
    }



   
    public Connection getConn() throws SQLException, ClassNotFoundException{
       URL = "jdbc:mysql://"+IP+":"+PORT;
		Class.forName("com.mysql.cj.jdbc.Driver");
		 conn = DriverManager.getConnection(URL, USER, PASSWORD);

       
        System.out.println("���ӳɹ�");
        return conn;
    }
    public Connection getConn(String DATABASE) throws ClassNotFoundException, SQLException {

        URL = "jdbc:mysql://"+IP+":"+PORT+"/"+DATABASE+"?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
        //1.������������
			Class.forName("com.mysql.cj.jdbc.Driver");
	        conn = DriverManager.getConnection(URL, USER, PASSWORD);
        //2.������ݿ�����
        System.out.println("���ӳɹ�");
        return conn;
    }

    public List<List<Object>> findColumnByRemark(String findDabaName,String findTableName,String str) {
        List<List<Object>> list=new LinkedList<List<Object>>();
        System.out.println("����ע�ͣ�������...");
        System.out.println(String.format("��������Ϣ�����ݿ�:%s  ����:%s ע��:%s",findDabaName,findTableName,str));

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
                            String isNull=nullable>0?"����":"�Ǳ���";
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
                            //list.add("���ݿ�:"+dabaName+" ����:"+tableName+" �ֶ�:"+columnName+"  �ֶ�����:"+columnType+"  �ֶδ�С"+datasize+"  С��λ��:"+digits+"  ע��:"+remarks+"  �Ƿ����:"+isNull);
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
        System.out.println("�����ֶΣ�������...");
        System.out.println(String.format("��������Ϣ�����ݿ�:%s  ����:%s �ֶ���:%s",findDabaName,findTableName,str));
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
                            String isNull = nullable > 0 ? "����" : "�Ǳ���";
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
                            //  list.add("���ݿ�:" + dabaName + " ����:" + tableName + " �ֶ�:" + columnName + "  �ֶ�����:" + columnType + "  �ֶδ�С" + datasize + "  С��λ��:" + digits + "  ע��:" + remarks + "  �Ƿ����:" + isNull);
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
            //3.ͨ�����ݿ�����Ӳ������ݿ⣬ʵ����ɾ�Ĳ飨ʹ��Statement�ࣩ
            st = conn.createStatement();
            re = st.executeUpdate(sql);
        }catch (Exception e){
            System.out.println("ִ��sql���ʧ��:"+e.toString());
        }
        return re;
    }


    public JSONObject select(String sql){
        ResultSet re=null;
        int column=0;
        int rows=0;
        JSONObject json = new JSONObject();
        try {
            //3.ͨ�����ݿ�����Ӳ������ݿ⣬ʵ����ɾ�Ĳ飨ʹ��Statement�ࣩ
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
            System.out.println("ִ��sql���ʧ��:"+e.toString());
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
	            System.out.println("�ر��쳣:"+e.toString());
	        }
	}

}