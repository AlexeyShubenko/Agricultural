package com.agricultural.dao.hourinformation;

import com.agricultural.dao.HibernateUtil;
import com.agricultural.domains.DataMassive;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DetailDataHour;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.domains.hoursvirobitok.HourTable;
import com.agricultural.domains.main.DateAndInformation;
import com.agricultural.domains.main.MachineTractorUnit;
import com.agricultural.domains.main.TechnologicalOperation;
import com.agricultural.service.MachineService;
import com.agricultural.service.OperationService;
import com.agricultural.service.impl.MachineServiceImpl;
import com.agricultural.service.impl.OperationServiceImpl;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 14.09.2017.
 */
public class InformationHourDaoImpl implements InformationHourDao {

    private static InformationHourDaoImpl instance = new InformationHourDaoImpl();

    private InformationHourDaoImpl(){}

    public static InformationHourDaoImpl getInstance() {
        return instance;
    }

    private EntityManager session;
    private OperationService operationService = OperationServiceImpl.getInstance();
    private MachineService machineService = MachineServiceImpl.getInstance();

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//для збереження в hourTable

    @Override
    public boolean saveOneRowHOURInf(Long driverId, String operationName, String machineName, String month, int year){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        TechnologicalOperation operation = operationService.getOperationByName(operationName);
        MachineTractorUnit machine = machineService.getMachineByName(machineName);

        try{
            tx.begin();
            ///зчитуємо потрібні дані з DatAndInformation
            Query<DateAndInformation> queryDateAndInf =
                    (Query<DateAndInformation>) session.createQuery("from DateAndInformation where driver.driver_id=:driverId " +
                            "and month=:month and year=:year");
            queryDateAndInf.setParameter("year", year);
            queryDateAndInf.setParameter("driverId",driverId);
            queryDateAndInf.setParameter("month", month);
            DateAndInformation dateAndInformation = queryDateAndInf.getSingleResult();

            ///зчитуэмо потрбні дані з hour table
            Query<HourTable> queryHourTable = (Query<HourTable>) session.createQuery("from  HourTable where dateAndInformation.date_id=:dateId");
            queryHourTable.setParameter("dateId", dateAndInformation.getDate_id());
            HourTable hourTable = queryHourTable.getSingleResult();

            ArrayList<DriverDataHour> tempArray;
            ///перевірка на наяність вже таких даних
            Query<DriverDataHour> queryData = (Query<DriverDataHour>) session.createQuery("from DriverDataHour where hourTable.hour_id=:hourId");
            queryData.setParameter("hourId", hourTable.getHour_id());
            tempArray = (ArrayList<DriverDataHour>) queryData.list();

            boolean flag = false;

            for(int i = 0; i<tempArray.size(); i++){
                DriverDataHour oneRow = tempArray.get(i);
                ///якщо запис з такими даними є то flag=true
                if((oneRow.getMachine().getName().equals(machine.getName()))
                        &&(oneRow.getOperation().getName().equals(operation.getName()))
                        &&(oneRow.getHourTable().getHour_id()==hourTable.getHour_id())){
                    flag=true;
                    break;
                }
            }
            if(!flag) {
                ///добавили інформацію в таблицю для одного рядка
                DriverDataHour oneRowHourInformation = new DriverDataHour();
                oneRowHourInformation.setOperation(operation);
                oneRowHourInformation.setMachine(machine);

                hourTable.getHourData().add(oneRowHourInformation);
                oneRowHourInformation.setHourTable(hourTable);

                DetailDataHour detailDataHour = new DetailDataHour();

                oneRowHourInformation.setDetailDataHour(detailDataHour);
//                detailDataHour.setDriverDataHour(driverDataHour);

                detailDataHour.setWorkedHoursString(DataMassive.getStringFromDoubleMassive(new double[31]));
                detailDataHour.setGivenFuelString(DataMassive.getStringFromDoubleMassive(new double[31]));
                detailDataHour.setUsedFuelAreaString(DataMassive.getStringFromDoubleMassive(new double[31]));

                session.persist(detailDataHour);
                session.persist(oneRowHourInformation);
                //збережено в базу
                return true;
            }
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return false;
    }

    @Override
    public List<DriverDataHour> getAllHourInf(Long driverId, String month, int year){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        List<DriverDataHour> hourInf = null;
        try{
            tx.begin();
            ///зчитуємо потрібні дані з DatAndInformation
            Query<DateAndInformation> queryDateAndInf =
                    (Query<DateAndInformation>) session.createQuery("from DateAndInformation where driver.driver_id=:driverId " +
                            "and month=:month and year=:year");
            queryDateAndInf.setParameter("driverId",driverId);
            queryDateAndInf.setParameter("month", month);
            queryDateAndInf.setParameter("year", year);
            DateAndInformation dateAndInformation = queryDateAndInf.getSingleResult();

            ///зчитуэмо потрбні дані з hour table
            Query<HourTable> queryHourTable =
                    (Query<HourTable>) session.createQuery("from  HourTable where dateAndInformation.date_id=:dateId");
            queryHourTable.setParameter("dateId", dateAndInformation.getDate_id());
            HourTable hourTable = queryHourTable.getSingleResult();

            Query<DriverDataHour> queryDriverData =
                    (Query<DriverDataHour>) session.createQuery("from DriverDataHour where hourTable.hour_id=:hectId");
            queryDriverData.setParameter("hectId",hourTable.getHour_id());
            hourInf = queryDriverData.getResultList();

            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return hourInf;
    }

    ///достає hour table по id dateAndInformation table
    @Override
    public  HourTable getHourTableIdByDateAndInformationId(Long dateId){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        HourTable hourTable = null;
        try{
            tx.begin();
            Query<HourTable> query =
                    (Query<HourTable>) session.createQuery("from  HourTable where dateAndInformation.date_id=:dateId");
            query.setParameter("dateId", dateId);
            hourTable = query.getSingleResult();
            tx.commit();
        }catch (Exception ex){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return hourTable;
    }

    @Override
    public List<DriverDataHour> getDriverDataHourByHourTableId(Long hourId){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        List<DriverDataHour> driverDataHours = null;
        try{
            tx.begin();
            Query<DriverDataHour> queryDriverData = (Query<DriverDataHour>) session.createQuery("from DriverDataHour where hourTable.hour_id=:hourId");
            queryDriverData.setParameter("hourId",hourId);
            driverDataHours = queryDriverData.getResultList();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return driverDataHours;
    }

    @Override
    public DriverDataHour getDriverDataHourById(Long dataId){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        DriverDataHour driverDataHour = null;
        try{
            tx.begin();

            Query<DriverDataHour> queryDriverData =
                    (Query<DriverDataHour>) session.createQuery("from DriverDataHour where data_id=:dataId");
            queryDriverData.setParameter("dataId",dataId);
            driverDataHour = queryDriverData.getSingleResult();

            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }

        return driverDataHour;
    }

    ////зберігаються внесені дані
    @Override
    public void editDriverDataHour(DriverDataHour driverDataHour){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();

            session.merge(driverDataHour);

            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }

    }

    ///видаляє дані з таблиці DriverDataHectare
    @Override
    public void deleteDriverDataHour(DriverDataHour driverDataHourToDelete) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            driverDataHourToDelete.setHourTable(null);
            session.createQuery("delete DriverDataHour where data_id=:id")
                    .setParameter("id",driverDataHourToDelete.getData_id()).executeUpdate();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
    }


}
