package com.agricultural.swing.listeners;

import com.agricultural.swing.frames.mainframes.TractorDriversFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexey on 13.02.2017.
 */
public class AllTractorDriversListener implements ActionListener {

    private JFrame frame;

    public AllTractorDriversListener(JFrame shouldClose){
       frame = shouldClose;
    }

    public void actionPerformed(ActionEvent e) {
        TractorDriversFrame tractorDriversFrame = new TractorDriversFrame();
//        frame.setVisible(false);
    }
}
