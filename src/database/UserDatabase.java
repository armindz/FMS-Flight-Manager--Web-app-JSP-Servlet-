package database;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import models.User;

public class UserDatabase {

	public void storeToDatabase(User user) {

		try {
			Configuration cfg = new Configuration().configure().addAnnotatedClass(User.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.save(user);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<User> fetchDatabaseContent() { // mechanism for fetching content from database
													// and returning as
		List<User> listOfUsers = new ArrayList<>();

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(User.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			listOfUsers = session.createQuery("from User").list();
			transaction.commit();

			return (ArrayList<User>) listOfUsers;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ArrayList<User>) listOfUsers;
	}

	public void updateDatabaseContent(int userID, String username, String password) {

		try {
			Configuration cfg = new Configuration().configure().addAnnotatedClass(User.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("UPDATE User SET username=? AND password=? WHERE user_id=?");
			query.setString(1, username);
			query.setString(2, password);
			query.setInteger(3, userID);
			transaction.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void updateDatabaseContent(User user) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(User.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.update(user);
			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteContentFromDatabase(int userID) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(User.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("DELETE FROM User WHERE user_id=?");
			query.setInteger(1, userID);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteContentFromDatabase(User user) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(User.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.delete(user);
			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// mechanism for generating userID based on last stored ID in database
	@SuppressWarnings("unchecked")
	public int generateUserID() {

		int userID = 0;

		try {

			List<User> list = new ArrayList<>();

			Configuration cfg = new Configuration().configure().addAnnotatedClass(User.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			list = session.createQuery("FROM User order by user_id desc limit 1").list();
			userID = list.get(0).getUserID();
			userID++;
			transaction.commit();

			return userID;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userID;
	}

}
