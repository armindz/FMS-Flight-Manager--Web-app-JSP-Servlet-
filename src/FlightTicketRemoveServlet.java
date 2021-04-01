
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import booking.BookingFlightTicket;
import database.SeatDatabase;
import management.FlightManagementSystem;
import models.Flight;
import models.FlightTicket;
import models.Seat;

@WebServlet("/FlightTicketRemoveServlet")
public class FlightTicketRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FlightTicketRemoveServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
	
		if (session.getAttribute("user") != null) {
			
			removeFlightTicket(request, response);
		} else {
			
			response.sendRedirect("form/login.html");
		}

	}

	protected void removeFlightTicket(HttpServletRequest request, HttpServletResponse response) {

		BookingFlightTicket bft = new BookingFlightTicket();
		SeatDatabase seatDb = new SeatDatabase();
		ArrayList<FlightTicket> flightTicketDataToList = bft.getListOfFlightTickets();
		FlightManagementSystem flightms = new FlightManagementSystem();
		try {
			Flight flight = flightms.getFlightFromFlightID(Integer.parseInt(request.getParameter("product_id")));
			char seatRow = request.getParameter("seatRow").charAt(0);
			int seatNumber = Integer.valueOf(request.getParameter("seatNumber"));
			Seat seat = seatDb.getSeatFromSeatData(flight, seatRow, seatNumber);
			for (int i = 0; i < flightTicketDataToList.size(); i++) {

				if (flightTicketDataToList.get(i).getFlight().getFlightId() == flight.getFlightId()
						&& flightTicketDataToList.get(i).getSeat().getSeatRow() == seatRow
						&& flightTicketDataToList.get(i).getSeat().getSeatNumber() == seatNumber) {

					bft.removeFlightTicketFromDatabase(flight, seat);
					response.sendRedirect("list/flightList.jsp");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
