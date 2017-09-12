package com.agricultural.dao.hectareinformation;

import com.agricultural.dao.HibernateUtil;
import com.agricultural.dao.operations.OperationDAOImpl;
import com.agricultural.domains.DataMassive;
import com.agricultural.domains.main.DateAndInformation;
import com.agricultural.domains.gectarniyvirobitok.DetailDataHectare;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.gectarniyvirobitok.HectareTable;
import com.agricultural.domains.hoursvirobitok.DetailDataHour;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.domains.hoursvirobitok.HourTable;
import com.agricultural.domains.main.MachineTractorUnit;
import com.agricultural.domains.main.TechnologicalOperation;
import com.agricultural.domains.main.TractorDriver;
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
 * Created by Alexey on 26.02.2017.
 */
public class InformationHectareDAOImpl implements InformationHectareDAO {

    private EntityManager session;

    private OperationService operationService = OperationServiceImpl.getInstance();
    private MachineService machineService = MachineServiceImpl.getInstance();

    ///створити метод для перевірки наявності hectare table i dateAndInformation що повязані з driver-ом

    ///для певної дати цей метод має визиватися тільки 1 раз
    ///метод для створення запису DateAndAllInformation i HectareTable для конкретного дати та тракториста
    public void createDateAndInformationHectareTableHourTable(Long driverId, String month, int year){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();

        TractorDriver driver = session.getReference(TractorDriver.class, driverId);
        ///таблиця гектарного обробітку для даної дати
        HectareTable hectareTable = new HectareTable();
        ///таблиця голинного виробітку
        HourTable hourTable = new HourTable();
        ///створюємо дану таблицю і записуємо туди дату
        DateAndInformation dateAndInformation = new DateAndInformation();
        dateAndInformation.setYear(year);
        dateAndInformation.setMonth(month);
        ///зв'язуємо тракториста i дані
        driver.getDateAndInformation().add(dateAndInformation);
        dateAndInformation.setDriver(driver);
        ///зв'язуємо дату i hectare
        dateAndInformation.setHectaretable(hectareTable);
        hectareTable.setDateAndInformation(dateAndInformation);
        ///зв'язуємо дату і hourtable
        dateAndInformation.setHourtable(hourTable);
        hourTable.setDateAndInformation(dateAndInformation);

            ///зберігаємо і оновлюємо дані та тракториста
            session.persist(dateAndInformation);
            session.persist(hectareTable);
            session.persist(hourTable);

            tx.commit();
        }catch (Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
    }

    ///достає hectaretable_id по id dateAndInformation table
    public  HectareTable getHectareTable_idByDateAndInformationId(Long dateId){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        HectareTable hectareTable = null;
        try{
            tx.begin();
            Query<HectareTable> query = (Query<HectareTable>) session.createQuery("from  HectareTable where dateAndInformation.date_id=:dateId");
            query.setParameter("dateId", dateId);
            hectareTable = query.getSingleResult();
            tx.commit();
        }catch (Exception ex){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return hectareTable;
    }

    ///достає dateAndInformation по driverId
    public List<DateAndInformation> getDateAndAllInformationByDriverId(Long driverId){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        List<DateAndInformation> dateAndInformation = null;
        try{
            tx.begin();
            ///запрос на дані по ід тракториста місяцю та року
            Query<DateAndInformation> query = (Query<DateAndInformation>) session.createQuery("from DateAndInformation where driver.driver_id=:driverId ");
            query.setParameter("driverId",driverId);
            dateAndInformation = query.list();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();

        }

        return dateAndInformation;
    }

    public void deleteHectareHourTable(Long id,String hectOrHour) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            if(hectOrHour.equals("hectare"))
                session.createQuery("delete HectareTable where hect_id=:id").setParameter("id",id).executeUpdate();
            if(hectOrHour.equals("hour"))
                session.createQuery("delete HourTable where hour_id=:id").setParameter("id", id).executeUpdate();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }
    public void deleteDateAndInformation(Long id) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
                session.createQuery("delete DateAndInformation where date_id=:id").setParameter("id",id).executeUpdate();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }


    ///достає dateAndInformation по driverId
    public DateAndInformation getDateAndAllInformationByDriverId(Long driverId,String month,int year){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        DateAndInformation dateAndInformation = null;
        try{
            tx.begin();
            ///запрос на дані по ід тракториста місяцю та року
            Query<DateAndInformation> query = (Query<DateAndInformation>) session.createQuery("from DateAndInformation where driver.driver_id=:driverId " +
                    "and month=:month and year=:year");
            query.setParameter("driverId",driverId);
            query.setParameter("month", month);
            query.setParameter("year", year);
            dateAndInformation = query.getSingleResult();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();

        }

        return dateAndInformation;
    }

    ///метод для перевірки наявності даного запису в таблиці з такими даними
    public boolean isDateAndInformationExist(Long driverId,String month,int year){
        DateAndInformation dateAndInformation = this.getDateAndAllInformationByDriverId(driverId,month,year);
        return dateAndInformation==null?false:true;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//для збереження в hectareTable

    public boolean saveOneRowHectareInf(Long driverId, String operationName, String machineName, String month, int year){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();

        TechnologicalOperation operation = operationService.getOperationByName(operationName);
        MachineTractorUnit machine = machineService.getMachineByName(machineName);
        DetailDataHectare detailDataHectare = new DetailDataHectare();
        try{
            tx.begin();
            ///зчитуємо потрібні дані з DatAndInformation
            Query<DateAndInformation> queryDateAndInf = (Query<DateAndInformation>) session.createQuery("from DateAndInformation where driver.driver_id=:driverId " +
                    "and month=:month and year=:year");
            queryDateAndInf.setParameter("driverId",driverId);
            queryDateAndInf.setParameter("month", month);
            queryDateAndInf.setParameter("year", year);
            DateAndInformation dateAndInformation = queryDateAndInf.getSingleResult();

            ///зчитуэмо потрбні дані з hectare table
            Query<HectareTable> queryHectareTable = (Query<HectareTable>) session.createQuery("from  HectareTable where dateAndInformation.date_id=:driverId");
            queryHectareTable.setParameter("date_id", dateAndInformation.getDate_id());
            HectareTable hectareTable = queryHectareTable.getSingleResult();

            ArrayList<DriverDataHectare> tempArray;
            ///перевірка на наяність вже таких даних
            Query<DriverDataHectare> queryData = (Query<DriverDataHectare>) session.createQuery("from DriverDataHectare where hectareTable.hect_id=:hectId ");
            queryData.setParameter("hectId", hectareTable.getHect_id());
            tempArray = (ArrayList<DriverDataHectare>) queryData.list();

            boolean flag = false;
            for(int i = 0; i<tempArray.size(); i++){
                DriverDataHectare oneRow = tempArray.get(i);
                ///якщо запис з такими даними є то flag=true
                if((oneRow.getMachine().getName().equals(machine.getName()))
                        &&(oneRow.getOperation().getName().equals(operation.getName()))
                        &&(oneRow.getHectareTable().getHect_id()==hectareTable.getHect_id())){
                    flag=true;
                    break;
                }
            }

            if(!flag) {
                ///добавили інформацію в таблицю для одного рядка
                DriverDataHectare oneRowInformation = new DriverDataHectare();
                oneRowInformation.setOperation(operation);
                oneRowInformation.setMachine(machine);

                hectareTable.getHectareData().add(oneRowInformation);
                oneRowInformation.setHectareTable(hectareTable);

                ///створення driverDataDetail разомпри стовренні driverDataHectare

                oneRowInformation.setDetailDataHectare(detailDataHectare);
                detailDataHectare.setDriverDataHectare(oneRowInformation);

                detailDataHectare.setCultivatedAreaString(DataMassive.getStringFromDoubleMassive(new double[31]));
                detailDataHectare.setGivenFuelString(DataMassive.getStringFromDoubleMassive(new double[31]));
                detailDataHectare.setUsedFuelAreaString(DataMassive.getStringFromDoubleMassive(new double[31]));

                session.persist(detailDataHectare);
                session.persist(oneRowInformation);
                ///збережено в базу
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

    public List<DriverDataHectare> getAllHectareInf(Long driverId, String month, int year){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();

        List<DriverDataHectare> hectareInf = null;
        try{
            tx.begin();

            ///зчитуємо потрібні дані з DatAndInformation
            Query<DateAndInformation> queryDateAndInf = (Query<DateAndInformation>) session.createQuery("from DateAndInformation where driver.driver_id=:driverId " +
                    "and month=:month and year=:year");
            queryDateAndInf.setParameter("driverId",driverId);
            queryDateAndInf.setParameter("month", month);
            queryDateAndInf.setParameter("year", year);
            DateAndInformation dateAndInformation = queryDateAndInf.getSingleResult();

            ///зчитуэмо потрбні дані з hectare table
            Query<HectareTable> queryHectareTable = (Query<HectareTable>) session.createQuery("from  HectareTable where dateAndInformation.date_id=:driverId");
            queryHectareTable.setParameter("date_id", dateAndInformation.getDate_id());
            HectareTable hectareTable = queryHectareTable.getSingleResult();

            Query<DriverDataHectare> queryDriverData = (Query<DriverDataHectare>) session.createQuery("from DriverDataHectare where hectareTable.hect_id=:hectId");
            queryDriverData.setParameter("hectId",hectareTable.getHect_id());
            hectareInf = queryDriverData.getResultList();

            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return hectareInf;
    }

    public DriverDataHectare getDriverDataHectareById(Long dataId){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        DriverDataHectare driverDataHectare = null;
        try{
            tx.begin();

            Query<DriverDataHectare> queryDriverData = (Query<DriverDataHectare>) session.createQuery("from DriverDataHectare where data_id=:dataId");
            queryDriverData.setParameter("dataId",dataId);
            driverDataHectare = queryDriverData.getSingleResult();

            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return driverDataHectare;
    }

    ///отримати масив driverDataHectare
    public List<DriverDataHectare> getDriverDataHectareByHectareTableId(Long hectId){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        List<DriverDataHectare> driverDataHectares = null;
        try{
            tx.begin();

            Query<DriverDataHectare> queryDriverData = (Query<DriverDataHectare>) session.createQuery("from DriverDataHectare where hectareTable.hect_id=:hectId");
            queryDriverData.setParameter("hectId",hectId);
            driverDataHectares = queryDriverData.getResultList();

            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return driverDataHectares;
    }

    ////зберігаються внесені дані
    public void editDriverDataHectare(DriverDataHectare driverDataHectare){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();

            session.merge(driverDataHectare);

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


    ////зберігаються внесені дані
    public List<DriverDataHectare> getDriverDataHectareByOperationMachine(String operationName){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        List<DriverDataHectare> dr=null;
        try{
            tx.begin();
            Query<DriverDataHectare> query = (Query<DriverDataHectare>) session.createQuery("from DriverDataHectare where operation.name=:operationName");
            query.setParameter("operationName",operationName);
            dr= query.getResultList();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return dr;
    }

    ///видаляє дані з таблиці DriverDataHectare
    public void deleteDriverDataHectareHour(DriverDataHectare driverDataHectareToDelete, DriverDataHour driverDataHourToDelete){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            if(driverDataHectareToDelete!=null) {
                driverDataHectareToDelete.setHectareTable(null);
                session.createQuery("delete DriverDataHectare where data_id=:id")
                        .setParameter("id",driverDataHectareToDelete.getData_id()).executeUpdate();
//                session.remove(driverDataHectareToDelete);
            }else{
                if(driverDataHourToDelete!=null){
                    driverDataHourToDelete.setHourTable(null);
                    session.createQuery("delete DriverDataHour where data_id=:id")
                            .setParameter("id",driverDataHourToDelete.getData_id()).executeUpdate();
//                    session.remove(driverDataHourToDelete);
                }
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
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//для збереження в hourTable

    public boolean saveOneRowHOURInf(Long driverId, String operationName, String machineName, String month, int year){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        TechnologicalOperation operation = operationService.getOperationByName(operationName);
        MachineTractorUnit machine = machineService.getMachineByName(machineName);

        try{
            tx.begin();
            ///зчитуємо потрібні дані з DatAndInformation
            Query<DateAndInformation> queryDateAndInf = (Query<DateAndInformation>) session.createQuery("from DateAndInformation where driver.driver_id=:driverId " +
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

    ///достає hourtable_id по id dateAndInformation table
    public  HourTable getHourTable_idByDateAndInformationId(Long dateId){
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

    public List<DriverDataHour> getDriverDataHectareByHourTableId(Long hourId){
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


}