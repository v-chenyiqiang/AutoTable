package com.dataoperation.autotable.ui;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONObject;
import com.dataoperation.autotable.bean.DatabaConnectBean;
import com.dataoperation.autotable.bean.Model;
import com.dataoperation.autotable.bean.SheetConfig;
import com.dataoperation.autotable.bean.TableBean;
import com.dataoperation.autotable.bean.Ziduan;
import com.dataoperation.autotable.bean.ZiduanConfig;
import com.dataoperation.autotable.excel.POIUtil;
import com.dataoperation.autotable.jdbc.JDBC;
import com.dataoperation.autotable.jdbc.MYJDBC;
import com.dataoperation.autotable.utils.FileUtils;

public class DealData {
	private static Logger logger = LogManager.getLogger(DealData.class.getName());
    private static String  errorString;
	private static File systemFile=new File(System.getProperty("user.dir")+"/autoTable");
	private static List<String> rightSheetList;//��ʱ����ʹ��
	private static List<String> errorSheetList;//��ʱ����ʹ��

	
	
	
	
	
    public static List<String> getRightSheetList() {
    	List<String> varList=rightSheetList;
    	rightSheetList=null;
		return varList;
	}

	public static List<String> getErrorSheetList() {
    	List<String> varList=errorSheetList;
    	errorSheetList=null;
		return varList;
	}



	public static  String getError() {
    	if(errorString!=null) {
    		String varString=errorString;
    		errorString=null;
    		return varString;
    	}
    	return errorString;
    }
    
    
    
	public  static JDBC getJDBC(DatabaConnectBean connectBean) throws ClassNotFoundException, SQLException {
		JDBC jdbc = MYJDBC.getJDBC(connectBean);
		if(connectBean.getCONNECTTYPE().equals("hive")) {
			jdbc.getConn(connectBean.getCONNECTTYPE());
		}
		else
			jdbc.getConn();
		return jdbc;
	} 
	public  static DatabaConnectBean getConnectBeanByName(String name) throws IOException {
		if(name==null||name.isEmpty()) {
			return null;
		}
		File file = new File(systemFile.getAbsoluteFile() + "/connectConfig/" + name);
		DatabaConnectBean bean = null;
		if (file.exists()) {
			String str = FileUtils.getStr(file);
			bean = JSONObject.parseObject(str, DatabaConnectBean.class);
		}
		return bean;
	}
 
    public static  SheetConfig getSheetConfigByName(String name) throws IOException {
		if (name.isEmpty()) {
			return null;
		}
		File file = new File(systemFile.getAbsoluteFile() + "/sheetConfig/" + name);
		if (!file.exists()) {
			return null;
		}
		JSONObject jsonObject = JSONObject.parseObject(FileUtils.getStr(file));
		SheetConfig sheetConfig = new SheetConfig(jsonObject);
		return sheetConfig;
	}
    
   
    
    public  static String getColSql(String type, Object[] values) {
		StringBuilder builder = new StringBuilder();
		// Object[] valuesObjects=new Object[]
		// {"COLUMN_NAME,DATA_TYPE,DATA_LENGTH,KEY,DATA_SCALE,NULLABLE,DATA_DEFAULT"};
		for (int i = 0; i < values.length; i++) {
			String string = values[i].toString();
			switch (string) {
			case "name": {
				if (type.equals("oracle")) {
					builder.append("col.COLUMN_NAME");
				} else if (type.equals("mysql")) {
					builder.append("col.COLUMN_NAME");
				}
				break;
			}
			case "type": {
				if (type.equals("oracle")) {
					builder.append("col.DATA_TYPE");
				} else if (type.equals("mysql")) {
					builder.append("col.DATA_TYPE");
				}
				break;
			}
			case "length": {
				if (type.equals("oracle")) {
					builder.append("col.DATA_LENGTH,col.DATA_PRECISION");
				} else if (type.equals("mysql")) {
					builder.append("col.CHARACTER_MAXIMUM_LENGTH,col.NUMERIC_PRECISION");
				}
				break;
			}
			case "xiaoshu": {
				if (type.equals("oracle")) {
					builder.append("col.DATA_SCALE");
				} else if (type.equals("mysql")) {
					builder.append("col.NUMERIC_SCALE");
				}
				break;
			}
			case "isKey": {
				break;
			}
			case "isEmtpy": {
				if (type.equals("oracle")) {
					builder.append("col.NULLABLE");
				} else if (type.equals("mysql")) {
					builder.append("col.IS_NULLABLE");
				}
				break;
			}
			case "defaultValue": {
				if (type.equals("oracle")) {
					builder.append("col.DATA_DEFAULT");
				} else if (type.equals("mysql")) {
					builder.append("col.COLUMN_DEFAULT");
				}
				break;
			}
			default:
				logger.debug("��ҪУ����ֶβ����ڸ�����:" + string);
			}
			builder.append(",");

		}
		if (builder == null) {
			return null;
		}
		String st = builder.toString().replace(",,", ",");

		return st.substring(0, st.length() - 1);
	}
    
    public static String[] getSQL(String database, String tableName, String type, Object[] values) {
		String[] sql = new String[2];
		String sqlString = null;
		String sql2 = null;
		boolean isHasKey = false;
		for (Object s : values) {
			if (s.toString().equals("isKey")) {
				isHasKey = true;
			}
		}
		String colomnString = null;
		colomnString = getColSql(type, values);
		if (colomnString == null) {
			logger.error("ƴ�ӵ�sql����Ϊ��,������Ҫ��֤�������ֶ�");
			logger.debug("ƴ�ӵ�sql����Ϊ��,������Ҫ��֤�������ֶ�");

		}
		if (isHasKey) {
			colomnString = colomnString.replace("col.KEY", "");
		}
		if (type.equals("oracle")) {	
			sqlString = "select " + colomnString + " from all_tab_columns col where table_name='" + tableName
					+ "' and OWNER='" + database + "'";
			
			if (isHasKey) {
				sql2 = "select  col.COLUMN_NAME from all_constraints con,all_cons_columns col where con.constraint_name=col.constraint_name"
						+ " and con.constraint_type='P' and col.table_name='" + tableName + "' and col.OWNER='"
						+ database + "'";
			}
		} else if (type.equals("mysql")) {
			sqlString = "select " + colomnString + " from information_schema.columns as col where " + "table_name='"
					+ tableName + "'and table_schema='" + database + "'";
			sql2 = "SELECT column_name FROM INFORMATION_SCHEMA.`KEY_COLUMN_USAGE`  WHERE" + " table_name='" + tableName
					+ "'  AND CONSTRAINT_SCHEMA='" + database + "' AND constraint_name='PRIMARY'";
		} else {
			sqlString="desc  "+database+"."+tableName;
		}
		logger.debug(type + "��ѯ��sql:" + sqlString);
		logger.debug(type + "��ѯ������sql:" + sql2);
		sql[0] = sqlString;
		sql[1] = sql2;
		return sql;
	}
	public  static  Map<String, List<TableBean>> dealExcel(File file, Model model) throws IOException {
		Map<String, String> map = model.getSelectSheetMap();
		int number = 0;
		logger.debug("ɨ���sheetҳ����Ϊ:" + map);
		Workbook workbook = POIUtil.getWorkBook(file);
		int num = workbook.getNumberOfSheets();
		Map<String, List<TableBean>> tList = new LinkedHashMap();
		for (String str : map.keySet()) {
			if(str!=null&&str.trim().length()==0) {
				continue;
			}
			SheetConfig sheetConfig = getSheetConfigByName(map.get(str));
			logger.debug("����" + str + "��");
			if (sheetConfig == null) {
				errorString=str+"�������ļ�"+map.get(str)+"������";
				logger.warn(errorString);
				return null;
			}
			List<TableBean> t1 = null;
			try {
				t1 = dealBySheet(workbook.getSheet(str), sheetConfig);
				if (t1 != null) {
					number += t1.size();
					if(rightSheetList==null) {
						rightSheetList=new LinkedList<>();
					}
					rightSheetList.add(str);
				}
				else {
					if(errorSheetList==null) {
						errorSheetList=new LinkedList<>();
					}
					errorSheetList.add(str);
				}
			} catch (Exception e2) {
				errorString="����"+str+"sheetҳ���ִ���";
				logger.error(errorString);
				logger.error(e2);
				errorSheetList.add(str);
				return null;
			}
			tList.put(str, t1);
		}
		logger.debug("�����ļ���" + file.getName() + "���������ı�ṹ:");
		logger.debug(tList);
		return tList;
	}
	public static boolean isHF(String str) {
			 Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			 Matcher m = p.matcher(str);
			 if (m.find()) {
			  return false;
			 }
			 return true;
			}
		
	
	
	
	
	
	public static  List<TableBean> dealBySheet(Sheet sheet, SheetConfig sheetConfig) {
		logger.debug("�����sheetҳΪ:" + sheet.getSheetName());
		logger.debug("��Ӧ��sheet����Ϊ:" + sheetConfig);
		List<ZiduanConfig> ziduanConfigs = sheetConfig.getZiduanConfig();
		int wz = sheetConfig.getTableLocation();
		String tableKey = sheetConfig.getTableName();

		List<String[]> list = POIUtil.getStrBySheet(sheet);
		Map<String, Integer> zdWz = null;
		boolean isTable = false;
		boolean isZD = false;
		int tableWz = -1;
		List<TableBean> tables = new LinkedList<>();
		List<Ziduan> ziduans = null;
		String tableName = null;
		int max=-1;
		for (int k = 0; k < list.size(); k++) {
			String[] rows = list.get(k);
			if (rows == null) {
				continue;
			}
			logger.debug(("������״̬:" + isTable));
			if (isTable == false) {
				int begin = 0;
				int end = rows.length;
				if (wz > -1) {
					begin = wz - 1;
					end = wz;
				}
				for (int i = begin; i < end; i++) {
					
					if (i>=rows.length) {
						continue;
					}
					if (rows[i] == null || rows[i].length() == 0) {
						continue;
					}
			
					if(rows[i].trim().equals(tableKey.trim())||(!isTable&&!isZD&&tables.size()>0&&rows[i]!=null)) {
						if(i+1>wz) {
							/*
							 * �������Ϸ���У��
							 */
			                 if(!isHF(rows[i+1].trim())) {
									logger.debug("1��ȡ������:" + rows[i + 1].trim()+"���Ϸ�����ǿ�ƽ�����sheetҳ");
									isTable=false;
									isZD = false;
									continue;
			                 }
							
							logger.debug("1��ȡ������:" + rows[i + 1].trim());
							logger.debug("��������");
							isTable = true;
							ziduans = new LinkedList<>();
							tableWz = i;
							tableName = rows[i + 1].trim();
							tableName=	sheetConfig.getTableNameUpdate().replace("{tableName}", tableName);
							logger.debug(tableWz);
							String varTableName=tableName;
							if (tableName != null) {
								if (sheetConfig.getTableType().equals("��д")) {
									tableName = tableName.toUpperCase();
								} else if (sheetConfig.getTableType().equals("Сд")) {
									tableName = tableName.toLowerCase();
								} else {
								}
								logger.debug("�ѽ�" + varTableName + "ת��Ϊ:" + tableName);
							}
							break;
						}
						else if(wz==i+1){	
						String[]	 varrows;	
						if (k+1>=list.size()) {
							continue;
						}
						varrows = list.get(k+1);
						if(varrows==null||varrows.length<=i) {
							continue;
						}
						/*
						 * �������Ϸ���У��
						 */
		                 if(!isHF( varrows[i].trim())) {
								logger.debug("2��ȡ������:" + varrows[i].trim()+"���Ϸ�����ǿ�ƽ�����sheetҳ");
								isTable=false;
								isZD = false;
								continue;
		                 }
						logger.debug("2��ȡ������:" + varrows[i].trim());
						logger.debug("��������");
							isTable = true;
							ziduans = new LinkedList<>();
							tableWz = i;
							tableName = varrows[i].trim();
							tableName=	sheetConfig.getTableNameUpdate().replace("{tableName}", tableName);
							if (tableName != null) {
								if (sheetConfig.getTableType().equals("��д")) {
									tableName = tableName.toUpperCase();
								} else if (sheetConfig.getTableType().equals("Сд")) {
									tableName = tableName.toLowerCase();
								} else {
								}
								logger.debug("�ѽ�" + tableName + "ת��Ϊ:" + tableName);
							}
							break;
						}
					}
				}
			}
		
			logger.debug("����Ƿ���Ͻ���ƥ����--isTable:(" + isTable + ")--isZD:(" + isZD + ")");
			if (isTable && isZD == false&&sheetConfig.isBegin()) {
				logger.debug("��鵽���ֶ�����Ϊ��ʼ��");
				zdWz = isZiduan(ziduanConfigs, rows);
				if (zdWz == null) {
					continue;
				}
				if (zdWz.size() > 0) {
					logger.debug("ƥ�䵽�ֶ���,����ƥ���ֶ�");
					isZD = true;
					for(String key:zdWz.keySet()) {
						if(zdWz.get(key)>max) {
							max=zdWz.get(key);
						}
					}
				}
			}
			if(isTable && isZD == false&&!sheetConfig.isBegin()) {
				logger.debug("��鵽�����ֶ�����Ϊ��ʼ��");

				Map<String, Integer> varZdMap=isZiduan(ziduanConfigs, rows);
				if(varZdMap==null) {
					if(zdWz==null) {
						continue;
					}
				}else {
				zdWz = isZiduan(ziduanConfigs, rows);
				for(String key:zdWz.keySet()) {
					if(zdWz.get(key)>max) {
						max=zdWz.get(key);
					}
				}
				}
					logger.debug("����ƥ���ֶ�");
					isZD = true;
			}

			logger.debug("����Ƿ���Ͻ���ƥ���ֶ�--isTable(" + isTable + ")--isZD(" + isZD + ")");
			if (isTable && isZD) {
				while (true) {
					k++;
					logger.debug("��鵱ǰ���Ƿ�����ĵ������:(" + (k >= list.size()) + ")");
					if ((k >= list.size())) {
						if(ziduans.size()==0) {
							logger.debug("��������"+tableName+"ҳ���ֶθ���Ϊ0,ǿ�ƽ���" );
							return null;
						}
						TableBean tableBean = new TableBean();
						tableBean.setName(tableName);
						tableBean.setZiduans(ziduans);
						tableBean.setDatabase(sheetConfig.getDatabase());
						tableBean.setColomn(sheetConfig.getColomn().split(","));
						JSONObject varBZ=new JSONObject();
						varBZ.put("�ֶ���", ziduans.size());
						tableBean.setBeizhu(varBZ);
						tables.add(tableBean);
						logger.debug("��ӱ�ṹ:" + tableBean);
						break;
					}
					rows = list.get(k);
					if (rows != null) {
						if (isTable && isZD) {
							logger.debug("�����н����Ƿ���:(" + sheetConfig.isUseNull());
				
							if (sheetConfig.isUseNull()) {
								if (rows == null || rows.length == 0 || strLeng(rows) == 0) {
									if(ziduans.size()==0) {
										logger.debug("��������"+tableName+"ҳ���ֶθ���Ϊ0,ǿ�ƽ���" );
										return null;
									}
									TableBean tableBean = new TableBean();
									tableBean.setName(tableName);
									tableBean.setZiduans(ziduans);
									tableBean.setDatabase(sheetConfig.getDatabase());
									tableBean.setColomn(sheetConfig.getColomn().split(","));
									JSONObject varBZ=new JSONObject();
									varBZ.put("�ֶ���", ziduans.size());
									tableBean.setBeizhu(varBZ);
									tables.add(tableBean);
									isZD = false;
									isTable = false;
									logger.debug("��ӱ�ṹ:" + tableBean);
									break;
								} else {
									// System.out.println("���ǿ���");
								}
							}
						}
					}
					if (rows == null || rows.length == 0) {
						// System.out.println("��鵽��������");
						continue;
					}
					if ( tableWz<rows.length&& isTable && isZD && rows[tableWz] != null&& rows[tableWz].equals(tableKey)&&!rows[tableWz+1].trim().equalsIgnoreCase(tableName.trim())&&isHF(rows[tableWz+1].trim())) {
						logger.debug("ƥ�䵽��1����,�Զ�����1��");
						if(ziduans.size()==0) {
							logger.debug("��������"+tableName+"ҳ���ֶθ���Ϊ0,ǿ�ƽ���" );
							return null;
						}
						TableBean tableBean = new TableBean();
						tableBean.setName(tableName);
						tableBean.setZiduans(ziduans);
						tableBean.setDatabase(sheetConfig.getDatabase());
						tableBean.setColomn(sheetConfig.getColomn().split(","));
						JSONObject varBZ=new JSONObject();
						varBZ.put("�ֶ���", ziduans.size());
						tableBean.setBeizhu(varBZ);
						tables.add(tableBean);
						k--;
						isZD = false;
						isTable = false;
						break;
					}
					else if( tableWz<rows.length&&isTable && isZD && rows[tableWz] != null &&!sheetConfig.isBegin()&&!rows[tableWz].isEmpty()&&!rows[tableWz].trim().equalsIgnoreCase(tableName.trim())&&isHF(rows[tableWz].trim())) {
				
						if(ziduans.size()==0) {
		
							logger.debug("��������"+tableName+"ҳ���ֶθ���Ϊ0,ǿ�ƽ���" );
							return null;
						}
						logger.debug("ƥ�䵽��2����,�Զ�����1��");
						TableBean tableBean = new TableBean();
						tableBean.setName(tableName.trim());
						tableBean.setZiduans(ziduans);
						tableBean.setDatabase(sheetConfig.getDatabase());
						tableBean.setColomn(sheetConfig.getColomn().split(","));
						JSONObject varBZ=new JSONObject();
						varBZ.put("�ֶ���", ziduans.size());
						tableBean.setBeizhu(varBZ);
						tables.add(tableBean);
						k--;
						isZD = false;
						isTable = false;
						break;
					}
					if (rows == null || rows.length <max+1) {
						continue;
					}
		

					Ziduan ziduan = new Ziduan();
					logger.debug("�������ֶζ���");
					if (zdWz.get("�ֶ���") != null) {
						if(rows[zdWz.get("�ֶ���")].isEmpty()) {
							logger.debug("�����ֶ���Ϊ���ַ������������ֶ�");
							continue;
						}
						
						/*
						 * �����ֶ����Ϸ���У��
						 */
		                 if(!isHF(rows[zdWz.get("�ֶ���")])) {
								logger.debug("1��ȡ������:" + rows[zdWz.get("�ֶ���")]+"���Ϸ�����ǿ�ƽ�����sheetҳ");
								isTable=false;
								isZD = false;
								continue;
		                 }
						
						
						ziduan.setName(rows[zdWz.get("�ֶ���")]);
						logger.debug("����ֶ���:" + rows[zdWz.get("�ֶ���")]);
					}
					if (zdWz.get("����") != null) {
						String stypeString = rows[zdWz.get("����")];
						ziduan.setType(rows[zdWz.get("����")]);
						logger.debug("����ֶ�����:" + rows[zdWz.get("����")]);
						if (stypeString != null) {
							stypeString=stypeString.replace("��", "(");
							stypeString=stypeString.replace("��", ")");

							if(stypeString.indexOf("(")!=-1) {
								if(stypeString.indexOf(",")!=-1) {
									if (zdWz.get("����") == null) {
										logger.debug("���������ȹؼ���ƥ��Ϊ��,���������з��ϳ��ȵ���Ϣ");
										ziduan.setLength(stypeString.substring(stypeString.indexOf("(")+1,stypeString.indexOf(",")));
										ziduan.setXiaoshu(stypeString.substring(stypeString.indexOf(",")+1, stypeString.length()-1));
										ziduan.setType(stypeString.substring(0,stypeString.indexOf("(")));
										logger.debug("����ֶγ���:" +stypeString.substring(stypeString.indexOf("(")+1,stypeString.indexOf(",")));
										logger.debug("����ֶ�С��:" +stypeString.substring(stypeString.indexOf(",")+1, stypeString.length()-1));
										logger.debug("����ֶ�����:" +stypeString.substring(0,stypeString.indexOf("(")));										
									}
								}
								else {
									ziduan.setLength(stypeString.substring(stypeString.indexOf("(")+1,stypeString.length()-1));
									ziduan.setType(stypeString.substring(0,stypeString.indexOf("(")));
									if (stypeString.equalsIgnoreCase("number")||stypeString.equalsIgnoreCase("int")||stypeString.equalsIgnoreCase("decimal")) {
										logger.debug("����ֶ�С��:" +0);
										ziduan.setXiaoshu(0);
									}
									
									logger.debug("����ֶγ���:" +stypeString.substring(stypeString.indexOf("(")+1,stypeString.length()-1));
									logger.debug("����ֶ�����:" +stypeString.substring(0,stypeString.indexOf("(")));				
								}
							}

						}

					}
					if (zdWz.get("����") != null) {
						String numString = rows[zdWz.get("����")];
						if (!numString.isEmpty()) {
							ziduan.setLength(Integer.valueOf(numString));
							logger.debug("����ֶγ���:" + rows[zdWz.get("����")]);
						} else {
							ziduan.setLength(0);
							logger.debug("�������ĵ����ȵ�Ԫ��Ϊ�գ��Զ����ֶγ�������Ϊ0");
						}

					}
					if (zdWz.get("�Ƿ�����") != null) {
						ziduan.setKey(rows[zdWz.get("�Ƿ�����")].equalsIgnoreCase("y")||rows[zdWz.get("�Ƿ�����")].equalsIgnoreCase("��")||rows[zdWz.get("�Ƿ�����")].equalsIgnoreCase("yes"));
						logger.debug("����Ƿ�����:" + rows[zdWz.get("�Ƿ�����")]);

					}
					if (zdWz.get("�Ƿ�Ϊ��") != null) {
						ziduan.setEmtpy(rows[zdWz.get("�Ƿ�Ϊ��")].equalsIgnoreCase("y")||rows[zdWz.get("�Ƿ�Ϊ��")].equalsIgnoreCase("��")||rows[zdWz.get("�Ƿ�Ϊ��")].equalsIgnoreCase("yes"));
						logger.debug("����Ƿ�Ϊ��:" + rows[zdWz.get("�Ƿ�Ϊ��")]);

					}

					if (zdWz.get("С��") != null) {
						String numString = rows[zdWz.get("С��")];
						if (!numString.isEmpty()) {
							ziduan.setXiaoshu(Integer.valueOf(numString));
							logger.debug("����ֶ�С��:" + rows[zdWz.get("С��")]);

						} else {
							ziduan.setXiaoshu(0);
							logger.debug("�������ĵ�С����Ԫ��Ϊ�գ��Զ����ֶ�С������Ϊ0");
						}
					}
					if (zdWz.get("Ĭ��ֵ") != null) {
						ziduan.setDefaultValue(rows[zdWz.get("Ĭ��ֵ")]);
						logger.debug("���Ĭ��ֵ:" + rows[zdWz.get("Ĭ��ֵ")]);

					}
					String oldName = ziduan.getName();
					if (sheetConfig.getZiduanType().equals("��д")) {
						ziduan.setName(ziduan.getName().toUpperCase());
					} else if (sheetConfig.getZiduanType().equals("Сд")) {
						ziduan.setName(ziduan.getName().toLowerCase());
					} else {
					}
					logger.debug("�ֶ�:" + oldName + "ת��Ϊ:" + ziduan.getName());
					ziduans.add(ziduan);
					logger.debug("����ֶ�:" + ziduan);
				}
			}
		}
		logger.debug("���ر�ṹΪ:" + tables);
		logger.debug("���ر���������:����" +tables.size()+"�ű�");
		logger.info("���ر���������:����" +tables.size()+"�ű�");
		if(tables.size()==0) {
			logger.debug("��������sheetҳ�б����Ϊ0,ǿ�ƽ���" );
			return null;
		}
		return tables;

	}
	
	
	public  static  Map<String, Integer> isZiduan(List<ZiduanConfig> list, String[] rows) {
		Map<String, Integer> map = new HashMap<>();
		for (ZiduanConfig config : list) {
			if (config.getKeyName() != null && !config.getKeyName().isEmpty()) {
				boolean is = false;
				for (int i = 0; i < rows.length; i++) {
					if (rows[i] == null || rows[i].isEmpty()) {
						continue;
					}
					if (rows[i].equals(config.getKeyName())) {
						is = true;
						map.put(config.getName(), i);
						break;
					}
				}
				if (!is) {
				//	logger.debug("���ֶβ����ֶ���:"+getArrayStr(rows));
					return null;
				}
			}
		}
	//	logger.debug("���ֶ����ֶ���");

		return map;
	}
	public  static  int strLeng(String[] str) {
		int num = 0;
		for (String s : str) {
			if (s != null)
				num += s.replaceAll(" ", "").trim().length();
		}
		return num;
	}

}
