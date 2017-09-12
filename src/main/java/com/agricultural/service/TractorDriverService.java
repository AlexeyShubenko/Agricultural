package com.agricultural.service;

import com.agricultural.domains.main.TractorDriver;

import java.util.ArrayList;

/**
 * Created by Alexey on 12.09.2017.
 */
public interface TractorDriverService {

    void createOrUpdateTractorDriver(TractorDriver tractorDriver);
    void deleteTractorDriver(TractorDriver driver);
    ArrayList<TractorDriver> getTractorDrivers();
    void editTractorDriver(TractorDriver driver);
    TractorDriver getTractorDriverByName(String name);
    TractorDriver getTractorDriverById(Integer driverId);
    String[] getAllTractorDriversName();

}
