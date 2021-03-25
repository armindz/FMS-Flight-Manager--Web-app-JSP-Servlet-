package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import database.FlightDatabase;

@Entity
@Table(name = "flights")
public class Flight {
	@Transient
	private ArrayList<Seat> listOfSeats = new ArrayList<Seat>();

	@Id
	@Column(name = "flight_ID")
	private int flightId;

	@OneToOne
	@JoinColumn(name = "airline", referencedColumnName = "airline_id")
	private Airline airline;

	@OneToOne
	@JoinColumn(name = "departure_airport", referencedColumnName = "airport_id")
	private Airport airport;

	@OneToOne
	@JoinColumn(name = "destination_airport", referencedColumnName = "airport_id")
	private Airport destinationAirport;

	@Column(name = "flight_class")
	private String flightClass;
	@Column(name = "date_of_flight")
	private Calendar dateOfFlight;
	@Column(name = "seat_row")
	private char seatRow;
	@Column(name = "seat_number")
	private int seatNumber;
	@Column(name = "flight_price")
	private double flightPrice;

	public Flight(int flightId, Airline airline, Airport airport, Airport destinationAirport, String flightClass,
			Calendar dateOfFlight, char seatRow, int seatNumber, double flightPrice) {

		this.airline = airline;
		this.airport = airport;
		this.destinationAirport = destinationAirport;
		this.flightClass = flightClass;
		this.dateOfFlight = dateOfFlight;
		this.seatRow = seatRow;
		this.seatNumber = seatNumber;
		this.flightPrice = flightPrice;
		this.flightId = flightId;
	}

	public Flight() {

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

	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
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

	public Timestamp getDateOfFlightInDateTime(Calendar dateOfFlight) {
		Timestamp timestamp = new Timestamp(dateOfFlight.getTimeInMillis());
		return timestamp;
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

	public ArrayList<Seat> getListOfSeats() {
		return listOfSeats;
	}

	public void addToListOfSeats(Seat seat) {

		listOfSeats.add(seat);
	}

	@Override
	public String toString() {
		return "\n\n\n" + "\n Flight ID: " + flightId + " |\n Airline  :  " + airline + "\n Airport  :  " + airport
				+ "\n Destination airport  :  " + destinationAirport + "\n Flight class  :  " + flightClass
				+ "\n Date of flight  :  " + dateOfFlight.getTime() + "\n Maximum seat row  :  " + seatRow
				+ "\n Number of seats per row :  " + seatNumber + "\n Flight price  :  " + flightPrice
				+ "KM|\n\n\n --------------------------------- \n";
	}

	public void setListOfSeats(ArrayList<Seat> listOfSeats) {
		this.listOfSeats = listOfSeats;
	}

}
