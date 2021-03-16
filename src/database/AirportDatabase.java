package database;

import java.sql.*;
import java.util.ArrayList;

import models.Airline;
import models.Airport;

public class AirportDatabase {

	private static String statementToStoreDataIntoAirports = "INSERT INTO airports"
			+ "(airport_id, airport_codename, airport_fullname, airport_type, airport_city, airport_country) values "
			+ " (?,?,?, ?,?, ?);";
	private static String statementToDisplayDataOfAirports = "SELECT * FROM airports";
	private static String statementToUpdateAirportsData = "UPDATE airports set airport_fullname = ?, airport_type = ?, airport_city =?, airport_country where  airport_codename= ?";
	private static String statementToDeleteDataFromAirports = "DELETE from airports where airport_codename= ?";
	private static String statementToGetIdFromAirportData = "SELECT airport_id FROM airports WHERE airport_codename=? "
			+ "AND airport_fullname=? AND airport_type=? AND airport_city=? AND airport_country=?";

	private static String statementToDisplayAirportFromAirportId = "SELECT * FROM airports WHERE airport_id=?";
	public void storeToDatabase(Airport airport) {

		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToStoreDataIntoAirports);

			preparedStmt.setInt(1, generateAirportId()); // airport_id Column
			preparedStmt.setString(2, airport.getAirportCodename()); // airport_codename Column
			preparedStmt.setString(3, airport.getAirportFullname()); // airport_fullname Column
			preparedStmt.setString(4, airport.getAirportType()); // airport_type Column
			preparedStmt.setString(5, airport.getAirportCity()); // airport_city Column
			preparedStmt.setString(6, airport.getAirportCountry()); // airport_country Column

			preparedStmt.execute();

			conn.close();
			preparedStmt.close();

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int generateAirportId() { // mechanism for generating airport ID based on last stored ID in database

		int airportID = 0;
		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(statementToDisplayDataOfAirports);

			while (rs.next()) {

				if (rs.isLast()) {
					airportID = rs.getInt(1);
					airportID++;
				}
			}

			return airportID;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return airportID;
	}

	public ArrayList<Airport> fetchDatabaseContent() { // mechanism for fetching content from database and returning as
														// ArrayList

		ArrayList<Airport> airports = new ArrayList<>();
		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(statementToDisplayDataOfAirports);
			airports.clear();
			while (rset.next()) {

				Airport airport = new Airport(rset.getString("airport_codename"), rset.getString("airport_fullname"),
						rset.getString("airport_type"), rset.getString("airport_city"),
						rset.getString("airport_country"));

				airports.add(airport);
			}

		}

		catch (Exception e) {

			System.out.println("Something went wrong");
			e.printStackTrace();
		}
		return airports;

	}

	public void updateDatabaseContent(String Airport_Codename, String Airport_Fullname, String Airport_Type,
			String Airport_City, String Airport_Country) {

		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToUpdateAirportsData);

			preparedStmt.setString(2, Airport_Fullname); // update Airport_Fullname column
			preparedStmt.setString(3, Airport_Type); // update Airport_Type column
			preparedStmt.setString(4, Airport_City); // update Airport_City column
			preparedStmt.setString(5, Airport_Country); // update Airport_Country
			preparedStmt.setString(6, Airport_Codename); // update Airport_Codename column
			preparedStmt.executeUpdate();

			conn.close();
			preparedStmt.close();
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteContentFromDatabase(String Airport_Codename) { // deleting from database content found using
																		// Airport_Codename as it is unique

		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToDeleteDataFromAirports);

			preparedStmt.setString(1, Airport_Codename);
			preparedStmt.executeUpdate();

			conn.close();
			preparedStmt.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getAirportIdFromAirport(Airport airport) {

		int airportID = 0;
		try {
			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToGetIdFromAirportData);

			preparedStmt.setString(1, airport.getAirportCodename());
			preparedStmt.setString(2, airport.getAirportFullname());
			preparedStmt.setString(3, airport.getAirportType());
			preparedStmt.setString(4, airport.getAirportCity());
			preparedStmt.setString(5, airport.getAirportCountry());
			preparedStmt.execute();

			ResultSet rset = preparedStmt.executeQuery();
			while (rset.next()) {
				airportID = rset.getInt("airport_id");
			}

			return airportID;

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return airportID;
	}
	
	public Airport getAirportFromAirportId(int airport_id) {

		Airport airport = null;
		try {
			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToDisplayAirportFromAirportId);

			preparedStmt.setInt(1, airport_id);
			preparedStmt.execute();

			ResultSet rset = preparedStmt.executeQuery();
			while (rset.next()) {
				airport = new Airport(rset.getString("airport_codename"), rset.getString("airport_fullname"),
						rset.getString("airport_type"), rset.getString("airport_city"),
						rset.getString("airport_country"));
				return airport;
			}
			
			

		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return airport;
		
	}
}
