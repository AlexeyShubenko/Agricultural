package com.agricultural.domains.hoursvirobitok;

import com.agricultural.domains.main.DateAndInformation;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 21.02.2017.
 */
@Data
@Entity
@Table(name = "hourtable")
// таблиця з даними по ГЕКТАРНОМУ ВИРОБІТКУ
public class HourTable implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long hour_id;

    @OneToMany(mappedBy = "hourTable",cascade = CascadeType.ALL)
    private List<DriverDataHour> hourData = new ArrayList<>();

    @OneToOne(mappedBy = "hourtable", cascade = CascadeType.ALL)
    private DateAndInformation dateAndInformation;

}