package com.agricultural.swing.frames.mainframes;

import com.agricultural.dao.operations.OperationDAOImpl;
import com.agricultural.service.OperationService;
import com.agricultural.service.impl.OperationServiceImpl;
import com.agricultural.swing.frames.FrameLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alexey on 14.02.2017.
 */
//class is not used now
public class AddOperationFrame extends JFrame {

    private OperationService operationService = OperationServiceImpl.getInstance();
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel("Операція");
    private JTextField textField = new JTextField(20);
    private JButton save = new JButton("Зберігти");
    private JButton cancel = new JButton("Закрити");

    ///допоміжні панелі
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();

    public AddOperationFrame(){
        super("Додати нову технологічну операцію");
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
                String value = textField.getText().trim();
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
                        operationService.createOperation(textField.getText());
                        TechnoOperationsFrame operations = new TechnoOperationsFrame();
                        dispose();
                    }else JOptionPane.showMessageDialog(null, "Технологічна оперція " + value + " вже існує",
                            "Увага!", JOptionPane.ERROR_MESSAGE);


                }else JOptionPane.showMessageDialog(null, "Заповніть необхіне поле","Увага!",JOptionPane.OK_CANCEL_OPTION);
            }
        });

        //listener на кнопку закрити
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TechnoOperationsFrame operations = new TechnoOperationsFrame();
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
