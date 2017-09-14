package com.agricultural.dao.hourinformation;

import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.domains.hoursvirobitok.HourTable;

import java.util.List;

/**
 * Created by Alexey on 14.09.2017.
 */
public interface InformationHourDao {

    boolean saveOneRowHOURInf(Long driverId, String operationName, String machineName, String month, int year);

    List<DriverDataHour> getAllHourInf(Long driverId, String month, int year);

    ///достає hourtable_id по id dateAndInformation table
    HourTable getHourTableIdByDateAndInformationId(Long dateId);

    List<DriverDataHour> getDriverDataHourByHourTableId(Long hourId);

    DriverDataHour getDriverDataHourById(Long dataId);

    ////зберігаються внесені дані
    void editDriverDataHour(DriverDataHour driverDataHour);

    ///видаляє дані з таблиці DriverDataHectare
    void deleteDriverDataHour(DriverDataHour driverDataHourToDelete);
}
