package models;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table (name="flight_tickets")
public class FlightTicket {
	
	@Id
	@Column (name="ticket_ID")
	private int ticketId;
	@Column (name="flight_ID")
	private int flightId;
	@OneToOne
	@JoinColumn (name="airline", referencedColumnName="airline_id")
	private Airline airline;
	@OneToOne
	@JoinColumn (name="departure_airport", referencedColumnName="airport_id")
	private Airport airport;
	@OneToOne
	@JoinColumn (name="destination_airport", referencedColumnName="airport_id")
	private Airport destinationAirport;
	@Column (name="flight_class")
	private String flightClass;
	@Column (name="date_of_flight")
	private Calendar dateOfFlight;
	@Column (name="seat_row")
	private char seatRow;
	@Column (name="seat_number")
	private int seatNumber;
	@Column (name="flight_price")
	private double flightPrice;
	@Column (name="buyers_name")
	private String buyersName;
	


	public FlightTicket (int ticketId, int flightId, Airline airline, Airport airport, Airport destinationAirport, String flightClass, Calendar dateOfFlight, char seatRow, int seatNumber, double flightPrice, String buyersName) {
		
		this.ticketId = ticketId;
		this.flightId = flightId;
		this.airline = airline;
		this.airport = airport;
		this.destinationAirport = destinationAirport;
		this.flightClass = flightClass;
		this.dateOfFlight = dateOfFlight;
		this.seatRow = seatRow;
		this.seatNumber = seatNumber;
		this.flightPrice = flightPrice;
		this.buyersName = buyersName;
		
	}
	
	public FlightTicket () {
		
	}

	public int getTicketId() {
		return ticketId;
	}


	public void setTicketId(int ticketId) {
		this.ticketId = ticketId;
	}

	public int getFlightId() {
		return flightId;
	}


	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}


	public Airline getAirline() {
		return airline;
	}


	public void setAirline(Airline airline) {
		this.airline = airline;
	}


	public Airport getAirport() {
		return airport;
	}


	public void setAirport(Airport airport) {
		this.airport = airport;
	}


	public Airport getDestinationAirport() {
		return destinationAirport;
	}


	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}


	public String getFlightClass() {
		return flightClass;
	}


	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}


	public Calendar getDateOfFlight() {
		return dateOfFlight;
	}


	public void setDateOfFlight(Calendar dateOfFlight) {
		this.dateOfFlight = dateOfFlight;
	}


	public char getSeatRow() {
		return seatRow;
	}


	public void setSeatRow(char seatRow) {
		this.seatRow = seatRow;
	}


	public int getSeatNumber() {
		return seatNumber;
	}


	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}


	public double getFlightPrice() {
		return flightPrice;
	}


	public void setFlightPrice(double flightPrice) {
		this.flightPrice = flightPrice;
	}


	public String getBuyersName() {
		return buyersName;
	}


	public void setBuyersName(String buyersName) {
		this.buyersName = buyersName;
	}
	
	public Timestamp getDateOfFlightTicketInDateTime(Calendar dateOfFlight) {
		Timestamp timestamp = new Timestamp(dateOfFlight.getTimeInMillis());
		return timestamp;
	}
}
