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

	
	
	public void createFlight(String airlineCodename, String airportCodename, String destinationAirportCodename,
			String flightClass, Calendar dateOfFlight, char flightSeatRows, int flightNumberOfSeatsPerRow,
			double flightPrice) throws SQLException {

		Flight flight = new Flight(flightdb.generateFlightId(), airlinems.getAirlineFromCodename(airlineCodename),
				airportms.getAirportFromCodename(airportCodename),
				airportms.getAirportFromCodename(destinationAirportCodename), flightClass, dateOfFlight, flightSeatRows,
				flightNumberOfSeatsPerRow, flightPrice);

		System.out.println(flight);
		if (isFlightDataValid(flight, flight.getSeatRow(), flight.getAirline().getAirlineCodename(),
				flight.getAirport().getAirportCodename(), flight.getDestinationAirport().getAirportCodename())) {

			addFlightToDatabase(flight);
			createSeatsAndStoreToDatabase(flight, flight.getSeatRow(), flight.getSeatNumber());
			

			System.out.println("Flight successfully created!");
		} else {
			System.out.println("Data is not unique or seat row not valid.");
		}

	}
	private boolean isFlightDataValid(Flight flight, char flightSeatRows, String airlineCodename,
			String airportCodename, String destinationAirportCodename) throws SQLException {

		if (isFlightDataUnique(flight) && isSeatRowValid(flightSeatRows) && isAirlineCodenameValid(airlineCodename)
				&& isAirportCodenameValid(airportCodename) && isAirportCodenameValid(destinationAirportCodename)) {
			return true;
		}

		return false;
	}

	

	private void createSeatsAndStoreToDatabase(Flight flight, char seatRow, int numberOfSeatsPerRow) throws SQLException {

		for (int i = 'A'; i <= seatRow; i++) {
			for (int j = 1; j <= numberOfSeatsPerRow; j++) {

				char seatRows = (char) i;
				Seat seat = new Seat(flight.getFlight_id(), seatRows, j, true);

				flight.addToListOfSeats(seat);
				addSeatToDatabase(seat);
			}

		}
		System.out.println("List" + flight.getListOfSeats());
	}

	public void displayAvailableSeatsInSpecificFlight(int flight_id) {

		ArrayList<Seat> listOfSeats = fetchSeatDatabaseContentToList(); // prepare listOfSeats ArrayList for use by
																		// fetching content from database

		for (int i = 0; i < listOfSeats.size(); i++) {

			if ((listOfSeats.get(i).getFlightId() == flight_id) && listOfSeats.get(i).isSeatAvailable()) {
				System.out.println(listOfSeats.get(i));
			}
		}
	}

	public void markSeatAsAvailable(int flightId, char seatRow, int seatNumber) {

		boolean isSeatAvailable = true;
		seatdb.updateDatabaseContent(flightId, seatRow, seatNumber, isSeatAvailable);

	}

	public void markSeatAsUnavailable(int flightId, char seatRow, int seatNumber) {

		boolean isSeatAvailable = false;
		seatdb.updateDatabaseContent(flightId, seatRow, seatNumber, isSeatAvailable);

	}

	private boolean isFlightDataUnique(Flight flight) throws SQLException { // mechanism for checking if forwarded flight is unique based on data from database

		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent(); // return flight database content through
																			// given ArrayList
		if (!listOfFlights.isEmpty() && listOfFlights.contains(flight)) {
			return false;
		}

		return true;
	}

	private boolean isAirlineCodenameValid(String airlineCodename) { // mechanism for checking if forwarded airline codename is valid

		ArrayList<Airline> listOfAirlines = airlinedb.fetchDatabaseContent(); // // return airline database content
																				// through
		// given ArrayList
		for (int i = 0; i < listOfAirlines.size(); i++) {
			String airlineCodenameFromList = listOfAirlines.get(i).getAirlineCodename();
			if (airlineCodenameFromList.equals(airlineCodename)) {
				return true;
			}
		}
		System.out.println("Airline codename is not in database.");
		return false;
	}

	private boolean isAirportCodenameValid(String airportCodename) { // mechanism for checking if forwarded airport codename is valid

		ArrayList<Airport> listOfAirports = airportdb.fetchDatabaseContent(); // return airport database content through
																				// given ArrayList

		for (int i = 0; i < listOfAirports.size(); i++) {
			String airportCodenameFromList = listOfAirports.get(i).getAirportCodename();

			if (airportCodenameFromList.equals(airportCodename)) {
				return true;
			}
		}
		System.out.println("Airport codename is not in database.");
		return false;
	}

	private boolean isSeatRowValid(char seatRow) { // mechanism for checking if forwarded seat row is valid

		if (seatRow == 'A' || seatRow == 'B' || seatRow == 'C' || seatRow == 'D' || seatRow == 'E' || seatRow == 'F') {
			return true;
		}
		return false;
	}

	public Flight getFlightFromFlightID(int flightID) {

		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent(); // return flight database content through
																			// given ArrayList

		for (int i = 0; i < listOfFlights.size(); i++) {

			int flightIDFromList = listOfFlights.get(i).getFlight_id();

			if (flightID == flightIDFromList) {
				Flight flight = new Flight(listOfFlights.get(i).getFlight_id(), listOfFlights.get(i).getAirline(),
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

		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent(); // return flight database content through
																			// given ArrayList

		for (int i = 0; i < listOfFlights.size(); i++) {

			Flight flightFromList = listOfFlights.get(i);
			if (flight.getAirline().equals(flightFromList.getAirline())
					&& flight.getAirport().equals(flightFromList.getAirport())
					&& flight.getDestinationAirport().equals(flightFromList.getDestinationAirport())
					&& flight.getFlightClass().equals(flightFromList.getFlightClass())
					&& flight.getSeatNumber() == flightFromList.getSeatNumber()
					&& flight.getSeatRow() == (flightFromList.getSeatRow())) {

				return flightFromList.getFlight_id();
			}
		}
			return 0;
		}
	

	public int getFlightIdFromFlightData(String airlineCodename, String airportCodename,
			String destinationAirportCodename, String flightClass, Calendar dateOfFlight, char flightSeatRows,
			int seatNumber, double flightPrice) {

		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent(); // return flight database content through
																			// given ArrayList

		for (int i = 0; i < listOfFlights.size(); i++) {

			Flight flightFromList = listOfFlights.get(i);
			if (airlineCodename.equals(flightFromList.getAirline().getAirlineCodename())
					&& airportCodename.equals(flightFromList.getAirport().getAirportCodename())
					&& destinationAirportCodename.equals(flightFromList.getDestinationAirport().getAirportCodename())
					&& dateOfFlight.equals(flightFromList.getDateOfFlight())
					&& flightClass.equals(flightFromList.getFlightClass())
					&& seatNumber == flightFromList.getSeatNumber()
					&& flightSeatRows == (flightFromList.getSeatRow())) {

				return flightFromList.getFlight_id();
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

	
	public ArrayList<Seat> fetchSeatDatabaseContentToList() { // fetch seat database content and return as ArrayList

		ArrayList<Seat> listOfSeats = seatdb.fetchDatabaseContent();
		if (listOfSeats.isEmpty()) {
			System.out.println("There's no seats stored in database!");
			return null;
		}
		return listOfSeats;
	}

	public ArrayList<Flight> fetchFlightDatabaseContentToList() { // fetch flight database content and return as ArrayList

		ArrayList<Flight> listOfFlights = flightdb.fetchDatabaseContent();

		if (listOfFlights.isEmpty()) {
			System.out.println("There's no flights stored in database!");
			return null;
		}
		return listOfFlights;
	}

	public void addFlightToDatabase(Flight flight) throws SQLException  {

		flightdb.storeToDatabase(flight);
	}

	public void removeFlightFromDatabase(int flight_ID) throws SQLException {    // delete flight content based on forwarded flight ID and delete seats related to flight

		flightdb.deleteContentFromDatabase(flight_ID);	
		seatdb.deleteContentFromDatabase(flight_ID); 
		flightTicketdb.deleteAllContentFromDatabaseRelatedToSpecificFlight(flight_ID);
	}

	private void addSeatToDatabase(Seat seat) throws SQLException { // add seat to database

		seatdb.storeToDatabase(seat);
	}
	
}
