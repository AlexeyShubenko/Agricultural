package com.agricultural.domains.main;

import com.agricultural.domains.gectarniyvirobitok.HectareTable;
import com.agricultural.domains.hoursvirobitok.HourTable;
import com.agricultural.domains.main.TractorDriver;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by Alexey on 20.02.2017.
 */
@Data
@Entity
@Table(name = "dateandinformation")
public class DateAndInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long date_id;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private int year;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hect_id")
    private HectareTable hectaretable;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hour_id")
    private HourTable hourtable;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empl_id")
    private TractorDriver driver;

}
