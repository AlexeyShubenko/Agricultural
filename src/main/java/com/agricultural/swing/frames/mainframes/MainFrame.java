package com.agricultural.swing.frames.mainframes;

import com.agricultural.swing.frames.FrameLocation;
import com.agricultural.swing.frames.allinformation.AllInformationFrame;
import com.agricultural.swing.listeners.AllTractorDriversListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alexey on 12.02.2017.
 */
public class MainFrame extends JFrame {

    private JPanel panel = new JPanel();

    private JButton allInformationButton = new JButton("Зведена інформація");
    private JButton addTractorDrivers = new JButton("Працівники");
    private JButton addOperations = new JButton("Види робіт");
    private JButton addMachines = new JButton("Машинно-тракторний агрегат");

//    private JLabel header = new JLabel("ТОВ \"МГМ-АГРО\"");

    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();

    private final Integer FRAME_WIDTH = Math.round(FrameLocation.screenSize.width/3f);
    private final Integer FRAME_HEIGHT = Math.round(FrameLocation.screenSize.height/5f);

    private final Integer DEFAULT_X_LOCATION = Math.round(FrameLocation.screenSize.width/2-FRAME_WIDTH/2);
    private final Integer DEFAULT_Y_LOCATION = Math.round(FrameLocation.screenSize.height/3-FRAME_HEIGHT/2);

    public MainFrame(){
        super("Agricultural");
//        super("ТОВ МГМ-АГРО");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_WIDTH,FRAME_HEIGHT);
        this.setVisible(true);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("icons/icon.png"));

        Container container= this.getContentPane();

        panel.setLayout(new GridLayout(1,1));
        //відкривається вікно з трактористами
        addTractorDrivers.addActionListener(new AllTractorDriversListener(this));
        addOperations.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    TechnoOperationsFrame frame = new TechnoOperationsFrame();
            }
        });

        addMachines.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MachinesUnitFrame frame = new MachinesUnitFrame();
            }
        });

        allInformationButton.addActionListener(e->{
            AllInformationFrame allInformationFrame = new AllInformationFrame(currentMonth(),currentYear());
        });

        Box box = new Box(BoxLayout.Y_AXIS);
//        header.setFont(new Font("Serif",Font.PLAIN,25));
       //додаються всі компоненти на панель
//        p1.add(header);

        JPanel inf = new JPanel();
        inf.add(allInformationButton);

        JPanel buttons = new JPanel();
        buttons.add(addOperations);
        buttons.add(addMachines);
        buttons.add(addTractorDrivers);

        Border border = BorderFactory.createMatteBorder(3,3,3,3,Color.LIGHT_GRAY);
        Border title = BorderFactory.createTitledBorder(border,"Основна інформація",2,2,
                new Font("Serif",Font.PLAIN,20));
        p2.add(buttons);
        p2.setBorder(title);

        box.add(p1);
        box.add(inf);
        box.add(p2);

        panel.add(box);
        container.add(panel);
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
