package com.dataoperation.autotable.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class FileUtils {
	public static String getStr(File file) throws IOException {
		if(file==null) {
			return null;
		}
		BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
		String lineString=null;
		StringBuilder builder=new StringBuilder();
		while((lineString=bufferedReader.readLine())
				!=null) {
			builder.append(lineString);
		}
		return builder.toString();
	}
	public static void writer(File file,String str) throws IOException {
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8")));
		out.write(str);
		out.flush();
		out.close();
	}

}
