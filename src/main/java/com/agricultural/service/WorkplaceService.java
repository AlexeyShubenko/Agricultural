package com.agricultural.service;

import com.agricultural.domains.main.Workplace;

import java.util.ArrayList;

/**
 * Created by Alexey on 12.09.2017.
 */
public interface WorkplaceService {

    void createWorkPlace(String workplaceName);
    void deleteWorPlace(Workplace workplace);
    void editWorkplace(Workplace operation);
    ArrayList<Workplace> getWorkplaces();
    String[] getAllWorkplaceName();
    Workplace getWorkplaceByName(String operationName);

}
