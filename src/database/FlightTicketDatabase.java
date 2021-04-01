
package database;

import java.util.ArrayList;

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
import models.Seat;

public class FlightTicketDatabase {

	AirlineManagementSystem airlinems = new AirlineManagementSystem();
	AirportManagementSystem airportms = new AirportManagementSystem();

	public void storeToDatabase(FlightTicket flightTicket) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class).addAnnotatedClass(Flight.class).addAnnotatedClass(Seat.class);
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
	public int generateTicketId() { // mechanism for generating seat ID based on last stored ID in database

		int ticketID = 0;
		try {

			ArrayList<FlightTicket> listOfFlightTickets = new ArrayList<FlightTicket>();

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class).addAnnotatedClass(Flight.class).addAnnotatedClass(Seat.class);
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

	// mechanism for fetching content from database and returning as ArrayList
	@SuppressWarnings("unchecked")
	public ArrayList<FlightTicket> fetchDatabaseContent() {

		ArrayList<FlightTicket> flightTickets = new ArrayList<>();

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class).addAnnotatedClass(Flight.class).addAnnotatedClass(Seat.class);
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

	public void updateDatabaseContent(FlightTicket ticket) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class).addAnnotatedClass(Flight.class).addAnnotatedClass(Seat.class);
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

		try {

			deleteContentFromDatabase(getFlightTicketFromTicketData(flightID, seatRow, seatNumber));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteContentFromDatabase(FlightTicket ticket) {

		try {
			System.out.println(ticket.toString());
			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class).addAnnotatedClass(Flight.class).addAnnotatedClass(Seat.class);
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

			Configuration cfg = new Configuration().configure().addAnnotatedClass(FlightTicket.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class).addAnnotatedClass(Flight.class).addAnnotatedClass(Seat.class);
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

	public FlightTicket getFlightTicketFromTicketData(int flightId, char seatRow, int seatNumber) { // IMPLEMENT FLIGHT AND SEAT OBJ

		FlightTicket ticket = null;

		try {
			ArrayList<FlightTicket> listOfTickets = fetchDatabaseContent();

			for (int i = 0; i < listOfTickets.size(); i++) {
				if (listOfTickets.get(i).getFlight().getFlightId() == flightId && listOfTickets.get(i).getSeat().getSeatRow() == seatRow
						&& listOfTickets.get(i).getSeat().getSeatNumber() == seatNumber) {
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
