import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import booking.BookingFlightTicket;
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
		AirlineManagementSystem airlinems = new AirlineManagementSystem();
		AirportManagementSystem airportms = new AirportManagementSystem();
		FlightDatabase flightDb = new FlightDatabase();
		BookingFlightTicket bft = new BookingFlightTicket();
		Date date = new Date();

	//	bft.bookAFlight(0, 'A', 1, "ARMIN");
		//flightDb.storeToDatabase(flight);
		System.out.println(seatdb.fetchDatabaseContent());

		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
