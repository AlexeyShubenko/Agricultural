package com.agricultural.service.impl;

import com.agricultural.dao.workplaces.WorkplaceDao;
import com.agricultural.dao.workplaces.WorkplaceDaoImpl;
import com.agricultural.domains.main.Workplace;
import com.agricultural.service.WorkplaceService;

import java.util.ArrayList;

/**
 * Created by Alexey on 12.09.2017.
 */
public class WorkplaceServiceImpl implements WorkplaceService {

    private static WorkplaceServiceImpl instance = new WorkplaceServiceImpl();
    private WorkplaceDao workplaceDao = WorkplaceDaoImpl.getInstance();

    private WorkplaceServiceImpl() {
    }

    public static WorkplaceServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void createWorkPlace(String workplaceName) {
        workplaceDao.createWorkPlace(workplaceName);
    }

    @Override
    public void deleteWorPlace(Workplace workplace) {
        workplaceDao.deleteWorPlace(workplace);
    }

    @Override
    public void editWorkplace(Workplace operation) {
        workplaceDao.editWorkplace(operation);
    }

    @Override
    public ArrayList<Workplace> getWorkplaces() {
        return workplaceDao.getWorkplaces();
    }

    @Override
    public String[] getAllWorkplaceName() {
        return workplaceDao.getAllWorkplaceName();
    }

    @Override
    public Workplace getWorkplaceByName(String workplaceName) {
        return workplaceDao.getWorkplaceByName(workplaceName);
    }

}
