package com.agricultural.domains.main;

import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 12.02.2017.
 */
@Data
@Entity
@Table(name = "operations")
public class TechnologicalOperation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long operation_id;
    @Column(name = "operation")
    private String name;

    @OneToMany(mappedBy = "operation")
    private List<DriverDataHectare> listDataHectare = new ArrayList<>();

    @OneToMany(mappedBy = "operation")
    private List<DriverDataHour> listDataHour = new ArrayList<>();

}
