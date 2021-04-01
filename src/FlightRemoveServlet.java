
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.FlightManagementSystem;

/**
 * Servlet implementation class FlightRemoveServlet
 */
@WebServlet("/FlightRemoveServlet")
public class FlightRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FlightRemoveServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			if (session.getAttribute("user") != null) {
				removeFlight(request, response);
			}

			else {
				response.sendRedirect("form/login.html");

			}
		} catch (SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}

	}

	private void removeFlight(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ParseException {

		FlightManagementSystem flightms = new FlightManagementSystem();

		int flightID = Integer.parseInt(request.getParameter("product_id"));

		flightms.removeFlightFromDatabase(flightID);
		response.sendRedirect("list/flightList.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
