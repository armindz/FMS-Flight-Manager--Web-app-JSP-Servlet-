package booking;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import database.FlightTicketDatabase;
import database.SeatDatabase;

import management.FlightManagementSystem;
import models.Airline;
import models.Airport;
import models.Flight;
import models.FlightTicket;
import models.Seat;

public class BookingFlightTicket {

	FlightManagementSystem flightms = new FlightManagementSystem();
	SeatDatabase seatdb = new SeatDatabase();
	FlightTicketDatabase flightTicketdb = new FlightTicketDatabase();
	private ArrayList<Seat> listOfSeats = flightms.getListOfSeats();

	public void bookAFlight(int flightId, char seatRow, int seatNumber, String buyers_Name) {

		try {
			
			System.out.println(isSeatAvailable(flightId, seatRow, seatNumber));
			if (isSeatAvailable(flightId, seatRow, seatNumber)) {
				
				Flight flight = flightms.getFlightFromFlightID(flightId);
				FlightTicket flightTicket = new FlightTicket(flightId, flight.getAirline(), flight.getAirport(),
						flight.getDestinationAirport(), flight.getFlightClass(), flight.getDateOfFlight(), seatRow,
						seatNumber, flight.getFlightPrice(), buyers_Name);
				addFlightTicketToDatabase(flightTicket);
				flightms.markSeatAsUnavailable(flightId, seatRow, seatNumber);
				System.out.println("Successfully booked!");

			} else {
				System.out
						.println("Problem with booking a flight. Seat is not available. Be aware of typing mistakes! ");
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isSeatAvailable(int flightId, char seatRow, int seatNumber) {

		ArrayList<Seat> listOfSeats = seatdb.fetchDatabaseContent();
		for (int i = 0; i < listOfSeats.size(); i++) {

			int flightIdFromList = listOfSeats.get(i).getFlightId();
			char seatRowFromList = listOfSeats.get(i).getSeatRow();
			int seatNumberFromList = listOfSeats.get(i).getSeatNumber();
			boolean isSeatAvailableFromList = listOfSeats.get(i).isSeatAvailable();

			if ((flightIdFromList == flightId) && (seatRow == seatRowFromList) && (seatNumber == seatNumberFromList)
					&& (isSeatAvailableFromList)) {

				return true;
			}

		}

		return false;
	}

	public ArrayList<FlightTicket> getListOfFlightTickets() {

		return fetchFlightTicketDatabaseContentToList();
	}

	public ArrayList<FlightTicket> fetchFlightTicketDatabaseContentToList() { // fetch flight database content and
																				// return as ArrayList

		ArrayList<FlightTicket> listOfFlightTickets = flightTicketdb.fetchDatabaseContent();

		if (listOfFlightTickets.isEmpty()) {
			System.out.println("There's no flights stored in database!");
			return null;
		}
		return listOfFlightTickets;
	}

	public void addFlightTicketToDatabase(FlightTicket flightTicket) throws SQLException {

		flightTicketdb.storeToDatabase(flightTicket);
	}

	public void removeFlightTicketFromDatabase(int flight_ID, char seatRow, int seatNumber) { // delete flight ticket and mark seat as available
		
		flightTicketdb.deleteContentFromDatabase(flight_ID, seatRow, seatNumber);
		flightms.markSeatAsAvailable(flight_ID, seatRow, seatNumber);

	}
	

	public void removeFLightTicketRelatedToSpecificFlight(int flight_ID) { // delete flight tickets, used when flight is being deleted

		flightTicketdb.deleteAllContentFromDatabaseRelatedToSpecificFlight(flight_ID);
		
	}

	public void updateFlightTicketData (int flight_ID, char seatRow, int seatNumber) {
		
	}
}
