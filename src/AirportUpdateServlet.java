
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

import database.AirportDatabase;

@WebServlet("/AirportUpdateServlet")
public class AirportUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AirportUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			if (true) {
				updateAirport(request, response);
			}

			else {
				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateAirport(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		AirportDatabase airportdb = new AirportDatabase();
		
		String airportCodename = request.getParameter("airportCodename");
		String airportFullname = request.getParameter("airportFullname");
		String airportType = request.getParameter("airportType");
		String airportCity = request.getParameter("airportCity");
		String airportCountry = request.getParameter("airportCountry");
		int airportId = airportdb.getAirportIdFromAirport(airportdb.getAirportFromAirportCodename(airportCodename));

		airportdb.updateDatabaseContent(airportId, airportCodename, airportFullname, airportType, airportCity, airportCountry);

		response.sendRedirect("list/airlineList.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
