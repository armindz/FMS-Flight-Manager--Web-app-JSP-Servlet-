
import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.AirportManagementSystem;

@WebServlet("/AirportAddServlet")
public class AirportAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AirportAddServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			if (session.getAttribute("user") != null) {
				createAirport(request, response);
			}

			else {
				response.sendRedirect("form/login.html");
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createAirport(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String airportCodename = request.getParameter("airportCodename").toUpperCase();
		String airportFullname = request.getParameter("airportFullname").toUpperCase();
		String airportType = request.getParameter("airportType").toUpperCase();
		String airportCity = request.getParameter("airportCity").toUpperCase();
		String airportCountry = request.getParameter("airportCountry").toUpperCase();
		AirportManagementSystem airportms = new AirportManagementSystem();

		airportms.createAirport(airportCodename, airportFullname, airportType, airportCity, airportCountry);
		response.sendRedirect("list/airportList.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
