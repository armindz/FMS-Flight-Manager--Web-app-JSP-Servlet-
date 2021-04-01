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

import models.Airline;
import models.Airport;
import models.Flight;
import models.Seat;

public class SeatDatabase {

	
	
	public void storeToDatabase(Seat seat) {

		try {
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class)
					.addAnnotatedClass(Flight.class).addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.save(seat);
			transaction.commit();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// mechanism for fetching content from database and returning asList
	@SuppressWarnings("unchecked")
	public ArrayList<Seat> fetchDatabaseContent() { 

		ArrayList<Seat> listOfSeats = new ArrayList<>();

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class).addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			listOfSeats = (ArrayList<Seat>) session.createQuery("from Seat").list();
			transaction.commit();

			return listOfSeats;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfSeats;
	}

	// mechanism for updating database content forwarding seat data
	public void updateDatabaseContent(Flight flight, char seatRow, int seatNumber, boolean isSeatAvailable) { 
	
		try {

			int seatID = getSeatIdFromSeatData(flight, seatRow, seatNumber);
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class).addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();

			Query query = session.createQuery(
					"UPDATE Seat SET is_seat_available=:isSeatAvailable WHERE seat_id=:seatId");
			query.setBoolean("isSeatAvailable", isSeatAvailable);
			query.setInteger("seatId", seatID);
			query.executeUpdate();

			transaction.commit();

		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public void updateDatabaseContentToChangeSeatAvailability(Seat seat, boolean isSeatAvailable) {
		
		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class).addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();

			Query query = session.createQuery(
					"UPDATE Seat SET is_seat_available=:isSeatAvailable WHERE seat_id=:seatId");
			query.setBoolean("isSeatAvailable", isSeatAvailable);
			query.setInteger("seatId", seat.getSeatId());
			query.executeUpdate();

			transaction.commit();

		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	// updating database content using seat obj
	public void updateDatabaseContent(Seat seat) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class).addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.update(seat);
			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// delete content from database with flight id
	public void deleteContentFromDatabase(Flight flight) { // deleting content from database via flightID

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class).addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();

			Query query = session.createQuery("DELETE From Seat WHERE flight_id=:flightId");
			query.setParameter("flightId", flight);
			query.executeUpdate();

			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// delete content from database using seat obj
	public void deleteContentFromDatabase(Seat seat) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class).addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			session.delete(seat);
			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// mechanism for generating seat ID based on last stored ID in database
	@SuppressWarnings("unchecked")
	public int generateSeatId() { 

		int seatID = 0;

		try {

			List<Seat> list = new ArrayList<>();

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class).addAnnotatedClass(Flight.class)
					.addAnnotatedClass(Airline.class).addAnnotatedClass(Airport.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			list = (ArrayList<Seat>) session.createQuery("FROM Seat order by seat_id desc limit 1").list();
			seatID = list.get(0).getSeatId();
			System.out.println("BROJ PRIJE:" + seatID);
			seatID++;

			System.out.println("BROJ : " + seatID);
			transaction.commit();

			return seatID;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return seatID;
	}


	public Seat getSeatFromSeatId(int seatId) {

		Seat seat = null;
		try {
			List<Seat> listOfSeats = fetchDatabaseContent();

			for (int i = 0; i < listOfSeats.size(); i++) {

				if (listOfSeats.get(i).getSeatId() == seatId) {
					seat = listOfSeats.get(i);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return seat;
	}
	
	public Seat getSeatFromSeatData(Flight flight, char seatRow, int seatNumber) {
		
		Seat seat = null;
		
		try {
			seat = getSeatFromSeatId(getSeatIdFromSeatData(flight,seatRow,seatNumber));
			return seat;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return seat;
	}
	public int getSeatIdFromSeatData(Flight flight, char seatRow, int seatNumber) {

		int seatID = 0;
		try {
			List<Seat> listOfSeats = fetchDatabaseContent();

			for (int i = 0; i < listOfSeats.size(); i++) {

				if (listOfSeats.get(i).getFlight().getFlightId()== flight.getFlightId() && listOfSeats.get(i).getSeatRow() == seatRow
						&& listOfSeats.get(i).getSeatNumber() == seatNumber) {

					seatID = listOfSeats.get(i).getSeatId();
					return seatID;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return seatID;
	}
}
