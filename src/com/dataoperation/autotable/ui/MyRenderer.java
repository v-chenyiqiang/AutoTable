package com.dataoperation.autotable.ui;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Red;

import java.awt.*;

public class MyRenderer extends JLabel implements TableCellRenderer {

  


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        String text = (value == null) ? "" : value.toString();
        
    	if(Integer.valueOf(table.getModel().getValueAt(row, 0).toString())%2==0) {
    		 super.setForeground(Color.BLUE);
    	}
    	else {
    		 super.setForeground(Color.BLACK);
    	}
    	
    	this.setHorizontalAlignment(JLabel.CENTER);
    	this.setToolTipText(text);

        setText(text);
    	
        return this;
    }


}