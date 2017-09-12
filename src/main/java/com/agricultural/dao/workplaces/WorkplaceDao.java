package com.agricultural.dao.workplaces;

import com.agricultural.domains.main.Workplace;

import java.util.ArrayList;

/**
 * Created by Alexey on 14.02.2017.
 */
public interface WorkplaceDao {

    void createWorkPlace(String workplaceName);
    void deleteWorPlace(Workplace workplace);
    void editWorkplace(Workplace operation);
    ArrayList<Workplace> getWorkplaces();
    String[] getAllWorkplaceName();
    Workplace getWorkplaceByName(String workplaceName);
}
