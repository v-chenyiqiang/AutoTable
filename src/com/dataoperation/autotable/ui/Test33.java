package com.dataoperation.autotable.ui;

import java.awt.EventQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;

public class Test33 {
	public static boolean isContainChinese(String str) {
		 Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		 Matcher m = p.matcher(str);
		 if (m.find()) {
		  return true;
		 }
		 return false;
		}
	public static void main(String[] args) {
		System.out.println(isContainChinese("o3"));
		System.out.println(isContainChinese("o_3s_3_"));
		System.out.println(isContainChinese("OQ_3"));
		System.out.println(isContainChinese("c²é1o3"));
	}
	
}
