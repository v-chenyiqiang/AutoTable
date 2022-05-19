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
 * excel��д������
 * @author sun.kai
 * 2016��8��21��
 */
public class POIUtil {

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * ����excel�ļ��������󷵻�
     * @param file
     * @throws IOException
     */
    public static Map<Integer,List<String []>> readExcel(File file,int lastNum) throws IOException{
        //����ļ�
        checkFile(file);
        //���Workbook����������
        Workbook workbook = getWorkBook(file);
        //�������ض��󣬰�ÿ���е�ֵ��Ϊһ�����飬��������Ϊһ�����Ϸ���
        Map<Integer,List<String []>> map = new HashMap<Integer, List<String[]>>();
        if(workbook != null){
            for(int sheetNum = 0;sheetNum < workbook.getNumberOfSheets();sheetNum++){
                //��õ�ǰsheet������
                    List<String[]> re=getStrBySheet(workbook,null,null,sheetNum,lastNum);
                    map.put(sheetNum,re);
            }
        }
        return map;
    }
    public static List<String> getSheet(File file) throws IOException{
    	//����ļ�
        checkFile(file);
        //���Workbook����������
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
        //��õ�ǰsheet�Ŀ�ʼ��
        int firstRowNum  = sheet.getFirstRowNum();
        //��õ�ǰsheet�Ľ�����
        int lastRowNum = sheet.getLastRowNum();
        //ѭ����������
        for(int rowNum = firstRowNum;rowNum <= lastRowNum;rowNum++) {
            //��õ�ǰ��
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
            //��õ�ǰ�еĿ�ʼ��
            int firstCellNum = row.getFirstCellNum();
            //��õ�ǰ�е�����
           int lastCellNum = row.getPhysicalNumberOfCells();
            if (lastNum!=-1){
                lastCellNum=lastNum;
            }
            //int lastCellNum = row.getLastCellNum();
            String[] cells = new String[lastCellNum];
            //ѭ����ǰ��
            for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                Cell cell = row.getCell(cellNum);
                cells[cellNum] = getCellValue(cell);
            }

            list.add(cells);
        }
        return list;

    }
    public static void checkFile(File file) throws IOException{
        //�ж��ļ��Ƿ����
        if(null == file){
            throw new FileNotFoundException("�ļ������ڣ�");
        }
        //����ļ���
        String fileName = file.getName();
        //�ж��ļ��Ƿ���excel�ļ�
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            throw new IOException(fileName + "����excel�ļ�");
        }
    }
    public static Workbook getWorkBook(File file) {
        //����ļ���
        String fileName = file.getName();
        //����Workbook���������󣬱�ʾ����excel
        Workbook workbook = null;
        try {
            //��ȡexcel�ļ���io��
            FileInputStream is=new FileInputStream(file);
            //�����ļ���׺����ͬ(xls��xlsx)��ò�ͬ��Workbookʵ�������
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
        //�����ֵ���String�������������1����1.0�����
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //�ж����ݵ�����
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //����
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //�ַ���
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //��ʽ
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //��ֵ
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //����
                cellValue = "�Ƿ��ַ�";
                break;
            default:
                cellValue = "δ֪����";
                break;
        }
        return cellValue;
    }
}
