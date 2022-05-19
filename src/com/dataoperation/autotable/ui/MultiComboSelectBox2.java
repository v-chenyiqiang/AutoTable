package com.dataoperation.autotable.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;

import org.apache.commons.lang.ObjectUtils.Null;

import com.cloudera.hiveserver1.sqlengine.aeprocessor.aebuilder.bool.AEBooleanExprOuterRefProcessor;
 
/**
 * 下拉复选框组件
 *
 */
public class MultiComboSelectBox2 extends JComponent implements ActionListener {
 
	private Map<String, String> values;
	private MultiPopup popup;
	private JTextField editor;
	protected JButton arrowButton;
	private String leftName;
	private String rightName;
	
	
 
	public MultiComboSelectBox2(Map<String, String> value,String leftName,String rightName) {
		this.leftName=leftName;
		this.rightName=rightName;
		if(value==null) {
			value=new LinkedHashMap<String, String>();
			value.put("3", "3");
		}
		values = value;
		initComponent();
	}
	
	public void updateValue(Map<String, String> value){
	
		editor.setText("1");
		values=value;
		popup.updateModel(value);
	}
	public void updateSelect(Map<String, String> value){
		if(value==null) {
			value=new LinkedHashMap<>();
			value.put("", "");
		}
		editor.setText(value.toString());
		values=value;
		popup.updateSelect(value);
	}
	
	public void updateModel(Map<String, String> value) {
		editor.setText("");
		values=value;
		popup.updateModel(value);
	}
	
 
	private void initComponent() {
		this.setLayout(new BorderLayout());
		popup = new MultiPopup(values);
		editor = new JTextField();
		editor.setBackground(Color.WHITE);
		editor.setEditable(false);
		editor.setPreferredSize(new Dimension(140, 22));
		editor.addActionListener(this);
		arrowButton = createArrowButton();
		arrowButton.addActionListener(this);
		add(editor, BorderLayout.WEST);
		add(arrowButton, BorderLayout.CENTER);
	}

	//获取选中的数据
	public Map<String, String> getSelectedValues() {
		return popup.getSelectedValues();
	}
	public Object[] getSelectIndex() {
		return popup.getSelectedIndex();
	}
	public Map<String, String> getValues(){
		return popup.values;
	}
 
	//设置需要选中的值
	public void setSelectValues(Map<String, String> map) {

		popup.setSelectValues(map);
		setText(map);
	}
    
	private void setText(Map<String, String> map) {
		if (map.size() > 0) {
			editor.setText(map.toString());
		}else {
			editor.setText("");
		}
	}
 
 
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (!popup.isVisible()) {
			popup.show(this, 0, getHeight());
		}
	}
 
	protected JButton createArrowButton() {
		JButton button = new BasicArrowButton(BasicArrowButton.SOUTH, UIManager.getColor("ComboBox.buttonBackground"),
				UIManager.getColor("ComboBox.buttonShadow"), UIManager.getColor("ComboBox.buttonDarkShadow"),
				UIManager.getColor("ComboBox.buttonHighlight"));
		button.setName("ComboBox.arrowButton");
		return button;
	}

	
	//内部类MultiPopup
	
	public class MultiPopup extends JPopupMenu implements ActionListener {
		private Map<String, String> values;
		private List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
		private List<JTextField> textBoxList= new ArrayList<JTextField>();
		private JButton commitButton;
		private JButton cancelButton;
		private JPanel checkboxPane;
 
		public MultiPopup(Map<String, String> value) {
			super();
			values = value;
			initComponent();
			setFocusable(true);
		}
		
		
		public void updateSelect(Map<String, String> map) {

			checkboxPane.removeAll();;
			checkBoxList.clear();
			textBoxList.clear();
			Object[] value=values.keySet().toArray();
			for (Object v : value) {
				JCheckBox temp = new JCheckBox(v.toString());
				if(map!=null) {
					for(String s:map.keySet()) {
						if(s.equals(v.toString())) {
						temp.setSelected(true);
						break;
						}
					}
					}
				checkBoxList.add(temp);
				JTextField textField=null;
				
				if(textField==null)
					textField=new JTextField(values.get(v.toString()));
				textBoxList.add(textField);
			}
			for(int i=0;i<16-value.length*2;i++) {
				JTextField textField=new JTextField();
				textBoxList.add(textField);
			}
			if (checkBoxList.get(0)!=null&&checkBoxList.get(0).getText().equals("统一模板")) {
			
				checkBoxList.get(0).addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						if (checkBoxList.get(0).isSelected()) {
						
							for (int i = 1; i < checkBoxList.size(); i++) {
								if (!checkBoxList.get(i).isSelected()) {
									checkBoxList.get(i).setSelected(true);
									textBoxList.get(i).setText(textBoxList.get(0).getText());
								}
							}
						} else {
							for (int i = 1; i < checkBoxList.size(); i++) {
								if (checkBoxList.get(i).isSelected()) {
									checkBoxList.get(i).setSelected(false);
								}
							}
						}
					}
				});
			}
			checkboxPane.setLayout(new GridLayout(9, 2, 3, 3));
			checkboxPane.add(new JLabel(leftName));
			checkboxPane.add(new JLabel(rightName));
			for (int i=0;i<checkBoxList.size();i++) {
				JCheckBox box =checkBoxList.get(i);
				JTextField jTextField=textBoxList.get(i);
				checkboxPane.add(box);
				checkboxPane.add(jTextField);
			}
			for(int i=0;i<16-checkBoxList.size()*2;i++) {
				JTextField jTextField=textBoxList.get(i+checkBoxList.size());
				checkboxPane.add(jTextField);
			}
		}
	
	public void updateModel(Map<String, String> map) {
			checkboxPane.removeAll();;
			checkBoxList.clear();
			textBoxList.clear();
			values=map;
			Object[] value=values.keySet().toArray();
			for (Object v : value) {
				JCheckBox temp = new JCheckBox(v.toString());
				checkBoxList.add(temp);
				JTextField textField=null;

				if(textField==null)
					textField=new JTextField(values.get(v.toString()));
				textBoxList.add(textField);
			}
			for(int i=0;i<20-value.length*2;i++) {
				JTextField textField=new JTextField();
				textBoxList.add(textField);
			}
			if (checkBoxList.get(0)!=null&&checkBoxList.get(0).getText().equals("统一模板")) {
			
				checkBoxList.get(0).addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						if (checkBoxList.get(0).isSelected()) {
						
							for (int i = 1; i < checkBoxList.size(); i++) {
								if (!checkBoxList.get(i).isSelected()) {
									checkBoxList.get(i).setSelected(true);
									textBoxList.get(i).setText(textBoxList.get(0).getText());
								}
							}
						} else {
							for (int i = 1; i < checkBoxList.size(); i++) {
								if (checkBoxList.get(i).isSelected()) {
									checkBoxList.get(i).setSelected(false);
								}
							}
						}
					}
				});
			}
			checkboxPane.setLayout(new GridLayout(11, 2, 3, 3));
			checkboxPane.add(new JLabel(leftName));
			checkboxPane.add(new JLabel(rightName));
			for (int i=0;i<checkBoxList.size();i++) {
				JCheckBox box =checkBoxList.get(i);
				JTextField jTextField=textBoxList.get(i);
				checkboxPane.add(box);
				checkboxPane.add(jTextField);
			}
			for(int i=0;i<20-checkBoxList.size()*2;i++) {
				JTextField jTextField=textBoxList.get(i+checkBoxList.size());
				checkboxPane.add(jTextField);
			}
			
		
		}
		
		
		
		private void initComponent() {
			
			checkboxPane = new JPanel();
			JPanel buttonPane = new JPanel();
			this.setLayout(new BorderLayout());
			Object[] value=values.keySet().toArray();
			for (Object v : value) {
				JCheckBox temp = new JCheckBox(v.toString());
				temp.setSelected(false);
				checkBoxList.add(temp);
				JTextField textField=new JTextField(values.get(v.toString()));
				textBoxList.add(textField);
			}
			/*
			 * 补充
			 */
			for(int i=0;i<20-value.length*2;i++) {
				JTextField textField=new JTextField();
				textBoxList.add(textField);
			}
			if (checkBoxList.get(0)!=null&&checkBoxList.get(0).getText().equals("统一模板")) {
			
				checkBoxList.get(0).addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						if (checkBoxList.get(0).isSelected()) {
						
							for (int i = 1; i < checkBoxList.size(); i++) {
								if (!checkBoxList.get(i).isSelected()) {
									checkBoxList.get(i).setSelected(true);
									textBoxList.get(i).setText(textBoxList.get(0).getText());
								}
							}
						} else {
							for (int i = 1; i < checkBoxList.size(); i++) {
								if (checkBoxList.get(i).isSelected()) {
									checkBoxList.get(i).setSelected(false);
								}
							}
						}
					}
				});
			}
 
			
			checkboxPane.setLayout(new GridLayout(11, 2, 3, 3));
			checkboxPane.add(new JLabel(leftName));
			checkboxPane.add(new JLabel(rightName));
			for (int i=0;i<checkBoxList.size();i++) {
				JCheckBox box =checkBoxList.get(i);
				JTextField jTextField=textBoxList.get(i);
				checkboxPane.add(box);
				checkboxPane.add(jTextField);
			}
			for(int i=0;i<20-checkBoxList.size()*2;i++) {
				JTextField jTextField=textBoxList.get(i+checkBoxList.size());
				checkboxPane.add(jTextField);
			}
			
			commitButton = new JButton("确定");
			commitButton.addActionListener(this);
 
			cancelButton = new JButton("取消");
			cancelButton.addActionListener(this);
 
			buttonPane.add(commitButton);
			buttonPane.add(cancelButton);
			this.add(checkboxPane, BorderLayout.CENTER);
			this.add(buttonPane, BorderLayout.SOUTH);
		}
 
		public void setSelectValues(Map<String, String> map) {
			Object[] values=map.keySet().toArray();
			if (values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					for (int j = 0; j < checkBoxList.size(); j++) {
						if (values[i].equals(checkBoxList.get(j).getText())) {
							checkBoxList.get(j).setSelected(true);
							textBoxList.get(j).setText(map.get(values[j]));
						}
					}
				}
				setText(getSelectedValues());
			}
		}
		public Object[] getSelectedIndex() {
			List<Integer> indexs=new LinkedList<>();
			Object[] value=values.keySet().toArray();
			Map<String, String> map=new LinkedHashMap<String, String>();
			if (checkBoxList.get(0).getText().equals("全选")) {
				if (checkBoxList.get(0).isSelected()) {
					for (int i = 1; i < checkBoxList.size(); i++) {
						map.put(value[i].toString(), textBoxList.get(i).getText());
						indexs.add(i-1);
					}
				} else {
					for (int i = 1; i < checkBoxList.size(); i++) {
						if (checkBoxList.get(i).isSelected()) {
							map.put(value[i].toString(), textBoxList.get(i).getText());
							indexs.add(i-1);
						}
					}
				}
			} else {
				
				for (int i = 0; i < checkBoxList.size(); i++) {
					if (checkBoxList.get(i).isSelected()) {
						map.put(value[i].toString(), textBoxList.get(i).getText());
						indexs.add(i);
					}					
				}
			}
 
			return indexs.toArray();
		}
		public Map<String, String> getValues(){
			Object[] value=values.keySet().toArray();
			Map<String, String> map=new LinkedHashMap<String, String>();
			for (int i = 0; i < checkBoxList.size(); i++) {
					map.put(value[i].toString(), textBoxList.get(i).getText());
			}
			for(int i=values.size();i<textBoxList.size();i+=2) {
					if (!textBoxList.get(i).getText().trim().isEmpty()||!textBoxList.get(i+1).getText().trim().isEmpty()) {
						map.put(textBoxList.get(i).getText().trim(), textBoxList.get(i+1).getText());
					}
			}
			values=map;
			return map;
		}
		
		public Map<String, String> getSelectedValues() {
			Object[] value=values.keySet().toArray();
			Map<String, String> map=new LinkedHashMap<String, String>();
			if (checkBoxList.get(0).getText().equals("全选")) {
				if (checkBoxList.get(0).isSelected()) {
					for (int i = 1; i < checkBoxList.size(); i++) {
						map.put(value[i].toString(), textBoxList.get(i).getText());
					}
					for(int i=0;i<textBoxList.size();i+=2) {
						if(!textBoxList.get(i).getText().isEmpty()) {
							map.put(textBoxList.get(i).getText().trim(), textBoxList.get(i+1).getText());
						}
					}
				} else {
					for (int i = 1; i < checkBoxList.size(); i++) {
						if (checkBoxList.get(i).isSelected()) {
							map.put(value[i].toString(), textBoxList.get(i).getText());
						}
					}
				}
			} else {
				for (int i = 0; i < checkBoxList.size(); i++) {
					if (checkBoxList.get(i).isSelected()) {
						map.put(value[i].toString(), textBoxList.get(i).getText());
					}
				}
				for(int i=values.size();i<textBoxList.size();i+=2) {
					if(!textBoxList.get(i).getText().isEmpty()) {
						map.put(textBoxList.get(i).getText().trim(), textBoxList.get(i+1).getText());
					}
				}
			}
			return map;
		}
 
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			Object source = arg0.getSource();
			if (source instanceof JButton) {
				JButton button = (JButton) source;
				if (button.equals(commitButton)) {
					getValues();
					setText(getSelectedValues());
					popup.setVisible(false);
				} else if (button.equals(cancelButton)) {
					popup.setVisible(false);
				}
			}
		}
 
	}
 
}