
import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.AirlineManagementSystem;

@WebServlet("/AirlineRemoveServlet")
public class AirlineRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AirlineRemoveServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		try {
			if (session.getAttribute("user") != null) {
				removeAirline(request, response);
			}

			else {
				response.sendRedirect("form/login.html");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void removeAirline(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		String airlineCodename = request.getParameter("product_id");
		AirlineManagementSystem airlinems = new AirlineManagementSystem();

		airlinems.removeAirlineFromDatabase(airlinems.getAirlineFromCodename(airlineCodename));
		response.sendRedirect("list/airlineList.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
