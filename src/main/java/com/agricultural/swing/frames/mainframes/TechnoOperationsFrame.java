package com.agricultural.swing.frames.mainframes;

import com.agricultural.dao.operations.OperationDAOImpl;
import com.agricultural.domains.ExcelDataWriter;
import com.agricultural.domains.main.TechnologicalOperation;
import com.agricultural.swing.frames.FrameLocation;
import com.agricultural.swing.frames.tablemodels.OperationTableModel;
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
 * Created by Alexey on 14.02.2017.
 */
public class TechnoOperationsFrame extends JFrame {

    private OperationDAOImpl service = new OperationDAOImpl();

    private  JButton writeExcel = new JButton("Експортувати в Excel");

    private JLabel newOperationLabel = new JLabel("Введіть нову технологічну операцію");
    private JTextField newOperationTextField = new JTextField(20);
    private JButton newOperationSaveButton = new JButton("Зберегти");

    private JPanel mainPanel = new JPanel();
    private JButton refreshButton = new JButton("Оновити дані в таблиці");

    /*додаткові панелі*/
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();
    private JPanel p4 = new JPanel();

    private OperationTableModel operationTableModel;
    private ArrayList<TechnologicalOperation> operations;

    private final Integer FRAME_WIDTH = Math.round(FrameLocation.screenSize.width/2.5f);
    private final Integer FRAME_HEIGHT = Math.round(FrameLocation.screenSize.height/2.5f);

    private final Integer DEFAULT_X_LOCATION = Math.round(FrameLocation.screenSize.width/2-FRAME_WIDTH/2);
    private final Integer DEFAULT_Y_LOCATION = Math.round(FrameLocation.screenSize.height/2-FRAME_HEIGHT/2);

    public TechnoOperationsFrame(){
        super("Вид робіт");
        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_WIDTH,FRAME_HEIGHT);
        this.setVisible(true);
        Container container = this.getContentPane();
        mainPanel.setLayout(new GridLayout(1,1));

        operations = service.getOperations();

        ///модель талиці для технологічної операції
        operationTableModel = new OperationTableModel(operations);

        writeExcel.addActionListener((e) -> {
            try {
                ExcelDataWriter.writeToExcelOperationMachine(operationTableModel,"Технологічні оперції",true);
                JOptionPane.showMessageDialog(null, "Дані експортовано в excel файл",
                        "Виконано успішно",JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });


        JTable table = new JTable(operationTableModel);
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Serif",Font.PLAIN,18));
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
                        int isDelete = JOptionPane.showConfirmDialog(TechnoOperationsFrame.this,
                                "Ви впевнені, що хочете видалити технологічну опрерацію " + "\"value\"" + "?",
                                "Увага!", JOptionPane.YES_NO_OPTION);
                        if (isDelete == 0) {
                            service.deleteOperation(operations.get(i));
                            TechnoOperationsFrame.this.dispose();
                            TechnoOperationsFrame operationsFrame = new TechnoOperationsFrame();
                        }

                    }
                }
            }
        });

        //listener на кнопку зберегти
        newOperationSaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String value = newOperationTextField.getText().trim();
                if(!value.equals("")) {
                    ///зчитується для перевірки на наявність
                    String[] allOperations = service.getAllOperationsName();
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
                        service.createOperation(newOperationTextField.getText());
                        TechnoOperationsFrame operations = new TechnoOperationsFrame();
                        dispose();
                    }else JOptionPane.showMessageDialog(null, "Технологічна оперція " + value + " вже існує",
                            "Увага!", JOptionPane.ERROR_MESSAGE);


                }else JOptionPane.showMessageDialog(null, "Заповніть необхіне поле","Увага!",JOptionPane.OK_CANCEL_OPTION);
            }
        });

        ///
        refreshButton.addActionListener(e-> {
                operationTableModel.updateTableData();
                dispose();
                TechnoOperationsFrame frame = new TechnoOperationsFrame();
        });


        ///box - де будуть знаходитися всі інші панелі
        Box box = new Box(BoxLayout.Y_AXIS);

        newOperationLabel.setFont(new Font("Serif",Font.PLAIN,18));

        p1.setLayout(new FlowLayout());
        p1.add(newOperationLabel);
        p1.add(newOperationTextField);

        p2.add(newOperationSaveButton);

        p3.add(refreshButton);
        p3.add(writeExcel);

        p4.setLayout(new BorderLayout());
        p4.add(new JScrollPane(table));



        box.add(p1);
        box.add(p2);
        box.add(p3);
        box.add(p4);

        mainPanel.add(box);

        container.add(mainPanel);
    }

}
