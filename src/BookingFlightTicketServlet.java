import management.FlightManagementSystem;
import models.Flight;
import models.FlightTicket;
import models.Seat;
import booking.BookingFlightTicket;
import database.FlightTicketDatabase;
import database.SeatDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BookingFlightTicketServlet")
public class BookingFlightTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BookingFlightTicketServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {

			if (session.getAttribute("user") != null) {

				bookAFlight(request, response);
				requestDispatcher(request, response);
			} else {
				response.sendRedirect("form/login.html");
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void bookAFlight(HttpServletRequest request, HttpServletResponse response) {

		try {
			// convert request Strings to int and char
			SeatDatabase seatDb = new SeatDatabase();
			BookingFlightTicket bft = new BookingFlightTicket();
			FlightManagementSystem flightms = new FlightManagementSystem();
			
			Flight flight = flightms.getFlightFromFlightID(Integer.parseInt(request.getParameter("flightID")));
			char seatRow = request.getParameter("seatRow").toUpperCase().charAt(0);
			int seatNumber = Integer.valueOf(request.getParameter("seatNumber"));
			String buyersName = request.getParameter("name").toUpperCase();
			
			Seat seat = seatDb.getSeatFromSeatData(flight, seatRow, seatNumber);
			bft.bookAFlight(flight, seat, buyersName);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void requestDispatcher(HttpServletRequest request, HttpServletResponse response) {
		
		int flightID = Integer.parseInt(request.getParameter("flightID"));
		char seatRow = request.getParameter("seatRow").toUpperCase().charAt(0);
		int seatNumber = Integer.valueOf(request.getParameter("seatNumber"));
		String buyersName = request.getParameter("name").toUpperCase();
		
		FlightManagementSystem flightms = new FlightManagementSystem();
		ArrayList<Flight> flightDataList = flightms.fetchFlightDatabaseContentToList();
		FlightTicketDatabase flightTicketDb = new FlightTicketDatabase();
		

		try {

			for (int i = 0; i < flightDataList.size(); i++) {
				String flightIdFromList = String.valueOf(flightDataList.get(i).getFlightId()); // convert flightID (int
																								// to string) in order
																								// to check if it is
																								// equal

				// prevent user to book the same ticket twice by validating seat availability
				if (flightIdFromList.equals(request.getParameter("flightID")) /*&& bft.isSeatAvailable(flightID, seatRow, seatNumber)*/) {

					FlightTicket flightTicket = flightTicketDb.getFlightTicketFromTicketData(flightID, seatRow, seatNumber);
					request.setAttribute("flightTicketData", flightTicket);

					RequestDispatcher rd = request.getRequestDispatcher("view/flightTicket.jsp");
					rd.forward(request, response);
				}
				
			/*	// if user is found and seat is not available notify user about that
				else if (flightIdFromList.equals(request.getParameter("flightID")) && !bft.isSeatAvailable(flightID, seatRow, seatNumber)){
					 response.setContentType("text/html");  
					 PrintWriter out = response.getWriter();  
					 out.println("<html>");
					 out.println("<body>");
					 out.println("<p>Operation couldn't be completed. Seat may be reserved!</p>");
					 out.println("</body>");
					 out.println("</html>");
				}*/
				
				
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
