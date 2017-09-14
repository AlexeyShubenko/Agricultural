package com.agricultural.swing.frames.allinformation;

import com.agricultural.domains.Month;
import com.agricultural.domains.main.Workplace;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.service.*;
import com.agricultural.service.impl.InformationHectareServiceImpl;
import com.agricultural.service.impl.InformationHourServiceImpl;
import com.agricultural.service.impl.TractorDriverServiceImpl;
import com.agricultural.service.impl.WorkplaceServiceImpl;
import com.agricultural.swing.frames.FrameLocation;
import com.agricultural.swing.frames.tablemodels.AllInformationTableModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.util.List;
import java.awt.*;
import java.util.*;

/**
 * Created by Alexey on 21.03.2017.
 */
public class AllInformationFrame extends JFrame{

    private JScrollPane mainScrollPane;
    private Box mainBox = new Box(BoxLayout.Y_AXIS);

    private JPanel comboBoxPanel = new JPanel();
    private JPanel mainPanel = new JPanel();

    private String[] years = {"2016","2017","2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030"
            ,"2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045"
            ,"2046","2047","2048","2049","2050"};

    private Font generalFont = new Font("Serif",Font.PLAIN,16);
    private JLabel monthLabel = new JLabel("Місяць");
    private JLabel yearLabel = new JLabel("Рік");
    private JLabel hectareHourLabel;
    private JComboBox monthCombo = new JComboBox();
    private JComboBox yearCombo = new JComboBox(years);
    private int checkedMonth;
    private int checkedYear;
    private JButton refreshButton = new JButton("Оновити");

    private WorkplaceService workplaceService = WorkplaceServiceImpl.getInstance();
    private TractorDriverService employeeService = TractorDriverServiceImpl.getInstance();

    private InformationHectareService infoHectareService = InformationHectareServiceImpl.getInstance();
    private InformationHourService infoHourService = InformationHourServiceImpl.getInstance();

    private final Integer DEFAULT_X_LOCATION=0;
    private final Integer DEFAULT_Y_LOCATION=0;

    private final Integer TABLE_WIDTH = FrameLocation.screenSize.width+200;
    private final Integer TABLE_HEIGHT = Math.round(FrameLocation.screenSize.height/7f);

    private final Integer FRAME_WIDTH = FrameLocation.screenSize.width+500;
    private final Integer FRAME_HEIGHT = FrameLocation.screenSize.height;

    private final Font TABLE_FONT = new Font("Serif", Font.PLAIN, 18);
    private final Color HEADER_COLOR = new Color(207, 207, 207);

    private JPanel headPanel;

    public AllInformationFrame(int month, int year){
        super("Зведена інформація");
        this.setBounds(DEFAULT_X_LOCATION,DEFAULT_Y_LOCATION,FRAME_WIDTH,FRAME_HEIGHT);
        this.setVisible(true);

        checkedMonth = month;
        checkedYear =  year;

        ///ComboBox заповнюємо місяцями
        for(int i = 0; i< Month.values().length; i++){
            monthCombo.addItem(Month.values()[i].getName());
        }
        ///Встановлює даний місяць
        monthCombo.setSelectedIndex(checkedMonth);
        monthCombo.setFont(generalFont);
        monthLabel.setFont(generalFont);
        ///Встановлює даний рік
        yearCombo.setFont(generalFont);
        yearLabel.setFont(generalFont);
        for(int i = 0; i<years.length;i++){
            if (Integer.parseInt(years[i])==checkedYear){
                yearCombo.setSelectedIndex(i);
                break;
            }
        }
        comboBoxPanel.add(monthLabel);    comboBoxPanel.add(monthCombo);
        comboBoxPanel.add(yearLabel);     comboBoxPanel.add(yearCombo);
        comboBoxPanel.add(refreshButton);
        refreshButton.addActionListener(e->{
            this.dispose();
            AllInformationFrame updateFrame = new AllInformationFrame(monthCombo.getSelectedIndex(),
                    Integer.valueOf((String) yearCombo.getSelectedItem()));
        });

        mainBox.add(comboBoxPanel);
        //////////////////////////////////////////////////////////////////////////////
        ///Всі workPlace
        ArrayList<Workplace> workplaces = workplaceService.getWorkplaces();
        ///Всі працівники
        ArrayList<TractorDriver> employees = employeeService.getTractorDrivers();
        ////треба працівників розділити на списки за workPlace
        ///відповідно до дати творюється n-кількість табличок для різної кількості workplace

        ////////////////////////////////////////////////////
        /////////////////ГЕКТАРНИЙ ВИРОБІТОК////////////////
        ////////////////////////////////////////////////////
        for (int i = 0; i < workplaces.size(); i++) {
            JPanel infoPanel = new JPanel();
            Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.LIGHT_GRAY);
            Border title = BorderFactory.createTitledBorder(border,
                    "Зведена інформація по \"" + workplaces.get(i).getWorkPlaceName() + "\"", 2, 2,
                    new Font("Serif", Font.PLAIN, 20));

            List<DriverDataHectare> employeeDataHectare = new ArrayList<>();
            for (int j = 0; j < employees.size(); j++) {
                ///розбиття робітників за workplace
                if (workplaces.get(i).getWorkPlaceName().equals(employees.get(j).getWorkplace().getWorkPlaceName())) {
                    ///по кожному працівнику треба отримати масив driverDataHectare
                    List<DriverDataHectare> e = infoHectareService.getAllHectareInf(employees.get(j).getDriver_id()
                            , String.valueOf(monthCombo.getSelectedItem()), checkedYear);
                    if (e != null) {
                        employeeDataHectare.addAll(e);
                    }
                }
            }

            AllInformationTableModel allInformationTableModel = new AllInformationTableModel(employeeDataHectare,null);
            JTable table = new JTable(allInformationTableModel);
            table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
            table.setRowHeight(30);


            ///TABLE RENDERER
            AllInfCellRenderer renderer = new AllInfCellRenderer();
            table.setDefaultRenderer(Object.class,renderer);
            table.getTableHeader().setFont(TABLE_FONT);
            table.getTableHeader().setBackground(HEADER_COLOR);
            table.getColumnModel().getColumn(1).setPreferredWidth(Math.round(TABLE_WIDTH*0.2f));
            table.getColumnModel().getColumn(2).setPreferredWidth(Math.round(TABLE_WIDTH*0.3f));

            hectareHourLabel = new JLabel("ГЕКТАРНИЙ ВИРОБІТОК");
            hectareHourLabel.setFont(new Font("Serif", Font.BOLD, 20));

            headPanel = new JPanel();
            headPanel.setBackground(new Color(144, 238, 144));
            headPanel.add(hectareHourLabel);
            infoPanel.setBorder(title);
            infoPanel.setLayout(new BorderLayout());
            infoPanel.add(new JScrollPane(table));

            mainBox.add(headPanel);
            mainBox.add(infoPanel);
        }

        ////////////////////////////////////////////////////
        /////////////////ГОДИННИЙ ВИРОБІТОК/////////////////
        ////////////////////////////////////////////////////
        for (int i = 0; i < workplaces.size(); i++) {
            JPanel infoPanel = new JPanel();
            Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.LIGHT_GRAY);
            Border title = BorderFactory.createTitledBorder(border,
                    "Зведена інформація по \"" + workplaces.get(i).getWorkPlaceName() + "\"", 2, 2,
                    new Font("Serif", Font.PLAIN, 20));

            List<DriverDataHour> employeeDataHour = new ArrayList<>();
            for (int j = 0; j < employees.size(); j++) {
                ///розбиття робітників за workplace
                if (workplaces.get(i).getWorkPlaceName().equals(employees.get(j).getWorkplace().getWorkPlaceName())) {
                    ///по кожному працівнику треба отримати масив driverDataHectare
                    List<DriverDataHour> e = infoHourService.getAllHourInf(employees.get(j).getDriver_id()
                            , String.valueOf(monthCombo.getSelectedItem()), checkedYear);
                    if (e != null) {
                        employeeDataHour.addAll(e);
                    }
                }
            }

            AllInformationTableModel allInformationTableModel = new AllInformationTableModel(null,employeeDataHour);
            JTable table = new JTable(allInformationTableModel);
            table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
            table.setRowHeight(30);

            ///TABLE RENDERER
            table.setDefaultRenderer(Object.class,new AllInfCellRenderer());
            table.getTableHeader().setFont(TABLE_FONT);
            table.getTableHeader().setBackground(HEADER_COLOR);

            table.getColumnModel().getColumn(1).setPreferredWidth(Math.round(TABLE_WIDTH*0.2f));
            table.getColumnModel().getColumn(2).setPreferredWidth(Math.round(TABLE_WIDTH*0.3f));

            hectareHourLabel = new JLabel("ГОДИННИЙ ВИРОБІТОК");
            hectareHourLabel.setFont(new Font("Serif", Font.BOLD, 20));

            headPanel = new JPanel();
            headPanel.setBackground(new Color(250, 128, 114));
            headPanel.add(hectareHourLabel);
            infoPanel.setBorder(title);
            infoPanel.setLayout(new BorderLayout());
            infoPanel.add(new JScrollPane(table));

            mainBox.add(headPanel);
            mainBox.add(infoPanel);
        }

        ////////////////////////////////////////////////////
        /////////////////ВИРОРИСТАНЕ ПАЛИВО/////////////////
        ////////////////////////////////////////////////////
        for (int i = 0; i < workplaces.size(); i++) {
            JPanel infoPanel = new JPanel();
            Border border = BorderFactory.createMatteBorder(3, 3, 3, 3, Color.LIGHT_GRAY);
            Border title = BorderFactory.createTitledBorder(border,
                    "Зведена інформація по \"" + workplaces.get(i).getWorkPlaceName() + "\"", 2, 2,
                    new Font("Serif", Font.PLAIN, 20));

            List<DriverDataHour> employeeDataHour = new ArrayList<>();

            for (int j = 0; j < employees.size(); j++) {
                ///розбиття робітників за workplace
                if (workplaces.get(i).getWorkPlaceName().equals(employees.get(j).getWorkplace().getWorkPlaceName())) {
                    ///по кожному працівнику треба отримати масив driverDataHour
                    List<DriverDataHour> e = infoHourService.getAllHourInf(employees.get(j).getDriver_id()
                            , String.valueOf(monthCombo.getSelectedItem()), checkedYear);
                    if (e != null) {
                        employeeDataHour.addAll(e);
                    }
                }
            }

            ///зчитується годинний виробіток
            List<DriverDataHectare> employeeDataHectare = new ArrayList<>();
            for (int j = 0; j < employees.size(); j++) {
                ///розбиття робітників за workplace
                if (workplaces.get(i).getWorkPlaceName().equals(employees.get(j).getWorkplace().getWorkPlaceName())) {
                    ///по кожному працівнику треба отримати масив driverDataHectare
                    List<DriverDataHectare> e = infoHectareService.getAllHectareInf(employees.get(j).getDriver_id()
                            , String.valueOf(monthCombo.getSelectedItem()), checkedYear);
                    if (e != null) {
                        employeeDataHectare.addAll(e);
                    }
                }
            }
            AllInformationTableModel allInformationTableModel = new AllInformationTableModel(employeeDataHectare,employeeDataHour);
            JTable table = new JTable(allInformationTableModel);
            table.setPreferredScrollableViewportSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
            table.setRowHeight(30);

            ///TABLE RENDERER
            table.setDefaultRenderer(Object.class,new AllInfCellRenderer());
            table.getTableHeader().setFont(TABLE_FONT);
            table.getTableHeader().setBackground(HEADER_COLOR);

            table.getColumnModel().getColumn(1).setPreferredWidth(Math.round(TABLE_WIDTH*0.2f));
            table.getColumnModel().getColumn(2).setPreferredWidth(Math.round(TABLE_WIDTH*0.3f));

            hectareHourLabel = new JLabel("ВИКОРИСТАНО ПАЛИВО");
            hectareHourLabel.setFont(new Font("Serif", Font.BOLD, 20));

            headPanel = new JPanel();
            headPanel.setBackground(new Color(224, 102, 255));
            headPanel.add(hectareHourLabel);
            infoPanel.setBorder(title);
            infoPanel.setLayout(new BorderLayout());
            infoPanel.add(new JScrollPane(table));

            mainBox.add(headPanel);
            mainBox.add(infoPanel);
        }

        /////////////////////////////////////////////////////////////////////////////
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(mainBox);
        mainScrollPane = new JScrollPane(mainPanel);

        add(mainScrollPane);
    }

}
