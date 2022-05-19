package com.dataoperation.autotable.utils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONObject;
import com.dataoperation.autotable.bean.Ziduan;
import com.dataoperation.autotable.excel.POIUtil;

public class HSTYPE {
	public static Map<String, String> getMap1(){
		String relativelyPath=System.getProperty("user.dir"); 
		File  file= new File(relativelyPath+"/恒生字段类型模板.xls");
		if(!file.exists()) {
			return null;
		}
		Workbook wk=POIUtil.getWorkBook(file);
		Sheet sheet=wk.getSheet("标准字段");
		List<String[]>list=POIUtil.getStrBySheet(sheet);
		int[] wz=new int[2];
		wz[0]=-1;
		wz[1]=-1;
		boolean isBegin=false;
		Map<String,String> map=new LinkedHashMap<>();
		for(int i=0;i<list.size();i++) {
			String[] rows=list.get(i);
			if(rows==null||rows.length==0) {
				continue;
			}
			for(int j=0;j<rows.length;j++) {
				String row=rows[j];
				if(row==null||row.isEmpty()) {
					continue;
				}
				if(row.equals("字段名")) {
					wz[0]=j;
				}
				else if(row.equals("字段类型")) {
					wz[1]=j;
				}
				else {
					continue;
				}
				if(wz[0]>-1&&wz[1]>-1) {
					isBegin=true;
				
					break;
				}
			}
			if(isBegin) {
				while(true) {
					i++;
					if(i<list.size()) {
					rows=list.get(i);
					}
					else {
						break;
					}
					if(rows==null||rows.length<wz[1]) {
						continue;
					}
					map.put(rows[wz[0]], rows[wz[1]]);
				}
			}
		}
		return map;
	}
	
	public static  Map<String, String> getMap2(String type) {
		String relativelyPath=System.getProperty("user.dir"); 
		File  file= new File(relativelyPath+"/恒生字段类型模板.xls");
		if(!file.exists()) {
			return null;
		}
		Workbook wk=POIUtil.getWorkBook(file);
		Sheet sheet=wk.getSheet("标准数据类型");
		List<String[]>list=POIUtil.getStrBySheet(sheet);
		int[] wz=new int[2];
		wz[0]=-1;
		wz[1]=-1;
		boolean isBegin=false;
		Map<String,String> map=new LinkedHashMap<>();
		for(int i=0;i<list.size();i++) {
			String[] rows=list.get(i);
			if(rows==null||rows.length==0) {
				continue;
			}
			for(int j=0;j<rows.length;j++) {
				String row=rows[j];
				if(row==null||row.isEmpty()) {
					continue;
				}
				if(row.equals("类型名")) {
					wz[0]=j;
				}
				else if(row.equalsIgnoreCase(type)) {
					wz[1]=j;
				}
				else {
					continue;
				}
				if(wz[0]>-1&&wz[1]>-1) {
					isBegin=true;
				
					break;
				}
			}
			if(isBegin) {
				while(true) {
					i++;
					if(i<list.size()) {
					rows=list.get(i);
					}
					else {
						break;
					}
					if(rows==null||rows.length<wz[1]) {
						continue;
					}
					map.put(rows[wz[0]], rows[wz[1]]);
				}
			}
			}
			return map;
			
	}
	
	public static List<Ziduan> getList(){
		String relativelyPath=System.getProperty("user.dir"); 
		File  file= new File(relativelyPath+"/恒生字段类型模板.xls");
		if(!file.exists()) {
			return null;
		}
		Workbook wk=POIUtil.getWorkBook(file);
		Sheet sheet=wk.getSheet("业务数据类型");
		List<String[]>list=POIUtil.getStrBySheet(sheet);
		int[] wz=new int[4];
		wz[0]=-1;
		wz[1]=-1;
		wz[2]=-1;
		wz[3]=-1;
		boolean isBegin=false;
		List<Ziduan> zds=new LinkedList<>();
		for(int i=0;i<list.size();i++) {
			String[] rows=list.get(i);
			if(rows==null||rows.length==0) {
				continue;
			}
			for(int j=0;j<rows.length;j++) {
				String row=rows[j];
				if(row==null||row.isEmpty()) {
					continue;
				}
				if(row.equals("类型名")) {
					wz[0]=j;
				}
				else if(row.equalsIgnoreCase("标准类型")) {
					wz[1]=j;
				}
				else if(row.equalsIgnoreCase("长度")) {
					wz[2]=j;
				}
				else if(row.equalsIgnoreCase("精度")) {
					wz[3]=j;
				}
				else {
					continue;
				}
				if(wz[0]>-1&&wz[1]>-1&&wz[2]>-1&&wz[3]>-1) {
					isBegin=true;
				
					break;
				}
			}
			if(isBegin) {
				while(true) {
					i++;
					if(i<list.size()) {
					rows=list.get(i);
					}
					else {
						break;
					}
					if(rows==null||rows.length<wz[3]) {
						continue;
					}
					Ziduan ziduan=new Ziduan();
					ziduan.setName(rows[wz[0]]);
					ziduan.setType(rows[wz[1]]);
					ziduan.setLength(rows[wz[2]]);
					ziduan.setXiaoshu(rows[wz[3]]);
					zds.add(ziduan);
				}
			}
			}
		return zds;
	}
	public static List<Ziduan> getFileZiduan(String type) {

		List<Ziduan> list=getList();
		Map<String, String> map=getMap2(type);
		for(int i=0;i<list.size();i++) {
			Ziduan zd=list.get(i);
			for(String key:map.keySet()) {
				if(key.equals(zd.getType())) {
					zd.setType(map.get(key));
					list.add(zd);
					break;
				}
			}
		}
		
		return list;
	}
	



}
