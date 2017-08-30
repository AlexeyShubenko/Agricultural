package com.agricultural.swing.listeners;



import com.agricultural.swing.frames.mainframes.AddOperationFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexey on 14.02.2017.
 */
public class AddOperationListener implements ActionListener {

    private JFrame frame;

    public AddOperationListener(JFrame f){
        frame = f;
    }

    public void actionPerformed(ActionEvent e) {
        frame.dispose();
        AddOperationFrame addframe = new AddOperationFrame();
    }
}
