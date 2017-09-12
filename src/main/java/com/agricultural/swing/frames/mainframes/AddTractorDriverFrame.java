package com.agricultural.swing.frames.mainframes;

import com.agricultural.dao.workplaces.WorkplaceDaoImpl;
import com.agricultural.domains.RegExp;
import com.agricultural.domains.main.Workplace;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.service.TractorDriverService;
import com.agricultural.service.WorkplaceService;
import com.agricultural.service.impl.TractorDriverServiceImpl;
import com.agricultural.service.impl.WorkplaceServiceImpl;
import com.agricultural.swing.frames.FrameLocation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Created by Alexey on 17.02.2017.
 */
public class AddTractorDriverFrame extends JFrame {

    private TractorDriverService tractorDriverService = TractorDriverServiceImpl.getInstance();
    private WorkplaceService workplaceService = WorkplaceServiceImpl.getInstance();

    private JButton save = new JButton("Зберегти");
    private JButton cancel = new JButton("Закрити");

    private JLabel fio = new JLabel("ФІО");
    private JTextField fioText = new JTextField(15);

    private JLabel wageRate = new JLabel("Ставка");
    private JTextField wageRateText = new JTextField(15);

    private JLabel position = new JLabel("Посада");
    private JTextField positionText = new JTextField(15);

    private JLabel workplace = new JLabel("За місцем роботи");

    ///допоміжні панелі
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();
    private JPanel p4 = new JPanel();
    private JPanel p5 = new JPanel();

    private final Integer DEFAULT_X_LOCATION = FrameLocation.screenSize.width/3;
    private final Integer DEFAULT_Y_LOCATION = FrameLocation.screenSize.height/4;

    private final Integer FRAME_X = Math.round(FrameLocation.screenSize.width/3);
    private final Integer FRAME_Y = Math.round(FrameLocation.screenSize.height/4);

    private ArrayList<Workplace> workplaces;
    private JComboBox workPlaceComboBox = new JComboBox<>();

    public AddTractorDriverFrame(TractorDriver driver, TractorDriversFrame ownerTractorDriversFrame){
        super("Додати нового тракториста");
        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_X,FRAME_Y);
        setVisible(true);

        workplaces = workplaceService.getWorkplaces();
        for (Workplace place:workplaces){
            workPlaceComboBox.addItem(place.getWorkPlaceName());
        }
        workPlaceComboBox.addItem("додати");


//        String workPlace = workPlaceComboBox.getSelectedItem().toString().trim();

        ///як передається заповнений об'єкт, тобто для оновлення, або новий з уже введеними даними
        if(driver!=null){
            fioText.setText(driver.getName());
            wageRateText.setText(String.valueOf(driver.getWageRate()));
            positionText.setText(driver.getPosition());
            if(driver.getWorkplace()!=null)
            workPlaceComboBox.setSelectedItem(driver.getWorkplace().getWorkPlaceName());
        }


        workPlaceComboBox.addActionListener(e->{
            String fioValue = fioText.getText().trim();
            String wageValue = wageRateText.getText().trim();
            String positionValue = positionText.getText().trim();
            if(driver==null){
                TractorDriver newEmployee = new TractorDriver();
                newEmployee.setName(fioValue);
                if (wageValue.equals("")) {
                    newEmployee.setWageRate(0);
                } else
                    newEmployee.setWageRate(Integer.valueOf(wageValue));

                newEmployee.setPosition(positionValue);

                //якщо треба додати нове значення місця роботи
                if(workPlaceComboBox.getSelectedItem().equals("додати")){
                    WorkPlaceFrame workPlaceFrame = new WorkPlaceFrame(newEmployee, ownerTractorDriversFrame);
                    workPlaceFrame.setOwnFrame(AddTractorDriverFrame.this);
                }
            }else{
                driver.setName(fioValue);
                driver.setWageRate(Integer.valueOf(wageValue));
                driver.setPosition(positionValue);
                if(workPlaceComboBox.getSelectedItem().equals("додати")){
                    WorkPlaceFrame workPlaceFrame = new WorkPlaceFrame(driver, ownerTractorDriversFrame);
                    workPlaceFrame.setOwnFrame(AddTractorDriverFrame.this);
                }
            }

        });

        save.addActionListener(e-> {
            String fioValue = fioText.getText().trim();
            String wageValue = wageRateText.getText().trim();
            String positionValue = positionText.getText().trim();
                ///Перевірка на те чи всі поля введені
                if(!fioValue.equals("") && !wageValue.equals("") && !positionValue.equals("") ) {
                    ///Перевірка ставки на наявність не правельних символі
                    if (RegExp.allNumbers(wageValue)) {
                        ///якщо ставка введена правильно то виконється далі
                        ///зчитаєються імена всіх трактористів
                        String[] allTractorDrivers = tractorDriverService.getAllTractorDriversName();

                        boolean flag = false;
                        for (int i = 0; i < allTractorDrivers.length; i++) {
                            //якщо введене значення вже існує в базі то виконується блок if
                            if (allTractorDrivers[i].compareToIgnoreCase(fioValue) == 0) {
                                //true - таке значення є
                                flag = true;
                                break;
                            }
                        }
                        ///Якщо тракториста не існую то він записуєтсья в базу
                        if(driver==null) {
                            if (!flag) {
                                TractorDriver newDriver = new TractorDriver();
                                newDriver.setName(fioValue);
                                newDriver.setWageRate(Integer.parseInt(wageValue));
                                newDriver.setPosition(positionValue);
                                newDriver.setWorkplace(workplaces.get(workPlaceComboBox.getSelectedIndex()));

                                tractorDriverService.createOrUpdateTractorDriver(newDriver);
                                ownerTractorDriversFrame.dispose();
                                TractorDriversFrame frame = new TractorDriversFrame();
                                this.dispose();


                            } else JOptionPane.showMessageDialog(null, "Механізатор " + fioValue + " вже існує",
                                    "Увага!", JOptionPane.ERROR_MESSAGE);

                            ///при редагуванні виконується оновлення нижче
                        }else  {
                            driver.setName(fioValue);
                            driver.setWageRate(Integer.parseInt(wageValue));
                            driver.setPosition(positionValue);
                            driver.setWorkplace(workplaces.get(workPlaceComboBox.getSelectedIndex()));
                            tractorDriverService.createOrUpdateTractorDriver(driver);
                            ownerTractorDriversFrame.dispose();
                            TractorDriversFrame frame = new TractorDriversFrame();
                            this.dispose();
                        }

                    }else JOptionPane.showMessageDialog(null, "Не правильно введені дані в поле Ставка  ",
                            "Увага!", JOptionPane.ERROR_MESSAGE);
                }else JOptionPane.showMessageDialog(null, "Заповніть необхіні поля","Увага!",JOptionPane.OK_CANCEL_OPTION);

        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ownerTractorDriversFrame.dispose();
                TractorDriversFrame frame = new TractorDriversFrame();
                dispose();
            }
        });

        ///listener на кнопку закрит
        cancel.addActionListener(e->{
            ownerTractorDriversFrame.dispose();
            TractorDriversFrame frame = new TractorDriversFrame();
            this.dispose();
        });

        p1.add(fio);
        p1.add(fioText);
        fio.setHorizontalAlignment(SwingConstants.CENTER);
        wageRate.setHorizontalAlignment(SwingConstants.CENTER);
        position.setHorizontalAlignment(SwingConstants.CENTER);
        workplace.setHorizontalAlignment(SwingConstants.CENTER);

        p2.add(wageRate);
        p2.add(wageRateText);

        p3.add(position);
        p3.add(positionText);

        p4.add(workplace);
        p4.add(workPlaceComboBox);

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(5,5));
        dataPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        dataPanel.add(p1);
        dataPanel.add(p2);
        dataPanel.add(p3);
        dataPanel.add(p4);

        p5.add(save);
        p5.add(cancel);

        dataPanel.add(p5);

        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(dataPanel);

        add(box);
    }

}
