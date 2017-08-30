package com.agricultural.swing.frames.tablemodels.totalmoney;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by Alexey on 11.03.2017.
 */
public class TotalMoneyCellRenderer extends DefaultTableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JTextField editor = new JTextField();
        if(value!=null)
        editor.setText(value.toString().trim());

        editor.setHorizontalAlignment(CENTER);
        editor.setFont(new Font("Serif",Font.PLAIN,24));
        editor.setBackground(new Color(207, 207, 207));

        return editor;
    }
}

