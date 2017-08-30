package com.agricultural.swing.frames.tablerenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Created by Alexey on 11.03.2017.
 */
public class DetailCellRenderer extends DefaultTableCellRenderer {


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JTextField editor = new JTextField();
        editor.setText(value.toString().trim());
        editor.setFont(new Font("Serif", Font.PLAIN, 16));
        editor.setHorizontalAlignment(CENTER);


        if (isSelected) {
            for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
                editor.setBackground(Color.ORANGE);
            }
        } else {
        if (column == 0) editor.setBackground(new Color(207, 207, 207));
        if (column == 1 | column == 2) editor.setBackground(new Color(255, 228, 196));
        if (column == 3 | column == 4) editor.setBackground(new Color(152, 251, 152));
        if (column == 5) editor.setBackground(new Color(176, 226, 255));
    }
        return editor;
    }
}

