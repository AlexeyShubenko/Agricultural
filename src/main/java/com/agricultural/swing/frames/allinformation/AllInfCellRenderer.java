package com.agricultural.swing.frames.allinformation;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by Alexey on 11.03.2017.
 */
public class AllInfCellRenderer extends DefaultTableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JTextField editor = new JTextField();
        if(value!=null)
        editor.setText(value.toString().trim());
        editor.setHorizontalAlignment(CENTER);
//        setHorizontalAlignment(JLabel.CENTER);
        editor.setFont(new Font("Serif",Font.PLAIN,18));
        if(isSelected){
            editor.setBackground(new Color(135, 206, 235));
        }else {
            if (column == 0) editor.setBackground(new Color(207, 207, 207));
            if (column == 1) editor.setBackground(new Color(255, 236, 139));
            if (column == 2) editor.setBackground(new Color(255, 236, 139));
            if(column>2)
            for (int i = 3; i < 33; i++) {
               editor.setBackground(new Color(255, 218, 185));
            }
        }
        return editor;
    }
}

