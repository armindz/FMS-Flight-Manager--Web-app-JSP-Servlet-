package management;

import java.util.ArrayList;

import database.AirportDatabase;
import models.Airport;

public class AirportManagementSystem {

	AirportDatabase airportdb = new AirportDatabase();
	private final int AIRPORT_CODENAME_LENGTH = 3;

	public void createAirport(String airportCodename, String airportFullname, String airportType, String airportCity,
			String airportCountry) {

		try {

			if (isAirportDataUnique(airportCodename) && isAirportCodenameValid(airportCodename)) {
				Airport airport = new Airport(airportCodename, airportFullname, airportType, airportCity,
						airportCountry);
				addAirportToDatabase(airport);
				System.out.println("Airport successfully created!");
			} else {
				System.out.println("Data not unique or airport codename not valid");
			}

		} catch (Exception e) {
			System.out.println("Something went wrong!");
			e.printStackTrace();
		}

	}

	public Airport getAirportFromCodename(String airportCodename) {

		ArrayList<Airport> listOfAirports = fetchDatabaseContentToList();

		for (int i = 0; i < listOfAirports.size(); i++) {
			String airportCodenameFromList = listOfAirports.get(i).getAirportCodename();
			String airportFullnameFromList = listOfAirports.get(i).getAirportFullname();
			String airportTypeFromList = listOfAirports.get(i).getAirportType();
			String airportCityFromList = listOfAirports.get(i).getAirportCity();
			String airportCountryFromList = listOfAirports.get(i).getAirportCountry();

			if (airportCodenameFromList.equals(airportCodename)) {
				Airport airport = new Airport(airportCodenameFromList, airportFullnameFromList, airportTypeFromList,
						airportCityFromList, airportCountryFromList);
				return airport;

			}
		}

		return null;

	}

	private boolean isAirportDataUnique(String airportCodename) {

		ArrayList<Airport> listOfAirports = fetchDatabaseContentToList();

		for (int i = 0; i < listOfAirports.size(); i++) {

			String airportCodenameFromList = listOfAirports.get(i).getAirportCodename();
			if ((!listOfAirports.isEmpty()) && (airportCodenameFromList.equals(airportCodename))) {

				return false;
			}
		}
		return true;
	}

	private boolean isAirportCodenameValid(String airportCodename) {

		if (airportCodename.length() == AIRPORT_CODENAME_LENGTH
				&& airportCodename.chars().allMatch(Character::isLetter)) {
			return true;
		}
		return false;
	}

	public ArrayList<Airport> getListOfAirports() {

		return fetchDatabaseContentToList();
	}

	public void removeAirportFromArrayList(String airportCodename) {

		ArrayList<Airport> listOfAirports = fetchDatabaseContentToList();

		for (int i = 0; i < listOfAirports.size(); i++) {
			String airlineCodenameFromList = listOfAirports.get(i).getAirportCodename();

			if (airlineCodenameFromList.equals(airportCodename)) {
				listOfAirports.remove(i);
			}
		}

	}

	public ArrayList<Airport> fetchDatabaseContentToList() {

		return airportdb.fetchDatabaseContent();
	}

	private void addAirportToDatabase(Airport airport) {
		airportdb.storeToDatabase(airport);

	}

	public void removeAirportFromDatabase(Airport airport) {
		airportdb.deleteContentFromDatabase(airport.getAirportCodename());
	}
	
	public int getAirportIdFromAirport(Airport airport) {
		return airportdb.getAirportIdFromAirport(airport);
	}
	
	public Airport getAirportFromAirportId(int airport_id) {
		return airportdb.getAirportFromAirportId(airport_id);
	}
}
