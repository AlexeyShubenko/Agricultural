package com.agricultural.service.impl;

import com.agricultural.dao.hectareinformation.InformationHectareDao;
import com.agricultural.dao.hectareinformation.InformationHectareDaoImpl;
import com.agricultural.dao.hourinformation.InformationHourDao;
import com.agricultural.dao.hourinformation.InformationHourDaoImpl;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.domains.hoursvirobitok.HourTable;
import com.agricultural.service.InformationHourService;

import java.util.List;

/**
 * Created by Alexey on 14.09.2017.
 */
public class InformationHourServiceImpl implements InformationHourService{

    private static InformationHourServiceImpl instance = new InformationHourServiceImpl();

    private InformationHourServiceImpl(){}

    public static InformationHourServiceImpl getInstance() {
        return instance;
    }

    private InformationHourDao informationHourDao = InformationHourDaoImpl.getInstance();
    private InformationHectareDao informationHectareDao = InformationHectareDaoImpl.getInstance();

    @Override
    public boolean saveOneRowHOURInf(Long driverId, String operationName, String machineName, String month, int year) {
        return informationHourDao.saveOneRowHOURInf(driverId,operationName,machineName,month,year);
    }

    @Override
    public List<DriverDataHour> getAllHourInf(Long driverId, String month, int year) {
        return informationHourDao.getAllHourInf(driverId, month, year);
    }

    @Override
    public HourTable getHourTableIdByDateAndInformationId(Long dateId) {
        return informationHourDao.getHourTableIdByDateAndInformationId(dateId);
    }

    @Override
    public List<DriverDataHour> getDriverDataHourByHourTableId(Long hourId) {
        return informationHourDao.getDriverDataHourByHourTableId(hourId);
    }

    @Override
    public DriverDataHour getDriverDataHourById(Long dataId) {
        return informationHourDao.getDriverDataHourById(dataId);
    }

    @Override
    public void editDriverDataHour(DriverDataHour driverDataHour) {
        informationHourDao.editDriverDataHour(driverDataHour);
    }

    @Override
    public void deleteDriverDataHour(DriverDataHour driverDataHourToDelete) {
        informationHourDao.deleteDriverDataHour(driverDataHourToDelete);
    }

    ////////////////////////////////////////////////////////////////////////////////
    //////////////TAKE FROM INFORMATION HECTARE INTERFACE//////////////////////////
    @Override
    public boolean isDateAndInformationExist(Long driverId, String month, int year) {
        return informationHectareDao.isDateAndInformationExist(driverId, month, year);
    }

    @Override
    public void createDateAndInformationHectareTableHourTable(Long driverId, String month, int year) {
        informationHectareDao.createDateAndInformationHectareTableHourTable(driverId, month, year);
    }
}
