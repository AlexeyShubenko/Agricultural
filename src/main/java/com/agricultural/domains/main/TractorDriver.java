package com.agricultural.domains.main;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Alexey on 12.02.2017.
 */
@Data
@Entity
@Table(name = "employee")
public class TractorDriver implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driver_id;

    @Column(name = "fio")
    private String name;
    //тарифна ставка
    @Column(name = "wage")
    private int wageRate;

    @Column(name = "position")
    private String position;

    @ManyToOne
    @JoinColumn(name = "workplace_id")
    private Workplace workplace;///за місцем роботи

    @OneToMany(mappedBy = "driver",cascade = CascadeType.ALL)
    private List<DateAndInformation> dateAndInformation = new ArrayList<>();

    public TractorDriver(){}

}
