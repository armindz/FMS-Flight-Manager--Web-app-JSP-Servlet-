package management;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import database.AirlineDatabase;
import models.Airline;

public class AirlineManagementSystem {
	

	private static ArrayList<Airline> listOfAirlines = new ArrayList<Airline>();
	AirlineDatabase airlinedb = new AirlineDatabase();
	final int AIRLINE_CODENAME_MAX_LENGTH = 6;
	
	
	public void createAirline(String airlineCodename, String airlineCallsign, String airlineCountry) {
		
		try {
		
		
			if (isAirlineDataUnique(airlineCodename) && isAirlineCodenameValid(airlineCodename)) {
				Airline airline = new Airline(airlinedb.generateAirlineId(), airlineCodename, airlineCallsign, airlineCountry);
				addAirlineToDatabase(airline);
				System.out.println("Airline successfully created!");
			} else {
				System.out.println("Data not unique or airline codename not valid.");
			}
			
		} catch (Exception e) {
			System.out.println("Something went wrong");
			e.printStackTrace();
		}
	}

	private boolean isAirlineDataUnique(String airlineCodename) {

		ArrayList <Airline> listOfAirlines = fetchDatabaseContentToList();
		
		for (int i = 0; i < listOfAirlines.size(); i++) {

			String airlineCodenameFromList = listOfAirlines.get(i).getAirlineCodename();
			if ((!listOfAirlines.isEmpty()) && (airlineCodenameFromList.equals(airlineCodename))) {
				System.out.println("Airline data not unique");
				return false;
			}
		}
		return true;
	}

	public Airline getAirlineFromCodename(String airlineCodename) {
		
		ArrayList <Airline> listOfAirlines = fetchDatabaseContentToList();
		
		for (int i = 0; i < listOfAirlines.size(); i++) {
			int airlineID = listOfAirlines.get(i).getAirlineId();
			String airlineCodenameFromList = listOfAirlines.get(i).getAirlineCodename();
			String airlineCallsignFromList = listOfAirlines.get(i).getAirlineCallsign();
			String airlineCountryFromList = listOfAirlines.get(i).getAirlineCountry();
		
			
			 if (airlineCodenameFromList.equals(airlineCodename) ) {
				 
				Airline airline = new Airline(airlineID, airlineCodenameFromList, airlineCallsignFromList, airlineCountryFromList);
				return airline;
			 }
						
			 }
			
			
			return null;
		

	}
	

	private boolean isAirlineCodenameValid(String airlineCodename) {

		if (airlineCodename.length() < AIRLINE_CODENAME_MAX_LENGTH
				&& airlineCodename.chars().allMatch(Character::isLetter)) {
			return true;
		}
		else {
			System.out.println("Airline codename not valid!");
			return false;
		}
		
	}

	public ArrayList<Airline> getListOfAirlines() {
		
		return fetchDatabaseContentToList();
	}

	public ArrayList <Airline> fetchDatabaseContentToList () {
		
		return (ArrayList<Airline>) airlinedb.fetchDatabaseContent();
	}
	private void addAirlineToDatabase(Airline airline) {
		try {
			airlinedb.storeToDatabase(airline);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void removeAirlineFromDatabase(Airline airline) {
		airlinedb.deleteContentFromDatabase(airline.getAirlineCodename());
	}
	
	public int getAirlineIdFromAirline (Airline airline) {
		return airlinedb.getAirlineIdFromAirline(airline);
	}
	
	public Airline getAirlineFromAirlineID (int airline_id) {
		return airlinedb.getAirlineFromAirlineId(airline_id);
	}
	
	
}
