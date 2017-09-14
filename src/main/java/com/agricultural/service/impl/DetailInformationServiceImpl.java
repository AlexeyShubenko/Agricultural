package com.agricultural.service.impl;

import com.agricultural.dao.detailnformation.DetailInformationDao;
import com.agricultural.dao.detailnformation.DetailInformationDaoImpl;
import com.agricultural.domains.gectarniyvirobitok.DetailDataHectare;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DetailDataHour;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.service.DetailInformationService;

/**
 * Created by Alexey on 14.09.2017.
 */
public class DetailInformationServiceImpl implements DetailInformationService {

    private static DetailInformationServiceImpl instance = new DetailInformationServiceImpl();

    private DetailInformationServiceImpl(){}

    public static DetailInformationServiceImpl getInstance() {
        return instance;
    }

    private DetailInformationDao detailInformationDao = DetailInformationDaoImpl.getInstance();

    @Override
    public void deleteDetailDataHectare(Long dataId) {
        detailInformationDao.deleteDetailDataHectare(dataId);
    }

    @Override
    public DetailDataHectare getDetailDataHectare(Long dataId) {
        return detailInformationDao.getDetailDataHectare(dataId);
    }

    @Override
    public boolean isDetailDataHectareExist(Long dataId) {
        return detailInformationDao.isDetailDataHectareExist(dataId);
    }

    @Override
    public void createDetailInformationHectare(DriverDataHectare driverDataHectare) {
        detailInformationDao.createDetailInformationHectare(driverDataHectare);
    }

    @Override
    public void editDetailDataHectare(DetailDataHectare detailDataHectare) {
        detailInformationDao.editDetailDataHectare(detailDataHectare);
    }

    @Override
    public void createDetailInformationHour(DriverDataHour driverDataHour) {
        detailInformationDao.createDetailInformationHour(driverDataHour);
    }

    @Override
    public DetailDataHour getDetailDataHour(Long dataId) {
        return detailInformationDao.getDetailDataHour(dataId);
    }

    @Override
    public void editDetailDataHour(DetailDataHour detailDataHour) {
        detailInformationDao.editDetailDataHour(detailDataHour);
    }

    @Override
    public void deleteDetailDataHour(Long dataId) {
        detailInformationDao.deleteDetailDataHour(dataId);
    }

    @Override
    public boolean isDetailDataHourExist(Long dataId) {
        return detailInformationDao.isDetailDataHourExist(dataId);
    }
}
