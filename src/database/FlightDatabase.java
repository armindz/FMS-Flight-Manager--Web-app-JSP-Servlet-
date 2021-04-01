
package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import org.hibernate.Transaction;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import management.AirlineManagementSystem;
import management.AirportManagementSystem;
import models.Airline;
import models.Airport;
import models.Flight;
import models.FlightTicket;

public class FlightDatabase {

	AirlineManagementSystem airlinems = new AirlineManagementSystem();
	AirportManagementSystem airportms = new AirportManagementSystem();

	public void storeToDatabase(Flight flight) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();

			session.save(flight);
			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public int generateFlightId() { // mechanism for generating flight ID based on last stored ID in database

		int flightID = 0;
		ArrayList<Flight> listOfFlights = new ArrayList<>();

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			listOfFlights = (ArrayList<Flight>) session.createQuery("from Flight order by flight_id desc limit 1")
					.list();
			flightID = listOfFlights.get(0).getFlightId();
			flightID++;
			transaction.commit();

			return flightID;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flightID;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Flight> fetchDatabaseContent() { // mechanism for fetching content from database and returning as ArrayList
														
		ArrayList<Flight> listOfFlights = new ArrayList<>();

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			listOfFlights = (ArrayList<Flight>) session.createQuery("from Flight").list();
			transaction.commit();

			return listOfFlights;
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return listOfFlights;
	}
	// CHANGE TO OBJ
	public void updateDatabaseContent(int FlightID, String airline, String departureAirport, String destinationAirport,
			String Flightclass, Calendar Date_of_flight, char seatRow, int seatNumber, double flight_Price) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery(
					"Update Flight set airline=? AND departure_airport=? AND destination_airport=? AND flight_class=? "
							+ "AND date_of_flight=? AND seat_row=? AND seat_number=? AND flight_price WHERE flight_id=?");
			
			transaction.commit();
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void updateDatabaseContent(Flight flight) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.update(flight);
			transaction.commit();

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteContentFromDatabase(Flight flight) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.delete(flight);
			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
	// deleting from database content found using flight_ID as it is unique
	public void deleteContentFromDatabase(int flightId) { 
															
		try {
			
			deleteContentFromDatabase(getFlightByFlightId(flightId));
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Flight getFlightByFlightId(int flightId) {

		Flight flight = null;
		
		try {
			
			ArrayList<Flight> listOfFlights = fetchDatabaseContent();

			for (int i = 0; i < listOfFlights.size(); i++) {

				if (listOfFlights.get(i).getFlightId() == flightId) {
					flight = listOfFlights.get(i);
					return flight;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flight;
	}
}
