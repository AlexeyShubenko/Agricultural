package com.agricultural.domains.gectarniyvirobitok;

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
@Table(name = "hectaremade")
//дані що знаходяться для ГЕКТАРНИЙ ВИРОБІОК
public class DriverDataHectare implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long data_id;

    //звязок з таблицею ГЕКТАРНИЙ ВИРОБІТОК
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hect_id")
    private HectareTable hectareTable;

    @ManyToOne
    @JoinColumn(name = "operation_id")
    private TechnologicalOperation operation;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private MachineTractorUnit machine;

    @Column(name = "cultivatedarea")
    private double cultivatedArea=0; //оброблена площа,          (вводити), обраховується з іншої таблиці
    @Column(name = "givenfuel")
    private double givenFuel=0; //отримано палива,               (вводити), обраховується з іншої таблиці
    @Column(name = "usedfuel")
    private double usedFuel=0; //витрата палива
    @Column(name = "workcost")
    private double workCost=0; //вартість робіт,                 (вводити), обраховується з іншої таблиці
    @Column(name = "overallworkcost")
    private double overallWorkCost=0; //загальна вартість робіт

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "detailHect_id")
    private DetailDataHectare detailDataHectare;

    public double calcOverallWorkCost(){
        BigDecimal cultArea = new BigDecimal(cultivatedArea);
        BigDecimal wCost = new BigDecimal(workCost);
        overallWorkCost =  cultArea.multiply(wCost,new MathContext(2, RoundingMode.HALF_UP)).doubleValue();
        return overallWorkCost;
    }

//    public double getWorkCost(){
//        BigDecimal wCost = new BigDecimal(workCost);
//        wCost.setScale(2,RoundingMode.HALF_UP);
//        return wCost.doubleValue();
//    }

}
