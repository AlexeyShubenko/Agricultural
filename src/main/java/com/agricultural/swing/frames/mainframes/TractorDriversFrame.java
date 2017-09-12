package com.agricultural.swing.frames.mainframes;



import com.agricultural.dao.workplaces.WorkplaceDaoImpl;
import com.agricultural.domains.main.Workplace;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.service.TractorDriverService;
import com.agricultural.service.WorkplaceService;
import com.agricultural.service.impl.TractorDriverServiceImpl;
import com.agricultural.service.impl.WorkplaceServiceImpl;
import com.agricultural.swing.frames.FrameLocation;
import com.agricultural.swing.frames.driverinformation.MainInfoFrame;
import com.agricultural.swing.frames.tablemodels.TractorDriverTableModel;
import com.agricultural.swing.frames.tablerenderer.OperationMachineCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Alexey on 13.02.2017.
 */

public class TractorDriversFrame extends JFrame {

    private TractorDriverService tractorDriverService = TractorDriverServiceImpl.getInstance();
    private WorkplaceService workplaceService = WorkplaceServiceImpl.getInstance();

    private JPanel panel = new JPanel();
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();

    private JButton addNew = new JButton("додати нового працівника");
//    private JButton deleteDriver = new JButton("видалити тракториста");
//    private JButton addWorkplace = new JButton("додати місце роботи");
//    private JTextField deleteField = new JTextField(10);

    private JButton refreshButton = new JButton("Оновити дані в таблиці");
    private TractorDriverTableModel driverTableModel;
    ///масив працівників які будуть відображуватися в таблиці
    private ArrayList<TractorDriver> drivers;

    private final Integer FRAME_WIDTH = Math.round(FrameLocation.screenSize.width/1.1f);
    private final Integer FRAME_HEIGHT = Math.round(FrameLocation.screenSize.height/2f);

    private final Integer TABLE_WIDTH = Math.round(FrameLocation.screenSize.width/2f);
    private final Integer TABLE_HEIGHT = Math.round(FrameLocation.screenSize.height/5f);

    private final Integer DEFAULT_X_LOCATION = Math.round(FrameLocation.screenSize.width/12);
    private final Integer DEFAULT_Y_LOCATION = Math.round(FrameLocation.screenSize.height/4);

    public TractorDriversFrame(){
        super("Працівники");
        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_WIDTH,FRAME_HEIGHT);
        setVisible(true);
        setBackground(Color.WHITE);

        ///Всі трактористи з бази
        drivers = sortedDriverMassive(tractorDriverService.getTractorDrivers());

        ///модель талиці для механізаторів
        driverTableModel = new TractorDriverTableModel(drivers);
        JTable table = new JTable(driverTableModel);
        table.setRowHeight(30);
//        table.setFont(new Font("Serif",Font.PLAIN,18));
        table.getTableHeader().setFont(new Font("Serif",Font.PLAIN,18));
        table.setDefaultRenderer(Object.class,new OperationMachineCellRenderer());
        table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));

        ///цифри задають % від значення ширини екрану
        table.getColumnModel().getColumn(0).setPreferredWidth(Math.round(TABLE_WIDTH*0.025f));
        table.getColumnModel().getColumn(1).setPreferredWidth(Math.round(TABLE_WIDTH*0.15f));
        table.getColumnModel().getColumn(2).setPreferredWidth(Math.round(TABLE_WIDTH*0.15f));
        table.getColumnModel().getColumn(3).setPreferredWidth(Math.round(TABLE_WIDTH*0.15f));
        table.getColumnModel().getColumn(4).setPreferredWidth(Math.round(TABLE_WIDTH*0.15f));
        table.getColumnModel().getColumn(5).setPreferredWidth(Math.round(TABLE_WIDTH*0.08f));
        table.getColumnModel().getColumn(6).setPreferredWidth(Math.round(TABLE_WIDTH*0.15f));
        table.getColumnModel().getColumn(7).setPreferredWidth(Math.round(TABLE_WIDTH*0.08f));

        JComboBox workPlaceComboBox = new JComboBox();
        String[] workplaceNames = workplaceService.getAllWorkplaceName();
        for (String  name: workplaceNames){
            workPlaceComboBox.addItem(name);
        }

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1){
                    JTable t = (JTable) e.getSource();

                    String  value = t.getValueAt(t.getSelectedRow(), t.getSelectedColumn()).toString();
                    int i= t.getSelectedRow();

                    ///зчитується номер рядка, з якого об'єкт передаємо далі на форму
                    if(value.equals("->") && t.getSelectedColumn()==6) {
                        ///На mainInfFrame передається тракторист дані якого будуть показуватися
                        MainInfoFrame frame = new MainInfoFrame(drivers.get(i),currentMonth(),currentYear());
                        frame.setEmployeeFrame(TractorDriversFrame.this);
                    }

                    if(t.getSelectedColumn()==5) {
                        TractorDriversFrame.this.dispose();
                        ///оновлення даних, false - о оновлюємо працівника
                        AddTractorDriverFrame tractorDriverFrame = new AddTractorDriverFrame(drivers.get(i),TractorDriversFrame.this);
                    }

                    if(value.equals("-")) {
                        int isDelete = JOptionPane.showConfirmDialog(TractorDriversFrame.this,
                                "Ви впевнені, що хочете видалити всі дані працівника " + drivers.get(i).getName() + " ?",
                                "Увага!", JOptionPane.YES_NO_OPTION);
                        if(isDelete==0) {
                            ///На mainInfFrame передається тракторист дані якого будуть показуватися
                            tractorDriverService.deleteTractorDriver(drivers.get(i));
                            TractorDriversFrame.this.dispose();
                            TractorDriversFrame newTractorDriversFrame = new TractorDriversFrame();
                        }
                    }

                }
            }
        });

        addNew.addActionListener(e->{
            ///null бо додаємо нового працівника
                AddTractorDriverFrame frame = new AddTractorDriverFrame(null, TractorDriversFrame.this);
//                dispose();
        });

        refreshButton.addActionListener(e -> {
                dispose();
                TractorDriversFrame frame = new TractorDriversFrame();
        });

        Box box = new Box(BoxLayout.Y_AXIS);

        panel.setLayout(new GridLayout(1,1));
        p1.add(addNew);

        p2.add(refreshButton,BorderLayout.NORTH);

        box.add(p1);
        box.add(p2);

        p3.setLayout(new BorderLayout());
        p3.add(new JScrollPane(table));

        box.add(p3);
        panel.add(box);

        JScrollPane scrollPane = new JScrollPane(panel);

        add(scrollPane);
    }

    private ArrayList<TractorDriver> sortedDriverMassive(ArrayList<TractorDriver> tractorDrivers) {
       ArrayList<TractorDriver> sortedWorkers = new ArrayList<>();
       ArrayList<Workplace> workplaces = workplaceService.getWorkplaces();

       for (int i = 0; i<workplaces.size();i++){
           for(int j = 0; j<tractorDrivers.size();j++){
               if(workplaces.get(i).getWorkPlaceName()
                       .equals(tractorDrivers.get(j).getWorkplace().getWorkPlaceName()))
               sortedWorkers.add(tractorDrivers.get(j));
           }
       }

        return sortedWorkers;
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
