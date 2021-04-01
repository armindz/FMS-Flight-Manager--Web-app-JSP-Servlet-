
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.AirportManagementSystem;

import models.Airport;

@WebServlet("/AirportPreviewServlet")
public class AirportPreviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AirportPreviewServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
	
				if (session.getAttribute("user") != null ) {
			airportData(request, response);
		} else {
			response.sendRedirect("form/login.html");
			
		 }

	}

	protected void airportData(HttpServletRequest request, HttpServletResponse response) {

		AirportManagementSystem airportms = new AirportManagementSystem();
		ArrayList<Airport> airportDataList = (ArrayList <Airport>) airportms.fetchDatabaseContentToList();

		try {

			for (int i = 0; i < airportDataList.size(); i++) {

				if (airportDataList.get(i).getAirportCodename().equals(request.getParameter("product_id"))) {

					Airport airport = new Airport(airportDataList.get(i).getAirportID(), airportDataList.get(i).getAirportCodename(),
							airportDataList.get(i).getAirportFullname(), airportDataList.get(i).getAirportType(),
							airportDataList.get(i).getAirportCity(), airportDataList.get(i).getAirportCountry());
					request.setAttribute("airportData", airport);

					RequestDispatcher rd = request.getRequestDispatcher("view/airportPreview.jsp");
					rd.forward(request, response);

				}
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
