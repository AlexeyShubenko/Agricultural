package com.agricultural.service.impl;

import com.agricultural.dao.tractordrivers.TractorDriverDao;
import com.agricultural.dao.tractordrivers.TractorDriverDaoImpl;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.service.TractorDriverService;

import java.util.ArrayList;

/**
 * Created by Alexey on 12.09.2017.
 */
public class TractorDriverServiceImpl implements TractorDriverService {

    private static TractorDriverServiceImpl instance = new TractorDriverServiceImpl();
    private TractorDriverDao tractorDriverDao = TractorDriverDaoImpl.getInstance();

    private TractorDriverServiceImpl() {
    }

    public static TractorDriverServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void createOrUpdateTractorDriver(TractorDriver tractorDriver) {
        tractorDriverDao.createOrUpdateTractorDriver(tractorDriver);
    }

    @Override
    public void deleteTractorDriver(TractorDriver driver) {
        tractorDriverDao.deleteTractorDriver(driver);
    }

    @Override
    public ArrayList<TractorDriver> getTractorDrivers() {
        return tractorDriverDao.getTractorDrivers();
    }

    @Override
    public void editTractorDriver(TractorDriver driver) {
        tractorDriverDao.editTractorDriver(driver);
    }

    @Override
    public TractorDriver getTractorDriverByName(String name) {
        return tractorDriverDao.getTractorDriverByName(name);
    }

    @Override
    public TractorDriver getTractorDriverById(Integer driverId) {
        return tractorDriverDao.getTractorDriverById(driverId);
    }

    @Override
    public String[] getAllTractorDriversName() {
        return tractorDriverDao.getAllTractorDriversName();
    }
}
