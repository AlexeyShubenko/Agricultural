package com.agricultural.swing.frames.mainframes;

import com.agricultural.dao.machinesunit.MachinesDAOImpl;
import com.agricultural.domains.ExcelDataWriter;
import com.agricultural.domains.main.MachineTractorUnit;
import com.agricultural.service.MachineService;
import com.agricultural.service.MachineServiceImpl;
import com.agricultural.swing.frames.FrameLocation;
import com.agricultural.swing.frames.tablemodels.MachineTableModel;
import com.agricultural.swing.frames.tablerenderer.OperationMachineCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alexey on 16.02.2017.
 */
public class MachinesUnitFrame extends JFrame{

    private MachineService machineService = MachineServiceImpl.getInstance();

    private JPanel panel = new JPanel();

    private JLabel newMachineLabel = new JLabel("Введіть новий машинно-тракторний агрегат");
    private JTextField newOMachineTextField = new JTextField(20);
    private JButton newOMachineSaveButton = new JButton("Зберегти");

    private JButton refreshButton = new JButton("Оновити дані в таблиці");
    private  JButton writeExcelMachine = new JButton("Експортувати в Excel");

    /*additional panels*/
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();
    private JPanel p4 = new JPanel();

    private Font defaultFont = new Font("Serif",Font.PLAIN,18);

    private final Integer FRAME_WIDTH = Math.round(FrameLocation.screenSize.width/2.5f);
    private final Integer FRAME_HEIGHT = Math.round(FrameLocation.screenSize.height/2.5f);

    private final Integer DEFAULT_X_LOCATION = Math.round(FrameLocation.screenSize.width/2-FRAME_WIDTH/2);
    private final Integer DEFAULT_Y_LOCATION = Math.round(FrameLocation.screenSize.height/2-FRAME_HEIGHT/2);

    private MachineTableModel machineTableModel;
    private ArrayList<MachineTractorUnit> machines;

    public MachinesUnitFrame(){
        super("Машинно-тракторні агрегати");
        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_WIDTH,FRAME_HEIGHT);
        this.setVisible(true);
        Container container = this.getContentPane();

        panel.setLayout(new GridLayout(1,1));

        //зчитування назв всіх машинно-тракторних агрегатів
        machines = machineService.getMachines();
        if(machines.size()==0){
            machineService.createMachine("відсутній");
            machines.add(new MachineTractorUnit("відсутній"));
        }

        ///модель талиці для машино-тракторного агрегата
        machineTableModel = new MachineTableModel(machines);
        JTable table = new JTable(machineTableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(this.defaultFont);
        table.setDefaultRenderer(Object.class,new OperationMachineCellRenderer());
        table.getColumnModel().getColumn(1).setPreferredWidth(Math.round(FRAME_WIDTH/2));
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable t = (JTable) e.getSource();
                    String value = t.getValueAt(t.getSelectedRow(), t.getSelectedColumn()).toString();
                    ///зчитується номер рядка, з якого об'єкт передаємо далі на форму
                    int i = t.getSelectedRow();

                    if (value.equals("-")) {
                        ///isDelete = 0 - натиснуто YES, isDelete = 1 - натиснуто NO,
                        int isDelete = JOptionPane.showConfirmDialog(MachinesUnitFrame.this,
                                "Ви впевнені, що хочете видалити технологічну опрерацію " + "\"value\"" + "?",
                                "Увага!", JOptionPane.YES_NO_OPTION);
                        if (isDelete == 0) {
                            machineService.deleteMachine(machines.get(i));
                            MachinesUnitFrame.this.dispose();
                            MachinesUnitFrame machinesUnitFrame = new MachinesUnitFrame();
                        }

                    }
                }
            }
        });

        writeExcelMachine.addActionListener((e) -> {
            try {
                ExcelDataWriter.writeToExcelOperationMachine(machineTableModel,"Машинно-тракторні агрегати",false);
                JOptionPane.showMessageDialog(null, "Дані експортовано в excel файл",
                        "Виконано успішно",JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        newOMachineSaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ///Введене значення
                String value = newOMachineTextField.getText().trim();
                if(!value.equals("")) {
                    ///Всі назви машинно-тракторних агрегатів
                    String[] allMachines = machineService.getAllMachinesName();
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
                        machineService.createMachine(newOMachineTextField.getText().trim());
                        MachinesUnitFrame operations = new MachinesUnitFrame();
                        dispose();
                    }else JOptionPane.showMessageDialog(null, "Машинно-тракторний агрегат " + value + " вже існує",
                            "Увага!", JOptionPane.ERROR_MESSAGE);

                }else JOptionPane.showMessageDialog(null, "Заповніть необхіне поле",
                        "Увага!",JOptionPane.OK_CANCEL_OPTION);
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                machineTableModel.updateTableData();
                dispose();
                MachinesUnitFrame frame = new MachinesUnitFrame();
            }
        });

        ///box - де будуть знаходитися всі інші панелі
        Box box = new Box(BoxLayout.Y_AXIS);

        newMachineLabel.setFont(this.defaultFont);

        p1.setLayout(new FlowLayout());
        p1.add(newMachineLabel);
        p1.add(newOMachineTextField);
        p2.add(newOMachineSaveButton);

        p3.add(refreshButton);
        p3.add(writeExcelMachine);
        p4.setLayout(new BorderLayout());
        p4.add(new JScrollPane(table));

        box.add(p1);
        box.add(p2);
        box.add(p3);
        box.add(p4);

        panel.add(box);

        container.add(panel);
    }


}
