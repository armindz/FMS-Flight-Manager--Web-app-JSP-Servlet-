

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.FlightTicketDatabase;
import models.FlightTicket;

@WebServlet("/FlightTicketPreviewServlet")
public class FlightTicketPreviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public FlightTicketPreviewServlet() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user") != null) {
			previewTicket(request,response);
		}
		
		else {
			response.sendRedirect("form/login.html");
		}
	}
	
	
	private void previewTicket (HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
		FlightTicketDatabase flightTicketDb = new FlightTicketDatabase();
		int ticketId = Integer.parseInt(request.getParameter("product_id"));
		FlightTicket ticket = flightTicketDb.getFlightTicketFromTicketId(ticketId);
		
		request.setAttribute("flightTicketData", ticket);
		RequestDispatcher rd = request.getRequestDispatcher("view/flightTicket.jsp");
		rd.forward(request, response);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
