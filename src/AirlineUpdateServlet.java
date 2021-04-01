
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
import models.Airline;

@WebServlet("/AirlineUpdateServlet")
public class AirlineUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AirlineUpdateServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			if (session.getAttribute("user") != null) {
				updateAirline(request, response);
			}

			else {
			response.sendRedirect("form/login.html");
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateAirline(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		AirlineDatabase airlinedb = new AirlineDatabase();
		String airlineCodename = request.getParameter("airlineCodename");
		String airlineCallsign = request.getParameter("airlineCallsign");
		String airlineCountry = request.getParameter("airlineCountry");
		int airlineId = airlinedb.getAirlineIdFromAirline(airlinedb.getAirlineFromAirlineCodename(airlineCodename));
		
		Airline airline = new Airline(airlineId,airlineCodename,airlineCallsign,airlineCountry);
		
		airlinedb.updateDatabaseContent(airline);
		response.sendRedirect("list/airlineList.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
