package com.agricultural.dao.operations;

import com.agricultural.dao.HibernateUtil;
import com.agricultural.domains.main.TechnologicalOperation;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;

/**
 * Created by Alexey on 14.02.2017.
 */
public class OperationDAOImpl implements OperationsDAO {

    private static OperationDAOImpl instance = new OperationDAOImpl();

    private OperationDAOImpl() {
    }

    public static OperationDAOImpl getInstance() {
        return instance;
    }

    private EntityManager session;

    public void createOperation(String operationName) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        TechnologicalOperation operation = new TechnologicalOperation();
        operation.setName(operationName);
        try{
            tx.begin();
            session.persist(operation);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    public void deleteOperation(TechnologicalOperation operation) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.createQuery("delete TechnologicalOperation where operation_id=:id")
                    .setParameter("id",operation.getOperation_id()).executeUpdate();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }

    }

    public void editOperation(TechnologicalOperation operation) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.merge(operation);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    public ArrayList<TechnologicalOperation> getOperations() {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        ArrayList<TechnologicalOperation> allOperations = null;
        try{
            tx.begin();
            Query<TechnologicalOperation> query = (Query<TechnologicalOperation>) session.createQuery("from TechnologicalOperation");
            allOperations =  (ArrayList<TechnologicalOperation>) query.list();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return allOperations;
    }

    public String[] getAllOperationsName(){
        ///отримаємо список всіх операцій
        ArrayList<TechnologicalOperation> arrayOperations = getOperations();
        String[] names = new String [arrayOperations.size()];
        ///записуєо назви операцій у масив
        for (int i=0;i<names.length;i++){
            names[i] = arrayOperations.get(i).getName();
        }
        return names;
    }

    @Override
    public TechnologicalOperation getOperationByName(String operationName) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        TechnologicalOperation operation = null;
        try{
            tx.begin();
            Query<TechnologicalOperation> query = (Query<TechnologicalOperation>) session.createQuery("from TechnologicalOperation where name=:name");
            query.setParameter("name",operationName);
            operation =  query.getSingleResult();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return operation;
    }


}
