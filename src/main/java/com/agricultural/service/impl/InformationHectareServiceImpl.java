package com.agricultural.service.impl;

import com.agricultural.dao.hectareinformation.InformationHectareDao;
import com.agricultural.dao.hectareinformation.InformationHectareDaoImpl;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.gectarniyvirobitok.HectareTable;
import com.agricultural.domains.main.DateAndInformation;
import com.agricultural.service.InformationHectareService;

import java.util.List;

/**
 * Created by Alexey on 14.09.2017.
 */
public class InformationHectareServiceImpl implements InformationHectareService {

    private static InformationHectareServiceImpl instance = new InformationHectareServiceImpl();

    private InformationHectareServiceImpl(){}

    public static InformationHectareServiceImpl getInstance() {
        return instance;
    }

    private InformationHectareDao informationHectareDao = InformationHectareDaoImpl.getInstance();


    @Override
    public void createDateAndInformationHectareTableHourTable(Long driverId, String month, int year) {
        informationHectareDao.createDateAndInformationHectareTableHourTable(driverId, month, year);
    }

    @Override
    public HectareTable getHectareTableIdByDateAndInformationId(Long dateId) {
        return informationHectareDao.getHectareTableIdByDateAndInformationId(dateId);
    }

    @Override
    public List<DateAndInformation> getListDateAndAllInformationByDriverId(Long driverId) {
        return informationHectareDao.getListDateAndAllInformationByDriverId(driverId);
    }

    @Override
    public void deleteHectareHourTable(Long id, String hectOrHour) {
        informationHectareDao.deleteHectareHourTable(id, hectOrHour);
    }

    @Override
    public void deleteDateAndInformation(Long id) {
        informationHectareDao.deleteDateAndInformation(id);
    }

    @Override
    public DateAndInformation getDateAndAllInformationByDriverId(Long driverId, String month, int year) {
        return informationHectareDao.getDateAndAllInformationByDriverId(driverId, month, year);
    }

    @Override
    public boolean isDateAndInformationExist(Long driverId, String month, int year) {
        return informationHectareDao.isDateAndInformationExist(driverId, month, year);
    }

    @Override
    public boolean saveOneRowHectareInf(Long driverId, String operationName, String machineName, String month, int year) {
        return informationHectareDao.saveOneRowHectareInf(driverId, operationName, machineName, month, year);
    }

    @Override
    public List<DriverDataHectare> getAllHectareInf(Long driverId, String month, int year) {
        return informationHectareDao.getAllHectareInf(driverId, month, year);
    }

    @Override
    public DriverDataHectare getDriverDataHectareById(Long dataId) {
        return informationHectareDao.getDriverDataHectareById(dataId);
    }

    @Override
    public List<DriverDataHectare> getDriverDataHectareByHectareTableId(Long hectId) {
        return informationHectareDao.getDriverDataHectareByHectareTableId(hectId);
    }

    @Override
    public void editDriverDataHectare(DriverDataHectare driverDataHectare) {
        informationHectareDao.editDriverDataHectare(driverDataHectare);
    }

    //TODO WHAT THE METHOD??
    @Override
    public List<DriverDataHectare> getDriverDataHectareByOperationMachine(String operationName) {
        return informationHectareDao.getDriverDataHectareByOperationMachine(operationName);
    }

    @Override
    public void deleteDriverDataHectare(DriverDataHectare driverDataHectareToDelete) {
        informationHectareDao.deleteDriverDataHectare(driverDataHectareToDelete);
    }
}
