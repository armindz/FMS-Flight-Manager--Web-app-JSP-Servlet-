
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.AirlineDatabase;
import management.AirlineManagementSystem;
import models.Airline;

@WebServlet("/AirlinePreviewServlet")
public class AirlinePreviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AirlinePreviewServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("user") != null ) {
			
			airlineData(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("form/login.html");
			rd.forward(request, response);
		}
	}

	protected void airlineData(HttpServletRequest request, HttpServletResponse response) {

		AirlineManagementSystem airlinems = new AirlineManagementSystem();
		ArrayList<Airline> airlineDataList = airlinems.fetchDatabaseContentToList();
		AirlineDatabase airlinedb = new AirlineDatabase();
		try {

			for (int i = 0; i < airlineDataList.size(); i++) {

				if (airlineDataList.get(i).getAirlineCodename().equals(request.getParameter("product_id"))) {

					Airline airline = new Airline(airlineDataList.get(i).getAirlineId(),airlineDataList.get(i).getAirlineCodename(),
							airlineDataList.get(i).getAirlineCallsign(), airlineDataList.get(i).getAirlineCountry());
					request.setAttribute("airlineData", airline);

					RequestDispatcher rd = request.getRequestDispatcher("view/airlinePreview.jsp");
					rd.forward(request, response);

				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
