package com.agricultural.swing.frames.driverinformation;

import com.agricultural.dao.detailnformation.DetailInformationDaoImpl;
import com.agricultural.domains.hoursvirobitok.DetailDataHour;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.service.DetailInformationService;
import com.agricultural.service.impl.DetailInformationServiceImpl;
import com.agricultural.swing.frames.FrameLocation;
import com.agricultural.swing.frames.tablemodels.DetailHourTableModel;
import com.agricultural.swing.frames.tablerenderer.DetailCellRenderer;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Alexey on 08.03.2017.
 */
public class DetailHourFrame extends JFrame{

    private JPanel mainPanel = new JPanel();

    private final Integer DEFAULT_X_LOCATION=FrameLocation.screenSize.width/3;
    private final Integer DEFAULT_Y_LOCATION=0;

    private final Integer FRAME_WIDTH= FrameLocation.screenSize.width/2;
    private final Integer FRAME_HEIGHT= FrameLocation.screenSize.height;

    private final Integer TABLE_WIDTH = FRAME_WIDTH-50;
    private final Integer TABLE_HEIGHT = FRAME_HEIGHT-50;

    private JTextField operation = new JTextField();
    private JTextField machine = new JTextField();

    private JButton refreshButton = new JButton("Зберегти / Обновити");

    private JPanel tablePanel = new JPanel();
    private JPanel headPanel = new JPanel();
    private JPanel infPanel = new JPanel();

    private final Font TABLE_FONT = new Font("Serif", Font.PLAIN, 16);

   private DetailHourTableModel detailHectareTableModel;

    private DetailInformationService detailService = DetailInformationServiceImpl.getInstance();

    public DetailHourFrame(Long data_id, String operationName, String machineName,
                           ///ці дані треба створення MainInfoFrame після закінчення внесення даних
                              JFrame mainInfoFrameOwner, TractorDriver driver, int checkedMonth, int checkedYear){
        super(operationName);

        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_WIDTH,FRAME_HEIGHT-100);
        this.setVisible(true);
        mainPanel.setLayout(new BorderLayout());

        Box box = new Box(BoxLayout.Y_AXIS);

        infPanel.setLayout(new GridLayout(2,1));
        Color textColorBackground = new Color(255, 255, 150);
        operation.setText(operationName);
        operation.setFont(TABLE_FONT);
        operation.setForeground(Color.RED);
        operation.setBackground(textColorBackground);
        operation.setEditable(false);
        operation.setHorizontalAlignment(SwingConstants.CENTER);

        machine.setText(machineName);
        machine.setFont(TABLE_FONT);
        machine.setForeground(Color.RED);
        machine.setBackground(textColorBackground);
        machine.setEditable(false);
        machine.setHorizontalAlignment(SwingConstants.CENTER);


        infPanel.add(operation);
        infPanel.add(machine);

        headPanel.add(infPanel);
        headPanel.add(refreshButton);

        refreshButton.addActionListener((e)->{
            detailHectareTableModel.updateTableData();
            dispose();
            mainInfoFrameOwner.dispose();
            MainInfoFrame mainInfoFrame = new MainInfoFrame(driver,checkedMonth,checkedYear);
            DetailHourFrame detailFrame = new DetailHourFrame(data_id,operationName,machineName,mainInfoFrameOwner,driver,checkedMonth,checkedYear);

        });

        ///отримуємо дані по id рядка даних в таблиці
        DetailDataHour detailDataHour = detailService.getDetailDataHour(data_id);

        detailHectareTableModel = new DetailHourTableModel(detailDataHour);
        JTable table = new JTable(detailHectareTableModel);

        table.setDefaultRenderer(Object.class,new DetailCellRenderer());
        table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        table.setRowHeight(TABLE_HEIGHT/38);

        table.getTableHeader().setFont(new Font("Serif",Font.PLAIN,16));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(table));


        box.add(headPanel);
        box.add(tablePanel);

        add(box);
    }

}
