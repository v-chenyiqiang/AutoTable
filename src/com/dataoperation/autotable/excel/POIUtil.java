package com.dataoperation.autotable.excel;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * excel读写工具类
 * @author sun.kai
 * 2016年8月21日
 */
public class POIUtil {

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * 读入excel文件，解析后返回
     * @param file
     * @throws IOException
     */
    public static Map<Integer,List<String []>> readExcel(File file,int lastNum) throws IOException{
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        Map<Integer,List<String []>> map = new HashMap<Integer, List<String[]>>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //获得当前sheet工作表
                    List<String[]> re=getStrBySheet(workbook,null,null,sheetNum,lastNum);
                    map.put(sheetNum,re);
            }
        }
        return map;
    }
    public static List<String> getSheet(File file) throws IOException{
    	//检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        int num=workbook.getNumberOfSheets();
        List<String> list=new LinkedList<>();
        for(int i=0;i<num;i++) {
        	list.add(workbook.getSheetName(i));
        }
        return list;
    }
    public static Map<Integer,List<String []>> readExcel(File file) throws IOException{
        return readExcel(file,-1);
    }
    public static List<String[]> getStrBySheet(File file,int sheetNum){
    	Workbook workbook=getWorkBook(file);
    	return getStrBySheet(workbook, sheetNum);
    }
    public static List<String[]> getStrBySheet(Sheet sheet){
       return getStrBySheet(null,sheet,null,-1,-1);
    }
    public static List<String[]> getStrBySheet(Workbook workbook,int sheetNum){
       return getStrBySheet(workbook,null,null,sheetNum,-1);
    }

    public static List<String[]> getStrBySheet(Workbook workbook,String sheetName){
    	return getStrBySheet(workbook,null,sheetName,-1,-1);
    }

    public static List<String[]> getStrBySheet(Workbook workbook,Sheet sheet,String sheetName,int sheetNum,int lastNum){
    	if(sheetName!=null) {
    		sheet=workbook.getSheet(sheetName);
    	}
		else {
			if (workbook != null) {
				sheet = workbook.getSheetAt(sheetNum);
			} else {
				sheet = sheet;
			}
		}
        if(sheet == null){
            return null;
        }
        List<String[]> list = new ArrayList<String[]>();
        //获得当前sheet的开始行
        int firstRowNum  = sheet.getFirstRowNum();
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        //循环的所有行
        for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++) {
            //获得当前行
            Row row = sheet.getRow(rowNum);
            if(row==null) {
            	list.add(null);
            	continue;
            }
            
            if (row.getPhysicalNumberOfCells() == 0) {
            	String[] cells=new String[0];
            	list.add(cells);
                continue;
            }
            //获得当前行的开始列
            int firstCellNum = row.getFirstCellNum();
            //获得当前行的列数
           int lastCellNum = row.getPhysicalNumberOfCells();
            if (lastNum!=-1){
                lastCellNum=lastNum;
            }
            //int lastCellNum = row.getLastCellNum();
            String[] cells = new String[lastCellNum];
            //循环当前行
            for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                Cell cell = row.getCell(cellNum);
                cells[cellNum] = getCellValue(cell);
            }

            list.add(cells);
        }
        return list;

    }
    public static void checkFile(File file) throws IOException{
        //判断文件是否存在
        if(null == file){
            throw new FileNotFoundException("文件不存在！");
        }
        //获得文件名
        String fileName = file.getName();
        //判断文件是否是excel文件
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "不是excel文件");
        }
    }
    public static Workbook getWorkBook(File file) {
        //获得文件名
        String fileName = file.getName();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            FileInputStream is=new FileInputStream(file);
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
           e.printStackTrace();
        }
        return workbook;
    }
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
}
