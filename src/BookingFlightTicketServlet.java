import management.FlightManagementSystem;
import models.Flight;
import models.FlightTicket;
import booking.BookingFlightTicket;
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

			if (true) {

				bookAFlight(request, response);
				requestDispatcher(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.forward(request, response);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void bookAFlight(HttpServletRequest request, HttpServletResponse response) {

		try {
			// convert request Strings to int and char

			int flightID = Integer.parseInt(request.getParameter("flightID"));
			char seatRow = request.getParameter("seatRow").charAt(0);
			int seatNumber = Integer.valueOf(request.getParameter("seatNumber"));
			String buyersName = request.getParameter("name");

			BookingFlightTicket bft = new BookingFlightTicket();

			bft.bookAFlight(flightID, seatRow, seatNumber, buyersName);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void requestDispatcher(HttpServletRequest request, HttpServletResponse response) {
		
		int flightID = Integer.parseInt(request.getParameter("flightID"));
		char seatRow = request.getParameter("seatRow").charAt(0);
		int seatNumber = Integer.valueOf(request.getParameter("seatNumber"));
		String buyersName = request.getParameter("name");
		
		FlightManagementSystem flightms = new FlightManagementSystem();
		BookingFlightTicket bft = new BookingFlightTicket();
		ArrayList<Flight> flightDataList = flightms.fetchFlightDatabaseContentToList();
		
		

		try {

			for (int i = 0; i < flightDataList.size(); i++) {
				String flightIdFromList = String.valueOf(flightDataList.get(i).getFlightId()); // convert flightID (int
																								// to string) in order
																								// to check if it is
																								// equal

				// prevent user to book the same ticket twice by validating seat availability
				if (flightIdFromList.equals(request.getParameter("flightID")) /*&& bft.isSeatAvailable(flightID, seatRow, seatNumber)*/) {

					FlightTicket flightTicket = new FlightTicket(flightID, flightDataList.get(i).getAirline(),
							flightDataList.get(i).getAirport(), flightDataList.get(i).getDestinationAirport(),
							flightDataList.get(i).getFlightClass(), flightDataList.get(i).getDateOfFlight(), seatRow,
							seatNumber, flightDataList.get(i).getFlightPrice(), buyersName);
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
