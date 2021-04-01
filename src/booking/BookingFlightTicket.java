package booking;

import java.sql.SQLException;
import java.util.ArrayList;

import database.FlightTicketDatabase;
import database.SeatDatabase;

import management.FlightManagementSystem;
import models.Flight;
import models.FlightTicket;
import models.Seat;

public class BookingFlightTicket {

	FlightManagementSystem flightms = new FlightManagementSystem();
	SeatDatabase seatdb = new SeatDatabase();
	FlightTicketDatabase flightTicketdb = new FlightTicketDatabase();

	public void bookAFlight(Flight flight, Seat seat, String buyers_Name) {

		try {
			
			if (isSeatAvailable(seat)) {
				
				
				FlightTicket flightTicket = new FlightTicket(flightTicketdb.generateTicketId(), flight, seat, buyers_Name);
			
				addFlightTicketToDatabase(flightTicket);
				flightms.markSeatAsUnavailable(seat);
				System.out.println("Successfully booked!");

			} else {
				System.out.println("Problem with booking a flight. ");
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isSeatAvailable(Seat seat) {

		ArrayList<Seat> listOfSeats = (ArrayList <Seat>) seatdb.fetchDatabaseContent();
		for (int i = 0; i < listOfSeats.size(); i++) {

			Flight flightFromList = listOfSeats.get(i).getFlight();
			char seatRowFromList = listOfSeats.get(i).getSeatRow();
			int seatNumberFromList = listOfSeats.get(i).getSeatNumber();
			boolean isSeatAvailableFromList = listOfSeats.get(i).isSeatAvailable();

			if ((flightFromList.getFlightId() == seat.getFlight().getFlightId()) && (seat.getSeatRow() == seatRowFromList) && (seat.getSeatNumber() == seatNumberFromList)
					&& (isSeatAvailableFromList)) {

				return true;
			}

		}

		return false;
	}

	public ArrayList<FlightTicket> getListOfFlightTickets() {

		return fetchFlightTicketDatabaseContentToList();
	}
	
	// fetch flight database content and return as ArrayList
	public ArrayList<FlightTicket> fetchFlightTicketDatabaseContentToList() { 
																				
		ArrayList<FlightTicket> listOfFlightTickets = flightTicketdb.fetchDatabaseContent();

		if (listOfFlightTickets.isEmpty()) {
			System.out.println("There's no flights stored in database!");
			return null;
		}
		return listOfFlightTickets;
	}

	public void addFlightTicketToDatabase(FlightTicket flightTicket) {
		
		try {
		flightTicketdb.storeToDatabase(flightTicket);
	}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// delete flight ticket and mark seat as available
	public void removeFlightTicketFromDatabase(Flight flight, Seat seat) { 
		
		flightTicketdb.deleteContentFromDatabase(flight.getFlightId(), seat.getSeatRow(), seat.getSeatNumber());
		flightms.markSeatAsAvailable(seat);

	}
	
	// delete flight tickets, used when flight is being deleted
	public void removeFLightTicketRelatedToSpecificFlight(int flight_ID) { 

		flightTicketdb.deleteAllContentFromDatabaseRelatedToSpecificFlight(flight_ID);
		
	}

	public void updateFlightTicketData (int flight_ID, char seatRow, int seatNumber) {
		
	}
}
