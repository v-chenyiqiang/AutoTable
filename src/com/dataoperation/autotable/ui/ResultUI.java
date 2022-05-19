package com.dataoperation.autotable.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.cont.ContinuableRecord;
import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dataoperation.autotable.bean.DatabaConnectBean;
import com.dataoperation.autotable.bean.Model;
import com.dataoperation.autotable.bean.ResultBean;
import com.dataoperation.autotable.bean.SheetConfig;
import com.dataoperation.autotable.bean.TableBean;
import com.dataoperation.autotable.bean.Ziduan;
import com.dataoperation.autotable.bean.ZiduanConfig;
import com.dataoperation.autotable.excel.POIUtil;
import com.dataoperation.autotable.jdbc.JDBC;
import com.dataoperation.autotable.utils.FileUtils;
import com.dataoperation.autotable.utils.HSTYPE;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.beans.VetoableChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;

public class ResultUI {

	private JFrame frame;
	private JTable table;
	private static UI window2;
	private File systemFile;
	private MultiComboBox multiComboBox;
	private static Logger logger = LogManager.getLogger(ResultUI.class.getName());
	private int alHasNum = 0;
	private int useTime = 0;
	private Map<String, List<ResultBean>> errorTableMap;
	private Map<String, List<ResultBean>> rightTableMap;
	private JLabel lblNewLabel_12_1_1_1_1_1_1;
	private JLabel lblNewLabel_12;
	private JLabel lblNewLabel_12_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_12_1_1;
	private JLabel lblNewLabel_12_1_1_1 ;
	private JLabel lblNewLabel_12_1_1_1_1;
	private JLabel lblNewLabel_12_1_1_1_1_1 ;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private SwingWorker bd = null;
	private Map<String, List<ResultBean>> rightMap;
	private Map<String, List<ResultBean>> errorMap;
	private Map<String, List<ResultBean>> nullTableMap;
	private JLabel lblNewLabel_15;
	private JComboBox comboBox_2;
	private  DefaultTableModel model;
	private 		Object[] colomns=new Object[] {"表名","序号","字段名","属性类型","文档的值","数据库的值","错误信息"};
	private JTextField textField;
	private Set<Integer> alreadyRead;//成功已阅
	private Set<Integer> alreadyRead2;//失败已阅
	private JLabel lblNewLabel_5 ;
    private JLabel lblNewLabel_6;
    private JLabel lblNewLabel_16;
    private JLabel lblNewLabel_17;
    private JLabel lblNewLabel_24;
    private JLabel lblNewLabel_23 ;
    private JLabel lblNewLabel_18;
    private JLabel lblNewLabel_19;
    private JComboBox comboBox_3;
    private JComboBox comboBox_4;
    private Map<String, List<TableBean>> tableList;


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ResultUI window = new ResultUI();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//	

	/**
	 * Create the application.
	 */
	public ResultUI(UI ui) {
		window2=ui;
		initialize();

	}
	public void setVisible(boolean isShow) {
		this.frame.setVisible(isShow);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1252, 692);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel_20 = new JPanel();
		panel_20.setBorder(new LineBorder(Color.LIGHT_GRAY));
		frame.getContentPane().add(panel_20, BorderLayout.CENTER);
		panel_20.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_20.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6);
		
		JLabel lblNewLabel_1 = new JLabel("\u6B63\u786E\u8868:");
		panel_6.add(lblNewLabel_1);
		
		 comboBox = new JComboBox();
		 comboBox.addItemListener(new ItemListener() {
		 	public void itemStateChanged(ItemEvent e) {
		 		changeValueByRight();
		 	}
		 });
		
		panel_6.add(comboBox);
		
		 lblNewLabel_2 = new JLabel("0\u5F20");
		panel_6.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("\u4E0A\u4E00\u5F20");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectNum=comboBox.getSelectedIndex();
				if(selectNum>0) {
					comboBox.setSelectedIndex(selectNum-1);
				}
			}
		});
		panel_6.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u4E0B\u4E00\u5F20");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rightMap==null) {
					return ;
				}
				
				int selectNum=comboBox.getSelectedIndex();
				if(selectNum<rightMap.size()-1) {
					comboBox.setSelectedIndex(selectNum+1);
				}
			}
		});
		panel_6.add(btnNewButton_1);
		
		 lblNewLabel_5 = new JLabel(" \u5F53\u524D:0");
		panel_6.add(lblNewLabel_5);
		
		 lblNewLabel_23 = new JLabel("\u5DF2\u9605:0");
		panel_6.add(lblNewLabel_23);
		
		 lblNewLabel_24 = new JLabel("\u672A\u9605:0");
		panel_6.add(lblNewLabel_24);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.SOUTH);
		
		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7);
		
		JLabel lblNewLabel_3 = new JLabel("\u9519\u8BEF\u8868:");
		panel_7.add(lblNewLabel_3);
		
		
		 comboBox_1 = new JComboBox();
		 comboBox_1.addItemListener(new ItemListener() {
		 	public void itemStateChanged(ItemEvent e) {
		 		
		 		changeValueByError();
		 	}
		 });
		
	
		panel_7.add(comboBox_1);
		
		 lblNewLabel_4 = new JLabel("0\u5F20");
		panel_7.add(lblNewLabel_4);
		
		JButton btnNewButton_2 = new JButton("\u4E0A\u4E00\u5F20");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selectNum=comboBox_1.getSelectedIndex();
				if(selectNum>0) {
					comboBox_1.setSelectedIndex(selectNum-1);
				}
			}
		});
		panel_7.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("\u4E0B\u4E00\u5F20");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(errorMap==null) {
					return ;
				}
				
				int selectNum=comboBox_1.getSelectedIndex();
				if(selectNum<errorMap.size()-1) {
					comboBox_1.setSelectedIndex(selectNum+1);
				}
			}
		});
		panel_7.add(btnNewButton_3);
		
		 lblNewLabel_6 = new JLabel(" \u5F53\u524D:0");
		panel_7.add(lblNewLabel_6);
		
		 lblNewLabel_16 = new JLabel("\u5DF2\u9605:0");
		panel_7.add(lblNewLabel_16);
		
	   lblNewLabel_17 = new JLabel("\u672A\u9605:0");
		panel_7.add(lblNewLabel_17);
		
		JPanel panel_8 = new JPanel();
		panel_2.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_13 = new JPanel();
		panel_8.add(panel_13, BorderLayout.NORTH);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_15 = new JPanel();
		panel_13.add(panel_15);
		
		JPanel panel_19 = new JPanel();
		panel_15.add(panel_19);
		
		JLabel lblNewLabel_7 = new JLabel("\u5F53\u524D\u8868\u540D:");
		panel_19.add(lblNewLabel_7);
		
		textField = new JTextField();
		panel_19.add(textField);
		textField.setColumns(30);
		
		JPanel panel_16 = new JPanel();
		panel_13.add(panel_16, BorderLayout.EAST);
		
		JPanel panel_17 = new JPanel();
		panel_16.add(panel_17);
		
		JButton btnNewButton_7 = new JButton("\u67E5\u770B\u5F53\u524D\u8868\u7ED3\u6784(\u81EA\u52A8\u590D\u5236)");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(String key:tableList.keySet()) {
					List<TableBean> tableBeans=tableList.get(key);
					for(TableBean tableBean :tableBeans) {
						if(tableBean.getName().trim().equals(textField.getText().trim())) {
							List<Ziduan> ziduans=tableBean.getZiduans();
							Object[] colObjects=new Object[] {"表名","序号","字段名","字段类型","字段长度","小数","主键","是否为空","默认值"};
							Object[][] rowsObjects=new Object[ziduans.size()][colObjects.length];
							for(int i=0;i<ziduans.size();i++) {
								rowsObjects[i][0]=tableBean.getName();
								rowsObjects[i][1]=i+1;
								rowsObjects[i][2]=ziduans.get(i).getName();
								rowsObjects[i][3]=ziduans.get(i).getType();
								rowsObjects[i][4]=ziduans.get(i).getLength();
								rowsObjects[i][5]=ziduans.get(i).getXiaoshu();
								rowsObjects[i][6]=ziduans.get(i).getIsKey();
								rowsObjects[i][7]=ziduans.get(i).getIsEmtpy();
								rowsObjects[i][8]=ziduans.get(i).getDefaultValue();
							}
					 		  model=new DefaultTableModel(rowsObjects,colObjects);
						      table.setModel(model);
						      return ;
						}
					}
					alert("当前表不存在："+textField.getText().trim(), 0);
				}
				
				
			}
		});
		panel_17.add(btnNewButton_7);
		
		JPanel panel_18 = new JPanel();
		panel_16.add(panel_18);
		
		JButton btnNewButton_8 = new JButton("\u67E5\u770B\u5F53\u524D\u6BD4\u5BF9\u7ED3\u679C(\u81EA\u52A8\u590D\u5236)");
		panel_18.add(btnNewButton_8);
		
		JPanel panel_14 = new JPanel();
		panel_8.add(panel_14, BorderLayout.CENTER);
		panel_14.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_14.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			colomns
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, Object.class, Object.class,Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
	
		
		JPanel panel = new JPanel();
		panel_20.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_9 = new JPanel();
		panel.add(panel_9, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_9.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("\u4E0D\u5B58\u5728\u7684\u8868:");
		panel_1.add(lblNewLabel);
		
		 comboBox_2 = new JComboBox();
		 comboBox_2.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		if(comboBox_2.getSelectedIndex()!=0) {
		 			copy(comboBox_2.getSelectedItem().toString());
		 		}
		 	}
		 });
		panel_1.add(comboBox_2);
		
		 lblNewLabel_15 = new JLabel("0\u5F20");
		panel_1.add(lblNewLabel_15);
		
		JPanel panel_10 = new JPanel();
		panel_9.add(panel_10);
		
		JButton btnNewButton_4 = new JButton("\u67E5\u770B\u6240\u6709\u6B63\u786E\u8868\u7ED3\u679C");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int allNum=0;
				for(String key:rightMap.keySet()) {
					List<ResultBean> resultBeans=rightMap.get(key);
					if(resultBeans==null) {
						continue;
					}
					allNum+=resultBeans.size()+1;
				}
				Object[][] rows=new Object[allNum][colomns.length];
				int beforeNum=0;
				for(String key:rightMap.keySet()) {
					List<ResultBean> resultBeans=rightMap.get(key);
					if(resultBeans==null) {
						continue;
					}
					for(int i=0;i<resultBeans.size();i++) {
			 			ResultBean bean=resultBeans.get(i);
			 			rows[beforeNum][0]=bean.getTableName();
			 			rows[beforeNum][1]=bean.getId();
			 			rows[beforeNum][2]=bean.getName();
			 			rows[beforeNum][3]=bean.getType();
			 			rows[beforeNum][4]=bean.getWordValue();
			 			rows[beforeNum][5]=bean.getDatabaseValue();
			 			rows[beforeNum][6]=bean.getError();
						beforeNum++;
			 		}
					for(int i=0;i<colomns.length;i++) {
						rows[beforeNum][i]="";
					}
					beforeNum++;
				}		 		
		 		  model=new DefaultTableModel(rows,colomns);
			      table.setModel(model);
			}
		});
		panel_10.add(btnNewButton_4);
		
		JPanel panel_12 = new JPanel();
		panel_9.add(panel_12);
		
		JButton btnNewButton_6 = new JButton("\u67E5\u770B\u6240\u6709\u9519\u8BEF\u8868\u7ED3\u679C");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int allNum=0;
				for(String key:errorMap.keySet()) {
					List<ResultBean> resultBeans=errorMap.get(key);
					if(resultBeans==null) {
						continue;
					}
					allNum+=resultBeans.size()+1;
				}
				Object[][] rows=new Object[allNum][colomns.length];
				int beforeNum=0;
				int notReNum=0;
				for(String key:errorMap.keySet()) {
					List<ResultBean> resultBeans=errorMap.get(key);
					if(resultBeans==null) {
						continue;
					}
					int beforeId=0;

					for(int i=0;i<resultBeans.size();i++) {
			 			ResultBean bean=resultBeans.get(i);
			 			if(beforeId!=bean.getId()) {
			 				beforeId=bean.getId();
			 			}else {
							
						}
					}
					 beforeId=0;
					boolean isRe=false;
					for(int i=0;i<resultBeans.size();i++) {
			 			ResultBean bean=resultBeans.get(i);
			 		
			 			if(beforeId!=bean.getId()) {
			 				beforeId=bean.getId();
			 				notReNum++;
			 				isRe=false;
			 			}
			 	
			 			rows[beforeNum][0]=bean.getTableName();
			 			rows[beforeNum][1]=bean.getId();
			 			rows[beforeNum][2]=bean.getName();
			 			rows[beforeNum][3]=bean.getType();
			 			rows[beforeNum][4]=bean.getWordValue();
			 			rows[beforeNum][5]=bean.getDatabaseValue();
			 			rows[beforeNum][6]=bean.getError();
						beforeNum++;
			 		}
					for(int i=0;i<colomns.length;i++) {
						rows[beforeNum][i]="";
					}
					beforeNum++;
				}
				Object[][] newRows=new Object[notReNum][colomns.length];
	
				
		 		  model=new DefaultTableModel(rows,colomns);
			      table.setModel(model);
			
			}
		});
		panel_12.add(btnNewButton_6);
		
		JPanel panel_21 = new JPanel();
		panel_21.setBorder(new LineBorder(Color.LIGHT_GRAY));
		frame.getContentPane().add(panel_21, BorderLayout.EAST);
		panel_21.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_86 = new JPanel();
		panel_21.add(panel_86, BorderLayout.NORTH);
		panel_86.setLayout(new BoxLayout(panel_86, BoxLayout.X_AXIS));
		
		JPanel panel_23 = new JPanel();
		panel_86.add(panel_23);
		panel_23.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_24 = new JPanel();
		panel_23.add(panel_24, BorderLayout.NORTH);
		
		JPanel panel_26 = new JPanel();
		panel_24.add(panel_26);
		
		JLabel lblNewLabel_9 = new JLabel("\u6570\u636E\u6BD4\u5BF9");
		panel_26.add(lblNewLabel_9);
		
		JPanel panel_25 = new JPanel();
		panel_23.add(panel_25, BorderLayout.CENTER);
		
		JPanel panel_27 = new JPanel();
		panel_25.add(panel_27);
		
		JLabel lblNewLabel_10 = new JLabel("\u6BD4\u5BF9\u6587\u6863\u9009\u62E9:");
		panel_27.add(lblNewLabel_10);
		
		 multiComboBox = new MultiComboBox((Object[]) null);
		multiComboBox.updateValue(getModelFile());
		panel_27.add(multiComboBox);
		
		JButton btnNewButton_9 = new JButton("\u6BD4\u5BF9");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				List<Model> models = getSelectModel();
				logger.info("数据比对开始,model信息:");
				logger.info(getSelectModel());
				alHasNum = 0;
				useTime = 0;
				for (Model model : models) {
					try {
					bidui(model);
					}catch (Exception e2) {
						// TODO: handle exception
						e2.printStackTrace();
					}
				}
				if(models.size()==0) {
					alert("无扫描到比对的表结构", 0);
				}
			
			}
		});
		panel_27.add(btnNewButton_9);
		
		JPanel panel_22 = new JPanel();
		panel_21.add(panel_22, BorderLayout.CENTER);
		panel_22.setLayout(new BoxLayout(panel_22, BoxLayout.Y_AXIS));
		
		JPanel panel_28 = new JPanel();
		panel_22.add(panel_28);
		panel_28.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_29 = new JPanel();
		panel_28.add(panel_29, BorderLayout.WEST);
		
		JPanel panel_31 = new JPanel();
		panel_29.add(panel_31);
		
		JLabel lblNewLabel_11 = new JLabel("    \u5F53\u524D\u6587\u6863\uFF1A");
		panel_31.add(lblNewLabel_11);
		
		JPanel panel_30 = new JPanel();
		panel_28.add(panel_30, BorderLayout.CENTER);
		
		JPanel panel_32 = new JPanel();
		panel_30.add(panel_32);
		
		 lblNewLabel_12 = new JLabel("                               ");
		lblNewLabel_12.setToolTipText("");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.LEFT);
		panel_32.add(lblNewLabel_12);
		
		JPanel panel_28_1 = new JPanel();
		panel_22.add(panel_28_1);
		panel_28_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_29_1 = new JPanel();
		panel_28_1.add(panel_29_1, BorderLayout.WEST);
		
		JPanel panel_31_1 = new JPanel();
		panel_29_1.add(panel_31_1);
		
		JLabel lblNewLabel_11_1 = new JLabel("     Sheet\u9875\uFF1A");
		panel_31_1.add(lblNewLabel_11_1);
		
		JPanel panel_30_1 = new JPanel();
		panel_28_1.add(panel_30_1, BorderLayout.CENTER);
		
		JPanel panel_32_1 = new JPanel();
		panel_30_1.add(panel_32_1);
		
		 lblNewLabel_12_1 = new JLabel("                               ");
		lblNewLabel_12_1.setToolTipText("");
		lblNewLabel_12_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_32_1.add(lblNewLabel_12_1);
		
		JPanel panel_28_2 = new JPanel();
		panel_22.add(panel_28_2);
		panel_28_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_29_1_1 = new JPanel();
		panel_28_2.add(panel_29_1_1, BorderLayout.WEST);
		
		JPanel panel_31_1_1 = new JPanel();
		panel_29_1_1.add(panel_31_1_1);
		
		JLabel lblNewLabel_11_1_1 = new JLabel("    \u6B63\u5728\u6BD4\u5BF9\uFF1A");
		panel_31_1_1.add(lblNewLabel_11_1_1);
		
		JPanel panel_30_1_1 = new JPanel();
		panel_28_2.add(panel_30_1_1, BorderLayout.CENTER);
		
		JPanel panel_32_1_1 = new JPanel();
		panel_30_1_1.add(panel_32_1_1);
		
		 lblNewLabel_12_1_1 = new JLabel("                               ");
		lblNewLabel_12_1_1.setToolTipText("");
		lblNewLabel_12_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_32_1_1.add(lblNewLabel_12_1_1);
		
		JPanel panel_28_3 = new JPanel();
		panel_22.add(panel_28_3);
		panel_28_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_29_1_1_1 = new JPanel();
		panel_28_3.add(panel_29_1_1_1, BorderLayout.WEST);
		
		JPanel panel_31_1_1_1 = new JPanel();
		panel_29_1_1_1.add(panel_31_1_1_1);
		
		JLabel lblNewLabel_11_1_1_1 = new JLabel("    \u5DF2\u6BD4\u5BF9\u8868\uFF1A");
		panel_31_1_1_1.add(lblNewLabel_11_1_1_1);
		
		JPanel panel_30_1_1_1 = new JPanel();
		panel_28_3.add(panel_30_1_1_1, BorderLayout.CENTER);
		
		JPanel panel_32_1_1_1 = new JPanel();
		panel_30_1_1_1.add(panel_32_1_1_1);
		
		 lblNewLabel_12_1_1_1 = new JLabel("                               ");
		lblNewLabel_12_1_1_1.setToolTipText("");
		lblNewLabel_12_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_32_1_1_1.add(lblNewLabel_12_1_1_1);
		
		JPanel panel_28_4 = new JPanel();
		panel_22.add(panel_28_4);
		panel_28_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_29_1_1_1_1 = new JPanel();
		panel_28_4.add(panel_29_1_1_1_1, BorderLayout.WEST);
		
		JPanel panel_31_1_1_1_1 = new JPanel();
		panel_29_1_1_1_1.add(panel_31_1_1_1_1);
		
		JLabel lblNewLabel_11_1_1_1_1 = new JLabel("    \u672A\u6BD4\u5BF9\u8868\uFF1A");
		panel_31_1_1_1_1.add(lblNewLabel_11_1_1_1_1);
		
		JPanel panel_30_1_1_1_1 = new JPanel();
		panel_28_4.add(panel_30_1_1_1_1, BorderLayout.CENTER);
		
		JPanel panel_32_1_1_1_1 = new JPanel();
		panel_30_1_1_1_1.add(panel_32_1_1_1_1);
		
		 lblNewLabel_12_1_1_1_1 = new JLabel("                               ");
		lblNewLabel_12_1_1_1_1.setToolTipText("");
		lblNewLabel_12_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_32_1_1_1_1.add(lblNewLabel_12_1_1_1_1);
		
		JPanel panel_28_5 = new JPanel();
		panel_22.add(panel_28_5);
		panel_28_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_29_1_1_1_1_1 = new JPanel();
		panel_28_5.add(panel_29_1_1_1_1_1, BorderLayout.WEST);
		
		JPanel panel_31_1_1_1_1_1 = new JPanel();
		panel_29_1_1_1_1_1.add(panel_31_1_1_1_1_1);
		
		JLabel lblNewLabel_11_1_1_1_1_1 = new JLabel(" \u603B\u7684\u5DF2\u6BD4\u8F83\u8868\uFF1A");
		panel_31_1_1_1_1_1.add(lblNewLabel_11_1_1_1_1_1);
		
		JPanel panel_30_1_1_1_1_1 = new JPanel();
		panel_28_5.add(panel_30_1_1_1_1_1, BorderLayout.CENTER);
		
		JPanel panel_32_1_1_1_1_1 = new JPanel();
		panel_30_1_1_1_1_1.add(panel_32_1_1_1_1_1);
		
		 lblNewLabel_12_1_1_1_1_1 = new JLabel("                               ");
		lblNewLabel_12_1_1_1_1_1.setToolTipText("");
		lblNewLabel_12_1_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_32_1_1_1_1_1.add(lblNewLabel_12_1_1_1_1_1);
		
		JPanel panel_28_6 = new JPanel();
		panel_22.add(panel_28_6);
		panel_28_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_29_1_1_1_1_1_1 = new JPanel();
		panel_28_6.add(panel_29_1_1_1_1_1_1, BorderLayout.WEST);
		
		JPanel panel_31_1_1_1_1_1_1 = new JPanel();
		panel_29_1_1_1_1_1_1.add(panel_31_1_1_1_1_1_1);
		
		JLabel lblNewLabel_11_1_1_1_1_1_1 = new JLabel("   \u5F53\u524D\u603B\u7528\u65F6\uFF1A");
		panel_31_1_1_1_1_1_1.add(lblNewLabel_11_1_1_1_1_1_1);
		
		JPanel panel_30_1_1_1_1_1_1 = new JPanel();
		panel_28_6.add(panel_30_1_1_1_1_1_1, BorderLayout.CENTER);
		
		JPanel panel_32_1_1_1_1_1_1 = new JPanel();
		panel_30_1_1_1_1_1_1.add(panel_32_1_1_1_1_1_1);
		
		 lblNewLabel_12_1_1_1_1_1_1 = new JLabel("                               ");
		lblNewLabel_12_1_1_1_1_1_1.setToolTipText("");
		lblNewLabel_12_1_1_1_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel_32_1_1_1_1_1_1.add(lblNewLabel_12_1_1_1_1_1_1);
		
		JPanel panel_33 = new JPanel();
		panel_33.setBorder(new LineBorder(Color.LIGHT_GRAY));
		frame.getContentPane().add(panel_33, BorderLayout.SOUTH);
		
		JPanel panel_36 = new JPanel();
		panel_33.add(panel_36);
		
		JLabel lblNewLabel_13 = new JLabel("\u5F53\u524D\u754C\u9762\u4E3A\u6570\u636E\u7ED3\u679C\u5C55\u793A\u754C\u9762,\u5982\u9700\u914D\u7F6E\u8BF7\u8F6C\u79FB\u914D\u7F6E\u754C\u9762");
		panel_36.add(lblNewLabel_13);
		
		JPanel panel_37 = new JPanel();
		panel_33.add(panel_37);
		
		JButton btnNewButton_10 = new JButton("\u5207\u6362\u914D\u7F6E\u754C\u9762");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							window2.setVisible(true);
							setVisible(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		panel_37.add(btnNewButton_10);
		
		JPanel panel_34 = new JPanel();
		panel_34.setBorder(new LineBorder(Color.LIGHT_GRAY));
		frame.getContentPane().add(panel_34, BorderLayout.NORTH);
		panel_34.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_40 = new JPanel();
		panel_34.add(panel_40, BorderLayout.WEST);
		
		JPanel panel_38 = new JPanel();
		panel_40.add(panel_38);
		
		JLabel lblNewLabel_8 = new JLabel("\u6210\u529F\u7684sheet\u9875");
		panel_38.add(lblNewLabel_8);
		
		 comboBox_3 = new JComboBox();
		panel_38.add(comboBox_3);
		
		 lblNewLabel_18 = new JLabel("10\u9875");
		panel_38.add(lblNewLabel_18);
		
		JPanel panel_42 = new JPanel();
		panel_40.add(panel_42);
		
		JLabel lblNewLabel_20 = new JLabel("\u8868\u540D\uFF1A");
		panel_42.add(lblNewLabel_20);
		
		JComboBox comboBox_5 = new JComboBox();
		panel_42.add(comboBox_5);
		
		JLabel lblNewLabel_21 = new JLabel("10\u5F20");
		panel_42.add(lblNewLabel_21);
		
		JPanel panel_41 = new JPanel();
		panel_34.add(panel_41, BorderLayout.EAST);
		
		JPanel panel_39 = new JPanel();
		panel_41.add(panel_39);
		
		JLabel lblNewLabel_14 = new JLabel("\u5931\u8D25\u7684sheet\u9875");
		panel_39.add(lblNewLabel_14);
		
		 comboBox_4 = new JComboBox();
		panel_39.add(comboBox_4);
		
		 lblNewLabel_19 = new JLabel("10\u9875");
		panel_39.add(lblNewLabel_19);
		
		JPanel panel_35 = new JPanel();
		frame.getContentPane().add(panel_35, BorderLayout.WEST);
	}
	public Object[] getModelFile() {
		String relativelyPath=System.getProperty("user.dir"); 
		systemFile = new File(relativelyPath+"/autoTable/");
		File file = new File(systemFile.getAbsoluteFile() + "/modelConfig");
		return file.list();
	}
	
	
	public void bidui(Model model) {

		Map<String, String> map = model.getSheetMap();
		bd = new SwingWorker() {
			@Override
			protected Object doInBackground() throws IOException {
				// TODO Auto-generated method stub
				SwingWorker dh = new SwingWorker() {

					@Override
					protected Object doInBackground() {
						// TODO Auto-generated method stub

						while (!bd.isDone()) {

							lblNewLabel_12_1_1_1_1_1_1.setText(useTime / 1000.0 + "s");
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								logger.error("数据比对计时器发生异常:", e);
							}
							useTime += 100;
						}
						return null;
					}
				};
				dh.execute();

				 tableList = null;
				logger.debug("连接成功");
				logger.debug("解析文件:" + model.getName());
				try {
					File excelFile = new File(model.getFile());
					tableList = DealData.dealExcel(excelFile, model);
					
					List<String> varPassList=DealData.getRightSheetList();
					List<String> varFailList=DealData.getErrorSheetList();
					if(varPassList==null) {
						varPassList=new LinkedList<String>(); 
					}
					comboBox_3.setModel(new DefaultComboBoxModel(varPassList.toArray()));
					 lblNewLabel_18.setText(varPassList.size()+"张");
					 if(varFailList==null) {
						 varFailList=new LinkedList<String>(); 
						}
					comboBox_4.setModel(new DefaultComboBoxModel(varFailList.toArray()));
					 lblNewLabel_19.setText(varFailList.size()+"张");
					
				} catch (IOException e) {
					logger.error("解析文件出错:" + model.getName(), e);
					logger.debug("解析文件出错:" + model.getName(), e);
				}
				if (tableList == null) {
					return null;
				}
				logger.debug("解析文件完成");
				rightMap=new LinkedHashMap<>();
				rightMap.put("", null);
				errorMap=new LinkedHashMap<>();
				errorMap.put("", null);
				nullTableMap=new LinkedHashMap<>();
				nullTableMap.put("", null);

				for (String str : tableList.keySet()) {
					SheetConfig sheetConfig = DealData.getSheetConfigByName(map.get(str));
					String connectName = sheetConfig.getConnectName();
					logger.debug("获取model的数据库连接配置");
					logger.debug("获取JDBC连接中....");
					JDBC jdbc = null;
					DatabaConnectBean connectBean = DealData.getConnectBeanByName(connectName);
					int nowNumber = 0;
					logger.debug("开始连接数据库比对sheet页为:" + str + "的表结构");
					if(tableList.get(str)==null) {
						continue;
					}
					for (TableBean bean : tableList.get(str)) {
						try {
							jdbc = DealData.getJDBC(connectBean);
						} catch (Exception e) {
							// TODO: handle exception
							logger.error("连接数据库出错:" + model.getName(), e);
						}
						if (jdbc == null) {
							logger.error("获取连接失败");
							return null;
						}
						String database = connectBean.getDATABASE();
						String tableName = bean.getName();
						Object[] columnsObjects = bean.getColomn();
						logger.debug("sheet页:" + str + "----文档的表配置信息为:" + bean);
						String[] sqlStrings = null;
						try {
							sqlStrings = DealData.getSQL(database, tableName, connectBean.getTYPE(), columnsObjects);
						} catch (Exception e) {
							// TODO: handle exception
							logger.error("查询数据库发生错误", e);
						}
						JSONObject result =null;
						try {
						 result = jdbc.select(sqlStrings[0]);// 非主键
						 
						}catch(Exception e) {
							logger.error("执行数据库查询失败,"+sqlStrings[0],e);
							alert("数据库查询失败,详情看日记", 0);
							break;
						}
					
						JSONObject result2 = null;// 主键
						if (sqlStrings[1] != null) {
							result2 = jdbc.select(sqlStrings[1]);// 主键
							logger.debug("数据库查询到主键:" + result2);
						}
						jdbc.close();
						TableBean table = new TableBean();
						List<Ziduan> zbeans = new LinkedList<>();
						String[] keys = null;
						if (result2 != null) {
							JSONArray array = result2.getJSONArray("data");
							if(array!=null) {
							keys = new String[array.size()];
							for (int i = 0; i < array.size(); i++) {
								keys[i] = array.getJSONObject(i).getString("0");
							}
							}
						}
						if (result != null) {
							logger.debug("数据库查询到字段结构:" + result);
							
							JSONArray array = result.getJSONArray("data");
							List<String> colomnList=(List<String>) result.get("colomnName");
							try {
								if(array==null) {
									array=new JSONArray();
								}
							for (int i = 0; i < array.size(); i++) {
								JSONObject o1 = array.getJSONObject(i);
								Ziduan ziduan = new Ziduan();
								boolean dealHiveFlag=false;
								
								List<String> varList=new LinkedList<>();

								for(int k=0;k<columnsObjects.length; k++) {
									String keyString = columnsObjects[k].toString();
									if (!keyString.equals("isKey")) {
										varList.add(keyString);
									}
								}
								String zdTypeString=null;
								int tsFlag=0;//特殊处理oracle的长度取自DATA_PRECISION，小数需要偏移+1
								for (int k = 0; k <varList.size(); k++) {
									String keyString = varList.get(k);
									if(dealHiveFlag==true) {
										break;
									}
									
									switch (keyString) {
									case "name": {
										ziduan.setName(o1.getString(k+tsFlag + ""));
										if (keys != null) {
											if (isHas(keys, o1.get(k + ""))) {
												ziduan.setIsKey(true);
											} else {
												ziduan.setIsKey(false);
											}
										}
										break;
									}
									case "type": {
										if(connectBean.getTYPE().equals("hive")) {
											try {
											String varValue=o1.getString(k + "");
											if(varValue==null) {
												break;
											}
											String varType=varValue.replaceAll("\\(.*", "").trim();
										
											ziduan.setType(varType);
										
											if(varValue.indexOf("(")!=-1) {
												String varLength=varValue.replaceAll(".*\\(|,.*", "").trim();
												ziduan.setLength(varLength);
											}
											
											if(varValue.indexOf(",")!=-1) {
												Object varXS=0;
												varXS=varValue.replaceAll(".*,|\\)", "").trim();
												ziduan.setXiaoshu(varXS);
											}
											dealHiveFlag=true;
											}catch (Exception e) {
												// TODO: handle exception
												e.printStackTrace();
												logger.error("解析hive的sql查询异常",e);
											}
											break;
										}
										ziduan.setType(o1.getString(k +tsFlag+ ""));
										zdTypeString=o1.getString(k+tsFlag + "");
										break;
									}
									case "length": {
										if (zdTypeString!=null) {  
											if(zdTypeString.equalsIgnoreCase("number")||zdTypeString.equalsIgnoreCase("int")||zdTypeString.equalsIgnoreCase("decimal")
													||zdTypeString.equalsIgnoreCase("number")||zdTypeString.equalsIgnoreCase("float")
													||zdTypeString.equalsIgnoreCase("number")||zdTypeString.equalsIgnoreCase("double")) {
												for(int j=0;j<colomnList.size();j++ ) {
													if (colomnList.get(j).equalsIgnoreCase("DATA_PRECISION")||colomnList.get(j).equalsIgnoreCase("NUMERIC_PRECISION")) {
														ziduan.setLength(o1.getString(j +tsFlag+ ""));
														tsFlag++;
													}
												}
											}
										else {
											ziduan.setLength(o1.getString(k +tsFlag+ ""));
										}
										}
										else {
											ziduan.setLength(o1.getString(k +tsFlag+ ""));
										}
										break;
									}
									case "xiaoshu": {
										ziduan.setXiaoshu(o1.getString(k +tsFlag+ ""));
										break;
									}
									case "isEmtpy": {
										ziduan.setIsEmtpy(o1.getString(k +tsFlag+ ""));
										break;
									}
									case "defaultValue": {
										ziduan.setDefaultValue(o1.getString(k +tsFlag+ ""));
										break;
									}
							

									default:
										throw new IllegalArgumentException("Unexpected value: " + keyString);
									}
								}
								zbeans.add(ziduan);
							}
							}catch (Exception e) {
								// TODO: handle exception
								logger.error("处理sql结果集错误",e);
							}
							table.setZiduans(zbeans);
						}
					
					
						logger.debug("参与数据比对的文档字段:" + bean.getZiduans());
						logger.debug("参与数据比对的数据库字段:" + table.getZiduans());
						try {
							List<Ziduan>adapterList=null;
							Map<String, String> adapter=sheetConfig.getSelectAdapte();
							for(String key:adapter.keySet()) {
								if(key.equals("恒生字典")) {
									adapterList=HSTYPE.getFileZiduan(connectBean.getTYPE());
								}
							}
						       isRight(tableName,bean.getZiduans(), table.getZiduans(),
						        		adapter, columnsObjects,adapterList);
						}catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					
				
						alHasNum++;
						nowNumber++;
						int maxLeng=40;
						lblNewLabel_12.setText(maxLabel(model.getName(),maxLeng));
						lblNewLabel_12.setToolTipText(model.getName());
						
						lblNewLabel_12_1.setText(maxLabel(str, maxLeng));
						lblNewLabel_12_1.setToolTipText(str);

						lblNewLabel_12_1_1_1_1.setText(tableList.get(str).size() - nowNumber + "张");
						 lblNewLabel_12_1_1_1 .setText(nowNumber + "张");
						 lblNewLabel_12_1_1_1_1_1 .setText(alHasNum + "张");
						lblNewLabel_2.setText(rightMap.size()-1 + "张");
						 lblNewLabel_4.setText(errorMap.size()-1 + "张");
						 lblNewLabel_15.setText(nullTableMap.size()-1+"张");
						 lblNewLabel_12_1_1.setText(maxLabel(tableName, maxLeng));
						 lblNewLabel_12_1_1.setToolTipText(tableName);
						 comboBox_2.setModel(new DefaultComboBoxModel(nullTableMap.keySet().toArray()));
						comboBox.setModel(new DefaultComboBoxModel(rightMap.keySet().toArray()));
						comboBox_1.setModel(new DefaultComboBoxModel(errorMap.keySet().toArray()));
						
					}					
				}

				return null;
			}
		};
		bd.execute();
	}
	public String maxLabel(String str,int num) {
		if(str.length()<num) {
			return str;
		}
		return str.substring(0, num-3)+"...";
	}
	
	public void isRight(String tableName,List<Ziduan> list1, List<Ziduan> list2, Map<String, String> adapter, Object[] values,List<Ziduan> adapterList) {	
		List<ResultBean> passList=new LinkedList<>();
		List<ResultBean> failList=new LinkedList<>();
		boolean isHasTable = true;
		int nowId=0;
		for (Ziduan z1 : list1) {
			nowId++;
			logger.debug("判断"+z1+"中");
			if (!isHasTable) {
				break;
			}
			JSONObject j1 = JSONObject.parseObject(z1.toString());
			boolean isJX = true;
			for (String key : j1.keySet()) {
				ResultBean bean=new ResultBean();
				bean.setTableName(tableName);
				logger.debug("判断属性:"+key+"中......");
				if (!isJX) {
					break;
				}
				if (!isHas(values, key)) {
					continue;
				}
				if (key.equals("type")) {
					String typeString = j1.getString("type");
					
					if(adapterList!=null) {
						for(Ziduan zd:adapterList) {
							if(zd.getName().equalsIgnoreCase(typeString.trim())) {
								j1.put(key, zd.getType().replaceAll("\\(.*", ""));
								break;
							}
						}
					}
					for (String k : adapter.keySet()) {
						String typeListString = adapter.get(k);
						String[] tStrings = typeListString.split(",");
						for (String s : tStrings) {
							if (s.trim().equalsIgnoreCase(typeString.trim())) {
								j1.put(key, k);
								break;
							}
						}
					}
				}
				if (list2 == null || list2.size() == 0) {
					passList=null;
					failList=null;
					isJX = false;
					isHasTable = false;
					break;
				}
				for (int z = 0; z < list2.size(); z++) {
					Ziduan z2 = list2.get(z);
					JSONObject j2 = JSONObject.parseObject(z2.toString());
					if (j1.getString("name").equalsIgnoreCase(j2.getString("name"))) {
						logger.debug("名字一样,比较key:"+key+"中："+j1.getString(key).equalsIgnoreCase(j2.getString(key)));
						if (j1.getString(key).equalsIgnoreCase(j2.getString(key))) {
							bean.setDatabaseValue(j2.getString(key));
							bean.setName( j1.getString("name"));
							bean.setType(typeAdapter(key));
							bean.setWordValue( j1.getString(key));
							bean.setId(nowId);
							break;
						} else {
							bean.setDatabaseValue(j2.getString(key));
							bean.setName( j1.getString("name"));
							bean.setType(typeAdapter(key));
							bean.setWordValue( j1.getString(key));
							bean.setId(nowId);
							bean.setError("属性:"+typeAdapter(key)+"不一致");
						//	isJX = false;   注释掉，一个字段需要把多个属性错误问题给爆出来
							break;
						}
					}
					if (z == list2.size() - 1) {
						bean.setName(j1.getString("name"));
						bean.setDatabaseValue("");
						bean.setWordValue( j1.getString(key));
						bean.setError("字段不存在");
						bean.setId(nowId);
						isJX = false;
					}
				}
				if(passList==null) {
					break;
				}
				if(bean.getError()!=null) {
					failList.add(bean);
					
				}
				else {
					passList.add(bean);

				}
			}
			
		
		}
		
		if(passList==null) {
			nullTableMap.put(tableName,null);
			logger.debug( tableName+"数据比对后的结果:"+"表不存在");
		}
		else if(failList.size()!=0) {
			errorMap.put(tableName, failList);
			lblNewLabel_17.setText("未阅:"+(errorMap.size()-1));
			logger.debug(tableName+ "数据比对后的结果,(存在"+failList.size()+"条错误字段)：" +failList);

		}
		else {
			rightMap.put(tableName, passList);
			lblNewLabel_24.setText("未阅:"+(rightMap.size()-1));
			logger.debug(tableName+ "数据比对后的结果,(都正确,有"+passList.size()+"条字段：" +passList);
		}


	}
	
	public void changeValueByRight() {

 		if(alreadyRead==null) {
 			alreadyRead=new LinkedHashSet<>();
 		}
 		if(comboBox.getSelectedIndex()!=0) {
 			alreadyRead.add(comboBox.getSelectedIndex()-1);
 			lblNewLabel_23.setText("已阅:"+alreadyRead.size());
 			lblNewLabel_24.setText("未阅:"+(rightMap.size()-alreadyRead.size()-1));
 		}
 		lblNewLabel_5.setText(" 当前:"+comboBox.getSelectedIndex());
		List<ResultBean> resultBeans=rightMap.get(comboBox.getSelectedItem());
 		textField.setText(comboBox.getSelectedItem().toString());
 		textField.setToolTipText(comboBox.getSelectedItem().toString());

		if(resultBeans==null) {
			  model=new DefaultTableModel(null,colomns);
		      table.setModel(model);
		      return;
		}
	
 		Object[][] rows=new Object[resultBeans.size()][colomns.length];
 		int beforeNum=1;
 		for(int i=0;i<resultBeans.size();i++) {
 			ResultBean bean=resultBeans.get(i);
 			rows[i][0]=bean.getTableName();
 			rows[i][1]=bean.getId();
 			rows[i][2]=bean.getName();
 			rows[i][3]=bean.getType();
 			rows[i][4]=bean.getWordValue();
 			rows[i][5]=bean.getDatabaseValue();
 			rows[i][6]=bean.getError();
 		
 			
 		}
 		
 		  model=new DefaultTableModel(rows,colomns);
	      table.setModel(model);
	 
 	
	}
	
	public void changeValueByError() {

		if(alreadyRead2==null) {
 			alreadyRead2=new LinkedHashSet<>();
 		}
 		if(comboBox_1.getSelectedIndex()!=0) {
 			alreadyRead2.add(comboBox_1.getSelectedIndex()-1);
 			lblNewLabel_16.setText("已阅:"+alreadyRead2.size());
 			lblNewLabel_17.setText("未阅:"+(errorMap.size()-alreadyRead2.size()-1));
 		}
 		lblNewLabel_6.setText(" 当前:"+comboBox_1.getSelectedIndex());
 		
 		List<ResultBean> resultBeans=errorMap.get(comboBox_1.getSelectedItem());
 		textField.setText(comboBox_1.getSelectedItem().toString());
 		textField.setToolTipText(comboBox_1.getSelectedItem().toString());

 		if(resultBeans==null) {
			  model=new DefaultTableModel(null,colomns);
		      table.setModel(model);
		      return;
		}
 		Object[][] rows=new Object[resultBeans.size()][colomns.length];
 		for(int i=0;i<resultBeans.size();i++) {
 			ResultBean bean=resultBeans.get(i);
 			rows[i][0]=bean.getTableName();
 			rows[i][1]=bean.getId();
 			rows[i][2]=bean.getName();
 			rows[i][3]=bean.getType();
 			rows[i][4]=bean.getWordValue();
 			rows[i][5]=bean.getDatabaseValue();
 			rows[i][6]=bean.getError();
 		}
 		
	        model=new DefaultTableModel(rows,colomns);
		      table.setModel(model);
		 
 	
	}
	
	
	public String  typeAdapter(String str) {
		switch(str) {
		case "type":return "类型";
		case "length":return "长度";
		case  "name": return  "名称";
		case  "isKey": return  "主键";
		case "xiaoshu": return "小数";
		case "isEmpty": return "是否为空";
		case "defaultValue": return "默认值";
		}
		return "无法识别的属性";
	}
	
	public List<Model> getSelectModel() {
		Object[] values = multiComboBox.getSelectedValues();
		List<Model> models = new LinkedList<>();
		for (Object s : values) {
			File file = new File(systemFile.getAbsoluteFile() + "/modelConfig/" + s);
			Model model = null;
			try {
				model = JSONObject.parseObject(FileUtils.getStr(file), Model.class);
			} catch (IOException e) {
				logger.error("获取选择的model文件，解析发生错误:", e);
			}
			models.add(model);
		}
		return models;
	}
	
	
	 public   boolean isHas(Object[] os, Object name) {
			for (Object o : os) {
				if (o.toString().equals(name.toString())) {
					return true;
				}
			}
			return false;
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
	/*
	 * 复制功能
	 */
	public  void copy(String text) {
		Clipboard  clipboard =Toolkit.getDefaultToolkit().getSystemClipboard();  //得到系统剪贴板  
	     StringSelection selection = new StringSelection(text);  
	     clipboard.setContents(selection, null);  
	}

}
