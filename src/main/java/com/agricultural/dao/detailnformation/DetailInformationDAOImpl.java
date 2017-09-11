package com.agricultural.dao.detailnformation;

import com.agricultural.dao.HibernateUtil;
import com.agricultural.domains.DataMassive;
import com.agricultural.domains.gectarniyvirobitok.DetailDataHectare;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DetailDataHour;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import org.hibernate.query.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;


/**
 * Created by Alexey on 09.03.2017.
 */

public class DetailInformationDAOImpl implements DetailInformationDAO{

    private EntityManager session;

    @Override
    public void deleteDetailDataHectare(Long dataId){

//        DetailDataHectare detailDataHectare = getDetailDataHectare(dataId);
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.createQuery("delete DetailDataHectare where driverDataHectare.data_id=:id")
                    .setParameter("id",dataId).executeUpdate();
//            session.remove(detailDataHectare);
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

    @Override
    public DetailDataHectare getDetailDataHectare(Long dataId){

        session = HibernateUtil.getSessionFactory().createEntityManager();
        DetailDataHectare detailDataHectare = null;
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            Query<DetailDataHectare> detailDataHectareQuery =
                    (Query<DetailDataHectare>) session.createQuery("from DetailDataHectare where driverDataHectare.data_id =:data_id");
            detailDataHectareQuery.setParameter("dataId",dataId);
            detailDataHectare = detailDataHectareQuery.getSingleResult();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return detailDataHectare;
    }

    ///метод для перевірки наявності даного запису в таблиці з такими даними
    @Override
    public boolean isDetailDataHectareExist(Long dataId){
        DetailDataHectare detailDataHectare = this.getDetailDataHectare(dataId);
        return detailDataHectare==null?false:true;
    }

    ///для певної дати цей метод має визиватися тільки 1 раз
    ///метод для створення запису DetailDataHectare для конкретного запису у  тракториста
    @Override
    public void createDetailInformationHectare(DriverDataHectare driverDataHectare){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();

            ///driverDataHectare - обновляться автоматично

            DetailDataHectare detailDataHectare = new DetailDataHectare();
            driverDataHectare.setDetailDataHectare(detailDataHectare);
            detailDataHectare.setDriverDataHectare(driverDataHectare);

            detailDataHectare.setCultivatedAreaString(DataMassive.getStringFromDoubleMassive(new double[31]));
            detailDataHectare.setGivenFuelString(DataMassive.getStringFromDoubleMassive(new double[31]));
            detailDataHectare.setUsedFuelAreaString(DataMassive.getStringFromDoubleMassive(new double[31]));

            session.persist(detailDataHectare);

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

    ///обновляємо дані DetailDataHectare
    @Override
    public void editDetailDataHectare(DetailDataHectare detailDataHectare) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.merge(detailDataHectare);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx=null;
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///DetailDataHour

    ///для 1 запису в DriverDataHour цей метод має визиватися тільки 1 раз
    ///метод для створення запису DetailDataHour для конкретного запису у  тракториста
    @Override
    public void createDetailInformationHour(DriverDataHour driverDataHour){
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();

            ///driverDataHectare - обновляться автоматично
            DetailDataHour detailDataHour = new DetailDataHour();
            driverDataHour.setDetailDataHour(detailDataHour);
            detailDataHour.setDriverDataHour(driverDataHour);

            detailDataHour.setWorkedHoursString(DataMassive.getStringFromDoubleMassive(new double[31]));
            detailDataHour.setGivenFuelString(DataMassive.getStringFromDoubleMassive(new double[31]));
            detailDataHour.setUsedFuelAreaString(DataMassive.getStringFromDoubleMassive(new double[31]));

            session.persist(detailDataHour);

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


    ///дістає DetailDataHour по DetailDataHour.dataId
    @Override
    public DetailDataHour getDetailDataHour(Long dataId){

        session = HibernateUtil.getSessionFactory().createEntityManager();
        DetailDataHour detailDataHour = null;
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            Query<DetailDataHour> detailDataHourQuery =
                    (Query<DetailDataHour>) session.createQuery("from DetailDataHour where driverDataHour.data_id =:data_id");
            detailDataHourQuery.setParameter("dataId",dataId);
            detailDataHour = detailDataHourQuery.getSingleResult();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx =null;
        }
        return detailDataHour;
    }

    ///метод для перевірки наявності даного запису в таблиці з такими даними
    public boolean isDetailDataHourExist(Long dataId){
        DetailDataHour detailDataHour = this.getDetailDataHour(dataId);
        return detailDataHour==null?false:true;
    }

    ///обновляємо дані DetailDataHour
    @Override
    public void editDetailDataHour(DetailDataHour detailDataHour) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.merge(detailDataHour);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx=null;
        }
    }

    @Override
    public void deleteDetailDataHour(Long dataId){

        session = HibernateUtil.getSessionFactory().createEntityManager();
        DetailDataHour detailDataHour = getDetailDataHour(dataId);
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.remove(detailDataHour);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
            tx=null;
        }

    }

}