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

public class DetailInformationDAOImpl {

    private EntityManager session;

    public void deleteDetailDataHectare(Long data_id){

//        DetailDataHectare detailDataHectare = getDetailDataHectare(data_id);
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.createQuery("delete DetailDataHectare where driverDataHectare.data_id=:id")
                    .setParameter("id",data_id).executeUpdate();
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

    public DetailDataHectare getDetailDataHectare(Long data_id){

        session = HibernateUtil.getSessionFactory().createEntityManager();
        DetailDataHectare detailDataHectare = null;
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            Query<DetailDataHectare> detailDataHectareQuery =
                    (Query<DetailDataHectare>) session.createQuery("from DetailDataHectare where driverDataHectare.data_id =:data_id");
            detailDataHectareQuery.setParameter("data_id",data_id);
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
    public boolean isDetailDataHectareExist(Long data_id){
        DetailDataHectare detailDataHectare = this.getDetailDataHectare(data_id);
        return detailDataHectare==null?false:true;
    }

    ///для певної дати цей метод має визиватися тільки 1 раз
    ///метод для створення запису DetailDataHectare для конкретного запису у  тракториста
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


    ///дістає DetailDataHour по DetailDataHour.data_id
    public DetailDataHour getDetailDataHour(Long data_id){

        session = HibernateUtil.getSessionFactory().createEntityManager();
        DetailDataHour detailDataHour = null;
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            Query<DetailDataHour> detailDataHourQuery =
                    (Query<DetailDataHour>) session.createQuery("from DetailDataHour where driverDataHour.data_id =:data_id");
            detailDataHourQuery.setParameter("data_id",data_id);
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
    public boolean isDetailDataHourExist(Long data_id){
        DetailDataHour detailDataHour = this.getDetailDataHour(data_id);
        return detailDataHour==null?false:true;
    }

    ///обновляємо дані DetailDataHour
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

    public void deleteDetailDataHour(Long data_id){

        session = HibernateUtil.getSessionFactory().createEntityManager();
        DetailDataHour detailDataHour = getDetailDataHour(data_id);
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
            tx =null;
        }

    }

}