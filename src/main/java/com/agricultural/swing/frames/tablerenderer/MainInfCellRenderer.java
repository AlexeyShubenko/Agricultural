package com.agricultural.swing.frames.tablerenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by Alexey on 11.03.2017.
 */
public class MainInfCellRenderer extends DefaultTableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JTextField editor = new JTextField();
        if(value!=null)
        editor.setText(value.toString().trim());

        editor.setHorizontalAlignment(CENTER);
        editor.setFont(new Font("Serif",Font.PLAIN,20));
        if(isSelected && hasFocus){
            editor.setBackground(Color.ORANGE);
        }else {
            if (column == 0) editor.setBackground(new Color(207, 207, 207));
            if (column == 1) editor.setBackground(new Color(255, 236, 139));
            if (column == 2) editor.setBackground(new Color(255, 236, 139));
            if (column == 3) editor.setBackground(new Color(255, 218, 185));
            if (column == 4) editor.setBackground(new Color(84, 255, 159));
            if (column == 5) editor.setBackground(new Color(224, 250, 250));
            if (column == 5) editor.setBackground(new Color(224, 250, 250));
            if (column == 7) editor.setBackground(new Color(224, 250, 250));
            if (column == 8) editor.setBackground(new Color(224, 250, 250));
        }
        return editor;
    }
}

