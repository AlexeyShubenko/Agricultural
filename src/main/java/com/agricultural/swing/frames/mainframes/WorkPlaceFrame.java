package com.agricultural.swing.frames.mainframes;

import com.agricultural.dao.workplaces.WorkplaceDAOImpl;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.domains.main.Workplace;
import com.agricultural.swing.frames.FrameLocation;
import com.agricultural.swing.frames.tablemodels.WorkplaceTableModel;
import com.agricultural.swing.frames.tablerenderer.OperationMachineCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Alexey on 14.02.2017.
 */
public class WorkPlaceFrame extends JFrame {

    private WorkplaceDAOImpl service = new WorkplaceDAOImpl();

    private JPanel mainPanel = new JPanel();
    private JTextField textField = new JTextField(20);
    private JButton save = new JButton("Додати  \"За місцем роботи\" ");
    private JButton refresh = new JButton("Оновити дані");
    private JFrame ownFrame;
    ///допоміжні панелі
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();

    private final Integer TABLE_WIDTH = Math.round(FrameLocation.screenSize.width/4);
    private final Integer TABLE_HEIGHT = Math.round(FrameLocation.screenSize.height/10);

    private final Integer FRAME_WIDTH = Math.round(FrameLocation.screenSize.width/3);
    private final Integer FRAME_HEIGHT = Math.round(FrameLocation.screenSize.height/4);

    private final Integer DEFAULT_X_LOCATION=FrameLocation.locationX;
    private final Integer DEFAULT_Y_LOCATION=FrameLocation.locationY;

    private ArrayList<Workplace> workplaces;
    private WorkplaceTableModel workplaceTableModel;

        public WorkPlaceFrame(TractorDriver driver, TractorDriversFrame tractorDriversFrame){
        super("За місцем роботи");
        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_WIDTH,FRAME_HEIGHT);
        this.setVisible(true);

        //listener на кнопку зберегти
        save.addActionListener(e->{
                String value = textField.getText().trim();
                if(!value.equals("")) {
                    ///зчитується для перевірки на наявність
                    String[] allWorkPlaces = service.getAllWorkplaceName();

                    ///якщо false то введенего значення немає в базі
                    boolean flag = false;
                    for(int i=0; i<allWorkPlaces.length;i++) {
                        //якщо введене значення вже існує в базі то виконується блок if
                        if (allWorkPlaces[i].compareToIgnoreCase(value) == 0) {
                            //true - таке значення є
                            flag = true; break;
                        }
                    }
                    //якщо введене значення не повторяється то воно записуєтсья в базу
                    if(!flag){
                        service.createWorkPlace(textField.getText());
                        this.dispose();
                        this.ownFrame.dispose();
                        AddTractorDriverFrame addTractorDriverFrame = new AddTractorDriverFrame(driver, tractorDriversFrame);
                        WorkPlaceFrame workPlaceFrame = new WorkPlaceFrame(driver, tractorDriversFrame);
                        workPlaceFrame.setOwnFrame(addTractorDriverFrame);

                    }else JOptionPane.showMessageDialog(null, "Значення \"" + value + "\"  вже існує",
                            "Увага!", JOptionPane.ERROR_MESSAGE);


                }else JOptionPane.showMessageDialog(null, "Заповніть необхіне поле","Увага!",JOptionPane.OK_CANCEL_OPTION);
        });

        refresh.addActionListener(e -> {
            workplaceTableModel.updateTableData();
            this.dispose();
            this.ownFrame.dispose();
            AddTractorDriverFrame addTractorDriverFrame = new AddTractorDriverFrame(driver, tractorDriversFrame);
            WorkPlaceFrame workPlaceFrame = new WorkPlaceFrame(driver, tractorDriversFrame);
            workPlaceFrame.setOwnFrame(addTractorDriverFrame);
        });

        workplaces = service.getWorkplaces();
        workplaceTableModel = new WorkplaceTableModel(workplaces);
        JTable workPlaceTable = new JTable(workplaceTableModel);
        workPlaceTable.setRowHeight(30);
        workPlaceTable.getColumnModel().getColumn(1).setPreferredWidth(Math.round(FRAME_WIDTH/2));

        workPlaceTable.getTableHeader().setFont(new Font("Serif",Font.PLAIN,18));
        workPlaceTable.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        workPlaceTable.setDefaultRenderer(Object.class,new OperationMachineCellRenderer());

        workPlaceTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount()==1){
                        JTable t = (JTable) e.getSource();
                        String value = t.getValueAt(t.getSelectedRow(),t.getSelectedColumn()).toString();
                        ///зчитується номер рядка, з якого об'єкт передаємо далі на форму
                        int i = t.getSelectedRow();

                        ///видалення рядка даних
                        ///value.equals("-")
                        if(value.equals("-")){
                            ///isDelete = 0 - натиснуто YES, isDelete = 1 - натиснуто NO,
                            int isDelete = JOptionPane.showConfirmDialog(WorkPlaceFrame.this,
                                    "Ви впевнені, що хочете видалити пункт " + workplaces.get(i).getWorkPlaceName() + "?",
                                    "Увага!", JOptionPane.YES_NO_OPTION);
                            if(isDelete==0){
                                service.deleteWorPlace(workplaces.get(i));
                                dispose();
                                ownFrame.dispose();
                                AddTractorDriverFrame addTractorDriverFrame = new AddTractorDriverFrame(driver, tractorDriversFrame);
                                WorkPlaceFrame workPlaceFrame = new WorkPlaceFrame(driver, tractorDriversFrame);
                                workPlaceFrame.setOwnFrame(addTractorDriverFrame);
                            }
                        }
                    }
                }
            });

        mainPanel.setLayout(new GridLayout(1,1));
        Box box = new Box(BoxLayout.Y_AXIS);

        p1.setLayout(new FlowLayout());
//        p1.add(label);
        p1.add(textField);

        p2.setLayout(new FlowLayout());
        p2.add(save);
        p2.add(refresh);

        p3.setLayout(new BorderLayout());
        p3.add(new JScrollPane(workPlaceTable));

        box.add(p1);
        box.add(p2);
        box.add(p3);

        mainPanel.add(box);

        add(mainPanel);

    }

    public JFrame getOwnFrame() {
        return ownFrame;
    }

    public void setOwnFrame(JFrame ownFrame) {
        this.ownFrame = ownFrame;
    }
}
