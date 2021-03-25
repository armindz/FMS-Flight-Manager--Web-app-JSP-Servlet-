package models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table (name="airlines")
public class Airline {
	
	@Id
	@Column (name="airline_id")
	private int airlineId;
	@Column (name="airline_codename")
	private String airlineCodename;
	@Column (name="airline_callsign")
	private String airlineCallsign;
	@Column (name="airline_country")
	private String airlineCountry;
	



	public Airline (int airlineId, String airlineCodename, String airlineCallsign, String airlineCountry) {
		
		this.airlineId = airlineId;
		this.airlineCodename = airlineCodename;
		this.airlineCallsign = airlineCallsign;
		this.airlineCountry = airlineCountry;
	}
	public Airline() {
		
	}
	
	
	public int getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(int airlineId) {
		this.airlineId = airlineId;
	}
	public String getAirlineCodename() {
		return airlineCodename;
	}


	public void setAirlineCodename(String airlineCodename) {
		this.airlineCodename = airlineCodename;
	}


	public String getAirlineCallsign() {
		return airlineCallsign;
	}


	public void setAirlineCallsign(String airlineCallsign) {
		this.airlineCallsign = airlineCallsign;
	}


	public String getAirlineCountry() {
		return airlineCountry;
	}


	public void setAirlineCountry(String airlineCountry) {
		this.airlineCountry = airlineCountry;
	}


	@Override
	public String toString() {
		return "\n|Airline codename :  " + airlineCodename + "\n Airline callsign :  " + airlineCallsign
				+ "\n Airline country :  " + airlineCountry + "|\n";
	}
	
	
}
