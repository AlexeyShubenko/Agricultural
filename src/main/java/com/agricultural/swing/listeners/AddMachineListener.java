package com.agricultural.swing.listeners;

import com.agricultural.swing.frames.mainframes.AddMachineFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexey on 16.02.2017.
 */
public class AddMachineListener  implements ActionListener {

    private JFrame frame;

    public AddMachineListener(JFrame f){
        frame = f;
    }

    public void actionPerformed(ActionEvent e) {

        frame.dispose();
        AddMachineFrame addframe = new AddMachineFrame();

    }
}
