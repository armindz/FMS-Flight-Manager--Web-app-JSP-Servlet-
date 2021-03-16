
package database;

import java.sql.*;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import models.Airline;
import models.Airport;

public class AirlineDatabase {

	private static String statementToStoreDataIntoAirlines = "INSERT INTO airlines"
			+ "(airline_id, airline_codename, airline_callsign, airline_country) values " + " (?,?,?,?);";
	private static String statementToDisplayDataOfAirlines = "SELECT * FROM airlines";
	private static String statementToUpdateAirlinesData = "UPDATE airlines set airline_callsign= ?, airline_country = ? where  airline_codename= ? ";
	private static String statementToDeleteDataFromAirlines = "DELETE from airlines where airline_codename=?";
	private static String statementToGetIdFromAirlineData = "SELECT airline_id FROM airlines where airline_codename= ? and airline_callsign= ? and airline_country= ?";
	private static String statementToDisplayAirlineFromAirlineId = "SELECT * FROM airlines WHERE airline_id=?";
	
	public void storeToDatabase(Airline airline) throws SQLException {

		try {
			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToStoreDataIntoAirlines);
			
			preparedStmt.setInt(1, generateAirlineId()); // airline_id column;
			preparedStmt.setString(2, airline.getAirlineCodename()); // airline_codename Column
			preparedStmt.setString(3, airline.getAirlineCallsign()); // airline_callsign Column
			preparedStmt.setString(4, airline.getAirlineCountry()); // airline_country Column
			preparedStmt.execute();

			conn.close();
			//preparedStmt.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	public static int generateAirlineId() { // mechanism for generating airline ID based on last stored ID in database
		
		int airlineID = 0;
		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(statementToDisplayDataOfAirlines);
			

			while (rs.next()) {

				if (rs.isLast()) {
					airlineID = rs.getInt(1);
					airlineID++;
				}
			}
			
			return airlineID;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return airlineID;
	}
	public ArrayList<Airline> fetchDatabaseContent() { // mechanism for fetching content from database and returning as
														// ArrayList

		ArrayList<Airline> airlines = new ArrayList<>();
		try {
			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(statementToDisplayDataOfAirlines);
			airlines.clear();
			while (rset.next()) {
				Airline airline = new Airline(rset.getString("airline_codename"), rset.getString("airline_callsign"),
						rset.getString("airline_country"));

				airlines.add(airline);
			}
			
		}

		catch (Exception e) {

			e.printStackTrace();
		}
		return airlines;
	}

	public void updateDatabaseContent(String Airline_Codename, String Airline_Callsign, String Airline_Country) {

		try {

			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToUpdateAirlinesData);

			preparedStmt.setString(1, Airline_Callsign); // update Airline_Callsign column
			preparedStmt.setString(2, Airline_Country); // update Airline_Country column
			preparedStmt.setString(3, Airline_Codename);
			preparedStmt.executeUpdate();

			conn.close();
			preparedStmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void deleteContentFromDatabase(String Airline_Codename) {

		try {
			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToDeleteDataFromAirlines);
			preparedStmt.setString(1, Airline_Codename);
			preparedStmt.executeUpdate();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int getAirlineIdFromAirline(Airline airline) {
		
		int airlineID = 0;
		try {
			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToGetIdFromAirlineData);
			
			
			preparedStmt.setString(1, airline.getAirlineCodename());
			preparedStmt.setString(2, airline.getAirlineCallsign());
			preparedStmt.setString(3, airline.getAirlineCountry());
			preparedStmt.execute();
			ResultSet rset = preparedStmt.executeQuery();
			while (rset.next()) {
			airlineID = rset.getInt("airline_id");
			}
			
			return airlineID;
			
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return airlineID;
	}
	
	public Airline getAirlineFromAirlineId(int airline_id) {

		Airline airline = null;
		try {
			DatabaseConnection dbConnection = DatabaseConnection.getInstance();
			Connection conn = dbConnection.getConnection();
			PreparedStatement preparedStmt = conn.prepareStatement(statementToDisplayAirlineFromAirlineId);

			preparedStmt.setInt(1, airline_id);
			preparedStmt.execute();

			ResultSet rset = preparedStmt.executeQuery();
			while (rset.next()) {
				 airline = new Airline(rset.getString("airline_codename"), rset.getString("airline_callsign"),
						rset.getString("airline_country"));

				return airline;
			}
			
			

		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return airline;
		
	}
}
