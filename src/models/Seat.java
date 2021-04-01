package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="seats")
public class Seat {
	
	@Id
	@Column(name="seat_id")
	private int seatId;
	@ManyToOne (targetEntity=Flight.class, cascade=CascadeType.REMOVE )
	@JoinColumn (name="flight_id", referencedColumnName="flight_ID")
	private Flight flight;
	@Column (name="seat_row")
	private char seatRow;
	@Column (name="seat_number")
	private int seatNumber;
	@Column (name="is_seat_available")
	private boolean isSeatAvailable = true;


public Seat (int seatId, Flight flight, char seatRow, int seatNumber, boolean isSeatAvailable) {
	
	this.seatId = seatId;
	this.flight = flight;
	this.seatRow = seatRow;
	this.seatNumber = seatNumber;
	this.isSeatAvailable = isSeatAvailable;
}

public Seat () {
	
}
public int getSeatId() {
	return seatId;
}


public void setSeatId(int seatId) {
	this.seatId = seatId;
}


public Flight getFlight() {
	return flight;
}

public void setFlight(Flight flight) {
	this.flight = flight;
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


public boolean isSeatAvailable() {
	return isSeatAvailable;
}


public void setSeatAvailable(boolean isSeatAvailable) {
	this.isSeatAvailable = isSeatAvailable;
}


@Override
public String toString() {
	return "Seat [seatId=" + seatId + ", flight=" + flight.getFlightId() + ", seatRow=" + seatRow + ", seatNumber=" + seatNumber
			+ ", isSeatAvailable=" + isSeatAvailable + "]";
}


}