package com.agricultural.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Persistence;

public class HibernateUtil {

	private static SessionFactory factory;

	static{
		try{
//		factory = new Configuration().configure().buildSessionFactory();
			factory = (SessionFactory) Persistence.createEntityManagerFactory( "org.hibernate.agricultural.jpa" );
		}catch(Exception e){
			System.out.println("SessionFactory is not created");
			System.out.println(e.toString());
		}
	}

	public static SessionFactory getSessionFactory(){
		return factory;
	}
	
}
