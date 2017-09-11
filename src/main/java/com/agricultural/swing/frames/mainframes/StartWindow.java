package com.agricultural.swing.frames.mainframes;

import com.agricultural.swing.frames.FrameLocation;
import com.agricultural.swing.frames.allinformation.AllInformationFrame;
import com.agricultural.swing.listeners.AllTractorDriversListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alexey on 12.02.2017.
 */
public class StartWindow extends JFrame {

    private JPanel mainPanel = new JPanel();

    private JButton allInformationButton = new JButton("Зведена інформація");
    private JButton addTractorDrivers = new JButton("Працівники");
    private JButton addOperations = new JButton("Види робіт");
    private JButton addMachines = new JButton("Машинно-тракторний агрегат");

    private JPanel buttonsBorderPanel = new JPanel();
    private JPanel infoButtonPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();

    private final String iconImage = "icons/iconImage.png";

    private final Integer FRAME_WIDTH = Math.round(FrameLocation.screenSize.width/3f);
    private final Integer FRAME_HEIGHT = Math.round(FrameLocation.screenSize.height/5f);
    private final Integer DEFAULT_X_LOCATION = Math.round(FrameLocation.screenSize.width/2-FRAME_WIDTH/2);
    private final Integer DEFAULT_Y_LOCATION = Math.round(FrameLocation.screenSize.height/3-FRAME_HEIGHT/2);

    public StartWindow(){
        super("Agricultural");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_WIDTH,FRAME_HEIGHT);
        this.setVisible(true);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(this.iconImage));

        mainPanel.setLayout(new GridLayout(1,1));
        //відкривається вікно з трактористами
        addTractorDrivers.addActionListener(new AllTractorDriversListener(this));
        addOperations.addActionListener(e->{
                    TechnoOperationsFrame frame = new TechnoOperationsFrame();
        });

        addMachines.addActionListener(e-> {
                MachinesUnitFrame frame = new MachinesUnitFrame();
        });

        allInformationButton.addActionListener(e->{
            AllInformationFrame allInformationFrame = new AllInformationFrame(currentMonth(),currentYear());
        });

        Box box = new Box(BoxLayout.Y_AXIS);
       //add all components to frame
        infoButtonPanel.add(allInformationButton);

        buttonsPanel.add(addOperations);
        buttonsPanel.add(addMachines);
        buttonsPanel.add(addTractorDrivers);

        Border border = BorderFactory.createMatteBorder(3,3,3,3,Color.LIGHT_GRAY);
        Border title = BorderFactory.createTitledBorder(border,"Основна інформація",2,2,
                new Font("Serif",Font.PLAIN,20));

        buttonsBorderPanel.add(buttonsPanel);
        buttonsBorderPanel.setBorder(title);

        box.add(infoButtonPanel);
        box.add(buttonsBorderPanel);

        mainPanel.add(box);
        add(mainPanel);
    }

    public int currentMonth(){
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.MONTH);
    }

    public int currentYear(){
        GregorianCalendar calendar = new GregorianCalendar();
        return calendar.get(Calendar.YEAR);
    }

}
