package com.agricultural.domains.dto;

/**
 * Created by Alexey on 02.04.2017.
 */
public class OperationDTO {

    private Integer id;
    private String name;

    public OperationDTO(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
