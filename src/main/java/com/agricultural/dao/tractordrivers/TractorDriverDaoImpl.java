package com.agricultural.dao.tractordrivers;

import com.agricultural.dao.HibernateUtil;
import com.agricultural.dao.detailnformation.DetailInformationDaoImpl;
import com.agricultural.domains.main.DateAndInformation;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.gectarniyvirobitok.HectareTable;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.domains.hoursvirobitok.HourTable;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.service.DetailInformationService;
import com.agricultural.service.InformationHectareService;
import com.agricultural.service.impl.DetailInformationServiceImpl;
import com.agricultural.service.impl.InformationHectareServiceImpl;
import com.agricultural.service.InformationHourService;
import com.agricultural.service.impl.InformationHourServiceImpl;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 14.02.2017.
 */
public class TractorDriverDaoImpl implements TractorDriverDao {

    private static TractorDriverDaoImpl instance = new TractorDriverDaoImpl();

    private TractorDriverDaoImpl() {
    }

    public static TractorDriverDaoImpl getInstance() {
        return instance;
    }
    private DetailInformationService detailService = DetailInformationServiceImpl.getInstance();

    private EntityManager session;

    private InformationHectareService infoHectareService = InformationHectareServiceImpl.getInstance();
    private InformationHourService infoHourService = InformationHourServiceImpl.getInstance();

    @Override
    public void createOrUpdateTractorDriver(TractorDriver tractorDriver) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.merge(tractorDriver);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public void deleteTractorDriver(TractorDriver driver) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        System.out.println("in delete method");

        List<DateAndInformation> dateAndInformation = infoHectareService.getListDateAndAllInformationByDriverId(driver.getDriver_id());
        for(DateAndInformation d: dateAndInformation) {
            infoHectareService.deleteDateAndInformation(d.getDate_id());
        }


        List<HectareTable> hectareTables = new ArrayList<>();
        for (HectareTable hectaretable:hectareTables){
            infoHectareService.deleteHectareHourTable(hectaretable.getHect_id(),"hectare");
        }
        List<HourTable> hourTables = new ArrayList<>();
        for (HourTable hourTable:hourTables){
            infoHectareService.deleteHectareHourTable(hourTable.getHour_id(),"hour");
        }

        List<DriverDataHectare> driverDataHectares = new ArrayList<>();
        if(driverDataHectares!=null)
        for (int i = 0; i < hectareTables.size(); i++) {
            hectareTables.get(i).setHectareData(null);
            for (int j = 0; j < driverDataHectares.size(); j++) {
                driverDataHectares = infoHectareService.getDriverDataHectareByHectareTableId(hectareTables.get(i).getHect_id());
                infoHectareService.deleteDriverDataHectare(driverDataHectares.get(i));
                detailService.deleteDetailDataHectare(driverDataHectares.get(j).getData_id());
            }
        }

        List<DriverDataHour> driverDataHours = new ArrayList<>();
        if(driverDataHours!=null)
        for (int i = 0; i < hourTables.size(); i++) {
            hectareTables.get(i).setHectareData(null);
            for (int j = 0; j < driverDataHours.size(); j++) {
                driverDataHours = infoHourService.getDriverDataHourByHourTableId(hectareTables.get(i).getHect_id());
                infoHourService.deleteDriverDataHour(driverDataHours.get(i));
                detailService.deleteDetailDataHour(driverDataHours.get(j).getData_id());
            }
        }
        try{
            tx.begin();

            session.createQuery("delete TractorDriver  where id=:id").setParameter("id",driver.getDriver_id()).executeUpdate();


            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public void editTractorDriver(TractorDriver driver) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();

            session.createQuery("update TractorDriver empl set empl.name=:name, empl.position=:position, empl.wageRate=:wageRate " +
                    " where  empl.driver_id=:id").setParameter("name",driver.getName()).setParameter("position",driver.getPosition())
                    .setParameter("wageRate",driver.getWageRate()).setParameter("id",driver.getDriver_id()).executeUpdate();

            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    @Override
    public ArrayList<TractorDriver> getTractorDrivers() {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        ArrayList<TractorDriver> allDrivers = null;
        try{
            tx.begin();
            Query<TractorDriver> query = (Query<TractorDriver>) session.createQuery("from TractorDriver");
            allDrivers =  (ArrayList<TractorDriver>) query.list();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return allDrivers;
    }

    @Override
    public TractorDriver getTractorDriverByName(String name) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        TractorDriver driver = null;
        try{
            tx.begin();
            Query<TractorDriver> query = (Query<TractorDriver>) session.createQuery("from TractorDriver where name=:name");
            query.setParameter("name",name);
            driver = query.getSingleResult();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }

        return driver;
    }

    @Override
    public TractorDriver getTractorDriverById(Integer driver_id) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        TractorDriver driver = null;

        try{
            tx.begin();
            driver = session.getReference(TractorDriver.class,driver_id);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }

        return driver;
    }

    @Override
    public String[] getAllTractorDriversName(){
        ///отримаємо список всіх операцій
        ArrayList<TractorDriver> arrayTractorDrivers = getTractorDrivers();
        String[] names = new String [arrayTractorDrivers.size()];
        ///записуєо назви операцій у масив
        for (int i=0;i<names.length;i++){
            names[i] = arrayTractorDrivers.get(i).getName();
        }
        return names;
    }


}
