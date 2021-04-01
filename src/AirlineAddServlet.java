
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AirlineDatabase;
import management.AirlineManagementSystem;

@WebServlet("/AirlineAddServlet")
public class AirlineAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AirlineAddServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		try {
			if (session.getAttribute("user") != null ) {
				createAirline(request, response);
				
			}

			else {
				response.sendRedirect("form/login.html");
				}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createAirline(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String airlineCodename = request.getParameter("airlineCodename").toUpperCase();
		String airlineCallsign = request.getParameter("airlineCallsign").toUpperCase();
		String airlineCountry = request.getParameter("airlineCountry").toUpperCase();
		AirlineManagementSystem airlinems = new AirlineManagementSystem();

		airlinems.createAirline(airlineCodename, airlineCallsign, airlineCountry);
		response.sendRedirect("list/airlineList.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
