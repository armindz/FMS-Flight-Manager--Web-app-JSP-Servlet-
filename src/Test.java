import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import database.AirportDatabase;
import database.FlightDatabase;
import database.SeatDatabase;
import management.AirlineManagementSystem;
import management.AirportManagementSystem;
import management.FlightManagementSystem;
import models.Airline;
import models.Airport;
import models.Flight;
import models.Seat;

public class Test {

	public static void main(String[] args) {
		
		try {
		SeatDatabase seatdb = new SeatDatabase();
		FlightManagementSystem flightms = new FlightManagementSystem();
		ArrayList <Seat> listOfSeats = flightms.getListOfSeats();
		AirlineManagementSystem airlinems = new AirlineManagementSystem();
		AirportManagementSystem airportms = new AirportManagementSystem();
		FlightDatabase flightDb = new FlightDatabase();
		
		Date date = new Date();
		
		
		System.out.println(flightDb.generateFlightId());
	
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		Airline airline = airlinems.getAirlineFromAirlineID(0);
		Airport airport =  airportms.getAirportFromAirportId(2);
		Airport destinationAirport = airportms.getAirportFromAirportId(0);
		
	
		
		
		
		Flight flight = new Flight(9, airline, airport, destinationAirport, "ECONOMY", cal, 'A', 10, 22.0);
		//flightDb.storeToDatabase(flight);
		
		flightms.createFlight("WZZ"	, "TZZ", "SAK", "Economy", cal, 'A', 5, 13);
		
		
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
