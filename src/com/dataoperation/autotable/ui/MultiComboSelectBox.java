package com.dataoperation.autotable.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicArrowButton;

import org.w3c.dom.events.MouseEvent;
 
/**
 * 下拉复选框组件
 *
 */
public class MultiComboSelectBox extends JComponent implements ActionListener {
 
	private Map<String, String> values;
	private MultiPopup popup;
	private JTextField editor;
	protected JButton arrowButton;
	private String leftName;
	private String rightName;
	
	
 
	public MultiComboSelectBox(Map<String, String> value,String leftName,String rightName) {
		this.leftName=leftName;
		this.rightName=rightName;
		if(value==null) {
			value=new LinkedHashMap<String, String>();
			value.put("", "");
		}
		values = value;
		initComponent();
	}
	
	public Map<String, String> getMap(){
		return popup.getValues();
	}
	public void updateValue(Map<String, String> value){
	
		editor.setText("");
		values=value;
		popup.updateValue(value);
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
		private JPanel fanyePane;
		private List<JButton> fanye=new LinkedList<>();
		private JPanel showPanel;
		private JPanel buttonPane;
        private int perNum;
		public MultiPopup(Map<String, String> value) {
			super();
			perNum=5;
			values = value;
			initComponent();
			setFocusable(true);
		}
		public void updateValue(Map<String, String> map) {
			showPanel.removeAll();
			checkboxPane.removeAll();
			fanyePane.removeAll();
			checkBoxList.clear();
			textBoxList.clear();
			values=map;
			Object[] value=values.keySet().toArray();
			for (Object v : value) {
				JCheckBox temp = new JCheckBox(v.toString());
				checkBoxList.add(temp);
				JTextField textField=new JTextField(values.get(v.toString()));
				textBoxList.add(textField);
			}
			if (checkBoxList.get(0).getText().equals("统一模板")) {
			
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
			checkboxPane.setLayout(new GridLayout(checkBoxList.size()+1, 2, 3, 3));
			checkboxPane.add(new JLabel(leftName));
			checkboxPane.add(new JLabel(rightName));
			for (int i=0;i<checkBoxList.size();i++) {
				JCheckBox box =checkBoxList.get(i);
				JTextField jTextField=textBoxList.get(i);
				checkboxPane.add(box);
				checkboxPane.add(jTextField);
			}
			boolean isFanye=false;
			this.remove(fanyePane);
			for(int i=0;i<checkBoxList.size()/perNum+1;i++) {
				JButton bu=new JButton((i+1)+"");
				bu.addActionListener(this);
				fanyePane.add(bu);
				isFanye=true;
			}
			if(isFanye) {
				showPanel=getFanYe(1);
			}
			else {
				showPanel=checkboxPane;
			}
			this.removeAll();
			this.add(showPanel);
			this.add(fanyePane);
			this.add(buttonPane);
			this.validate();
			this.repaint();
		}
		
		
		public void updateSelect(Map<String, String> map) {
			showPanel.removeAll();
			checkboxPane.removeAll();
			fanyePane.removeAll();
			checkBoxList.clear();
			textBoxList.clear();
		//	values=map;
			Object[] value=values.keySet().toArray();
			for (Object v : value) {
				JCheckBox temp = new JCheckBox(v.toString());
				for(String k:map.keySet()) {
					if(k.equals(v.toString())) {
							temp.setSelected(true);
					}
				}
				checkBoxList.add(temp);
				JTextField textField=new JTextField(values.get(v.toString()));
				
				textBoxList.add(textField);
			}
			if (checkBoxList.get(0).getText().equals("统一模板")) {
			
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
			checkboxPane.setLayout(new GridLayout(checkBoxList.size()+1, 2, 3, 3));
			checkboxPane.add(new JLabel(leftName));
			checkboxPane.add(new JLabel(rightName));
			for (int i=0;i<checkBoxList.size();i++) {
				JCheckBox box =checkBoxList.get(i);
				JTextField jTextField=textBoxList.get(i);
				checkboxPane.add(box);
				checkboxPane.add(jTextField);
			}
			boolean isFanye=false;
			for(int i=0;i<checkBoxList.size()/perNum+1;i++) {
				JButton bu=new JButton((i+1)+"");
				bu.addActionListener(this);
				fanyePane.add(bu);
				isFanye=true;
			}
			if(isFanye) {
				showPanel=getFanYe(1);
			}
			else {
				showPanel=checkboxPane;
			}
			this.removeAll();
			this.add(showPanel);
			this.add(fanyePane);
			this.add(buttonPane);
			this.validate();
			this.repaint();
			
		
		}
		private void initComponent() {
			fanyePane=new JPanel();
			checkboxPane = new JPanel();
			 buttonPane = new JPanel();
			Object[] value=values.keySet().toArray();
			for (Object v : value) {
				JCheckBox temp = new JCheckBox(v.toString());
				temp.setSelected(true);
				checkBoxList.add(temp);
				JTextField textField=new JTextField(values.get(v.toString()));
				textBoxList.add(textField);
			}
			if (checkBoxList.get(0).getText().equals("统一模板")) {
			
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

			checkboxPane.setLayout(new GridLayout(checkBoxList.size()+1, 2, 3, 3));
			checkboxPane.add(new JLabel(leftName));
			checkboxPane.add(new JLabel(rightName));

			for (int i=0;i<checkBoxList.size();i++) {
				JCheckBox box =checkBoxList.get(i);
				JTextField jTextField=textBoxList.get(i);
				checkboxPane.add(box);
				checkboxPane.add(jTextField);
			}

			boolean isFanye=false;
			
			for(int i=0;i<checkBoxList.size()/perNum;i++) {
				JButton bu=new JButton((i+1)+"");
				bu.addActionListener(this);
				fanye.add(bu);
				fanyePane.add(bu);
				isFanye=true;
			}
			
			if(isFanye) {
				showPanel=getFanYe(1);
			}
			else {
				showPanel=checkboxPane;
			}
			commitButton = new JButton("确定");
			commitButton.addActionListener(this);
 
			cancelButton = new JButton("取消");
			cancelButton.addActionListener(this);
			
			buttonPane.add(commitButton);
			buttonPane.add(cancelButton);


			
			
			this.setLayout(new BoxLayout(this, 1));
			this.add(showPanel);
			this.add(fanyePane);
			this.add(buttonPane);
		}
		
		public JPanel getFanYe(int num) {
			JPanel varCheckBoxPane=new JPanel();
			if (num==1) {
				varCheckBoxPane.setLayout(new GridLayout(perNum+1, 2, 3, 3));
				for (int i =( num-1)*perNum; i <=perNum*num&&i<checkBoxList.size(); i++) {
					    varCheckBoxPane.add(checkBoxList.get(i));
					    varCheckBoxPane.add(textBoxList.get(i));
				}
				return varCheckBoxPane;
			}
			else {
			varCheckBoxPane.setLayout(new GridLayout(perNum, 2, 3, 3));
			for (int i =( num-1)*perNum+1; i <=perNum*num&&i<checkBoxList.size(); i++) {
				    varCheckBoxPane.add(checkBoxList.get(i));
				    varCheckBoxPane.add(textBoxList.get(i));

			}
			return varCheckBoxPane;
			}
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
			if (checkBoxList.get(0).getText().equals("统一模板")) {
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
		
		public Map<String, String> getSelectedValues() {
			Object[] value=values.keySet().toArray();
			Map<String, String> map=new LinkedHashMap<String, String>();
			if (checkBoxList.get(0).getText().equals("统一模板")) {
				if (checkBoxList.get(0).isSelected()) {
					for (int i = 1; i < checkBoxList.size(); i++) {
						map.put(value[i].toString(), textBoxList.get(i).getText());
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
			}
			return map;
		}
		public Map<String, String> getValues() {
			Object[] value=values.keySet().toArray();
			Map<String, String> map=new LinkedHashMap<String, String>();
			for (int i = 1; i < checkBoxList.size(); i++) {
						map.put(value[i].toString(), textBoxList.get(i).getText());
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
					setText(getSelectedValues());
					popup.setVisible(false);
				} else if (button.equals(cancelButton)) {
					popup.setVisible(false);
				}
				else {
					this.removeAll();
					showPanel=getFanYe(Integer.valueOf(button.getText().trim()));
					this.add(showPanel);
					this.add(fanyePane);
					this.add(buttonPane);
					this.validate();
					this.repaint();
				}
				

			}
		}
	
	}
 
}