package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="airports")
public class Airport {
	
	@Column (name="airport_id")
	@Id
	private int airportID;
	@Column (name="airport_codename")
	private String airportCodename;
	@Column (name="airport_fullname")
	private String airportFullname;
	@Column (name="airport_type")
	private String airportType;
	@Column (name="airport_city")
	private String airportCity;
	@Column (name="airport_country")
	private String airportCountry;
	
	
	


	public Airport (int airportID, String airportCodename, String airportFullname, String airportType, String airportCity, String airportCountry) {
		
		this.airportID = airportID;
		this.airportCodename = airportCodename;
		this.airportFullname = airportFullname;
		this.airportType = airportType;
		this.airportCity = airportCity;
		this.airportCountry = airportCountry;
		
	}
	
	public Airport () {
		
	}
	
	public int getAirportID() {
		return airportID;
	}


	public void setAirportID(int airportID) {
		this.airportID = airportID;
	}

	public String getAirportCodename() {
		return airportCodename;
	}


	public void setAirportCodename(String airportCodename) {
		this.airportCodename = airportCodename;
	}


	public String getAirportFullname() {
		return airportFullname;
	}


	public void setAirportFullname(String airportFullname) {
		this.airportFullname = airportFullname;
	}


	public String getAirportType() {
		return airportType;
	}


	public void setAirportType(String airportType) {
		this.airportType = airportType;
	}


	public String getAirportCity() {
		return airportCity;
	}


	public void setAirportCity(String airportCity) {
		this.airportCity = airportCity;
	}


	public String getAirportCountry() {
		return airportCountry;
	}


	public void setAirportCountry(String airportCountry) {
		this.airportCountry = airportCountry;
	}


	@Override
	public String toString() {
		return "\n | Airport codename :  " + airportCodename + "\n AirportFullname :  " + airportFullname + "\n Airport type :  "
				+ airportType + "\n Airport city :  " + airportCity + "\n Airport country  :  " + airportCountry + "|\n";
	}
	
	
	
}
