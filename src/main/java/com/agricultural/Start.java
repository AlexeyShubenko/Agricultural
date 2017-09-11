package com.agricultural;

/**
 * Created by Alexey on 12.02.2017.
 */

import com.agricultural.swing.frames.mainframes.StartWindow;

import javax.swing.*;
import java.awt.*;


public class Start extends JFrame{

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            StartWindow startWindow = new StartWindow();
        });

//        Set<Map.Entry<String,String>> set = System.getenv().entrySet();
//        for (Map.Entry<String,String> entry:set) {
//            System.out.println(entry.getKey() + ":  " + entry.getValue());
//        }


    }
}
