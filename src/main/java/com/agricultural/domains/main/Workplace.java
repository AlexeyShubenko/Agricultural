package com.agricultural.domains.main;

import com.agricultural.domains.main.TractorDriver;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 15.03.2017.
 */
@Data
@Entity
@Table(name = "workplace")
@ToString
public class Workplace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String workPlaceName;

    @OneToMany(mappedBy = "workplace", cascade = CascadeType.ALL)
    private List<TractorDriver> tractorDriver = new ArrayList<>();

}
