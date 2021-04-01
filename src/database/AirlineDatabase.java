
package database;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import models.Airline;

public class AirlineDatabase {


	public void storeToDatabase(Airline airline) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airline.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();
		
			Transaction transaction = session.beginTransaction();
			session.save(airline);
			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Airline> fetchDatabaseContent() { // mechanism for fetching content from database and returning as List

		List<Airline> airlines = new ArrayList<>();
		
		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airline.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			airlines = session.createQuery("from Airline").list();
			transaction.commit();

			return (ArrayList <Airline>) airlines;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return  (ArrayList <Airline>) airlines;
	}

	public void updateDatabaseContent(Airline airline) {

		try {
			
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airline.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.update(airline);
			transaction.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void updateDatabaseContent(int airlineId, String airlineCodename, String airlineCallsign, String airlineCountry) {
	
		try {
			
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airline.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			Airline airline = getAirlineFromAirlineId(airlineId);
			airline.setAirlineCodename(airlineCodename);
			airline.setAirlineCallsign(airlineCallsign);
			airline.setAirlineCountry(airlineCountry);
			session.update(airline);
			transaction.commit();

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteContentFromDatabase(String airlineCodename) {

		try {
		
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airline.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.delete(getAirlineFromAirlineCodename(airlineCodename));
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteContentFromDatabase(Airline airline) {

		try {
		
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airline.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.delete(airline);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public int generateAirlineId() { // mechanism for generating airline ID based on last stored ID in database

		List<Airline> airlines = new ArrayList<>();
		int airlineID = 0;
	
		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Airline.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			airlines = session.createQuery("from Airline order by airline_id desc limit 1").list();
			airlineID = airlines.get(0).getAirlineId();
			transaction.commit();
			airlineID++;
			return airlineID;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return airlineID;
	}

	public int getAirlineIdFromAirline(Airline airline) {

		ArrayList<Airline> listOfAirlines = fetchDatabaseContent();
		int airlineID = 0;
	
		try {

			for (int i = 0; i < listOfAirlines.size(); i++) {
				if (listOfAirlines.get(i).equals(airline)) {
					listOfAirlines.get(i).getAirlineId();
					return airlineID;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return airlineID;
	}

	public Airline getAirlineFromAirlineId(int airlineID) {

		Airline airline = null;

		try {

			ArrayList<Airline> listOfAirlines = fetchDatabaseContent();
			for (int i = 0; i < listOfAirlines.size(); i++) {
				if (listOfAirlines.get(i).getAirlineId() == airlineID) {
					airline = listOfAirlines.get(i);
					return airline;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airline;

	}

	public Airline getAirlineFromAirlineCodename(String airlineCodename) {

		Airline airline = null;
		try {
			ArrayList<Airline> listOfAirlines = fetchDatabaseContent();

			for (int i = 0; i < listOfAirlines.size(); i++) {
				if (listOfAirlines.get(i).getAirlineCodename().equals(airlineCodename)) {
					airline = listOfAirlines.get(i);
					return airline;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return airline;
	}
}
