package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="seats")
public class Seat {
	
	@Column(name="seat_id")
	@Id
	private int seatId;
	@Column (name="flight_id")
	private int flightId;
	@Column (name="seat_row")
	private char seatRow;
	@Column (name="seat_number")
	private int seatNumber;
	@Column (name="is_seat_available")
	private boolean isSeatAvailable = true;


public Seat (int flightId, char seatRow, int seatNumber, boolean isSeatAvailable) {
	
	this.flightId = flightId;
	this.seatRow = seatRow;
	this.seatNumber = seatNumber;
	this.isSeatAvailable = isSeatAvailable;
}

public int getSeatId() {
	return seatId;
}


public void setSeatId(int seatId) {
	this.seatId = seatId;
}
public int getFlightId() {
	return flightId;
}


public void setFlightId(int flightId) {
	this.flightId = flightId;
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
	return "|Row   :  " + seatRow + "\t| Number  :  " + seatNumber + "\t| " + isSeatAvailable + "|\n";
}


}