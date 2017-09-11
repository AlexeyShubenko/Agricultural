package com.agricultural.dao.machinesunit;

import com.agricultural.dao.HibernateUtil;
import com.agricultural.domains.main.MachineTractorUnit;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;

/**
 * Created by Alexey on 14.02.2017.
 */
public class MachinesDAOImpl implements MachinesDAO {

    private static MachinesDAOImpl instance = new MachinesDAOImpl();

    private MachinesDAOImpl() {
    }

    public static MachinesDAOImpl getInstance() {
        return instance;
    }

    private EntityManager session;

    public void createMachine(String machineName) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        MachineTractorUnit machine = new MachineTractorUnit();
        machine.setName(machineName);
        try{
            tx.begin();
            session.persist(machine);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    public void deleteMachine(MachineTractorUnit machine) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.createQuery("delete MachineTractorUnit where machine_id=:id")
                    .setParameter("id",machine.getMachine_id()).executeUpdate();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    public void editMachine(MachineTractorUnit tractor) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        try{
            tx.begin();
            session.merge(tractor);
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
    }

    public ArrayList<MachineTractorUnit> getMachines() {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        ArrayList<MachineTractorUnit> allMachines = null;
        try{
            tx.begin();
            Query<MachineTractorUnit> query = (Query<MachineTractorUnit>) session.createQuery("from MachineTractorUnit");
            allMachines =  (ArrayList<MachineTractorUnit>) query.list();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return allMachines;
    }

    public String[] getAllMachinesName(){
        ArrayList<MachineTractorUnit> arrayMachines = getMachines();
        String[] names = new String [arrayMachines.size()];
        for (int i=0;i<names.length;i++){
            names[i] = arrayMachines.get(i).getName();
        }
        return names;
    }

    @Override
    public MachineTractorUnit getMachineByName(String machineName) {
        session = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = session.getTransaction();
        MachineTractorUnit machine = null;
        try{
            tx.begin();
            Query<MachineTractorUnit> query = (Query<MachineTractorUnit>) session.createQuery("from MachineTractorUnit where name=:name");
            query.setParameter("name",machineName);
            machine =  query.getSingleResult();
            tx.commit();
        }catch(Exception e){
            if (tx != null) {
                tx.rollback();
            }
        }finally {
            session.close();
        }
        return machine;
    }

}
