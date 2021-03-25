
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Flight;
import management.FlightManagementSystem;

@WebServlet("/BookAFlight")
public class BookAFlight extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookAFlight() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 HttpSession session = request.getSession(false);
		if (true) {
			flightData(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("login.html");
			rd.forward(request, response);
		}

	}

	private void flightData(HttpServletRequest request, HttpServletResponse response) {

		FlightManagementSystem flightms = new FlightManagementSystem();
		ArrayList<Flight> flightDataList = flightms.fetchFlightDatabaseContentToList();

		try {

			for (int i = 0; i < flightDataList.size(); i++) {
				String flightID = String.valueOf(flightDataList.get(i).getFlightId()); // convert flightID (int to
																						// string) in order to check if
																						// it is equal
				// if( flightID.equals(request.getParameter("flightID"))) {
				if (flightID.equals(request.getParameter("product_id"))) {

					Flight flight = new Flight(flightDataList.get(i).getFlightId(), flightDataList.get(i).getAirline(),
							flightDataList.get(i).getAirport(), flightDataList.get(i).getDestinationAirport(),
							flightDataList.get(i).getFlightClass(), flightDataList.get(i).getDateOfFlight(),
							flightDataList.get(i).getSeatRow(), flightDataList.get(i).getSeatNumber(),
							flightDataList.get(i).getFlightPrice());
					request.setAttribute("flightData", flight);

					RequestDispatcher rd = request.getRequestDispatcher("bookAFlight.jsp");
					rd.forward(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
