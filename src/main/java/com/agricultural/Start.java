package com.agricultural;

/**
 * Created by Alexey on 12.02.2017.
 */

import com.agricultural.swing.frames.mainframes.StartWindow;

import javax.swing.*;
import java.awt.*;

/*Start point of the program*/
public class Start extends JFrame{

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            StartWindow startWindow = new StartWindow();
        });

    }
}
