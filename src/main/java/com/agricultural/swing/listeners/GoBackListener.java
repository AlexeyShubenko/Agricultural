package com.agricultural.swing.listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexey on 13.02.2017.
 */
public class GoBackListener implements ActionListener {

    private JFrame frame;

    public GoBackListener(JFrame shouldClose){
        frame = shouldClose;
    }

    public void actionPerformed(ActionEvent e) {
        frame.setVisible(false);
    }
}
