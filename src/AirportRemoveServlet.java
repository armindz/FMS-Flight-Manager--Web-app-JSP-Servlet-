
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

import management.AirportManagementSystem;

@WebServlet("/AirportRemoveServlet")
public class AirportRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AirportRemoveServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			if (session.getAttribute("user") != null) {
				removeAirport(request, response);
			}

			else {
				response.sendRedirect("form/login.html");
			}
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void removeAirport(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String airportCodename = request.getParameter("product_id");
		AirportManagementSystem airportms = new AirportManagementSystem();

		airportms.removeAirportFromDatabase(airportms.getAirportFromCodename(airportCodename));
		response.sendRedirect("list/airportList.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
