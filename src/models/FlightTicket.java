package models;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table (name="flight_tickets")
public class FlightTicket {
	
	@Id
	@Column (name="ticket_ID")
	private int ticketId;
	@ManyToOne
		@JoinColumn(name="flight_ID", referencedColumnName="flight_ID")
	private Flight flight;
	@OneToOne
		@JoinColumn(name="seat_id", referencedColumnName="seat_id")
	private Seat seat;
	@Column (name="buyers_name")
	private String buyersName;
	


	public FlightTicket (int ticketId, Flight flight, Seat seat, String buyersName) {
		
		this.ticketId = ticketId;
		this.flight = flight;
		this.seat = seat;
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

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
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
