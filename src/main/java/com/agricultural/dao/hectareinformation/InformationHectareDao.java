package com.agricultural.dao.hectareinformation;

import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.domains.hoursvirobitok.HourTable;
import com.agricultural.domains.main.DateAndInformation;
import com.agricultural.domains.gectarniyvirobitok.HectareTable;

import java.util.List;

/**
 * Created by Alexey on 05.03.2017.
 */
public interface InformationHectareDao {

    void createDateAndInformationHectareTableHourTable(Long driverId, String month, int year);
    HectareTable getHectareTableIdByDateAndInformationId(Long dateId);
    ///достає dateAndInformation по driverId
    List<DateAndInformation> getListDateAndAllInformationByDriverId(Long driverId);
    void deleteHectareHourTable(Long id, String hectOrHour);
    void deleteDateAndInformation(Long id);
    DateAndInformation getDateAndAllInformationByDriverId(Long driverId, String month, int year);
    boolean isDateAndInformationExist(Long driverId,String month,int year);
    boolean saveOneRowHectareInf(Long driverId, String operationName, String machineName, String month, int year);

    List<DriverDataHectare> getAllHectareInf(Long driverId, String month, int year);

    DriverDataHectare getDriverDataHectareById(Long dataId);

    ///отримати масив driverDataHectare
    List<DriverDataHectare> getDriverDataHectareByHectareTableId(Long hectId);

    ////зберігаються внесені дані
    void editDriverDataHectare(DriverDataHectare driverDataHectare);

    List<DriverDataHectare> getDriverDataHectareByOperationMachine(String operationName);

    ///видаляє дані з таблиці DriverDataHectare
    void deleteDriverDataHectare(DriverDataHectare driverDataHectareToDelete);

}
