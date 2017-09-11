package com.agricultural.swing.frames;

import java.awt.*;

/**
 * Created by Alexey on 13.02.2017.
 */
///для фіксування початкового положення вікна
public class FrameLocation {

    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static int locationX = (screenSize.width - 700) / 2;
    public static int locationY = (screenSize.height - 400) / 2;

}
