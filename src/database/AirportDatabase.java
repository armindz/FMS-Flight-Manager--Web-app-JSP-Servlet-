package database;


import java.util.ArrayList;
import java.util.List;

import models.Airport;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class AirportDatabase {


	public void storeToDatabase(Airport airport) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.save(airport);
			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@SuppressWarnings("unchecked")
	public ArrayList<Airport> fetchDatabaseContent() { // mechanism for fetching content from database and returning as
													// ArrayList

		ArrayList<Airport> airports = new ArrayList<>();
		try {
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airport.class);
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			airports = (ArrayList <Airport>) session.createQuery("from Airport").list();
			transaction.commit();
			return (ArrayList <Airport>) airports;
		}

		catch (Exception e) {

			System.out.println("Something went wrong with fetching database content");
			e.printStackTrace();
		}
		return airports;

	}

	public void updateDatabaseContent(int airportID, String airportCodename, String airportFullname, String airportType,
			String airportCity, String airportCountry) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airport.class);
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);
			Session session = sessionFactory.openSession();
			
			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("update Airport set airportCodename=? AND airportFullname=? AND airportType=? "
					+ "AND airportCity=? AND airportCountry=? WHERE airportID=?");
			
			query.setString(1, airportCodename);
			query.setString(2, airportFullname);
			query.setString(3, airportType);
			query.setString(4, airportCity);
			query.setString(5, airportCountry);
			query.setInteger(6, airportID);
			
			query.executeUpdate();
			transaction.commit();
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateDatabaseContent (Airport airport) {
		
		try {
		
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airport.class);
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceRegistry);
			Session session = sessionFactory.openSession();
			
			Transaction transaction = session.beginTransaction();
			session.update(airport);
			transaction.commit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteContentFromDatabase(String airportCodename) { // deleting from database content found using
																	// Airport_Codename as it is unique
		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.delete(getAirportFromAirportCodename(airportCodename));
			transaction.commit();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteContentFromDatabase(Airport airport) {


		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.delete(airport);
			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static int generateAirportId() { // mechanism for generating airport ID based on last stored ID in database

		List<Airport> listOfAirports = new ArrayList<>();
		int airportID = 0;
		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			listOfAirports = session.createQuery("from Airport order by airport_id desc limit1").list();
			airportID = listOfAirports.get(0).getAirportID();
			airportID++;
			transaction.commit();

			return airportID;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return airportID;
	}

	public int getAirportIdFromAirport(Airport airport) {

		int airportID = 0;
		
		try {
			
			ArrayList<Airport> listOfAirports = fetchDatabaseContent();
			for (int i = 0; i < listOfAirports.size(); i++) {
				if (listOfAirports.get(i).equals(airport)) {
					airportID = listOfAirports.get(i).getAirportID();
					return airportID;
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return airportID;
	}

	public Airport getAirportFromAirportCodename(String airportCodename) {

		Airport airport = null;
		
		try {
			
			ArrayList<Airport> listOfAirports = fetchDatabaseContent();
			for (int i = 0; i < listOfAirports.size(); i++) {
				if (listOfAirports.get(i).getAirportCodename().equals(airportCodename)) {
					airport = listOfAirports.get(i);
					return airport;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airport;
	}

	public Airport getAirportFromAirportId(int airport_id) {

		Airport airport = null;
		
		try {
			
			ArrayList<Airport> listOfAirports = fetchDatabaseContent();
			for (int i = 0; i < listOfAirports.size(); i++) {
				if (listOfAirports.get(i).getAirportID() == airport_id) {
					airport = listOfAirports.get(i);
					return airport;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airport;
	}
}
