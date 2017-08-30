package com.agricultural.domains.gectarniyvirobitok;

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
@Table(name = "hectaretable")
// таблиця з даними по ГЕКТАРНОМУ ВИРОБІТКУ
public class HectareTable implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long hect_id;

    @OneToMany(mappedBy = "hectareTable",cascade = CascadeType.ALL)
    private List<DriverDataHectare> hectareData = new ArrayList<>();

    @OneToOne(mappedBy = "hectaretable", cascade = CascadeType.ALL)
    private DateAndInformation dateAndInformation;

}
