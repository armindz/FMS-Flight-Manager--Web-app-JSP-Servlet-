
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

import models.FlightTicket;

public class FlightTicketDatabase {

	AirlineManagementSystem airlinems = new AirlineManagementSystem();
	AirportManagementSystem airportms = new AirportManagementSystem();

	public void storeToDatabase(FlightTicket flightTicket) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.save(flightTicket);
			transaction.commit();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static int generateTicketId() { // mechanism for generating seat ID based on last stored ID in database

		int ticketID = 0;
		try {

			ArrayList<FlightTicket> listOfFlightTickets = new ArrayList<FlightTicket>();

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			listOfFlightTickets = (ArrayList<FlightTicket>) session
					.createQuery("From FlightTicket order by ticket_id desc limit 1").list();
			ticketID = listOfFlightTickets.get(0).getTicketId();
			ticketID++;
			transaction.commit();

			return ticketID;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return ticketID;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<FlightTicket> fetchDatabaseContent() { // mechanism for fetching content from database and
															// returning as ArrayList

		ArrayList<FlightTicket> flightTickets = new ArrayList<>();
		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			flightTickets = (ArrayList<FlightTicket>) session.createQuery("From FlightTicket").list();
			transaction.commit();

			return flightTickets;

		}

		catch (Exception e) {

			e.printStackTrace();
		}

		return flightTickets;
	}
/*
	public void updateDatabaseContent(int flightID, String airline, String
	  departureAirport, String destinationAirport, String flightclass, Calendar
	  dateOfFlight, char seatRow, int seatNumber, double flightPrice, String
	  buyersName) {
	  
	  Timestamp timestamp = new Timestamp(dateOfFlight.getTimeInMillis());
	  
	  try {
	  
		  Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery(buyersName)
			transaction.commit();
			
	  DatabaseConnection dbConnection = DatabaseConnection.getInstance();
	  Connection conn = dbConnection.getConnection(); PreparedStatement
	  preparedStmt = conn.prepareStatement(statementToUpdateFlightTicketsData);
	  
	  preparedStmt.setInt(1,airlinems.getAirlineIdFromAirline(airlinems.getAirlineFromCodename(airline)))
	  ; // update // Airline_Codename // column preparedStmt.setInt(2,
	  airportms.getAirportIdFromAirport(airportms.getAirportFromCodename(
	  departureAirport))); // update // Airport_Codename // column
	  preparedStmt.setInt(3,
	  airportms.getAirportIdFromAirport(airportms.getAirportFromCodename(
	  destinationAirport))); // update // Destination_Airport // column
	  preparedStmt.setString(4, flightclass); // update Flight_class column
	  preparedStmt.setTimestamp(5, timestamp); // preparedStmt.setDouble(6,
	  flightPrice); // update flight_price preparedStmt.setInt(7, flightID); //
	  where FlightID preparedStmt.setString(8, String.valueOf(seatRow)); // update
	  seatRow column preparedStmt.setInt(9, seatNumber); // update seatNumber
	  column preparedStmt.setString(10, buyersName);
	  
	  preparedStmt.executeUpdate();
	  
	  conn.close(); preparedStmt.close(); }
	  
	  catch (Exception ex) { ex.printStackTrace(); }
	 
	 }
*/
	public void updateDatabaseContent(FlightTicket ticket) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.update(ticket);
			transaction.commit();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteContentFromDatabase(int flightID, char seatRow, int seatNumber) {

	}

	public void deleteContentFromDatabase(FlightTicket ticket) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.delete(ticket);
			transaction.commit();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteAllContentFromDatabaseRelatedToSpecificFlight(int flight_ID) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();

			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			Query query = session.createQuery("Delete from FlightTicket where flight_id = ?");
			query.setInteger(1, flight_ID);
			query.executeUpdate();
			transaction.commit();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public FlightTicket getFlightTicketFromTicketId(int ticketId) {

		FlightTicket ticket = null;
		try {
			ArrayList<FlightTicket> listOfTickets = fetchDatabaseContent();

			for (int i = 0; i < listOfTickets.size(); i++) {
				if (listOfTickets.get(i).getTicketId() == ticketId) {
					ticket = listOfTickets.get(i);
					return ticket;
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}

	public FlightTicket getFlightTicketFromTicketData(int flightId, char seatRow, int seatNumber) {

		FlightTicket ticket = null;
		try {
			ArrayList<FlightTicket> listOfTickets = fetchDatabaseContent();

			for (int i = 0; i < listOfTickets.size(); i++) {
				if (listOfTickets.get(i).getFlightId() == flightId && listOfTickets.get(i).getSeatRow() == seatRow
						&& listOfTickets.get(i).getSeatNumber() == seatNumber) {
					ticket = listOfTickets.get(i);
					return ticket;
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}
}
