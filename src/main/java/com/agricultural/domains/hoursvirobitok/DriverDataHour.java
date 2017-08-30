package com.agricultural.domains.hoursvirobitok;

import com.agricultural.domains.gectarniyvirobitok.HectareTable;
import com.agricultural.domains.main.MachineTractorUnit;
import com.agricultural.domains.main.TechnologicalOperation;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by Alexey on 17.02.2017.
 */
@Data
@Entity
@Table(name = "hourmade")
//дані що знаходяться для ГОДИННИЙ ВИРОБІОК
public class DriverDataHour implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long data_id;

    //звязок з таблицею ГЕКТАРНИЙ ВИРОБІТОК
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hour_id")
    private HourTable hourTable;

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private TechnologicalOperation operation;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private MachineTractorUnit machine;

    @Column(name = "workedHours")
    private double workedHours =0; //відпрацьованих годин,          (вводити), обраховується з іншої таблиці
    @Column(name = "givenfuel")
    private double givenFuel=0; //отримано палива,               (вводити), обраховується з іншої таблиці
    @Column(name = "usedfuel")
    private double usedFuel=0; //витрата палива
    @Column(name = "workcost")
    private double workCost=0; //вартість робіт грн/год,                 (вводити), обраховується з іншої таблиці
    @Column(name = "overallworkcost")
    private double overallWorkCost=0; //загальна вартість робіт

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detailHour_id")
    private DetailDataHour detailDataHour;

    public double calcOverallWorkCost(){
        BigDecimal wHours = new BigDecimal(workedHours);
        BigDecimal wCost = new BigDecimal(workCost);
        overallWorkCost =  wHours.multiply(wCost,new MathContext(2, RoundingMode.HALF_UP)).doubleValue();
        return overallWorkCost;
    }

}
