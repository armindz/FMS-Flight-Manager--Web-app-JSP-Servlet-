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
import models.Seat;

public class SeatDatabase {

	public void storeToDatabase(Seat seat) {

		try {
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class);
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

	@SuppressWarnings("unchecked")
	public ArrayList<Seat> fetchDatabaseContent() { // mechanism for fetching content from database and returning as List

		List<Seat> listOfSeats = new ArrayList<>();

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			listOfSeats = session.createQuery("from Seat").list();
			transaction.commit();

			return  (ArrayList <Seat>) listOfSeats;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return  (ArrayList <Seat>) listOfSeats;
	}
	
	// mechanism for updating database content
	public void updateDatabaseContent(int flightID, char seatRow, int seatNumber, boolean isSeatAvailable) { 
		try {
			
			int seatID = getSeatIdFromSeatData(flightID,seatRow,seatNumber);
			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();
			
			Transaction transaction = session.beginTransaction();

			Query query = session.createQuery(
					"UPDATE Seat SET flightId=? AND seatRow=? AND seatNumber=? AND isSeatAvailable=? WHERE seatId=?");
			query.setInteger(1, flightID);
			query.setCharacter(2, seatRow);
			query.setInteger(3, seatNumber);
			query.setBoolean(4, isSeatAvailable);
			query.setInteger(5, seatID);
			query.executeUpdate();

			transaction.commit();

		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void updateDatabaseContent(Seat seat) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class);
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

	public void deleteContentFromDatabase(int flight_ID) { // deleting content from database via flightID

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();

			Query query = session.createQuery("DELETE Seat  WHERE flightId=?");
			query.setInteger(1, flight_ID);
			query.executeUpdate();

			transaction.commit();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteContentFromDatabase(Seat seat) {

		try {

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class);
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

	@SuppressWarnings("unchecked")
	public static int generateSeatId() { // mechanism for generating seat ID based on last stored ID in database

		int seatID = 0;

		try {

			List<Seat> list = new ArrayList<>();

			Configuration cfg = new Configuration().configure().addAnnotatedClass(Seat.class);
			ServiceRegistry serviceReg = new ServiceRegistryBuilder().applySettings(cfg.getProperties())
					.buildServiceRegistry();
			SessionFactory sessionFactory = cfg.buildSessionFactory(serviceReg);
			Session session = sessionFactory.openSession();

			Transaction transaction = session.beginTransaction();
			list = session.createQuery("FROM Seat order by seat_id desc limit 1").list();
			seatID = list.get(0).getSeatId();
			seatID++;
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
	
	public int getSeatIdFromSeatData (int flightId, char seatRow, int seatNumber) {
		
		int seatID= 0;
		try {
		List <Seat> listOfSeats = fetchDatabaseContent();
		
		for (int i=0; i<listOfSeats.size();i++) {
		
			if (listOfSeats.get(i).getFlightId()==flightId && listOfSeats.get(i).getSeatRow()==seatRow && listOfSeats.get(i).getSeatNumber()==seatNumber) {
				
				seatID = listOfSeats.get(i).getSeatId();
				return seatID;
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return seatID;
	}
}
