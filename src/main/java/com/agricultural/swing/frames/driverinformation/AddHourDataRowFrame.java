package com.agricultural.swing.frames.driverinformation;

import com.agricultural.dao.hectareinformation.InformationHectareDAOImpl;
import com.agricultural.dao.machinesunit.MachinesDAOImpl;
import com.agricultural.dao.operations.OperationDAOImpl;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.swing.frames.FrameLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexey on 25.02.2017.
 */
///вікно з полями для введення даних для одного рядка
public class AddHourDataRowFrame extends JFrame{

    private OperationDAOImpl operationService = new OperationDAOImpl();
    private MachinesDAOImpl machinesService = new MachinesDAOImpl();
    private InformationHectareDAOImpl infService = new InformationHectareDAOImpl();

    private JComboBox operationComboBox;
    private JComboBox machinesComboBox;

    private JButton saveButton = new JButton("Додати");

    private JButton addOperation = new JButton("+ операцію");
    private JTextField addOperationField = new JTextField(15);
    private JButton addMachine = new JButton("+ машинно-тракторний агрегат");
    private JTextField addMachineField = new JTextField(15);


    private JPanel mainPanel = new JPanel();
    private Box panelBox = new Box(BoxLayout.X_AXIS);
    private JPanel panelButton = new JPanel();

    private final Integer FRAME_WIDTH = Math.round(FrameLocation.screenSize.width/2.5f);
    private final Integer FRAME_HEIGHT = Math.round(FrameLocation.screenSize.height/9);

    private final Integer DEFAULT_X_LOCATION = FrameLocation.locationX;
    private final Integer DEFAULT_Y_LOCATION = FrameLocation.locationY;

    ///вибрана технологічна операція
    private String operationSelected;
    ///вибраний машинно-тракторний агрегат
    private String machineSelected;

    public AddHourDataRowFrame(TractorDriver driver, int checkedMonth, String month, int year, MainInfoFrame mainInfoFrame){
        super(driver.getName() + " Годинний виробіток");
        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_WIDTH,FRAME_HEIGHT);
        this.setVisible(true);
        setLayout(new BorderLayout());

        ///заповнюєть випадаючий список всіма можливими варіантами операцій
        operationComboBox = new JComboBox(operationService.getAllOperationsName());
        ///в початковий момент вибраний 1 item в списку операцій
        operationSelected = (String) operationComboBox.getItemAt(0);
        ///записується у змінну те що вибрали у випадайці
        operationComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                operationSelected = (String) comboBox.getSelectedItem();
            }
        });
        ///заповнюєть випадаючий список всіма можливими варіантами машин
        machinesComboBox = new JComboBox(machinesService.getAllMachinesName());
        ///в початковий момент вибраний 1 item в списку машин
        machineSelected = (String) machinesComboBox.getItemAt(0);
        ///записується у змінну те що вибрали у випадайці
        machinesComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                machineSelected = (String) comboBox.getSelectedItem();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //зберегти в базу
                ///перевірка наявності запису з такими даними
                ///для таблиці dateAndInformation, якщо таких записів немає то і немає записів Hectare table
                if(!infService.isDateAndInformationExist(driver.getDriver_id(),month,year)){
                    infService.createDateAndInformationHectareTableHourTable(driver.getDriver_id(),month,year);
                }

                if(infService.saveOneRowHOURInf(driver.getDriver_id(),
                        operationSelected,machineSelected,month,year)){

                    mainInfoFrame.dispose();
                    MainInfoFrame frame = new MainInfoFrame(driver,checkedMonth, year);
                    dispose();
            }else JOptionPane.showMessageDialog(null, "Такі данні вже існують",
                    "Увага!", JOptionPane.ERROR_MESSAGE);
            }
        });

        addOperation.addActionListener(e->{
            String value = addOperationField.getText().trim();
            if(!value.equals("")) {
                ///зчитується для перевірки на наявність
                String[] allOperations = operationService.getAllOperationsName();
                ///якщо false то введенего значення немає в базі
                boolean flag = false;
                for(int i=0; i<allOperations.length;i++) {
                    //якщо введене значення вже існує в базі то виконується блок if
                    if (allOperations[i].compareToIgnoreCase(value) == 0) {
                        //true - таке значення є
                        flag = true; break;
                    }
                }
                //якщо введене значення не повторяється то воно записуєтсья в базу
                if(!flag){
                    operationService.createOperation(addOperationField.getText());
                    AddHourDataRowFrame operations = new AddHourDataRowFrame( driver, checkedMonth,  month,  year,  mainInfoFrame);
                    AddHourDataRowFrame.this.dispose();
                }else JOptionPane.showMessageDialog(null, "Технологічна оперція " + value + " вже існує",
                        "Увага!", JOptionPane.ERROR_MESSAGE);
            }else JOptionPane.showMessageDialog(null, "Заповніть необхіне поле","Увага!",JOptionPane.OK_CANCEL_OPTION);

        });

        addMachine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ///Введене значення
                String value = addMachineField.getText().trim();
                if(!value.equals("")) {
                    ///Всі назви машинно-тракторних агрегатів
                    String[] allMachines = machinesService.getAllMachinesName();
                    ///якщо false то введенего значення немає в базі
                    boolean flag = false;
                    for (int i = 0; i < allMachines.length; i++) {
                        //якщо введене значення вже існує в базі то виконується блок if
                        if (allMachines[i].compareToIgnoreCase(value) == 0) {
                            //true - таке значення є
                            flag = true;
                            break;
                        }
                    }
                    //якщо введене значення не повторяється то воно записуєтсья в базу
                    if (!flag) {
                        machinesService.createMachine(addMachineField.getText().trim());
                        AddHourDataRowFrame operations = new AddHourDataRowFrame( driver, checkedMonth,  month,  year,  mainInfoFrame);
                        AddHourDataRowFrame.this.dispose();
                    }else JOptionPane.showMessageDialog(null, "Машинно-тракторний агрегат " + value + " вже існує",
                            "Увага!", JOptionPane.ERROR_MESSAGE);

                }else JOptionPane.showMessageDialog(null, "Заповніть необхіне поле",
                        "Увага!",JOptionPane.OK_CANCEL_OPTION);
            }
        });

        ///верхня частина вікна, де обираються існуючі дані
        JPanel left1 = new JPanel();
        left1.setLayout(new BorderLayout());

        JPanel right1 = new JPanel();
        right1.setLayout(new BorderLayout());

        JPanel operationHead = new JPanel();
        operationHead.add(new JLabel("Технологічна операція"));
        operationHead.add(operationComboBox);
        left1.add(operationHead);

        JPanel machineHead = new JPanel();
        machineHead.add(new JLabel("Машинно-тракторний агрегат"));
        machineHead.add(machinesComboBox);
        right1.add(machineHead);

        JPanel headPanel = new JPanel();
        headPanel.setLayout(new BorderLayout());
        headPanel.add(left1,BorderLayout.WEST);
        headPanel.add(right1,BorderLayout.EAST);
        ////////////////////////////////

        ///нижня часина вікна де можна добавити нові операцію та машину
        JPanel left2 = new JPanel();
        left2.setLayout(new BorderLayout());

        JPanel operationLast = new JPanel();
        operationLast.add(addOperationField);
        operationLast.add(addOperation);
        left2.add(operationLast);

        JPanel right2 = new JPanel();
        right2.setLayout(new BorderLayout());

        JPanel machineLast = new JPanel();
        machineLast.add(addMachineField);
        machineLast.add(addMachine);
        right2.add(machineLast);

        JPanel lastPanel = new JPanel();
        lastPanel.setLayout(new BorderLayout());
        lastPanel.add(left2,BorderLayout.WEST);
        lastPanel.add(right2,BorderLayout.EAST);
        ////////////////////////////////////////


        panelButton.add(saveButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headPanel,BorderLayout.NORTH);
        mainPanel.add(panelButton);
        mainPanel.add(lastPanel,BorderLayout.SOUTH);

        add(mainPanel);
        pack();
    }

}
