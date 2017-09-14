package com.agricultural.swing.frames.driverinformation;

import com.agricultural.domains.ExcelDataWriter;
import com.agricultural.domains.Month;
import com.agricultural.dao.detailnformation.DetailInformationDaoImpl;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.domains.main.Workplace;
import com.agricultural.service.*;
import com.agricultural.service.impl.*;
import com.agricultural.swing.frames.FrameLocation;
import com.agricultural.swing.frames.mainframes.TractorDriversFrame;
import com.agricultural.swing.frames.tablemodels.totalmoney.TotalLastTableCellRenderer;
import com.agricultural.swing.frames.tablemodels.totalmoney.TotalMoneyCellRenderer;
import com.agricultural.swing.frames.tablemodels.totalmoney.TotalMoneyTableModel;
import com.agricultural.swing.frames.tablerenderer.MainInfCellRenderer;
import com.agricultural.swing.frames.tablemodels.MainInformationHectareTableModel;
import com.agricultural.swing.frames.tablemodels.MainInformationHourTableModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

/**
 * Created by Alexey on 22.02.2017.
 */
public class MainInfoFrame extends JFrame {

    private JPanel mainpanel = new JPanel();
    private JScrollPane scrollPane;
    //для висвітлення назви фірми
    private JPanel headNamePanel = new JPanel();
    private JPanel p1 = new JPanel();
    private JPanel p2 = new JPanel();
    private JPanel p3 = new JPanel();
    private JPanel p31 = new JPanel();
    private JPanel p32 = new JPanel();
    private JPanel p33 = new JPanel();
    private JPanel headPanel = new JPanel();
    private JPanel hectarePanel = new JPanel();
    private JPanel timePanel = new JPanel();

    private JFrame employeeFrame;

    private JButton findInformation = new JButton("Оновити");
    private JTextField findInfLabel = new JTextField("виберіть місяць та рік і натисніть кнопку \"Оновити\" ");

    private JTextField positionLabel = new JTextField("Посада");
    private JTextField positionText = new JTextField();

    private JTextField pibLabel = new JTextField("ПІБ");
    private JTextField pibText = new JTextField();

    private JTextField workPlaceLabel = new JTextField("За місцем роботи");
    private JTextField workPlaceText = new JTextField();

    private JTextField wageLabel = new JTextField("Тарифна ставка, грн");
    private JTextField wageText = new JTextField();

    private JLabel monthLabel = new JLabel("Місяць");
    private JLabel yearLabel = new JLabel("Рік");

    private JButton addInfButtonHectare = new JButton("+");
    private JButton writeToExcel = new JButton("Експортувати дані у Excel");
    private JButton addInfButtonHour = new JButton("+");

    private String[] years = {"2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"
            ,"2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045"
            ,"2046","2047","2048","2049","2050"};

    private JComboBox monthCombo = new JComboBox();
    private JComboBox yearCombo = new JComboBox(years);
    private final Font TABLE_FONT = new Font("Serif", Font.PLAIN, 18);
    private final Font BORDER_FONT = new Font("Serif", Font.PLAIN, 22);
    private final Color HEADER_COLOR = new Color(207, 207, 207);
    ///тракторист інформацы якого знаходиться у таблиці
    private TractorDriver tractorDriver;

    private InformationHectareService infoHectareService = InformationHectareServiceImpl.getInstance();
    private InformationHourService infoHourService = InformationHourServiceImpl.getInstance();

    private DetailInformationService detailService = DetailInformationServiceImpl.getInstance();
    private TractorDriverService employeeService = TractorDriverServiceImpl.getInstance();
    private WorkplaceService workplaceService = WorkplaceServiceImpl.getInstance();

    private final Integer DEFAULT_X_LOCATION=0;
    private final Integer DEFAULT_Y_LOCATION=0;

    private final Integer FRAME_WIDTH=FrameLocation.screenSize.width;
    private final Integer FRAME_HEIGHT=FrameLocation.screenSize.height;

    private final Integer TABLE_WIDTH = FrameLocation.screenSize.width-10;
    private final Integer TABLE_HEIGHT = Math.round(FrameLocation.screenSize.height/5.5f);

    private List<DriverDataHectare> driverDataHectare;
    private List<DriverDataHour> driverDataHour;

    private MainInformationHectareTableModel modelHectare;
    private MainInformationHourTableModel modelTime;
    private double moneyForHectareAndHour;

    public MainInfoFrame(TractorDriver driver, int checkedMonth,int checkedYear){
        super(driver.getName());
        this.setLocation(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION);
        ///відкриття на весь екран
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(FRAME_WIDTH,FRAME_HEIGHT);
        this.setVisible(true);

        ///В шапці вікна текст для label становиться імя і зарплата тракториста

        positionText.setText(driver.getPosition());
        pibText.setText(driver.getName());
        workPlaceText.setText(driver.getWorkplace().getWorkPlaceName());
        //could not be changed
        workPlaceText.setEditable(false);
        wageText.setText(String.valueOf(driver.getWageRate()));

        ///ComboBox заповнюємо місяцями
        for(int i=0; i<Month.values().length;i++){
            monthCombo.addItem(Month.values()[i].getName());
        }
        ///Встановлює даний місяць
        monthCombo.setSelectedIndex(checkedMonth);
        ///Встановлює даний рік
        for(int i = 0; i<years.length;i++){
            if (Integer.parseInt(years[i])==checkedYear){
                yearCombo.setSelectedIndex(i);
                break;
            }
        }

//////////////////////////////////////////////////////////////////////////////////////////////////////////
        setLayout(new FlowLayout());

        Color textColorBackground = new Color(255, 255, 150);
        JTextField[] textLabels = {positionLabel,wageLabel,pibLabel,workPlaceLabel};
        for(JTextField field:textLabels){
            field.setFont(TABLE_FONT);
            field.setForeground(Color.RED);
            field.setBackground(textColorBackground);
            field.setEditable(false);
            field.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JTextField[] textFields = {pibText,workPlaceText,positionText,wageText};
        for(JTextField field:textFields){
            field.setFont(TABLE_FONT);
            field.setForeground(Color.RED);
            field.setBackground(textColorBackground);
            field.setHorizontalAlignment(SwingConstants.CENTER);
        }

        findInfLabel.setFont(TABLE_FONT);
        findInfLabel.setEditable(false);

        monthLabel.setFont(TABLE_FONT);
        yearLabel.setFont(TABLE_FONT);
        monthCombo.setFont(TABLE_FONT);
        yearCombo.setFont(TABLE_FONT);

        setLayout(new BorderLayout());
        mainpanel.setLayout(new FlowLayout());

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///Верхня панель
        headPanel.setLayout(new BorderLayout());
        p1.setLayout(new GridLayout(4,2));
        p1.add(positionLabel); p1.add(positionText);
        p1.add(pibLabel);      p1.add(pibText);
        p1.add(workPlaceLabel);p1.add(workPlaceText);
        p1.add(wageLabel);     p1.add(wageText);

        p2.add(monthLabel);    p2.add(monthCombo);
        p2.add(yearLabel);     p2.add(yearCombo);


        p3.setLayout(new BorderLayout());
        p31.add(findInfLabel);
        p32.add(findInformation);
        p32.add(writeToExcel);


        JLabel salaryLabel = new JLabel("Заробітня плата");
        JTextField salaryText = new JTextField(6);
        salaryText.setEditable(false);
        salaryLabel.setFont(new Font("Serif",Font.PLAIN,24));
        salaryLabel.setForeground(new Color(255, 106, 106));
        salaryText.setFont(new Font("Serif",Font.PLAIN,24));
        salaryText.setBackground(new Color(255, 106, 106));

        p33.add(salaryLabel);
        p33.add(salaryText);
        p3.add(p31, BorderLayout.NORTH);
        p3.add(p32,BorderLayout.CENTER);
        p3.add(p33,BorderLayout.SOUTH);

        JLabel headLabel = new JLabel("ОПЕРАТИВНІ ДАНІ ПІДПРИЄМСТВА");
        headLabel.setFont(new Font("Serif", Font.BOLD, 20));
        headNamePanel.add(headLabel);
        headNamePanel.setBackground(Color.GREEN);

        headPanel.add(p1, BorderLayout.WEST);
        headPanel.add(p2);
        headPanel.add(p3, BorderLayout.EAST);
        headPanel.add(headNamePanel,BorderLayout.NORTH);


//////////////////////////////////////////////////////////////////////////////////////////////////
        ///Кнопка для добавлення для гектарного виробітку
        ///рік передаємо як значення, місяць як порядковий номер-1
        addInfButtonHectare.addActionListener((e)->{
            AddHectareDataRowFrame frame = new AddHectareDataRowFrame(driver,checkedMonth,
                    monthCombo.getSelectedItem().toString(),Integer.parseInt((String) yearCombo.getSelectedItem()),this);
        });
        ///Кнопка для добавлення для годинного виробітку
        ///рік передаємо як значення, місяць як порядковий номер-1
        addInfButtonHour.addActionListener((e)->{
            AddHourDataRowFrame frame = new AddHourDataRowFrame(driver,checkedMonth,
                    monthCombo.getSelectedItem().toString(),Integer.parseInt((String) yearCombo.getSelectedItem()),this);
        });

        ///Рамка для таблиці Гектарний виробіток
        Border borderHectare = BorderFactory.createMatteBorder(3,3,3,3,Color.LIGHT_GRAY);
        Border titleHectare = BorderFactory.createTitledBorder(borderHectare,"ГЕКТАРНИЙ ВИРОБІТОК",2,2,BORDER_FONT);

        ///Таблиця Гектарний Виробіток
        driverDataHectare = infoHectareService.getAllHectareInf(driver.getDriver_id(), (String) monthCombo.getSelectedItem(), Integer.parseInt((String) yearCombo.getSelectedItem()));
        modelHectare = new MainInformationHectareTableModel(driverDataHectare);
        JTable tableHectare = new JTable(modelHectare);
        MainInfCellRenderer mainInfCellRenderer = new MainInfCellRenderer();
        tableHectare.setDefaultRenderer(Object.class,mainInfCellRenderer);
        tableHectare.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        tableHectare.setRowHeight(30);
        tableHectare.getTableHeader().setFont(TABLE_FONT);
        tableHectare.getTableHeader().setBackground(HEADER_COLOR);

        tableHectare.getColumnModel().getColumn(0).setMaxWidth(60);
        tableHectare.getColumnModel().getColumn(8).setMaxWidth(300);
        tableHectare.getColumnModel().getColumn(9).setMaxWidth(300);

        tableHectare.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==1) {
                    JTable t = (JTable) e.getSource();
                    String value = t.getValueAt(t.getSelectedRow(), t.getSelectedColumn()).toString();
                    ///зчитується номер рядка, з якого об'єкт передаємо далі на форму
                    int i = t.getSelectedRow();

                    DriverDataHectare dataHectare = driverDataHectare.get(i);

                    ///детальна інформація
                    ///value.equals("->")
                    if (value.equals("->") && t.getSelectedColumn() == 8) {
                        boolean flag = detailService.isDetailDataHectareExist(dataHectare.getData_id());
                        if (!flag) {
                            detailService.createDetailInformationHectare(dataHectare);
                        }
                        ///На mainInfFrame передається тракторист дані якого будуть показуватися
                        DetailHectareFrame frame = new DetailHectareFrame(dataHectare.getData_id(),
                                driverDataHectare.get(i).getOperation().getName(), driverDataHectare.get(i).getMachine().getName(), MainInfoFrame.this,
                                driver, monthCombo.getSelectedIndex(), Integer.parseInt((String) yearCombo.getSelectedItem()));
                    }

                    ///value.equals("-")
                    if (value.equals("-")) {
                        ///isDelete = 0 - натиснуто YES, isDelete = 1 - натиснуто NO,
                        int isDelete = JOptionPane.showConfirmDialog(MainInfoFrame.this, "Ви впевнені, що хочете видалити дані?",
                                "Увага!", JOptionPane.YES_NO_OPTION);
                        if (isDelete == 0) {
                            infoHectareService.deleteDriverDataHectare(dataHectare);
                            detailService.deleteDetailDataHectare(dataHectare.getData_id());
                            MainInfoFrame.this.dispose();
                            MainInfoFrame mainInfoFrame = new MainInfoFrame(driver,
                                    monthCombo.getSelectedIndex(), Integer.parseInt((String) yearCombo.getSelectedItem()));
                        }
                    }
                }
            }
        });

        ///панель для таблиці Гектарний Виробіткок
        hectarePanel.setBorder(titleHectare);
        Box addInfBoxHectare = new Box(BoxLayout.Y_AXIS);
        addInfBoxHectare.add(addInfButtonHectare);
        addInfBoxHectare.add(new JScrollPane(tableHectare));

        ///Таблиця для виводу повної суми грошей для гектарного виробітку
        TotalMoneyTableModel moneyHectareTableModel = new TotalMoneyTableModel(driverDataHectare,null);
        JTable totalMoneyHectareTable = new JTable(moneyHectareTableModel);
        totalMoneyHectareTable.setDefaultRenderer(Object.class,new TotalMoneyCellRenderer());
        totalMoneyHectareTable.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        totalMoneyHectareTable.setRowHeight(30);
        totalMoneyHectareTable.getTableHeader().setFont(TABLE_FONT);
        totalMoneyHectareTable.getTableHeader().setBackground(HEADER_COLOR);
        totalMoneyHectareTable.getColumnModel().getColumn(0).setMaxWidth(60);
        totalMoneyHectareTable.getColumnModel().getColumn(8).setMaxWidth(300);
        totalMoneyHectareTable.getColumnModel().getColumn(9).setMaxWidth(300);

        addInfBoxHectare.add(totalMoneyHectareTable);

        hectarePanel.add(addInfBoxHectare);
        ////////////////////////////////////////////////////////

        /// Рамка для таблиці Годинний виробіток
        Border borderTime = BorderFactory.createMatteBorder(3,3,3,3,Color.LIGHT_GRAY);
        Border titleTime = BorderFactory.createTitledBorder(borderTime,"ГОДИННИЙ ВИРОБІТОК",2,2,BORDER_FONT);
        ///Таблиця Годинного виробітку
        driverDataHour = infoHourService.getAllHourInf(driver.getDriver_id(), (String) monthCombo.getSelectedItem(), Integer.parseInt((String) yearCombo.getSelectedItem()));
        modelTime = new MainInformationHourTableModel(driverDataHour);

        JTable tableTime = new JTable(modelTime);
        tableTime.getTableHeader().setFont(TABLE_FONT);
        tableTime.getTableHeader().setBackground(HEADER_COLOR);
        tableTime.setDefaultRenderer(Object.class,mainInfCellRenderer);
        tableTime.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        tableTime.setRowHeight(30);

        tableTime.getColumnModel().getColumn(0).setMaxWidth(60);
        tableTime.getColumnModel().getColumn(8).setMaxWidth(300);
        tableTime.getColumnModel().getColumn(9).setMaxWidth(300);
        tableTime.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable t = (JTable) e.getSource();
                    String value = t.getValueAt(t.getSelectedRow(), t.getSelectedColumn()).toString();
                    ///зчитується номер рядка, з якого об'єкт передаємо далі на форму
                    int i = t.getSelectedRow();

                    DriverDataHour driverHour = driverDataHour.get(i);

                    if (value.equals("->")) {
                        boolean flag = detailService.isDetailDataHourExist(driverHour.getData_id());
                        if (!flag) {
                            detailService.createDetailInformationHour(driverHour);
                        }
                        ///На mainInfFrame передається тракторист дані якого будуть показуватися
                        DetailHourFrame frame = new DetailHourFrame( driverHour.getData_id(),
                                driverDataHour.get(i).getOperation().getName(), driverDataHour.get(i).getMachine().getName(),MainInfoFrame.this,
                                driver,monthCombo.getSelectedIndex(),Integer.parseInt((String) yearCombo.getSelectedItem()));
                    }

                    ///видалення рядка даних
                    ///value.equals("-")
                    if(value.equals("-")){
                        int isDelete = JOptionPane.showConfirmDialog(MainInfoFrame.this, "Ви впевнені, що хочете видалити дані?",
                                "Увага!", JOptionPane.YES_NO_OPTION);
                        if(isDelete==0){
                            infoHourService.deleteDriverDataHour(driverHour);
                            MainInfoFrame.this.dispose();
                            MainInfoFrame mainInfoFrame = new MainInfoFrame(driver,
                                    monthCombo.getSelectedIndex(),Integer.parseInt((String) yearCombo.getSelectedItem()));
                        }
                    }
                }
            }
        });
        ///панель для таблиці Годинний Виробіток
        timePanel.setBorder(titleTime);
        Box addInfBoxTime = new Box(BoxLayout.Y_AXIS);
        addInfBoxTime.add(addInfButtonHour);
        addInfBoxTime.add(new JScrollPane(tableTime));

        ///Таблиця для виводу повної суми грошей для годинного виробітку
        TotalMoneyTableModel moneyHourTableModel = new TotalMoneyTableModel(null,driverDataHour);
        JTable totalMoneyHourTable = new JTable(moneyHourTableModel);
        totalMoneyHourTable.setDefaultRenderer(Object.class,new TotalMoneyCellRenderer());
        totalMoneyHourTable.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH,TABLE_HEIGHT));
        totalMoneyHourTable.setRowHeight(30);
        totalMoneyHourTable.getTableHeader().setFont(TABLE_FONT);
        totalMoneyHourTable.getTableHeader().setBackground(HEADER_COLOR);
        totalMoneyHourTable.getColumnModel().getColumn(0).setMaxWidth(60);
        totalMoneyHourTable.getColumnModel().getColumn(8).setMaxWidth(300);
        totalMoneyHourTable.getColumnModel().getColumn(9).setMaxWidth(300);

        addInfBoxTime.add(totalMoneyHourTable);

        timePanel.add(addInfBoxTime);

        TotalMoneyTableModel lastTotalTableModel = new TotalMoneyTableModel(moneyHectareTableModel.getTotalMoney(),moneyHectareTableModel.getTotalGivenFuel(),
                moneyHourTableModel.getTotalMoney(),moneyHourTableModel.getTotalGivenFuel());
        JTable totalValues = new JTable(lastTotalTableModel);
        totalValues.setDefaultRenderer(Object.class,new TotalLastTableCellRenderer());
        totalValues.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH,40));
        totalValues.setRowHeight(30);
        totalValues.getTableHeader().setFont(TABLE_FONT);
        totalValues.getTableHeader().setBackground(HEADER_COLOR);
        totalValues.getColumnModel().getColumn(0).setMaxWidth(60);
        totalValues.getColumnModel().getColumn(8).setMaxWidth(300);
        totalValues.getColumnModel().getColumn(9).setMaxWidth(300);

        moneyForHectareAndHour = lastTotalTableModel.getTotalForAll();

        ///після обрахунку сумарної кількості грошей за гектарний і годинний виробіток, порівнюємо зі ставкої і більше виводиться як зарплата
        if(moneyForHectareAndHour>driver.getWageRate())salaryText.setText(String.valueOf(moneyForHectareAndHour));
        else salaryText.setText(String.valueOf(driver.getWageRate()));

        ///Таблиця в якій виводиться повна сума грошей за гектарний і годинний виробітки
        Border borderTotal = BorderFactory.createMatteBorder(3,3,3,3,Color.LIGHT_GRAY);
        Border titleTotal = BorderFactory.createTitledBorder(borderTotal,"Сумарний результат",2,2,
                new Font("Serif",Font.PLAIN,24));
        ///панель на якій остання таблиця
        JPanel totalPanel = new JPanel();
        totalPanel.setBorder(titleTotal);
        totalPanel.add( new JScrollPane(totalValues));

        ///////////////////////////////////////////////////////////////////////////
        ///listener на кнопку додати Інфу у таблицю гектарний виробіток
        Workplace headWorkPlace = workplaceService.getWorkplaceByName(workPlaceText.getText().trim());
        findInformation.addActionListener((e)-> {
            ///треба оновлювати данні по працівникам
            ///якщо змінені якісь дані трактористу то оновлюємо їх
            TractorDriversFrame emplFrame = null;
            boolean isUpdate = false;
            if(!headWorkPlace.getWorkPlaceName().equals(workPlaceText.getText().trim())
                    || !driver.getName().equals(pibText.getText().trim())
                    || driver.getWageRate() != Integer.valueOf(wageText.getText().trim())
                    || !driver.getPosition().equals(positionText.getText().trim())) {
                isUpdate=true;
            }

            if(isUpdate) {
                //update workplace
                if (!headWorkPlace.getWorkPlaceName().equals(workPlaceText.getText().trim())) {
                    headWorkPlace.setWorkPlaceName(workPlaceText.getText().trim());
                    workplaceService.editWorkplace(headWorkPlace);
                    driver.setWorkplace(headWorkPlace);
                }

                if (!driver.getName().equals(pibText.getText().trim()) || driver.getWageRate()!=Integer.valueOf(wageText.getText().trim())
                        || !driver.getPosition().equals(positionText.getText().trim())) {
                    driver.setName(pibText.getText().trim());
                    driver.setPosition(positionText.getText().trim());
                    driver.setWageRate(Integer.valueOf(wageText.getText().trim()));
                    employeeService.editTractorDriver(driver);
                    MainInfoFrame.this.dispose();
//                    driver.setWorkplace(headWorkPlace);
                }
                this.getEmployeeFrame().dispose();
                emplFrame = new TractorDriversFrame();
            }
            ///оновлюються дані, які можливо були введені у 6 колонку
            modelHectare.updateTableData();
            modelTime.updateTableData();
            dispose();
            MainInfoFrame mainInfoFrame = new MainInfoFrame(driver,
                    monthCombo.getSelectedIndex(),Integer.parseInt((String) yearCombo.getSelectedItem()));
            if(isUpdate)
                mainInfoFrame.setEmployeeFrame(emplFrame);
            else mainInfoFrame.setEmployeeFrame(this.getEmployeeFrame());
        });

        writeToExcel.addActionListener(e -> {
            try {
                ExcelDataWriter.writeToExcelAllDriverInformation(driver,
                        String.valueOf(monthCombo.getSelectedItem()), String.valueOf(checkedYear),modelHectare,
                        moneyHectareTableModel,modelTime,moneyHourTableModel,lastTotalTableModel);
                JOptionPane.showMessageDialog(null, "Дані експортовано в excel файл",
                        "Виконано успішно",JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        });

        mainpanel.setLayout(new BorderLayout());
        mainpanel.add(headPanel,BorderLayout.NORTH);

        Box tempBox = new Box(BoxLayout.Y_AXIS);
        tempBox.add(hectarePanel);
        tempBox.add(timePanel);

        mainpanel.add(tempBox,BorderLayout.CENTER);
        mainpanel.add(totalPanel,BorderLayout.SOUTH);

        scrollPane = new JScrollPane(mainpanel);
        add(scrollPane);

    }

    public JFrame getEmployeeFrame() {
        return employeeFrame;
    }

    public void setEmployeeFrame(JFrame employeeFrame) {
        this.employeeFrame = employeeFrame;
    }

}