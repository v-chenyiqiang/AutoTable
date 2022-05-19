package com.dataoperation.autotable.ui;

import java.awt.EventQueue;
import java.awt.datatransfer.StringSelection;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataoperation.autotable.bean.DatabaConnectBean;
import com.dataoperation.autotable.bean.Model;
import com.dataoperation.autotable.bean.Result;
import com.dataoperation.autotable.bean.SheetConfig;
import com.dataoperation.autotable.bean.TableBean;
import com.dataoperation.autotable.bean.Ziduan;
import com.dataoperation.autotable.bean.ZiduanConfig;
import com.dataoperation.autotable.excel.POIUtil;
import com.dataoperation.autotable.jdbc.JDBC;
import com.dataoperation.autotable.jdbc.MYJDBC;
import com.dataoperation.autotable.utils.FileUtils;
import com.dataoperation.autotable.utils.HSTYPE;

import oracle.net.aso.m;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

import java.awt.FlowLayout;
import java.awt.BorderLayout;

import javax.sql.rowset.BaseRowSet;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

public class UI {

	private static Logger logger = LogManager.getLogger(UI.class.getName());

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JLabel lblNewLabel_2;
	private Map<String, String> sheetNames;
	private JPanel panel_1_2;
	private JCheckBox chckbxNewCheckBox_1;
	private JCheckBox chckbxNewCheckBox_1_1;
	private JTextField textField_22;
	private MultiComboSelectBox multiComboBox_1;
	private File systemFile;
	private JComboBox comboBox_1;
	private JLabel lblNewLabel_17;
	private JComboBox comboBox_2;
	private SwingWorker excelWorker = null;
	private JLabel lblNewLabel_17_1_1_1_2;
	private JTextField textField_23;
	private JTextField textField_24;
	private MultiComboBox multiComboBox_3;
	private List<TableBean> tableBeans;
	private MultiComboSelectBox2 multiComboBox_2;
	private Map<String, JSONObject> resultMap;
	private SwingWorker bd = null;
	private Map<String, JSONObject> errorTableMap;
	private Map<String, JSONObject> rightTableMap;
	private JComboBox comboBox_6;
	private JComboBox comboBox_6_1;
	private JTextField textField_15;
	private JTextField textField_16;
	private JTextField textField_17;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_18;
	private JComboBox comboBox_3;
	private JComboBox comboBox;
	private JLabel lblNewLabel_13;
	private int alHasNum = 0;
	private int useTime = 0;
	private JTextField textField_21;
	private 	JComboBox comboBox_7;
	private JCheckBox chckbxNewCheckBox_3;
	private JCheckBox chckbxNewCheckBox;
	private Map<String, List<TableBean>> lBeans;
	private JTextField txttablename;
	private static ResultUI resultUI;
	private static UI window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 window = new UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UI() {
		try {
			String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception ex) {
			logger.warn("动调整外观样式失败");
			logger.warn(ex);
		}
		
		initialize();
	}
	public void setVisible(boolean isShown) {
		window.frame.setVisible(isShown);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		logger.info("初始化文件中....");
		initByWindows();
		logger.info("文件初始化完成");
		int screenWidth = ((int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().width);
		int screenHeight = ((int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
		frame = new JFrame();
		frame.setBounds(100, 100, screenWidth * 7 / 8, screenHeight * 7 / 8);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Object[] vObjects = new Object[] { "name", "type", "length", "isKey", "xiaoshu", "isEmtpy", "defaultValue" };
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_1_3 = new JPanel();
		panel.add(panel_1_3, BorderLayout.CENTER);
		panel_1_3.setForeground(Color.GRAY);
		panel_1_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_66 = new JPanel();
		panel_1_3.add(panel_66, BorderLayout.NORTH);
		panel_66.setLayout(new BorderLayout(0, 0));

		JPanel panel_72 = new JPanel();
		panel_66.add(panel_72, BorderLayout.NORTH);

		JLabel lblNewLabel_4 = new JLabel("Sheet\u9875\u6A21\u677F\u914D\u7F6E");
		panel_72.add(lblNewLabel_4);
		lblNewLabel_4.setForeground(Color.BLACK);
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 18));

		JPanel panel_67 = new JPanel();
		panel_66.add(panel_67);
		panel_67.setLayout(new BorderLayout(0, 0));

		JPanel panel_68 = new JPanel();
		panel_67.add(panel_68, BorderLayout.WEST);

		JLabel lblNewLabel_22 = new JLabel("\u5F53\u524D\u6A21\u677F:");
		panel_68.add(lblNewLabel_22);

		comboBox_1 = new JComboBox(new Object[] { "" });
		panel_68.add(comboBox_1);

		JPanel panel_69 = new JPanel();
		panel_67.add(panel_69, BorderLayout.EAST);

		JLabel lblNewLabel_20 = new JLabel("--------");
		panel_69.add(lblNewLabel_20);

		JCheckBox chckbxNewCheckBox_4 = new JCheckBox("\u4FEE\u6539sheet\u6A21\u677F\u540D");
		panel_69.add(chckbxNewCheckBox_4);

		textField_22 = new JTextField();
		panel_69.add(textField_22);
		textField_22.setEditable(false);
		textField_22.setColumns(18);

		JButton btnNewButton_3 = new JButton("\u4FDD\u5B58sheet\u914D\u7F6E");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Result result=clickSheetButton();
				alert(result.getMsg(), result.getCode());
			}
		});
		panel_69.add(btnNewButton_3);

		JButton btnNewButton_8 = new JButton("\u65B0\u5EFA\u6A21\u677F");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckbxNewCheckBox_4.setSelected(true);
				textField_22.setText("");
				textField_22.setEditable(true);
				chckbxNewCheckBox_3.setSelected(true);
				textField_21.setEditable(true);
				try {
					setSheetConfig(null);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					logger.error("加载sheet配置文件发生错误", e1);
				}
			}
		});
		panel_69.add(btnNewButton_8);
		chckbxNewCheckBox_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_22.setEditable(chckbxNewCheckBox_4.isSelected());

			}
		});
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox com=(JComboBox) e.getSource();
				String varName = comboBox_1.getSelectedItem().toString();
				comboBox_7.setModel(new DefaultComboBoxModel(getConnectBeanFromFile()));
				if (varName.isEmpty()) {
					alert("模板为空", 0);
				}
				File file = getSheetConfigFileByName(varName);
				if (file == null) {
					alert("模板不存在,检查是否被删除了-path:" + file.getAbsolutePath(), 0);
				}
				;
				try {
					dealSheetConfig(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					logger.error("切换并且解析sheet模板配置文件出错", e1);
				}


			}
		});

		JPanel panel_70 = new JPanel();
		panel_1_3.add(panel_70, BorderLayout.CENTER);
		panel_70.setLayout(new BorderLayout(0, 0));

		JPanel panel_3 = new JPanel();
		panel_70.add(panel_3, BorderLayout.NORTH);
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_3.setLayout(new BorderLayout(0, 0));

		JPanel panel_59 = new JPanel();
		panel_3.add(panel_59, BorderLayout.NORTH);

		JLabel lblNewLabel_8 = new JLabel("\u8868\u540D\u914D\u7F6E");
		panel_59.add(lblNewLabel_8);
		lblNewLabel_8.setFont(new Font("宋体", Font.PLAIN, 16));

		JPanel panel_60 = new JPanel();
		panel_3.add(panel_60, BorderLayout.CENTER);
		panel_60.setLayout(new BorderLayout(0, 0));

		JPanel panel_61 = new JPanel();
		panel_60.add(panel_61, BorderLayout.WEST);

		JPanel panel_62 = new JPanel();
		panel_61.add(panel_62);

		JLabel lblNewLabel_6 = new JLabel("\u7B2C\u51E0\u5217\u5355\u5143\u683C");
		panel_62.add(lblNewLabel_6);

		textField_1 = new JTextField();
		panel_62.add(textField_1);
		textField_1.setColumns(10);

		JPanel panel_63 = new JPanel();
		panel_61.add(panel_63);

		JLabel lblNewLabel_7 = new JLabel("\u5173\u952E\u5B57\u5339\u914D:");
		panel_63.add(lblNewLabel_7);

		textField_2 = new JTextField();
		panel_63.add(textField_2);
		textField_2.setColumns(10);

		JPanel panel_64 = new JPanel();
		panel_60.add(panel_64, BorderLayout.EAST);

		chckbxNewCheckBox_1 = new JCheckBox("\u7A7A\u884C\u4F5C\u4E3A\u7ED3\u675F\u7B26");
		chckbxNewCheckBox_1.setSelected(true);
		panel_64.add(chckbxNewCheckBox_1);

		chckbxNewCheckBox_1_1 = new JCheckBox("\u9047\u5230\u65B0\u8868\u4F5C\u4E3A\u7ED3\u675F\u7B26");
		panel_64.add(chckbxNewCheckBox_1_1);
		chckbxNewCheckBox_1_1.setSelected(true);

		JPanel panel_71 = new JPanel();
		panel_70.add(panel_71, BorderLayout.CENTER);
		panel_71.setLayout(new BorderLayout(5, 5));

		JPanel panel_3_1 = new JPanel();
		panel_71.add(panel_3_1, BorderLayout.CENTER);
		panel_3_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_3_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_106 = new JPanel();
		panel_3_1.add(panel_106, BorderLayout.CENTER);
		panel_106.setLayout(new BorderLayout(0, 0));

		JPanel panel_21 = new JPanel();
		panel_106.add(panel_21, BorderLayout.NORTH);
		panel_21.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_112 = new JPanel();
		panel_21.add(panel_112, BorderLayout.CENTER);

		JLabel lblNewLabel_8_1 = new JLabel("\u5B57\u6BB5\u914D\u7F6E");
		panel_112.add(lblNewLabel_8_1);
		lblNewLabel_8_1.setFont(new Font("宋体", Font.PLAIN, 16));
		
		JPanel panel_100 = new JPanel();
		panel_21.add(panel_100, BorderLayout.EAST);
		
		 chckbxNewCheckBox = new JCheckBox("\u5B57\u6BB5\u5217\u4F5C\u4E3A\u5F00\u59CB\u7B26");
		chckbxNewCheckBox.setSelected(true);
		panel_100.add(chckbxNewCheckBox);

		JPanel panel_22 = new JPanel();
		panel_106.add(panel_22);
		panel_22.setLayout(new GridLayout(2, 4, 5, 5));

		JPanel panel_4 = new JPanel();
		panel_22.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_23 = new JPanel();
		panel_23.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4.add(panel_23);
		panel_23.setLayout(new BorderLayout(0, 0));

		JPanel panel_24 = new JPanel();
		panel_23.add(panel_24, BorderLayout.NORTH);

		JLabel lblNewLabel_5 = new JLabel("\u5B57\u6BB5\u540D");
		panel_24.add(lblNewLabel_5);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_25 = new JPanel();
		panel_23.add(panel_25, BorderLayout.CENTER);
		panel_25.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9 = new JLabel("\u5217\u5355\u5143\u683C");
		panel_25.add(lblNewLabel_9);

		textField_3 = new JTextField();
		textField_3.setEnabled(false);
		panel_25.add(textField_3);
		textField_3.setColumns(12);

		JPanel panel_26 = new JPanel();
		panel_23.add(panel_26, BorderLayout.SOUTH);
		panel_26.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_1 = new JLabel("\u5173\u952E\u5339\u914D");
		lblNewLabel_9_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_26.add(lblNewLabel_9_1);

		textField_4 = new JTextField();
		panel_26.add(textField_4);
		textField_4.setColumns(12);

		JPanel panel_4_1 = new JPanel();
		panel_22.add(panel_4_1);
		panel_4_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_45 = new JPanel();
		panel_45.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4_1.add(panel_45);
		panel_45.setLayout(new BorderLayout(0, 0));

		JPanel panel_27 = new JPanel();
		panel_45.add(panel_27, BorderLayout.NORTH);

		JLabel lblNewLabel_5_1 = new JLabel("\u7C7B\u578B");
		panel_27.add(lblNewLabel_5_1);
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_28 = new JPanel();
		panel_45.add(panel_28, BorderLayout.CENTER);
		panel_28.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_2 = new JLabel("\u5217\u5355\u5143\u683C");
		panel_28.add(lblNewLabel_9_2);

		textField_5 = new JTextField();
		textField_5.setEnabled(false);
		panel_28.add(textField_5);
		textField_5.setColumns(12);

		JPanel panel_29 = new JPanel();
		panel_45.add(panel_29, BorderLayout.SOUTH);
		panel_29.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_1_1 = new JLabel("\u5173\u952E\u5339\u914D");
		panel_29.add(lblNewLabel_9_1_1);

		textField_6 = new JTextField();
		panel_29.add(textField_6);
		textField_6.setColumns(12);

		JPanel panel_4_1_1 = new JPanel();
		panel_22.add(panel_4_1_1);
		panel_4_1_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_46 = new JPanel();
		panel_46.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4_1_1.add(panel_46);
		panel_46.setLayout(new BorderLayout(0, 0));

		JPanel panel_30 = new JPanel();
		panel_46.add(panel_30, BorderLayout.NORTH);

		JLabel lblNewLabel_5_1_1 = new JLabel("\u957F\u5EA6");
		panel_30.add(lblNewLabel_5_1_1);
		lblNewLabel_5_1_1.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_31 = new JPanel();
		panel_46.add(panel_31, BorderLayout.CENTER);
		panel_31.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_2_1 = new JLabel("\u5217\u5355\u5143\u683C");
		panel_31.add(lblNewLabel_9_2_1);

		textField_7 = new JTextField();
		textField_7.setEnabled(false);
		panel_31.add(textField_7);
		textField_7.setColumns(12);

		JPanel panel_32 = new JPanel();
		panel_46.add(panel_32, BorderLayout.SOUTH);
		panel_32.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_1_1_1 = new JLabel("\u5173\u952E\u5339\u914D");
		panel_32.add(lblNewLabel_9_1_1_1);

		textField_8 = new JTextField();
		panel_32.add(textField_8);
		textField_8.setColumns(12);

		JPanel panel_4_1_1_1_2 = new JPanel();
		panel_22.add(panel_4_1_1_1_2);
		panel_4_1_1_1_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_47 = new JPanel();
		panel_47.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4_1_1_1_2.add(panel_47);
		panel_47.setLayout(new BorderLayout(0, 0));

		JPanel panel_33 = new JPanel();
		panel_47.add(panel_33, BorderLayout.NORTH);

		JLabel lblNewLabel_5_1_1_1_2 = new JLabel("\u9ED8\u8BA4\u503C");
		panel_33.add(lblNewLabel_5_1_1_1_2);
		lblNewLabel_5_1_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_34 = new JPanel();
		panel_47.add(panel_34, BorderLayout.CENTER);
		panel_34.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_2_1_1_2 = new JLabel("\u5217\u5355\u5143\u683C");
		panel_34.add(lblNewLabel_9_2_1_1_2);

		textField_13 = new JTextField();
		textField_13.setEnabled(false);
		panel_34.add(textField_13);
		textField_13.setColumns(12);

		JPanel panel_35 = new JPanel();
		panel_47.add(panel_35, BorderLayout.SOUTH);
		panel_35.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_1_1_1_1_2 = new JLabel("\u5173\u952E\u5339\u914D");
		panel_35.add(lblNewLabel_9_1_1_1_1_2);

		textField_14 = new JTextField();
		panel_35.add(textField_14);
		textField_14.setColumns(12);

		JPanel panel_4_1_1_1_2_1 = new JPanel();
		panel_22.add(panel_4_1_1_1_2_1);
		panel_4_1_1_1_2_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_48 = new JPanel();
		panel_48.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4_1_1_1_2_1.add(panel_48);
		panel_48.setLayout(new BorderLayout(0, 0));

		JPanel panel_36 = new JPanel();
		panel_48.add(panel_36, BorderLayout.NORTH);

		JLabel lblNewLabel_5_1_1_1_2_1 = new JLabel("\u662F\u5426\u4E3B\u952E");
		panel_36.add(lblNewLabel_5_1_1_1_2_1);
		lblNewLabel_5_1_1_1_2_1.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_37 = new JPanel();
		panel_48.add(panel_37, BorderLayout.CENTER);
		panel_37.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_2_1_1_2_1 = new JLabel("\u5217\u5355\u5143\u683C");
		panel_37.add(lblNewLabel_9_2_1_1_2_1);

		textField_23 = new JTextField();
		textField_23.setEnabled(false);
		panel_37.add(textField_23);
		textField_23.setColumns(12);

		JPanel panel_38 = new JPanel();
		panel_48.add(panel_38, BorderLayout.SOUTH);
		panel_38.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_1_1_1_1_2_1 = new JLabel("\u5173\u952E\u5339\u914D");
		panel_38.add(lblNewLabel_9_1_1_1_1_2_1);

		textField_24 = new JTextField();
		panel_38.add(textField_24);
		textField_24.setColumns(12);

		JPanel panel_4_1_1_1 = new JPanel();
		panel_22.add(panel_4_1_1_1);
		panel_4_1_1_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_49 = new JPanel();
		panel_49.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4_1_1_1.add(panel_49);
		panel_49.setLayout(new BorderLayout(0, 0));

		JPanel panel_39 = new JPanel();
		panel_49.add(panel_39, BorderLayout.NORTH);

		JLabel lblNewLabel_5_1_1_1 = new JLabel("\u5C0F\u6570");
		panel_39.add(lblNewLabel_5_1_1_1);
		lblNewLabel_5_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_40 = new JPanel();
		panel_49.add(panel_40, BorderLayout.CENTER);
		panel_40.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_2_1_1 = new JLabel("\u5217\u5355\u5143\u683C");
		panel_40.add(lblNewLabel_9_2_1_1);

		textField_9 = new JTextField();
		textField_9.setEnabled(false);
		panel_40.add(textField_9);
		textField_9.setColumns(12);

		JPanel panel_41 = new JPanel();
		panel_49.add(panel_41, BorderLayout.SOUTH);
		panel_41.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_1_1_1_1 = new JLabel("\u5173\u952E\u5339\u914D");
		panel_41.add(lblNewLabel_9_1_1_1_1);

		textField_10 = new JTextField();
		panel_41.add(textField_10);
		textField_10.setColumns(12);

		JPanel panel_4_1_1_1_1 = new JPanel();
		panel_22.add(panel_4_1_1_1_1);
		panel_4_1_1_1_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_50 = new JPanel();
		panel_50.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4_1_1_1_1.add(panel_50);
		panel_50.setLayout(new BorderLayout(0, 0));

		JPanel panel_42 = new JPanel();
		panel_50.add(panel_42, BorderLayout.NORTH);

		JLabel lblNewLabel_5_1_1_1_1 = new JLabel("\u662F\u5426\u4E3A\u7A7A");
		panel_42.add(lblNewLabel_5_1_1_1_1);
		lblNewLabel_5_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_43 = new JPanel();
		panel_50.add(panel_43, BorderLayout.WEST);
		panel_43.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_2_1_1_1 = new JLabel("\u5217\u5355\u5143\u683C");
		panel_43.add(lblNewLabel_9_2_1_1_1);

		textField_11 = new JTextField();
		textField_11.setEnabled(false);
		panel_43.add(textField_11);
		textField_11.setColumns(12);

		JPanel panel_44 = new JPanel();
		panel_50.add(panel_44, BorderLayout.SOUTH);
		panel_44.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_9_1_1_1_1_1 = new JLabel("\u5173\u952E\u5339\u914D");
		panel_44.add(lblNewLabel_9_1_1_1_1_1);

		textField_12 = new JTextField();
		panel_44.add(textField_12);
		textField_12.setColumns(12);

		JPanel panel_8 = new JPanel();
		panel_106.add(panel_8, BorderLayout.EAST);
		panel_8.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_8.setLayout(new BorderLayout(0, 0));

		JPanel panel_51 = new JPanel();
		panel_8.add(panel_51, BorderLayout.NORTH);

		JLabel lblNewLabel_37 = new JLabel("\u9700\u8981\u6BD4\u5BF9\u7684\u5B57\u6BB5");
		panel_51.add(lblNewLabel_37);

		multiComboBox_3 = new MultiComboBox(vObjects);
		panel_51.add(multiComboBox_3);

		JPanel panel_52 = new JPanel();
		panel_8.add(panel_52, BorderLayout.CENTER);
		panel_52.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_54 = new JPanel();
		panel_52.add(panel_54);
		panel_54.setLayout(new BoxLayout(panel_54, BoxLayout.Y_AXIS));

		JPanel panel_56 = new JPanel();
		panel_54.add(panel_56);

		JLabel lblNewLabel_27 = new JLabel("\u8868\u540D\u5927\u5C0F\u5199:");
		panel_56.add(lblNewLabel_27);

		comboBox_6 = new JComboBox();
		panel_56.add(comboBox_6);
		comboBox_6.setModel(new DefaultComboBoxModel(new String[] { "\u9ED8\u8BA4", "\u5927\u5199", "\u5C0F\u5199" }));

		JPanel panel_57 = new JPanel();
		panel_54.add(panel_57);
		panel_57.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_105 = new JPanel();
		panel_57.add(panel_105);

		JLabel lblNewLabel_27_1 = new JLabel("\u5B57\u6BB5\u5927\u5C0F\u5199:");
		panel_105.add(lblNewLabel_27_1);

		comboBox_6_1 = new JComboBox();
		panel_105.add(comboBox_6_1);
		comboBox_6_1
				.setModel(new DefaultComboBoxModel(new String[] { "\u9ED8\u8BA4", "\u5927\u5199", "\u5C0F\u5199" }));
		
		JPanel panel_115 = new JPanel();
		panel_54.add(panel_115);
		
		JLabel lblNewLabel_49 = new JLabel("\u8868\u540D\u66F4\u6B63:");
		panel_115.add(lblNewLabel_49);
		
		txttablename = new JTextField();
		txttablename.setText("{tableName}");
		panel_115.add(txttablename);
		txttablename.setColumns(17);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_1_1 = new JPanel();
		panel_1.add(panel_1_1, BorderLayout.NORTH);
		panel_1_1.setForeground(Color.GRAY);
		panel_1_1.setBorder(new LineBorder(Color.GRAY));
		panel_1_1.setLayout(new BorderLayout(0, 0));

		JPanel panel_84 = new JPanel();
		panel_1_1.add(panel_84, BorderLayout.WEST);

		JLabel lblNewLabel = new JLabel("\u5F53\u524D\u6587\u4EF6:");
		panel_84.add(lblNewLabel);

		textField = new JTextField();
		panel_84.add(textField);
		textField.setEnabled(false);
		textField.setColumns(40);

		JButton btnNewButton = new JButton("\u9009\u62E9\u6587\u4EF6");
		panel_84.add(btnNewButton);

		JPanel panel_85 = new JPanel();
		panel_1_1.add(panel_85, BorderLayout.CENTER);
		
		JButton btnNewButton_9 = new JButton("\u67E5\u770B\u7ED3\u679C\u8868");
		panel_85.add(btnNewButton_9);
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(resultUI==null) {
					 resultUI = new ResultUI(window);
				}
				resultUI.setVisible(true);
				setVisible(false);
			}
		});

		panel_1_2 = new JPanel();
		panel_1.add(panel_1_2);
		panel_1_2.setForeground(Color.GRAY);
		panel_1_2.setBorder(new LineBorder(Color.GRAY));
		panel_1_2.setLayout(new BorderLayout(0, 0));

		JPanel panel_65 = new JPanel();
		panel_1_2.add(panel_65, BorderLayout.NORTH);
		panel_65.setLayout(new BorderLayout(0, 0));

		JPanel panel_73 = new JPanel();
		panel_65.add(panel_73, BorderLayout.WEST);

		JLabel lblNewLabel_1 = new JLabel("model\u540D\u79F0:");
		panel_73.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("\u6682\u65E0                                                         ");
		panel_73.add(lblNewLabel_2);

		JPanel panel_75 = new JPanel();
		panel_65.add(panel_75, BorderLayout.CENTER);

		JPanel panel_74 = new JPanel();
		panel_75.add(panel_74);

		JLabel lblNewLabel_3 = new JLabel("sheet\u626B\u63CF\u8BBE\u7F6E");
		panel_74.add(lblNewLabel_3);

		multiComboBox_1 = new MultiComboSelectBox(null, "Sheet页名", "模板名称");
		panel_74.add(multiComboBox_1);

		/*
		 * 保存model按钮
		 */
		JButton btnNewButton_4 = new JButton("\u4FDD\u5B58\u6587\u6863\u914D\u7F6E");
		panel_74.add(btnNewButton_4);

		JButton btnNewButton_2 = new JButton("扫描文档");
		panel_75.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File excelFile = new File(textField.getText().trim());
				if (!excelFile.exists()) {
					alert("excel文件不存在，可能已经被删除，请检查", 0);
					return;
				}

				if (excelWorker != null && !excelWorker.isDone()) {
					alert("扫描文档正在进行执行器,请等待", 1);
					return;
				}
				excelWorker = new SwingWorker() {
					@Override
					protected Object doInBackground() {
						// TODO Auto-generated method stub
						SwingWorker dh = new SwingWorker() {
							@Override
							protected Object doInBackground() {
								// TODO Auto-generated method stub
								int k = 0;
								while (!excelWorker.isDone()) {
									lblNewLabel_17_1_1_1_2.setText(k / 1000.0 + "s");
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										logger.error("异步任务,记录扫描时间休眠100毫秒发生异常", e);
									}
									k += 100;
								}
								return null;
							}
						};
						dh.execute();
						
						try {
							logger.info("开始处理文件:" + excelFile.getName());
							Model model = JSONObject
									.parseObject(FileUtils.getStr(getModelFile(lblNewLabel_2.getText())), Model.class);
							lBeans = DealData.dealExcel(excelFile, model);
						} catch (Exception ex2) {
							// TODO: handle exception
							logger.error("处理文件发送错误", ex2);
						}
						List<String> nameList = null;
						if (lBeans != null) {
							int num = 0;
							nameList = new LinkedList<>();
							for (String str : lBeans.keySet()) {
								for (TableBean tableBean : lBeans.get(str)) {
									nameList.add(tableBean.getName());
									num++;
								}
							}
							lblNewLabel_17.setText(num + "");
							comboBox_2.setModel(new DefaultComboBoxModel(nameList.toArray()));
						} else {
							alert("扫描结果为空", 1);
						}
						logger.info("文件:" + excelFile.getName() + ",处理后的表名有:" + nameList);

						return null;
					}

				};
				excelWorker.execute();

			}
		});
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Result result=clickModelButton();
				alert(result.getMsg(), result.getCode());
			}
		});

		JPanel panel_76 = new JPanel();
		panel_1_2.add(panel_76);
		panel_76.setLayout(new BorderLayout(0, 0));

		JPanel panel_82 = new JPanel();
		panel_76.add(panel_82, BorderLayout.WEST);

		JPanel panel_77 = new JPanel();
		panel_82.add(panel_77);

		JLabel lblNewLabel_14 = new JLabel("\u626B\u63CF\u7ED3\u679C:");
		panel_77.add(lblNewLabel_14);

		JLabel lblNewLabel_15 = new JLabel("false");
		panel_77.add(lblNewLabel_15);

		JPanel panel_78 = new JPanel();
		panel_82.add(panel_78);

		JLabel lblNewLabel_16 = new JLabel("\u6210\u529F\u8868\u6570\u76EE:");
		panel_78.add(lblNewLabel_16);

		lblNewLabel_17 = new JLabel("0");
		panel_78.add(lblNewLabel_17);
		lblNewLabel_17.setFont(new Font("宋体", Font.PLAIN, 18));

		JPanel panel_79 = new JPanel();
		panel_82.add(panel_79);

		JLabel lblNewLabel_16_1 = new JLabel("\u626B\u63CF\u5230\u7684\u8868\u540D:");
		panel_79.add(lblNewLabel_16_1);

		comboBox_2 = new JComboBox();
		comboBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nu=comboBox_2.getSelectedIndex();
				List<TableBean> list=new LinkedList<>();
				for(String key:lBeans.keySet()) {
					List<TableBean> b=lBeans.get(key);
					list.addAll(b);
				}
				Object text=list.get(nu);
				copy(text.toString());
				alert("表结构已复制", 1);
			}
		});
		panel_79.add(comboBox_2);

		JPanel panel_83 = new JPanel();
		panel_76.add(panel_83, BorderLayout.CENTER);

		JPanel panel_81 = new JPanel();
		panel_83.add(panel_81);

		JLabel lblNewLabel_19_1_2 = new JLabel("\u626B\u63CF\u8017\u65F6:");
		panel_81.add(lblNewLabel_19_1_2);

		lblNewLabel_17_1_1_1_2 = new JLabel("0s");
		panel_81.add(lblNewLabel_17_1_1_1_2);
		lblNewLabel_17_1_1_1_2.setFont(new Font("宋体", Font.PLAIN, 18));

		JPanel panel_58 = new JPanel();
		frame.getContentPane().add(panel_58, BorderLayout.NORTH);

		JLabel lblNewLabel_35 = new JLabel("autoTable");
		lblNewLabel_35.setForeground(new Color(106, 90, 205));
		lblNewLabel_35.setFont(new Font("微软雅黑 Light", Font.PLAIN, 20));
		panel_58.add(lblNewLabel_35);

		JPanel panel_99 = new JPanel();
		frame.getContentPane().add(panel_99, BorderLayout.SOUTH);

		JCheckBox chckbxNewCheckBox_2 = new JCheckBox(
				"\u542F\u52A8DEBUG(\u6587\u4EF6\u4F1A\u6682\u7528\u8F83\u591A\u5185\u5B58,\u4EC5\u5728\u8C03\u8BD5\u4F7F\u7528)");
		chckbxNewCheckBox_2.setSelected(true);
		chckbxNewCheckBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!chckbxNewCheckBox_2.isSelected()) {
					Level INFO = Level.getLevel("INFO");
					Configurator.setLevel(LogManager.getLogger(UI.class).getName(), INFO);
				}else {
					Level DEBUUG = Level.getLevel("DEBUUG");
					Configurator.setLevel(LogManager.getLogger(UI.class).getName(), DEBUUG);
				}
			}
		});
		panel_99.add(chckbxNewCheckBox_2);
		
		comboBox_1.setModel(new DefaultComboBoxModel(getSheetConfig()));
		
				JPanel panel_107 = new JPanel();
				panel_67.add(panel_107, BorderLayout.CENTER);
				
						JLabel lblNewLabel_24 = new JLabel("\u7C7B\u578B\u914D\u7F6E:");
						panel_107.add(lblNewLabel_24);
						
								multiComboBox_2 = new MultiComboSelectBox2(getTypeMapByOracle(), "字段类型", "可取值(用,隔开)");
								panel_107.add(multiComboBox_2);
																																																																																								 												
																																																																																								 												JPanel panel_114 = new JPanel();
																																																																																								 												panel_1_3.add(panel_114, BorderLayout.SOUTH);
																																																																																								 												panel_114.setLayout(new BorderLayout(0, 0));
																																																																																								 												
																																																																																								 												JPanel panel_113 = new JPanel();
																																																																																								 												panel_114.add(panel_113, BorderLayout.SOUTH);
																																																																																								 												panel_113.setLayout(new BorderLayout(0, 0));
																																																																																								 												
																																																																																								 														JPanel panel_9 = new JPanel();
																																																																																								 														panel_113.add(panel_9, BorderLayout.SOUTH);
																																																																																								 														panel_9.setBorder(UIManager.getBorder("ComboBox.border"));
																																																																																								 														panel_9.setLayout(new BorderLayout(0, 0));
																																																																																								 														
																																																																																								 																JPanel panel_97 = new JPanel();
																																																																																								 																panel_97.setBorder(new LineBorder(Color.LIGHT_GRAY));
																																																																																								 																panel_9.add(panel_97, BorderLayout.NORTH);
																																																																																								 																panel_97.setLayout(new BorderLayout(0, 0));
																																																																																								 																
																																																																																								 																		JPanel panel_53 = new JPanel();
																																																																																								 																		panel_97.add(panel_53);
																																																																																								 																		
																																																																																								 																				JLabel lblNewLabel_38 = new JLabel("\u6570\u636E\u5E93\u914D\u7F6E");
																																																																																								 																				lblNewLabel_38.setFont(new Font("宋体", Font.PLAIN, 15));
																																																																																								 																				panel_53.add(lblNewLabel_38);
																																																																																								 																				
																																																																																								 																						JPanel panel_108 = new JPanel();
																																																																																								 																						panel_53.add(panel_108);
																																																																																								 																						
																																																																																								 																								JLabel lblNewLabel_46 = new JLabel(
																																																																																								 																										"(\u6CE8\u610F\u4F18\u5148\u9009\u62E9\u6570\u636E\u5E93\u7C7B\u578B,\u4E0D\u540C\u7C7B\u578B\u4F1A\u5F71\u54CD\u7C7B\u578B\u914D\u7F6E)");
																																																																																								 																								lblNewLabel_46.setForeground(Color.RED);
																																																																																								 																								panel_108.add(lblNewLabel_46);
																																																																																								 																								
																																																																																								 																										JPanel panel_109 = new JPanel();
																																																																																								 																										panel_9.add(panel_109, BorderLayout.CENTER);
																																																																																								 																										panel_109.setLayout(new BorderLayout(0, 0));
																																																																																								 																										
																																																																																								 																												JPanel panel_10 = new JPanel();
																																																																																								 																												panel_109.add(panel_10, BorderLayout.CENTER);
																																																																																								 																												panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
																																																																																								 																												
																																																																																								 																														JPanel panel_17 = new JPanel();
																																																																																								 																														panel_10.add(panel_17);
																																																																																								 																														
																																																																																								 																																JLabel lblNewLabel_11 = new JLabel("\u6570\u636E\u5E93\u7C7B\u578B:");
																																																																																								 																																panel_17.add(lblNewLabel_11);
																																																																																								 																																
																																																																																								 																																		comboBox = new JComboBox();
																																																																																								 																																		
																																																																																								 																																				comboBox.addActionListener(new ActionListener() {
																																																																																								 																																					public void actionPerformed(ActionEvent e) {
																																																																																								 																																						
																																																																																								 																																						comboBox_3.setEnabled(comboBox.getSelectedItem().toString().equals("oracle"));
																																																																																								 																																						textField_20.setEditable(comboBox.getSelectedItem().toString().equals("oracle"));
																																																																																								 																																						if(comboBox.getSelectedItem().toString().equals("oracle")){
																																																																																								 																																							if(multiComboBox_2.getSelectedValues().size()>0) {
																																																																																								 																																								return;
																																																																																								 																																							}
																																																																																								 																																							multiComboBox_2.updateModel(getTypeMapByOracle());
																																																																																								 																																						}
																																																																																								 																																						else {
																																																																																								 																																							if(multiComboBox_2.getSelectedValues().size()>0) {
																																																																																								 																																								return;
																																																																																								 																																							}
																																																																																								 																																							multiComboBox_2.updateModel(getTypeMapByMysql());
																																																																																								 																																						}
																																																																																								 																																					}
																																																																																								 																																				});
																																																																																								 																																				comboBox.setModel(new DefaultComboBoxModel(new String[] { "oracle", "mysql", "hive" }));
																																																																																								 																																				panel_17.add(comboBox);
																																																																																								 																																				
																																																																																								 																																						JPanel panel_11 = new JPanel();
																																																																																								 																																						panel_10.add(panel_11);
																																																																																								 																																						
																																																																																								 																																								JLabel lblNewLabel_10 = new JLabel("IP:");
																																																																																								 																																								panel_11.add(lblNewLabel_10);
																																																																																								 																																								
																																																																																								 																																										textField_15 = new JTextField();
																																																																																								 																																										textField_15.setText("10.20.30.34");
																																																																																								 																																										textField_15.setColumns(10);
																																																																																								 																																										panel_11.add(textField_15);
																																																																																								 																																										
																																																																																								 																																												JPanel panel_12 = new JPanel();
																																																																																								 																																												panel_10.add(panel_12);
																																																																																								 																																												
																																																																																								 																																														JLabel lblNewLabel_10_1 = new JLabel("\u7AEF\u53E3:");
																																																																																								 																																														panel_12.add(lblNewLabel_10_1);
																																																																																								 																																														
																																																																																								 																																																textField_16 = new JTextField();
																																																																																								 																																																textField_16.setText("1521");
																																																																																								 																																																textField_16.setColumns(10);
																																																																																								 																																																panel_12.add(textField_16);
																																																																																								 																																																
																																																																																								 																																																		JPanel panel_13 = new JPanel();
																																																																																								 																																																		panel_10.add(panel_13);
																																																																																								 																																																		
																																																																																								 																																																				JLabel lblNewLabel_10_1_1 = new JLabel("\u8D26\u53F7:");
																																																																																								 																																																				panel_13.add(lblNewLabel_10_1_1);
																																																																																								 																																																				
																																																																																								 																																																						textField_17 = new JTextField();
																																																																																								 																																																						textField_17.setText("uis");
																																																																																								 																																																						textField_17.setColumns(10);
																																																																																								 																																																						panel_13.add(textField_17);
																																																																																								 																																																						
																																																																																								 																																																								JPanel panel_14 = new JPanel();
																																																																																								 																																																								panel_10.add(panel_14);
																																																																																								 																																																								
																																																																																								 																																																										JLabel lblNewLabel_10_1_1_1 = new JLabel("\u5BC6\u7801:");
																																																																																								 																																																										panel_14.add(lblNewLabel_10_1_1_1);
																																																																																								 																																																										
																																																																																								 																																																												textField_19 = new JTextField();
																																																																																								 																																																												textField_19.setText("123456");
																																																																																								 																																																												textField_19.setColumns(10);
																																																																																								 																																																												panel_14.add(textField_19);
																																																																																								 																																																												
																																																																																								 																																																														JPanel panel_98 = new JPanel();
																																																																																								 																																																														panel_109.add(panel_98, BorderLayout.SOUTH);
																																																																																								 																																																														
																																																																																								 																																																																JPanel panel_18 = new JPanel();
																																																																																								 																																																																panel_98.add(panel_18);
																																																																																								 																																																																
																																																																																								 																																																																		JLabel lblNewLabel_36 = new JLabel("\u6240\u5728\u6570\u636E\u5E93");
																																																																																								 																																																																		panel_18.add(lblNewLabel_36);
																																																																																								 																																																																		
																																																																																								 																																																																				textField_18 = new JTextField();
																																																																																								 																																																																				textField_18.setColumns(10);
																																																																																								 																																																																				panel_18.add(textField_18);
																																																																																								 																																																																				
																																																																																								 																																																																						JPanel panel_19 = new JPanel();
																																																																																								 																																																																						panel_98.add(panel_19);
																																																																																								 																																																																						
																																																																																								 																																																																								JButton btnNewButton_1 = new JButton("\u6D4B\u8BD5\u8FDE\u901A\u6027");
																																																																																								 																																																																								btnNewButton_1.addActionListener(new ActionListener() {
																																																																																								 																																																																									public void actionPerformed(ActionEvent e) {
																																																																																								 																																																																										JDBC jdbc = null;
																																																																																								 																																																																										try {
																																																																																								 																																																																											jdbc = DealData.getJDBC(getConnectBean());
																																																																																								 																																																																										} catch (Exception e1) {
																																																																																								 																																																																											// TODO Auto-generated catch block
																																																																																								 																																																																											e1.printStackTrace();
																																																																																								 																																																																											logger.error("数据库连接失败");
																																																																																								 																																																																											logger.error(e1);
																																																																																								 																																																																										}
																																																																																								 																																																																										if (jdbc != null) {
																																																																																								 																																																																											lblNewLabel_13.setText("true");
																																																																																								 																																																																											jdbc.close();
																																																																																								 																																																																										} else {
																																																																																								 																																																																											lblNewLabel_13.setText("false");

																																																																																								 																																																																										}
																																																																																								 																																																																									}
																																																																																								 																																																																								});
																																																																																								 																																																																								panel_19.add(btnNewButton_1);
																																																																																								 																																																																								
																																																																																								 																																																																										JPanel panel_20 = new JPanel();
																																																																																								 																																																																										panel_98.add(panel_20);
																																																																																								 																																																																										
																																																																																								 																																																																												JLabel lblNewLabel_12 = new JLabel("\u8FDE\u63A5\u72B6\u6001:");
																																																																																								 																																																																												panel_20.add(lblNewLabel_12);
																																																																																								 																																																																												
																																																																																								 																																																																														lblNewLabel_13 = new JLabel("false");
																																																																																								 																																																																														lblNewLabel_13.setForeground(Color.RED);
																																																																																								 																																																																														panel_20.add(lblNewLabel_13);
																																																																																								 																																																																														
																																																																																								 																																																																																JPanel panel_16 = new JPanel();
																																																																																								 																																																																																panel_98.add(panel_16);
																																																																																								 																																																																																
																																																																																								 																																																																																		JPanel panel_15 = new JPanel();
																																																																																								 																																																																																		panel_16.add(panel_15);
																																																																																								 																																																																																		
																																																																																								 																																																																																				JLabel lblNewLabel_10_1_1_1_1 = new JLabel("\u8FDE\u63A5\u65B9\u5F0F:");
																																																																																								 																																																																																				panel_15.add(lblNewLabel_10_1_1_1_1);
																																																																																								 																																																																																				
																																																																																								 																																																																																						comboBox_3 = new JComboBox();
																																																																																								 																																																																																						comboBox_3.setModel(new DefaultComboBoxModel(new String[] { "\u670D\u52A1\u540D", "sid" }));
																																																																																								 																																																																																						panel_15.add(comboBox_3);
																																																																																								 																																																																																						
																																																																																								 																																																																																								JLabel lblNewLabel_21 = new JLabel("\u670D\u52A1\u540D:");
																																																																																								 																																																																																								panel_16.add(lblNewLabel_21);
																																																																																								 																																																																																								
																																																																																								 																																																																																										textField_20 = new JTextField();
																																																																																								 																																																																																										textField_20.setText("cdb1");
																																																																																								 																																																																																										textField_20.setColumns(10);
																																																																																								 																																																																																										panel_16.add(textField_20);
																																																																																								 																																																																																										
																																																																																								 																																																																																												JPanel panel_110 = new JPanel();
																																																																																								 																																																																																												panel_109.add(panel_110, BorderLayout.NORTH);
																																																																																								 																																																																																												panel_110.setLayout(new BorderLayout(0, 0));
																																																																																								 																																																																																												
																																																																																								 																																																																																														JPanel panel_55 = new JPanel();
																																																																																								 																																																																																														panel_110.add(panel_55, BorderLayout.WEST);
																																																																																								 																																																																																														
																																																																																								 																																																																																																JLabel lblNewLabel_47 = new JLabel("\u6570\u636E\u5E93\u6A21\u677F");
																																																																																								 																																																																																																panel_55.add(lblNewLabel_47);
																																																																																								 																																																																																																
																																																																																								 																																																																																																		 comboBox_7 = new JComboBox();
																																																																																								 																																																																																																		 comboBox_7.addActionListener(new ActionListener() {
																																																																																								 																																																																																																		 	public void actionPerformed(ActionEvent e) {

				String varName = comboBox_7.getSelectedItem().toString();
				if (varName.isEmpty()) {
					alert("模板为空", 0);
				}
				 DatabaConnectBean bean = null;
				try {
					bean = getConnectBeanByName(varName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					logger.error("解析ConnectConfig配置文件出错",e1);
				}
				 setDataConnectBean(varName,bean);
				
																																																																																								 																																																																																																		 	}
																																																																																								 																																																																																																		 });
																																																																																								 																																																																																																		 panel_55.add(comboBox_7);
																																																																																								 																																																																																																		 
																																																																																								 																																																																																																		 		JPanel panel_111 = new JPanel();
																																																																																								 																																																																																																		 		panel_110.add(panel_111, BorderLayout.EAST);
																																																																																								 																																																																																																		 		
																																																																																								 																																																																																																		 				JLabel lblNewLabel_48 = new JLabel("--------");
																																																																																								 																																																																																																		 				panel_111.add(lblNewLabel_48);
																																																																																								 																																																																																																		 				
																																																																																								 																																																																																																		 						chckbxNewCheckBox_3 = new JCheckBox("\u4FEE\u6539\u6570\u636E\u5E93\u6A21\u677F\u540D");
																																																																																								 																																																																																																		 						chckbxNewCheckBox_3.addActionListener(new ActionListener() {
																																																																																								 																																																																																																		 							public void actionPerformed(ActionEvent e) {
																																																																																								 																																																																																																		 								textField_21.setEditable(chckbxNewCheckBox_3.isSelected());
																																																																																								 																																																																																																		 							}
																																																																																								 																																																																																																		 						});
																																																																																								 																																																																																																		 						panel_111.add(chckbxNewCheckBox_3);
																																																																																								 																																																																																																		 						
																																																																																								 																																																																																																		 								textField_21 = new JTextField();
																																																																																								 																																																																																																		 								textField_21.setEditable(false);
																																																																																								 																																																																																																		 								panel_111.add(textField_21);
																																																																																								 																																																																																																		 								textField_21.setColumns(20);
																																																																																								 																																																																																																		 								
																																																																																								 																																																																																																		 										JButton btnNewButton_5 = new JButton("\u4FDD\u5B58\u6570\u636E\u5E93\u914D\u7F6E");
																																																																																								 																																																																																																		 										btnNewButton_5.addActionListener(new ActionListener() {
																																																																																								 																																																																																																		 											public void actionPerformed(ActionEvent e) {
																																																																																								 																																																																																																		 												Result result=clickConnectButton();
																																																																																								 																																																																																																		 												alert(result.getMsg(), result.getCode());
																																																																																								 																																																																																																		 											
																																																																																								 																																																																																																		 											}
																																																																																								 																																																																																																		 										});
																																																																																								 																																																																																																		 										panel_111.add(btnNewButton_5);
																																																																																								 																																																																																																		 										
																																																																																								 																																																																																																		 												JButton btnNewButton_6 = new JButton("\u65B0\u5EFA\u6A21\u677F");
																																																																																								 																																																																																																		 												btnNewButton_6.addActionListener(new ActionListener() {
																																																																																								 																																																																																																		 													public void actionPerformed(ActionEvent e) {

																																																																																								 																																																																																																		 														setDataConnectBean(null,null);

																																																																																								 																																																																																																		 													}
																																																																																								 																																																																																																		 												});
																																																																																								 																																																																																																		 												panel_111.add(btnNewButton_6);
																																																																																								 																																																																																																		 												comboBox_7.setModel(new DefaultComboBoxModel(getConnectBeanFromFile()));
		/*
		 * 处理选择文件
		 */
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				File file = null;
				File excelFile;
				String path = null;
				JFileChooser fileChooser = new JFileChooser();
				FileSystemView fsv = FileSystemView.getFileSystemView();
				FileFilter fillFilter = new FileFilter() {
					@Override
					public String getDescription() {
						// TODO Auto-generated method stub
						return ".xls||xlsx";
					}

					@Override
					public boolean accept(File f) {
						// TODO Auto-generated method stub
						String s = f.getName().toLowerCase();
						if (f.isDirectory() || (s.endsWith(".xls") || s.endsWith(".xlsx"))) {
							return true;
						}
						return false;
					}
				};
				fileChooser.addChoosableFileFilter(fillFilter);
				fileChooser.setFileFilter(fillFilter);
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setDialogTitle("请选择要上传的文件...");
				fileChooser.setApproveButtonText("确定");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				result = fileChooser.showOpenDialog(frame);
				excelFile = fileChooser.getSelectedFile();
				if (JFileChooser.APPROVE_OPTION == result && excelFile != null) {
					String nameString = excelFile.getName();
					textField.setText(excelFile.getAbsolutePath());
					Map<String, String> sheetNameMap = new LinkedHashMap<>();
					List<String> sheetList = null;
					/*
					 * 调用处理文件
					 */
					try {
						sheetList = getSheet(excelFile);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					sheetNameMap.put("统一模板", "");
					Map<String, String> useMap = null;
					try {
						String modelString = getInitModel(getModelFile(), excelFile.getAbsolutePath());
						if (modelString != null) {
							lblNewLabel_2.setText(modelString);
						} else {
							lblNewLabel_2.setText(nameString.replaceAll(".xlsx|.xls", ".model"));
						}
						for (String s : sheetList) {
							sheetNameMap.put(s, "");
						}
						File modelFile = getModelFile(lblNewLabel_2.getText().trim());
						if (modelFile.exists()) {
							JSONObject js = JSONObject.parseObject(FileUtils.getStr(modelFile));
							Map<String, String> map = (Map<String, String>) js.get("sheetMap");
							useMap = (Map<String, String>) js.get("selectSheetMap");
							for (String key : map.keySet()) {
								sheetNameMap.put(key, map.get(key));
							}
						}
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					multiComboBox_1.updateValue(sheetNameMap);
					multiComboBox_1.updateSelect(useMap);

					String varName = comboBox_1.getSelectedItem()==null?"":comboBox_1.getSelectedItem().toString();
					if (!varName.isEmpty()) {
						File file2 = getSheetConfigFileByName(varName);
						if (file2 != null) {
							try {
								dealSheetConfig(file2);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								logger.error("初始化excel文件-处理sheet配置文件发生错误", e1);
							}
						}

					}

				}

			}
		});
	}

	public List<String> getSheet(File file) throws IOException {
		return POIUtil.getSheet(file);
	}



	public JSONObject dealHive(String lineString,Object[] conlomns) {
		if(lineString.isEmpty()||conlomns==null) {
			return null;
		}
		lineString=lineString.replace("（","(");
		lineString=lineString.replace("）",")");

		JSONObject re=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		String[] line=lineString.split("\n");
		for(int i=1;i<line.length-1;i++) {
			line[i]=line[i].trim();
			String[] zd=line[i].split(" ");
			String name=zd[0].replace("`","");
			String type=zd[1].replaceAll("\\(.*", "");
			String length;
			String xiaoshu;
			if(zd[1].indexOf("(")!=-1) {
				 length=zd[1].replaceAll(".*\\(|,.*", "");
				 xiaoshu=zd[1].replaceAll(".*,|\\)", "");
			}
			else {
				length="0";
				xiaoshu="0";
			}
			boolean isNull=line[i].indexOf("NOT NULL")==-1;
			JSONObject data=new JSONObject(); 
			for(int j=0;j<conlomns.length;j++) {
				String keyString=conlomns[j].toString();
				switch (keyString) {
				case "name": {
					data.put(j+"", name);
					break;
				}
				case "type": {
					data.put(j+"", type);
					break;
				}
				case "length": {
					data.put(j+"", length);
					break;
				}
				case "xiaoshu": {
					data.put(j+"", xiaoshu);
					break;
				}
				case "isEmtpy": {
					data.put(j+"", isNull);
					break;
				}
				default:
					throw new IllegalArgumentException("Unexpected value: " + keyString);
				}
			}
			jsonArray.add(data);
		}
		re.put("data", jsonArray);
		return re;
	}
	public void dealSheetConfig(File file) throws IOException {
		String str = null;
		try {
			str = FileUtils.getStr(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (str.isEmpty()) {
			return;
		}
		JSONObject jsonObject = JSONObject.parseObject(str);
		SheetConfig sheetConfig = new SheetConfig(jsonObject);
		setSheetConfig(sheetConfig);

	}

	public DatabaConnectBean getConnectBean() {
		DatabaConnectBean bean = new DatabaConnectBean();
		bean.setIP(textField_15.getText().trim());
		bean.setPORT(textField_16.getText().trim().isEmpty() ? 0 : Integer.valueOf(textField_16.getText()));
		bean.setUSER(textField_17.getText().trim());
		bean.setPASS(textField_19.getText().trim());
		bean.setDATABASE(textField_18.getText());
		bean.setTYPE(comboBox.getSelectedItem().toString());
		bean.setCONNECTTYPE(comboBox_3.getSelectedItem().toString());
		bean.setSERVER(textField_20.getText().trim());
		return bean;
	}

	public Map<String, String> getSelectAdapter() {
		return multiComboBox_2.getSelectedValues();
	}
	
	public Map<String, String> getAdapter() {
		return multiComboBox_2.getValues();
	}

	public void setSheetModel(SheetConfig sheetConfig) {
		sheetConfig.setTableName(textField_2.getText());
		sheetConfig.setTableLocation(Integer.valueOf(textField_1.getText() == null ? "-1" : textField_1.getText()));
		sheetConfig.setUseNull(chckbxNewCheckBox_1.isSelected());
		sheetConfig.setUseTable(chckbxNewCheckBox_1_1.isSelected());

	}

	public Result saveSheetFile(String name, SheetConfig sheetConfig) {
		File file = new File(systemFile.getAbsoluteFile() + "/sheetConfig/" + name);
		boolean isWriter = false;
		if (file.exists()) {
			int close = JOptionPane.showConfirmDialog(null, "该sheet配置模板已经存在,请确认是否继续？", "警告", JOptionPane.YES_NO_OPTION);
			if (close == 0) {
				isWriter = true;
			} else {
				isWriter = false;
			}
		} else {
			isWriter = true;
		}
		if (isWriter) {
			try {
				FileUtils.writer(file, sheetConfig.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error("写入sheet模板数据失败",e);
				return Result.setError("更新失败,错误请看日记");
			}
			return Result.setSuccess("更新sheet模板最新数据成功");
		}
		return Result.setSuccess("取消写入成功");
	}

	public SheetConfig getSheetConfigModel() {
		SheetConfig sheetConfig = new SheetConfig();
		sheetConfig.setTableLocation(!textField_1.getText().isEmpty() ? Integer.valueOf(textField_1.getText()) : -1);
		sheetConfig.setUseNull(chckbxNewCheckBox_1.isSelected());
		sheetConfig.setUseTable(chckbxNewCheckBox_1_1.isSelected());
		sheetConfig.setTableName(textField_2.getText());
		sheetConfig.setColomn(multiComboBox_3.getSelectedText());
		sheetConfig.setTableType(comboBox_6.getSelectedItem().toString());
		sheetConfig.setZiduanType(comboBox_6_1.getSelectedItem().toString());
		sheetConfig.setBegin(chckbxNewCheckBox.isSelected());
		sheetConfig.setTableNameUpdate(txttablename.getText().trim());
		List<ZiduanConfig> ziduanConfigs = new ArrayList<ZiduanConfig>();
		String[] ziduanValues = new String[] { "字段名", "类型", "长度", "是否主键", "小数", "是否为空", "默认值" };
		
		List<String> valuesList = getValueByZiduan();
		for (int i = 0; i < ziduanValues.length; i++) {
			ZiduanConfig zConfig = new ZiduanConfig();
			zConfig.setName(ziduanValues[i]);
			zConfig.setLen(valuesList.get(i * 2) == null ? Integer.valueOf(valuesList.get(i * 2)) : -1);
			zConfig.setKeyName(valuesList.get(i * 2 + 1));
			ziduanConfigs.add(zConfig);
		}
		sheetConfig.setZiduanConfig(ziduanConfigs);
		sheetConfig.setName(textField_22.getText());
		sheetConfig.setConnectName(textField_21.getText().trim());
		sheetConfig.setAdapter(getAdapter());
		sheetConfig.setSelectAdapte(getSelectAdapter());
		return sheetConfig;
	}

	public void setSheetConfig(SheetConfig config) throws IOException {
		Object[] vObjects = new Object[] { "name", "type", "length","isKey", "xiaoshu", "isEmtpy", "defaultValue" };
		if (config == null) {
			textField_1.setText("");
			textField_2.setText("");
			chckbxNewCheckBox_1.setSelected(false);
			chckbxNewCheckBox_1_1.setSelected(true);
			textField_22.setText("");
			textField_18.setText("");
			comboBox_6.setSelectedItem("");
			comboBox_6_1.setSelectedItem("");
			multiComboBox_3.updateIndex(vObjects);

			textField_3.setText("");
			textField_4.setText("");
			textField_5.setText("");
			textField_6.setText("");
			textField_7.setText("");
			textField_8.setText("");
			textField_9.setText("");
			textField_10.setText("");
			textField_11.setText("");
			textField_12.setText("");
			textField_13.setText("");
			textField_14.setText("");
			textField_23.setText("");
			textField_24.setText("");
			textField_15.setText("");
			textField_16.setText("");
			textField_17.setText("");
			textField_19.setText("");
			textField_18.setText("");
			comboBox_3.setSelectedIndex(0);
			textField_20.setText("");
			lblNewLabel_13.setText("false");
			txttablename.setText("{tableName}");
			chckbxNewCheckBox.setSelected(true);
			return;
		}
		txttablename.setText(config.getTableNameUpdate());
		textField_1.setText(config.getTableLocation() + "");
		textField_2.setText(config.getTableName());
		chckbxNewCheckBox_1.setSelected(config.isUseNull());
		chckbxNewCheckBox_1_1.setSelected(config.isUseTable());
		textField_22.setText(config.getName());
		textField_18.setText(config.getDatabase());
		comboBox_6.setSelectedItem(config.getTableName());
		comboBox_6_1.setSelectedItem(config.getZiduanType());
		comboBox_7.setModel(new DefaultComboBoxModel(getConnectBeanFromFile()));
		comboBox_1.setModel(new DefaultComboBoxModel(getSheetConfig()));
		
		comboBox_7.setSelectedItem(config.getConnectName());
		comboBox_1.setSelectedItem(config.getName());

		chckbxNewCheckBox.setSelected(config.isBegin());
		Object[] vObjects2 = config.getColomn().split(",");
		multiComboBox_3.updateIndex(
				vObjects2 == null || vObjects2.length == 0 || vObjects2[0].toString().length() == 0 ? vObjects
						: vObjects2);
		DatabaConnectBean bean = getConnectBeanByName(config.getConnectName());
		setDataConnectBean(config.getConnectName(),bean);
		multiComboBox_2.updateValue(config.getAdapter());
		multiComboBox_2.updateSelect(config.getSelectAdapte());
		List<ZiduanConfig> ziduanConfigs = config.getZiduanConfig();
		for (ZiduanConfig zdConfig : ziduanConfigs) {
			String name = zdConfig.getName();
			switch (name) {
			case "字段名": {
				textField_3.setText(zdConfig.getLen() + "");
				textField_4.setText(zdConfig.getKeyName());
				break;
			}
			case "类型": {
				textField_5.setText(zdConfig.getLen() + "");
				textField_6.setText(zdConfig.getKeyName());
				break;
			}
			case "长度": {
				textField_7.setText(zdConfig.getLen() + "");
				textField_8.setText(zdConfig.getKeyName());
				break;
			}
			case "是否主键": {
				textField_23.setText(zdConfig.getLen() + "");
				textField_24.setText(zdConfig.getKeyName());
				break;
			}
			case "小数": {
				textField_9.setText(zdConfig.getLen() + "");
				textField_10.setText(zdConfig.getKeyName());
				break;
			}
			case "是否为空": {
				textField_11.setText(zdConfig.getLen() + "");
				textField_12.setText(zdConfig.getKeyName());
				break;
			}
			case "默认值": {
				textField_13.setText(zdConfig.getLen() + "");
				textField_14.setText(zdConfig.getKeyName());
				break;
			}
			default:
				break;
			}

		}

	}

	public List<String> getValueByZiduan() {
		List<String> list = new LinkedList<>();
		list.add(textField_3.getText());
		list.add(textField_4.getText());
		list.add(textField_5.getText());
		list.add(textField_6.getText());
		list.add(textField_7.getText());
		list.add(textField_8.getText());
		list.add(textField_23.getText());// 主键区域
		list.add(textField_24.getText());
		list.add(textField_9.getText());
		list.add(textField_10.getText());
		list.add(textField_11.getText());
		list.add(textField_12.getText());
		list.add(textField_13.getText());
		list.add(textField_14.getText());

		return list;
	}

	public Map<String, String> getSheetMap() {
		return multiComboBox_1.getMap();
	}

	public Model getModel() {
		Model model = new Model();
		model.setSheetMap(getSheetMap());
		model.setName(lblNewLabel_2.getText().trim());
		model.setFile(textField.getText().trim());
		model.setSelectSheetMap(multiComboBox_1.getSelectedValues());
		return model;
	}


	public Object[] getModelFile() {
		File file = new File(systemFile.getAbsoluteFile() + "/modelConfig");
		return file.list();
	}

	/*
	 * 复制功能
	 */
	public  void copy(String text) {
		Clipboard  clipboard =Toolkit.getDefaultToolkit().getSystemClipboard();  //得到系统剪贴板  
	     StringSelection selection = new StringSelection(text);  
	     clipboard.setContents(selection, null);  
	}
	public String getArrayStr(Object[] args) {
		StringBuilder builder=new StringBuilder();
		for(Object s:args) {
			builder.append(s.toString());
		}
		return builder.toString();
	}


	public Object[] getSheetConfig() {
		File sheetConfigFile = new File(systemFile.getAbsolutePath() + "/sheetConfig");
		Object[] objects = sheetConfigFile.list();
		return objects;
	}

	public void initByWindows() {
		String relativelyPath=System.getProperty("user.dir"); 
		systemFile = new File(relativelyPath+"/autoTable/");
		System.out.println(systemFile.getAbsolutePath());
		if (!systemFile.exists()) {
			systemFile.mkdirs();
		}
		File sheetConfigFile = new File(systemFile.getAbsolutePath() + "/sheetConfig");
		if (!sheetConfigFile.exists()) {
			sheetConfigFile.mkdirs();
		}
		File modelConfigFile = new File(systemFile.getAbsolutePath() + "/modelConfig");
		if (!modelConfigFile.exists()) {
			modelConfigFile.mkdirs();
		}
		File logsFile = new File(systemFile.getAbsolutePath() + "/logs");
		if (!logsFile.exists()) {
			logsFile.mkdirs();
		}
		File configFile = new File(systemFile.getAbsolutePath() + "/connectConfig");
		if (!configFile.exists()) {
			configFile.mkdirs();
		}
	}

	public void alert(String str, int type) {
		SwingWorker dh = new SwingWorker() {
			@Override
			protected Object doInBackground() throws Exception {
				// TODO Auto-generated method stub
				if (type == 1) {
					JOptionPane.showMessageDialog(null, str);
				} else
					JOptionPane.showMessageDialog(null, str, "错误", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		};
		dh.execute();
	}

	public File getSheetConfigFileByName(String name) {
		File file = new File(systemFile.getAbsoluteFile() + "/sheetConfig/" + name);
		return file;
	}



	public Result saveModel() {
		File file = new File(systemFile.getAbsoluteFile() + "/modelConfig/" + lblNewLabel_2.getText().trim());
		boolean isWriter = false;
		boolean isFirst = false;
		if (file.exists()) {
			int close = JOptionPane.showConfirmDialog(null, "该model模板已经存在,请确认是否继续？", "警告", JOptionPane.YES_NO_OPTION);
			if (close == 0) {
				isWriter = true;
				isFirst = true;
			}
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("创建新的model文件失败",e);
				return Result.setError("创建新的model文件失败,详情看日记");
			}
			isWriter = true;
		}
		Model model = getModel();
		if (isFirst) {
			model.setCreateTime(new Date().getTime());
		} else {
			model.setUpdateTime(new Date().getTime());
		}
		try {
			FileUtils.writer(file, model.toString());
			return Result.setSuccess("更新Model文件成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("写入Model文件失败",e);
			return Result.setError("更新Model文件失败,详情看日记");
		}

	}

	public Map<String, String> getTypeMapByOracle() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("恒生字典","此空格无效");
		map.put("VARCHAR2", "varchar,str,list,string");
		map.put("NUMBER", "Integer,int,decimal");
		map.put("TIMESTAMP", "timestamp,time");
		map.put("TIMESTAMP(6)", "date");
		return map;
	}
	public Map<String, String> getTypeMapByMysql() {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("恒生字典","此空格无效");
		map.put("varchar", "varchar,str,list,string");
		map.put("decimal", "decimal");
		map.put("int", "int");
		map.put("float", "float");
		map.put("timestamp", "timestamp,time");
		return map;
	}

	public String getInitModel(Object[] objects, String fileName) throws IOException {
		for (Object s : objects) {
			File file = new File(systemFile.getAbsoluteFile() + "/modelConfig/" + s.toString());
			JSONObject jsonObject;
			try {
				jsonObject = JSONObject.parseObject(FileUtils.getStr(file));
			} catch (Exception e) {
				// TODO: handle exception
				alert("解析模板的json发生异常：" + e.toString(), 0);
				logger.error("解析模板的json发生错误", e);
				return null;
			}
			if (jsonObject.getString("file").equals(fileName)) {
				return file.getName();
			}
		}
		return null;
	}

	public DatabaConnectBean getConnectBeanByName(String name) throws IOException {
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

	public void setDataConnectBean(String  name, DatabaConnectBean bean) {
		if (bean == null) {
			chckbxNewCheckBox_3.setSelected(true);
			textField_15.setText("");
			textField_16.setText("");
			textField_17.setText("");
			textField_19.setText("");
			textField_18.setText("");
			comboBox_3.setSelectedIndex(0);
			textField_20.setText("");
			lblNewLabel_13.setText("false");
			textField_21.setText("");
			textField_21.setEditable(true);
			
			return;
		}
		comboBox.setSelectedItem(bean.getTYPE());
		textField_15.setText(bean.getIP());
		textField_16.setText(bean.getPORT() + "");
		textField_17.setText(bean.getUSER());
		textField_19.setText(bean.getPASS());
		textField_18.setText(bean.getDATABASE());
		comboBox_3.setSelectedItem(bean.getCONNECTTYPE());
		textField_20.setText(bean.getSERVER());
		textField_21.setText(name);
		lblNewLabel_13.setText("false");
	}

	public Result saveConnectBean(String name) {
		DatabaConnectBean connectBean = getConnectBean();
		File file = new File(systemFile.getAbsoluteFile() + "/" + "connectConfig/" + name);
		boolean isWriter = false;
		if (file.exists()) {
			int close = JOptionPane.showConfirmDialog(null, "该数据库模板已经存在,请确认是否继续？", "警告", JOptionPane.YES_NO_OPTION);
			if (close == 0) {
				isWriter = true;
			} else {
				isWriter = false;
			}
		} else {
			isWriter = true;
		}
		if (isWriter) {
			try {
				FileUtils.writer(file, connectBean.toString());
				return Result.setSuccess("保存成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("写入数据库配置文件失败",e);
				return Result.setError("保存数据库配置失败,详情看日记");
			}
		}
		return Result.setSuccess("取消更新数据库配置成功");
	}
	
	/***
	 * 保存model配置文件
	 * @return
	 */
	public Result clickModelButton() {
		Result getConnResult=clickSheetButton();
		if(getConnResult.getCode()==0) {
			return getConnResult;
		}
		if (textField.getText().isEmpty()) {
			return Result.setError("excel为空");
		}

		 Result result=	saveModel();
		 if(result.getCode()==1) {
				logger.info("保存model文件:" + lblNewLabel_2);
				return Result.setSuccess("更新model成功");
		 }
		 else {
			 return result;
		 }

	}
	
	/***
	 * 保存数据库模板文件
	 * @return
	 */
	public Result clickConnectButton() {
		if (textField_21.getText().isEmpty()) {
			return Result.setError("数据库模板名不能为空");
		}
	 Result result=saveConnectBean(textField_21.getText().trim());
		if(result.getCode()==1) {
			comboBox_7.setModel(new DefaultComboBoxModel(getConnectBeanFromFile()));
			comboBox_7.setSelectedItem(textField_21.getText());
			return Result.setSuccess("保存数据库配置成功");
		}
		else {
			return result;
		}

	}
	
	/***
	 * 保存sheet配置文件
	 * @return
	 */
	public Result clickSheetButton() {
		Result getConnResult=clickConnectButton();
		if(getConnResult.getCode()==0) {
			return getConnResult;
		}
		if (textField_22.getText().isEmpty()) {
			return Result.setError("sheet模板名为空");
		}
		SheetConfig sheetConfig = getSheetConfigModel();
		Map<String, String> sheetMap = getSheetMap();
		if (sheetMap == null) {
			sheetMap = new HashMap<>();
		}
			Result result=saveSheetFile(textField_22.getText(), sheetConfig);
			if (result.getCode()==1) {
				comboBox_1.setModel(new DefaultComboBoxModel(getSheetConfig()));
				comboBox_1.setSelectedItem(textField_22.getText());
				return Result.setSuccess("sheet配置保存成功");
			}
			else {
				return result;
			}
	}
	/***
	 * 获取数据库配置文件目录
	 * @return
	 */
	public String[] getConnectBeanFromFile() {
		File file=new File(systemFile.getAbsoluteFile()+"/"+"connectConfig");
		return file.list();
	}

	/***
	 * 获取当前时间
	 * @return
	 */
	public String getTime() {
		long now = new Date().getTime();
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fm.format(now);
	}

	/***
	 * 获取modelConfig下的model文件
	 * @param name 文件名字
	 * @return
	 */
	public File getModelFile(String name) {
		if (name.isEmpty()) {
			return null;
		}
		return new File(systemFile.getAbsoluteFile() + "/modelConfig/" + name);
	}
}
