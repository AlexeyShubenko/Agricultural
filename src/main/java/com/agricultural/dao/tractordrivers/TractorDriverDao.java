package com.agricultural.dao.tractordrivers;

import com.agricultural.domains.main.TractorDriver;

import java.util.ArrayList;

/**
 * Created by Alexey on 14.02.2017.
 */
public interface TractorDriverDao {

    void createOrUpdateTractorDriver(TractorDriver tractorDriver);
    void deleteTractorDriver(TractorDriver driver);
    void editTractorDriver(TractorDriver driver);
    ArrayList<TractorDriver> getTractorDrivers();
    TractorDriver getTractorDriverByName(String name);
    TractorDriver getTractorDriverById(Integer driver_id);
    String[] getAllTractorDriversName();
}
