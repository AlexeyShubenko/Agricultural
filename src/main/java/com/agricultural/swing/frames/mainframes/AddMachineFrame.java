package com.agricultural.swing.frames.mainframes;

import com.agricultural.service.MachineService;
import com.agricultural.service.MachineServiceImpl;
import com.agricultural.swing.frames.FrameLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexey on 14.02.2017.
 */
//class is not used now
public class AddMachineFrame extends JFrame {

    private MachineService machineService = MachineServiceImpl.getInstance();

    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("Машинно-тракторний агрегат");
    private JTextField textField = new JTextField(20);
    private JButton save = new JButton("Зберігти");
    private JButton cancel = new JButton("Закрити");

    ///допоміжні панелі
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();

    public AddMachineFrame(){
        super("Додати машинно-тракторний агрегат");
        this.setBounds(FrameLocation.locationX,FrameLocation.locationY,500,150);
        this.setVisible(true);
        Container container = this.getContentPane();
        panel.setLayout(new GridLayout(2,1));
        p1.setLayout(new FlowLayout());
        p1.add(label);
        p1.add(textField);

        //listener на кнопку зберегти
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ///Введене значення
                String value = textField.getText().trim();
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
                        machineService.createMachine(textField.getText().trim());
                        MachinesUnitFrame operations = new MachinesUnitFrame();
                        dispose();
                    }else JOptionPane.showMessageDialog(null, "Машинно-тракторний агрегат " + value + " вже існує",
                            "Увага!", JOptionPane.ERROR_MESSAGE);

                }else JOptionPane.showMessageDialog(null, "Заповніть необхіне поле",
                        "Увага!",JOptionPane.OK_CANCEL_OPTION);
            }
        });

        //listener на кнопку закрити
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MachinesUnitFrame operations = new MachinesUnitFrame();
                dispose();
            }
        });

        p2.setLayout(new FlowLayout());
        p2.add(save);
        p2.add(cancel);
        panel.add(p1);
        panel.add(p2);
        container.add(panel);

    }


}
