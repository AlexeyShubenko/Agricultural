package com.agricultural.domains.hoursvirobitok;

import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Alexey on 06.03.2017.
 */

@Data
@Entity
@Table(name = "detaildatahour")
public class DetailDataHour implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long detailH_id;

    @Column(name = "workedHours")
    private String workedHoursString;

    @Column(name = "givenFuel")
    private String givenFuelString;

    @Column(name = "usedFuelArea")
    private String usedFuelAreaString;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "detailDataHour")
    private DriverDataHour driverDataHour;

}