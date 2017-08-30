package com.agricultural.domains.gectarniyvirobitok;

import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by Alexey on 06.03.2017.
 */

@Data
@Entity
@Table(name = "detaildatahectare")
public class DetailDataHectare implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long detailH_id;

    @Column(name = "cultivatedArea")
    private String cultivatedAreaString;

    @Column(name = "givenFuel")
    private String givenFuelString;

    @Column(name = "usedFuelArea")
    private String usedFuelAreaString;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "detailDataHectare")
    private DriverDataHectare driverDataHectare;

}