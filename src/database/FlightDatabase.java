
package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import management.AirlineManagementSystem;
import management.AirportManagementSystem;
import models.Airport;
import models.Flight;

public class FlightDatabase {

	private static String statementToStoreDataIntoFlights = "INSERT INTO flights"
			+ "(flight_ID, airline, departure_airport, destination_airport, flight_class, date_of_flight, seat_row, seat_number, flight_price) values "
			+ " (?,?,?,?,?,?,?,?,?);";
	private static String statementToDisplayDataOfFlights = "SELECT * FROM flights";
	private static String statementToUpdateFlightsData = "UPDATE flights set airline= ?, departure_airport= ?, destination_airport = ?, flight_class = ?, "
			+ "date_of_flight = ?, seat_row = ?, seat_number = ?, flight_price= ? where flight_ID= ?";
	private static String statementToDeleteDataFromFlights = "DELETE from flights where flight_ID=?";
	final String STATEMENT_IF_CODENAME_IS_NULL = "NOT AVAILABLE";
	AirlineManagementSystem airlinems = new AirlineManagementSystem();
	AirportManagementSystem airportms = new AirportManagementSystem();

	public void storeToDatabase(Flight flight) {

		try {
			
			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToStoreDataIntoFlights);

			preparedStmt.setInt(1, flight.getFlight_id()); // Flight_ID Column
			preparedStmt.setInt(2, airlinems.getAirlineIdFromAirline(flight.getAirline())); // AirlineCodename Column
			preparedStmt.setInt(3, airportms.getAirportIdFromAirport(flight.getAirport())); // AirportCodename Column
			preparedStmt.setInt(4, airportms.getAirportIdFromAirport(flight.getDestinationAirport())); // Destination Airport
																							
			preparedStmt.setString(5, flight.getFlightClass()); // Flight_Class Column
			preparedStmt.setTimestamp(6, flight.getDateOfFlightInDateTime(flight.getDateOfFlight())); // Date_OF_Flight
																										// Column
			preparedStmt.setString(7, String.valueOf(flight.getSeatRow())); // SeatRow Column
			preparedStmt.setInt(8, flight.getSeatNumber()); // Seat_Number Column
			preparedStmt.setDouble(9, flight.getFlightPrice()); // Flight_Price Column

			preparedStmt.execute();

			// conn.close();
			// preparedStmt.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int generateFlightId() { // mechanism for generating flight ID based on last stored ID in database

		int flightID = 0;
		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(statementToDisplayDataOfFlights);

			while (rs.next()) {

				if (rs.isLast()) {
					flightID = rs.getInt(1);
					flightID++;
				}
			}

			return flightID;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public ArrayList<Flight> fetchDatabaseContent() { // mechanism for fetching content from database and returning as
														// ArrayList

		ArrayList<Flight> flights = new ArrayList<>();

		try {
			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(statementToDisplayDataOfFlights);

			flights.clear();
			while (rset.next()) {

				Calendar cal = Calendar.getInstance();
				Timestamp timestamp = rset.getTimestamp("date_of_flight");
				cal.setTime(timestamp);

				if (airlinems.getAirlineFromAirlineID(rset.getInt("airline")) != null
						&& airportms.getAirportFromAirportId(rset.getInt("departure_airport")) != null
						&& airportms.getAirportFromAirportId(rset.getInt("destination_airport")) != null) {

					Flight flight = new Flight(rset.getInt("flight_ID"),
							airlinems.getAirlineFromAirlineID(rset.getInt("airline")),
							airportms.getAirportFromAirportId(rset.getInt("departure_airport")),
							airportms.getAirportFromAirportId(rset.getInt("destination_airport")),
							rset.getString("flight_class"), cal, rset.getString("seat_row").charAt(0),
							rset.getInt("seat_number"), rset.getDouble("flight_price"));

					flights.add(flight);
					System.out.println(flight);

				}
			}

			return flights;
		}

		catch (Exception e) {

			e.printStackTrace();
		}

		return flights;
	}

	public void updateDatabaseContent(int FlightID, String airline, String departureAirport,
			String destinationAirport, String Flightclass, Calendar Date_of_flight, char seatRow, int seatNumber,
			double flight_Price) {

		Timestamp timestamp = new Timestamp(Date_of_flight.getTimeInMillis());

		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToUpdateFlightsData);

			preparedStmt.setInt(1, airlinems.getAirlineIdFromAirline(airlinems.getAirlineFromCodename(airline))); // update Airline_Codename column
			preparedStmt.setInt(2, airportms.getAirportIdFromAirport(airportms.getAirportFromCodename(departureAirport))); // update Airport_Codename column
			preparedStmt.setInt(3, airportms.getAirportIdFromAirport(airportms.getAirportFromCodename(destinationAirport))); // update Destination_Airport column
			preparedStmt.setString(4, Flightclass); // update Flight_class column
			preparedStmt.setTimestamp(5, timestamp); //
			preparedStmt.setString(6, String.valueOf(seatRow)); // update seatRow column
			preparedStmt.setInt(7, seatNumber); // update seatNumber column
			preparedStmt.setDouble(8, flight_Price); // update flight_price
			preparedStmt.setInt(9, FlightID); // where FlightID

			preparedStmt.executeUpdate();

			conn.close();
			preparedStmt.close();
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void deleteContentFromDatabase(int flight_ID) { // deleting from database content found using flight_ID as it
															// is unique

		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToDeleteDataFromFlights);

			preparedStmt.setInt(1, flight_ID);
			preparedStmt.executeUpdate();

			conn.close();
			preparedStmt.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}
	


}
