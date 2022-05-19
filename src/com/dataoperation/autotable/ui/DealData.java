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
	private static List<String> rightSheetList;//暂时兼容使用
	private static List<String> errorSheetList;//暂时兼容使用

	
	
	
	
	
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
				logger.debug("需要校验的字段不存在该类型:" + string);
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
			logger.error("拼接的sql属性为空,请检查需要验证的属性字段");
			logger.debug("拼接的sql属性为空,请检查需要验证的属性字段");

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
		logger.debug(type + "查询的sql:" + sqlString);
		logger.debug(type + "查询的主键sql:" + sql2);
		sql[0] = sqlString;
		sql[1] = sql2;
		return sql;
	}
	public  static  Map<String, List<TableBean>> dealExcel(File file, Model model) throws IOException {
		Map<String, String> map = model.getSelectSheetMap();
		int number = 0;
		logger.debug("扫描的sheet页配置为:" + map);
		Workbook workbook = POIUtil.getWorkBook(file);
		int num = workbook.getNumberOfSheets();
		Map<String, List<TableBean>> tList = new LinkedHashMap();
		for (String str : map.keySet()) {
			if(str!=null&&str.trim().length()==0) {
				continue;
			}
			SheetConfig sheetConfig = getSheetConfigByName(map.get(str));
			logger.debug("处理" + str + "中");
			if (sheetConfig == null) {
				errorString=str+"的配置文件"+map.get(str)+"不存在";
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
				errorString="解析"+str+"sheet页发现错误";
				logger.error(errorString);
				logger.error(e2);
				errorSheetList.add(str);
				return null;
			}
			tList.put(str, t1);
		}
		logger.debug("处理文件：" + file.getName() + "，解析到的表结构:");
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
		logger.debug("处理的sheet页为:" + sheet.getSheetName());
		logger.debug("对应的sheet配置为:" + sheetConfig);
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
			logger.debug(("检查表锁状态:" + isTable));
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
							 * 做表名合法性校验
							 */
			                 if(!isHF(rows[i+1].trim())) {
									logger.debug("1读取到表名:" + rows[i + 1].trim()+"不合法，已强制结束该sheet页");
									isTable=false;
									isZD = false;
									continue;
			                 }
							
							logger.debug("1读取到表名:" + rows[i + 1].trim());
							logger.debug("开启表锁");
							isTable = true;
							ziduans = new LinkedList<>();
							tableWz = i;
							tableName = rows[i + 1].trim();
							tableName=	sheetConfig.getTableNameUpdate().replace("{tableName}", tableName);
							logger.debug(tableWz);
							String varTableName=tableName;
							if (tableName != null) {
								if (sheetConfig.getTableType().equals("大写")) {
									tableName = tableName.toUpperCase();
								} else if (sheetConfig.getTableType().equals("小写")) {
									tableName = tableName.toLowerCase();
								} else {
								}
								logger.debug("已将" + varTableName + "转换为:" + tableName);
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
						 * 做表名合法性校验
						 */
		                 if(!isHF( varrows[i].trim())) {
								logger.debug("2读取到表名:" + varrows[i].trim()+"不合法，已强制结束该sheet页");
								isTable=false;
								isZD = false;
								continue;
		                 }
						logger.debug("2读取到表名:" + varrows[i].trim());
						logger.debug("开启表锁");
							isTable = true;
							ziduans = new LinkedList<>();
							tableWz = i;
							tableName = varrows[i].trim();
							tableName=	sheetConfig.getTableNameUpdate().replace("{tableName}", tableName);
							if (tableName != null) {
								if (sheetConfig.getTableType().equals("大写")) {
									tableName = tableName.toUpperCase();
								} else if (sheetConfig.getTableType().equals("小写")) {
									tableName = tableName.toLowerCase();
								} else {
								}
								logger.debug("已将" + tableName + "转换为:" + tableName);
							}
							break;
						}
					}
				}
			}
		
			logger.debug("检查是否符合进入匹配列--isTable:(" + isTable + ")--isZD:(" + isZD + ")");
			if (isTable && isZD == false&&sheetConfig.isBegin()) {
				logger.debug("检查到以字段列作为开始符");
				zdWz = isZiduan(ziduanConfigs, rows);
				if (zdWz == null) {
					continue;
				}
				if (zdWz.size() > 0) {
					logger.debug("匹配到字段列,进入匹配字段");
					isZD = true;
					for(String key:zdWz.keySet()) {
						if(zdWz.get(key)>max) {
							max=zdWz.get(key);
						}
					}
				}
			}
			if(isTable && isZD == false&&!sheetConfig.isBegin()) {
				logger.debug("检查到不以字段列作为开始符");

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
					logger.debug("进入匹配字段");
					isZD = true;
			}

			logger.debug("检查是否符合进入匹配字段--isTable(" + isTable + ")--isZD(" + isZD + ")");
			if (isTable && isZD) {
				while (true) {
					k++;
					logger.debug("检查当前列是否大于文档最大列:(" + (k >= list.size()) + ")");
					if ((k >= list.size())) {
						if(ziduans.size()==0) {
							logger.debug("解析到的"+tableName+"页中字段个数为0,强制结束" );
							return null;
						}
						TableBean tableBean = new TableBean();
						tableBean.setName(tableName);
						tableBean.setZiduans(ziduans);
						tableBean.setDatabase(sheetConfig.getDatabase());
						tableBean.setColomn(sheetConfig.getColomn().split(","));
						JSONObject varBZ=new JSONObject();
						varBZ.put("字段数", ziduans.size());
						tableBean.setBeizhu(varBZ);
						tables.add(tableBean);
						logger.debug("添加表结构:" + tableBean);
						break;
					}
					rows = list.get(k);
					if (rows != null) {
						if (isTable && isZD) {
							logger.debug("检查空行结束是否开启:(" + sheetConfig.isUseNull());
				
							if (sheetConfig.isUseNull()) {
								if (rows == null || rows.length == 0 || strLeng(rows) == 0) {
									if(ziduans.size()==0) {
										logger.debug("解析到的"+tableName+"页中字段个数为0,强制结束" );
										return null;
									}
									TableBean tableBean = new TableBean();
									tableBean.setName(tableName);
									tableBean.setZiduans(ziduans);
									tableBean.setDatabase(sheetConfig.getDatabase());
									tableBean.setColomn(sheetConfig.getColomn().split(","));
									JSONObject varBZ=new JSONObject();
									varBZ.put("字段数", ziduans.size());
									tableBean.setBeizhu(varBZ);
									tables.add(tableBean);
									isZD = false;
									isTable = false;
									logger.debug("添加表结构:" + tableBean);
									break;
								} else {
									// System.out.println("不是空行");
								}
							}
						}
					}
					if (rows == null || rows.length == 0) {
						// System.out.println("检查到空行跳过");
						continue;
					}
					if ( tableWz<rows.length&& isTable && isZD && rows[tableWz] != null&& rows[tableWz].equals(tableKey)&&!rows[tableWz+1].trim().equalsIgnoreCase(tableName.trim())&&isHF(rows[tableWz+1].trim())) {
						logger.debug("匹配到新1表名,自动回退1行");
						if(ziduans.size()==0) {
							logger.debug("解析到的"+tableName+"页中字段个数为0,强制结束" );
							return null;
						}
						TableBean tableBean = new TableBean();
						tableBean.setName(tableName);
						tableBean.setZiduans(ziduans);
						tableBean.setDatabase(sheetConfig.getDatabase());
						tableBean.setColomn(sheetConfig.getColomn().split(","));
						JSONObject varBZ=new JSONObject();
						varBZ.put("字段数", ziduans.size());
						tableBean.setBeizhu(varBZ);
						tables.add(tableBean);
						k--;
						isZD = false;
						isTable = false;
						break;
					}
					else if( tableWz<rows.length&&isTable && isZD && rows[tableWz] != null &&!sheetConfig.isBegin()&&!rows[tableWz].isEmpty()&&!rows[tableWz].trim().equalsIgnoreCase(tableName.trim())&&isHF(rows[tableWz].trim())) {
				
						if(ziduans.size()==0) {
		
							logger.debug("解析到的"+tableName+"页中字段个数为0,强制结束" );
							return null;
						}
						logger.debug("匹配到新2表名,自动回退1行");
						TableBean tableBean = new TableBean();
						tableBean.setName(tableName.trim());
						tableBean.setZiduans(ziduans);
						tableBean.setDatabase(sheetConfig.getDatabase());
						tableBean.setColomn(sheetConfig.getColomn().split(","));
						JSONObject varBZ=new JSONObject();
						varBZ.put("字段数", ziduans.size());
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
					logger.debug("创建新字段对象");
					if (zdWz.get("字段名") != null) {
						if(rows[zdWz.get("字段名")].isEmpty()) {
							logger.debug("遇到字段名为空字符串，跳过该字段");
							continue;
						}
						
						/*
						 * 做表字段名合法性校验
						 */
		                 if(!isHF(rows[zdWz.get("字段名")])) {
								logger.debug("1读取到表名:" + rows[zdWz.get("字段名")]+"不合法，已强制结束该sheet页");
								isTable=false;
								isZD = false;
								continue;
		                 }
						
						
						ziduan.setName(rows[zdWz.get("字段名")]);
						logger.debug("添加字段名:" + rows[zdWz.get("字段名")]);
					}
					if (zdWz.get("类型") != null) {
						String stypeString = rows[zdWz.get("类型")];
						ziduan.setType(rows[zdWz.get("类型")]);
						logger.debug("添加字段类型:" + rows[zdWz.get("类型")]);
						if (stypeString != null) {
							stypeString=stypeString.replace("（", "(");
							stypeString=stypeString.replace("）", ")");

							if(stypeString.indexOf("(")!=-1) {
								if(stypeString.indexOf(",")!=-1) {
									if (zdWz.get("长度") == null) {
										logger.debug("解析到长度关键字匹配为空,且类型中有符合长度的信息");
										ziduan.setLength(stypeString.substring(stypeString.indexOf("(")+1,stypeString.indexOf(",")));
										ziduan.setXiaoshu(stypeString.substring(stypeString.indexOf(",")+1, stypeString.length()-1));
										ziduan.setType(stypeString.substring(0,stypeString.indexOf("(")));
										logger.debug("添加字段长度:" +stypeString.substring(stypeString.indexOf("(")+1,stypeString.indexOf(",")));
										logger.debug("添加字段小数:" +stypeString.substring(stypeString.indexOf(",")+1, stypeString.length()-1));
										logger.debug("添加字段类型:" +stypeString.substring(0,stypeString.indexOf("(")));										
									}
								}
								else {
									ziduan.setLength(stypeString.substring(stypeString.indexOf("(")+1,stypeString.length()-1));
									ziduan.setType(stypeString.substring(0,stypeString.indexOf("(")));
									if (stypeString.equalsIgnoreCase("number")||stypeString.equalsIgnoreCase("int")||stypeString.equalsIgnoreCase("decimal")) {
										logger.debug("添加字段小数:" +0);
										ziduan.setXiaoshu(0);
									}
									
									logger.debug("添加字段长度:" +stypeString.substring(stypeString.indexOf("(")+1,stypeString.length()-1));
									logger.debug("添加字段类型:" +stypeString.substring(0,stypeString.indexOf("(")));				
								}
							}

						}

					}
					if (zdWz.get("长度") != null) {
						String numString = rows[zdWz.get("长度")];
						if (!numString.isEmpty()) {
							ziduan.setLength(Integer.valueOf(numString));
							logger.debug("添加字段长度:" + rows[zdWz.get("长度")]);
						} else {
							ziduan.setLength(0);
							logger.debug("解析到文档长度单元格为空，自动将字段长度设置为0");
						}

					}
					if (zdWz.get("是否主键") != null) {
						ziduan.setKey(rows[zdWz.get("是否主键")].equalsIgnoreCase("y")||rows[zdWz.get("是否主键")].equalsIgnoreCase("是")||rows[zdWz.get("是否主键")].equalsIgnoreCase("yes"));
						logger.debug("添加是否主键:" + rows[zdWz.get("是否主键")]);

					}
					if (zdWz.get("是否为空") != null) {
						ziduan.setEmtpy(rows[zdWz.get("是否为空")].equalsIgnoreCase("y")||rows[zdWz.get("是否为空")].equalsIgnoreCase("是")||rows[zdWz.get("是否为空")].equalsIgnoreCase("yes"));
						logger.debug("添加是否为空:" + rows[zdWz.get("是否为空")]);

					}

					if (zdWz.get("小数") != null) {
						String numString = rows[zdWz.get("小数")];
						if (!numString.isEmpty()) {
							ziduan.setXiaoshu(Integer.valueOf(numString));
							logger.debug("添加字段小数:" + rows[zdWz.get("小数")]);

						} else {
							ziduan.setXiaoshu(0);
							logger.debug("解析到文档小数单元格为空，自动将字段小数设置为0");
						}
					}
					if (zdWz.get("默认值") != null) {
						ziduan.setDefaultValue(rows[zdWz.get("默认值")]);
						logger.debug("添加默认值:" + rows[zdWz.get("默认值")]);

					}
					String oldName = ziduan.getName();
					if (sheetConfig.getZiduanType().equals("大写")) {
						ziduan.setName(ziduan.getName().toUpperCase());
					} else if (sheetConfig.getZiduanType().equals("小写")) {
						ziduan.setName(ziduan.getName().toLowerCase());
					} else {
					}
					logger.debug("字段:" + oldName + "转化为:" + ziduan.getName());
					ziduans.add(ziduan);
					logger.debug("添加字段:" + ziduan);
				}
			}
		}
		logger.debug("返回表结构为:" + tables);
		logger.debug("返回表数据内容:存在" +tables.size()+"张表");
		logger.info("返回表数据内容:存在" +tables.size()+"张表");
		if(tables.size()==0) {
			logger.debug("解析到的sheet页中表个数为0,强制结束" );
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
				//	logger.debug("该字段不是字段列:"+getArrayStr(rows));
					return null;
				}
			}
		}
	//	logger.debug("该字段是字段列");

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
