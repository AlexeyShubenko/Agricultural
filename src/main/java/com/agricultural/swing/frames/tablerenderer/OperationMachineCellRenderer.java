package com.agricultural.swing.frames.tablerenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by Alexey on 11.03.2017.
 */
public class OperationMachineCellRenderer extends DefaultTableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JTextField editor = new JTextField();
        if(value!=null)
        editor.setText(value.toString().trim());

        editor.setHorizontalAlignment(CENTER);
        editor.setFont(new Font("Serif",Font.PLAIN,16));
        if(isSelected){
            editor.setBackground(Color.ORANGE);
        }else {
            editor.setBackground(new Color(224, 250, 250));

        }
        return editor;
    }
}

