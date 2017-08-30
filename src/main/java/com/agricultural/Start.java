package com.agricultural;

/**
 * Created by Alexey on 12.02.2017.
 */

import com.agricultural.swing.frames.mainframes.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Set;


public class Start extends JFrame{

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
        });

//        Set<Map.Entry<String,String>> set = System.getenv().entrySet();
//        for (Map.Entry<String,String> entry:set) {
//            System.out.println(entry.getKey() + ":  " + entry.getValue());
//        }


    }
}
