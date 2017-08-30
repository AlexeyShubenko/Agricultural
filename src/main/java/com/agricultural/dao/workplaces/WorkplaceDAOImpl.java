package com.agricultural.dao.workplaces;

import com.agricultural.dao.HibernateUtil;
import com.agricultural.domains.main.Workplace;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;

/**
 * Created by Alexey on 14.02.2017.
 */
public class WorkplaceDAOImpl implements WorkplacesDAO {

    private EntityManager session;

    public void createWorkPlace(String workplaceName) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        Workplace workplace = new Workplace();
        workplace.setWorkPlaceName(workplaceName);
        try{
            tx.begin();
            session.persist(workplace);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    public void deleteWorPlace(Workplace workplace) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.createQuery("delete Workplace where id=:id")
                    .setParameter("id",workplace.getId()).executeUpdate();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    public void editWorkplace(Workplace workplace) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.merge(workplace);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    public ArrayList<Workplace> getWorkplaces() {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        ArrayList<Workplace> allWorkplaces = null;
        try{
            tx.begin();
            Query<Workplace> query = (Query<Workplace>) session.createQuery("from Workplace");
            allWorkplaces =  (ArrayList<Workplace>) query.list();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return allWorkplaces;
    }

    public String[] getAllWorkplaceName(){
        ///отримаємо список всіх операцій
        ArrayList<Workplace> arrayWorkplaces = getWorkplaces();
        String[] names = new String [arrayWorkplaces.size()];
        ///записуєо назви операцій у масив
        for (int i=0;i<names.length;i++){
            names[i] = arrayWorkplaces.get(i).getWorkPlaceName();
        }
        return names;
    }

    @Override
    public Workplace getWorkplaceByName(String operationName) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        Workplace workplace = null;
        try{
            tx.begin();
            Query<Workplace> query = (Query<Workplace>) session.createQuery("from Workplace where workPlaceName=:name");
            query.setParameter("name",operationName);
            workplace =  query.getSingleResult();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return workplace;
    }


}
