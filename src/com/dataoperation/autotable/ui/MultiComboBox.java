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
import java.util.List;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicArrowButton;
 
/**
 * 下拉复选框组件
 *
 */
public class MultiComboBox extends JComponent implements ActionListener {
 
	private Object[] values;
	private MultiPopup popup;
	private JTextField editor;
	protected JButton arrowButton;
	
	
 
	public MultiComboBox(Object[] value) {
		if(value==null||value.length==0) {
			value=new Object[]{""};
		}
		values = value;
		initComponent();
	}

	public void updateValue(Object[] value){
		editor.setText("");
		values=value;
		if(values==null) {
			values= new Object[]{""};
		}
		popup.updateValue(values);
	}
	
	
	
	public void updateIndex(Object[] value) {
		editor.setText("");
		if(values==null) {
			values= new Object[]{""};
		}
		popup.updateIndex(value);
		
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
	public void setText(String text) {
		editor.setText(text);
	}
	public void setEditable(Boolean is) {
		editor.setEditable(is);
	}
	public String getText() {
		return editor.getText();
	}
	//获取选中的数据
	public Object[] getSelectedValues() {
		return popup.getSelectedValues();
	}
	public String getSelectedText() {
		return popup.getSelectedText();
	}
 
	//设置需要选中的值
	public void setSelectValues(Object[] selectvalues) {
		popup.setSelectValues(selectvalues);
		setText(selectvalues);
	}

    
	private void setText(Object[] values) {
		if (values.length > 0) {
			String value = Arrays.toString(values);
			value = value.replace("[", "");
			value = value.replace("]", "");
			editor.setText(value);
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
		private Object[] values;
		private List<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
		private JButton commitButton;
		private JButton cancelButton;
		private JPanel checkboxPane;
 
		public MultiPopup(Object[] value) {
			super();
			values = value;
			initComponent();
		}
		public void updateValue(Object[] value) {
		
			values=value;
			checkboxPane.removeAll();;
			checkBoxList.clear();
			JCheckBox qb = new JCheckBox("全选");
			checkBoxList.add(qb);
			for (Object v : value) {
				JCheckBox temp = new JCheckBox(v.toString());
				checkBoxList.add(temp);
				if (checkBoxList.get(0).getText().equals("全选")) {
					checkBoxList.get(0).addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							if (checkBoxList.get(0).isSelected()) {
								for (int i = 1; i < checkBoxList.size(); i++) {
									if (!checkBoxList.get(i).isSelected()) {
										checkBoxList.get(i).setSelected(true);
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
			}
			checkboxPane.setLayout(new GridLayout(checkBoxList.size(), 1, 3, 3));

			for (JCheckBox box : checkBoxList) {
				checkboxPane.add(box);
			}
		
		}
		
		public void updateIndex(Object[] value) {
			checkboxPane.removeAll();;
			checkBoxList.clear();
			JCheckBox qb = new JCheckBox("全选");
			checkBoxList.add(qb);
			for (Object v : values) {
				JCheckBox temp = new JCheckBox(v.toString());
				checkBoxList.add(temp);
				if (checkBoxList.get(0).getText().equals("全选")) {
					checkBoxList.get(0).addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							if (checkBoxList.get(0).isSelected()) {
								for (int i = 1; i < checkBoxList.size(); i++) {
									if (!checkBoxList.get(i).isSelected()) {
										checkBoxList.get(i).setSelected(true);
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
			}
			checkboxPane.setLayout(new GridLayout(checkBoxList.size(), 1, 3, 3));

			for (JCheckBox box : checkBoxList) {
				for(Object o:value) {
					if(o.toString().equals(box.getText())) {
						box.setSelected(true);
					}
				}
				checkboxPane.add(box);
			}
			setText(value);
		
		}
		
		
		private void initComponent() {
			
			checkboxPane = new JPanel();
			JPanel buttonPane = new JPanel();
			this.setLayout(new BorderLayout());
			JCheckBox qb = new JCheckBox("全选");
			checkBoxList.add(qb);
			for (Object v : values) {
				JCheckBox temp = new JCheckBox(v.toString());
				checkBoxList.add(temp);
			}
			if (checkBoxList.get(0).getText().equals("全选")) {
				checkBoxList.get(0).addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						if (checkBoxList.get(0).isSelected()) {
							for (int i = 1; i < checkBoxList.size(); i++) {
								if (!checkBoxList.get(i).isSelected()) {
									checkBoxList.get(i).setSelected(true);
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
 
			
			checkboxPane.setLayout(new GridLayout(checkBoxList.size(), 1, 3, 3));
		
			for (JCheckBox box : checkBoxList) {
				checkboxPane.add(box);
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
 
		public void setSelectValues(Object[] values) {
			if (values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					for (int j = 0; j < checkBoxList.size(); j++) {
						if (values[i].equals(checkBoxList.get(j).getText())) {
							checkBoxList.get(j).setSelected(true);
						}
					}
				}
				setText(getSelectedValues());
			}
		}
 
		public String getSelectedText() {
			StringBuilder builder=new StringBuilder();
			Object[]  ob=getSelectedValues();
			for(int i=0;i<ob.length;i++) {
				builder.append(ob[i]);
				if(i!=ob.length-1) {
					builder.append(",");
				}
			}
			return builder.toString();
		}
		public Object[] getSelectedValues() {
			List<Object> selectedValues = new ArrayList<Object>();
 
			if (checkBoxList.get(0).getText().equals("全选")) {
				if (checkBoxList.get(0).isSelected()) {
					for (int i = 1; i < checkBoxList.size(); i++) {
						selectedValues.add(values[i-1]);
					}
				} else {
					for (int i = 1; i < checkBoxList.size(); i++) {
						if (checkBoxList.get(i).isSelected()) {
							selectedValues.add(values[i-1]);
						}
					}
				}
			} else {
				for (int i = 0; i < checkBoxList.size(); i++) {
					if (checkBoxList.get(i).isSelected()) {
						selectedValues.add(values[i-1]);
					}
				}
			}
 
			return selectedValues.toArray(new Object[selectedValues.size()]);
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
			}
		}
 
	}
 
}