package management;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import database.AirlineDatabase;
import database.AirportDatabase;
import database.FlightDatabase;
import database.SeatDatabase;
import database.FlightTicketDatabase;
import models.Airline;
import models.Airport;
import models.Flight;
import models.Seat;

public class FlightManagementSystem {

	AirlineManagementSystem airlinems = new AirlineManagementSystem();
	AirportManagementSystem airportms = new AirportManagementSystem();
	AirlineDatabase airlinedb = new AirlineDatabase();
	AirportDatabase airportdb = new AirportDatabase();
	SeatDatabase seatdb = new SeatDatabase();
	FlightDatabase flightdb = new FlightDatabase();
	FlightTicketDatabase flightTicketdb = new FlightTicketDatabase();

	// create flight with relevant flight data & generate flight ID
	public void createFlight(String airlineCodename, String airportCodename, String destinationAirportCodename,
			String flightClass, Calendar dateOfFlight, char flightSeatRows, int flightNumberOfSeatsPerRow,
			double flightPrice) {

		try {
			Flight flight = new Flight(flightdb.generateFlightId(), airlinems.getAirlineFromCodename(airlineCodename),
					airportms.getAirportFromCodename(airportCodename),
					airportms.getAirportFromCodename(destinationAirportCodename), flightClass, dateOfFlight,
					flightSeatRows, flightNumberOfSeatsPerRow, flightPrice);

			if (isFlightDataValid(flight, flight.getSeatRow(), flight.getAirline().getAirlineCodename(),
					flight.getAirport().getAirportCodename(), flight.getDestinationAirport().getAirportCodename())) {

				addFlightToDatabase(flight);
				createSeatsAndStoreToDatabase(flight, flight.getSeatRow(), flight.getSeatNumber());

				System.out.println("Flight successfully created!");
			} else {
				System.out.println("Data is not unique or seat row not valid.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean isFlightDataValid(Flight flight, char flightSeatRows, String airlineCodename,
			String airportCodename, String destinationAirportCodename) {

		try {
			if (isFlightDataUnique(flight) && isSeatRowValid(flightSeatRows) && isAirlineCodenameValid(airlineCodename)
					&& isAirportCodenameValid(airportCodename) && isAirportCodenameValid(destinationAirportCodename)) {
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void createSeatsAndStoreToDatabase(Flight flight, char seatRow, int numberOfSeatsPerRow)       
			throws SQLException {

		for (int i = 'A'; i <= seatRow; i++) {
			for (int j = 1; j <= numberOfSeatsPerRow; j++) {

				char seatRows = (char) i;
				Seat seat = new Seat(seatdb.generateSeatId(), flight, seatRows, j, true);
				
				flight.addToListOfSeats(seat);
				addSeatToDatabase(seat);
				System.out.println(seat.toString());
			}

		}
		System.out.println("List" + flight.getListOfSeats());
	}

	public void displayAvailableSeatsInSpecificFlight(int flight_id) {						// CHECK IF NOT USED

		ArrayList<Seat> listOfSeats = fetchSeatDatabaseContentToList(); // prepare listOfSeats ArrayList for use by
																		// fetching content from database

		for (int i = 0; i < listOfSeats.size(); i++) {

			if ((listOfSeats.get(i).getFlight().getFlightId() == flight_id) && listOfSeats.get(i).isSeatAvailable()) {
				System.out.println(listOfSeats.get(i));
			}
		}
	}

	public void markSeatAsAvailable(Seat seat) {

		boolean isSeatAvailable = true;

		seatdb.updateDatabaseContentToChangeSeatAvailability(seat, isSeatAvailable);
	}

	public void markSeatAsUnavailable(Seat seat) {

		boolean isSeatAvailable = false;

		seatdb.updateDatabaseContentToChangeSeatAvailability(seat, isSeatAvailable);
	}

	// mechanism for checking if forwarded flight is unique based fetched database
	// content
	private boolean isFlightDataUnique(Flight flight) throws SQLException {

		// return flight database content through given list
		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent();

		if (!listOfFlights.isEmpty() && listOfFlights.contains(flight)) {
			return false;
		}

		return true;
	}

	// mechanism for checking if forwarded airline code name is valid
	private boolean isAirlineCodenameValid(String airlineCodename) {

		// return airline database content through given list
		ArrayList<Airline> listOfAirlines = (ArrayList<Airline>) airlinedb.fetchDatabaseContent();

		for (int i = 0; i < listOfAirlines.size(); i++) {
			String airlineCodenameFromList = listOfAirlines.get(i).getAirlineCodename();
			if (airlineCodenameFromList.equals(airlineCodename)) {
				return true;
			}
		}
		System.out.println("Airline codename is not in database.");
		return false;
	}

	// mechanism for checking if forwarded airport code name is valid
	private boolean isAirportCodenameValid(String airportCodename) {

		// return airport database content through given list
		ArrayList<Airport> listOfAirports = (ArrayList<Airport>) airportdb.fetchDatabaseContent();

		for (int i = 0; i < listOfAirports.size(); i++) {
			String airportCodenameFromList = listOfAirports.get(i).getAirportCodename();

			if (airportCodenameFromList.equals(airportCodename)) {
				return true;
			}
		}
		System.out.println("Airport codename is not in database.");
		return false;
	}
	
	// mechanism for checking if forwarded seat row is valid
	private boolean isSeatRowValid(char seatRow) { 

		if (seatRow == 'A' || seatRow == 'B' || seatRow == 'C' || seatRow == 'D' || seatRow == 'E' || seatRow == 'F') {
			return true;
		}
		return false;
	}

	public Flight getFlightFromFlightID(int flightID) {

		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent(); 
		
		for (int i = 0; i < listOfFlights.size(); i++) {

			int flightIDFromList = listOfFlights.get(i).getFlightId();

			if (flightID == flightIDFromList) {
				Flight flight = new Flight(listOfFlights.get(i).getFlightId(), listOfFlights.get(i).getAirline(),
						listOfFlights.get(i).getAirport(), listOfFlights.get(i).getDestinationAirport(),
						listOfFlights.get(i).getFlightClass(), listOfFlights.get(i).getDateOfFlight(),
						listOfFlights.get(i).getSeatRow(), listOfFlights.get(i).getSeatNumber(),
						listOfFlights.get(i).getFlightPrice());
				return flight;

			}
		}

		return null;

	}

	public int getFlightIdFromFlight(Flight flight) {

		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent(); 

		for (int i = 0; i < listOfFlights.size(); i++) {

			Flight flightFromList = listOfFlights.get(i);
			if (flight.getAirline().equals(flightFromList.getAirline())
					&& flight.getAirport().equals(flightFromList.getAirport())
					&& flight.getDestinationAirport().equals(flightFromList.getDestinationAirport())
					&& flight.getFlightClass().equals(flightFromList.getFlightClass())
					&& flight.getSeatNumber() == flightFromList.getSeatNumber()
					&& flight.getSeatRow() == (flightFromList.getSeatRow())) {

				return flightFromList.getFlightId();
			}
		}
		return 0;
	}

	public int getFlightIdFromFlightData(String airlineCodename, String airportCodename,
			String destinationAirportCodename, String flightClass, Calendar dateOfFlight, 
			char flightSeatRows,int seatNumber, double flightPrice) {

		// return flight database content through given list
		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent();

		for (int i = 0; i < listOfFlights.size(); i++) {

			Flight flightFromList = listOfFlights.get(i);
			if (airlineCodename.equals(flightFromList.getAirline().getAirlineCodename())
					&& airportCodename.equals(flightFromList.getAirport().getAirportCodename())
					&& destinationAirportCodename.equals(flightFromList.getDestinationAirport().getAirportCodename())
					&& dateOfFlight.equals(flightFromList.getDateOfFlight())
					&& flightClass.equals(flightFromList.getFlightClass())
					&& seatNumber == flightFromList.getSeatNumber()
					&& flightSeatRows == (flightFromList.getSeatRow())) {

				return flightFromList.getFlightId();
			}
		}
		return 0;
	}

	public ArrayList<Flight> getListOfFlights() {

		return fetchFlightDatabaseContentToList();
	}

	public ArrayList<Seat> getListOfSeats() {

		return fetchSeatDatabaseContentToList();
	}

	// fetch seat database content and return as ArrayList
	public ArrayList<Seat> fetchSeatDatabaseContentToList() { 

		ArrayList<Seat> listOfSeats = (ArrayList<Seat>) seatdb.fetchDatabaseContent();
		if (listOfSeats.isEmpty()) {
			System.out.println("There's no seats stored in database!");
			return null;
		}
		return listOfSeats;
	}

	// fetch flight database content and return as list
	public ArrayList<Flight> fetchFlightDatabaseContentToList() { 

		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent();

		if (listOfFlights.isEmpty()) {
			System.out.println("There's no flights stored in database!");
			return null;
		}
		return listOfFlights;
	}

	public void addFlightToDatabase(Flight flight) throws SQLException {

		flightdb.storeToDatabase(flight);
	}

	// delete flight content based on forwarded flight ID and delete seats related to flight
	public void removeFlightFromDatabase(int flight_ID) throws SQLException { 
	
		try {
		flightTicketdb.deleteAllContentFromDatabaseRelatedToSpecificFlight(flight_ID);
		seatdb.deleteContentFromDatabase(flightdb.getFlightByFlightId(flight_ID));
		flightdb.deleteContentFromDatabase(flight_ID);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addSeatToDatabase(Seat seat) throws SQLException { // add seat to database

		seatdb.storeToDatabase(seat);
	}

}
