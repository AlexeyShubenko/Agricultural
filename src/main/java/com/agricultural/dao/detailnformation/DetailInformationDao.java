package com.agricultural.dao.detailnformation;

import com.agricultural.domains.gectarniyvirobitok.DetailDataHectare;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DetailDataHour;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;

/**
 * Created by Alexey on 11.09.2017.
 */
public interface DetailInformationDao {

    void deleteDetailDataHectare(Long dataId);

    DetailDataHectare getDetailDataHectare(Long dataId);

    ///метод для перевірки наявності даного запису в таблиці з такими даними
    boolean isDetailDataHectareExist(Long dataId);

    ///для певної дати цей метод має визиватися тільки 1 раз
    ///метод для створення запису DetailDataHectare для конкретного запису у  тракториста
    void createDetailInformationHectare(DriverDataHectare driverDataHectare);

    ///обновляємо дані DetailDataHectare
    void editDetailDataHectare(DetailDataHectare detailDataHectare);

    ///для 1 запису в DriverDataHour цей метод має визиватися тільки 1 раз
    ///метод для створення запису DetailDataHour для конкретного запису у  тракториста
    void createDetailInformationHour(DriverDataHour driverDataHour);

    ///дістає DetailDataHour по DetailDataHour.dataId
    DetailDataHour getDetailDataHour(Long dataId);

    ///метод для перевірки наявності даного запису в таблиці з такими даними
    boolean isDetailDataHourExist(Long dataId);

    ///обновляємо дані DetailDataHour
    void editDetailDataHour(DetailDataHour detailDataHour);

    void deleteDetailDataHour(Long dataId);
}
