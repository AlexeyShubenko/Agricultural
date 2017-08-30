package com.agricultural.dao.hectareinformation;

import com.agricultural.domains.main.DateAndInformation;
import com.agricultural.domains.gectarniyvirobitok.HectareTable;

/**
 * Created by Alexey on 05.03.2017.
 */
public interface InformationHectareDAO {

    void createDateAndInformationHectareTableHourTable(Long driver_id, String month, int year);
    HectareTable getHectareTable_idByDateAndInformationId(Long date_id);
    DateAndInformation getDateAndAllInformationByDriverId(Long driver_id, String month, int year);
    boolean isDateAndInformationExist(Long driver_id,String month,int year);
    boolean saveOneRowHectareInf(Long driver_id, String operationName, String machineName, String month, int year);

}
