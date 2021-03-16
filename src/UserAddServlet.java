

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import management.UserManagementSystem;


@WebServlet(name = "/UserAddServlet", urlPatterns = {"/user-add"})
public class UserAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserAddServlet() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		createUser(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void createUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		try {
			
		UserManagementSystem userms = new UserManagementSystem();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		userms.createUser(username, password);
		
		response.sendRedirect("settings.jsp");
	

		
		}
		
		catch (Exception e) {
			e.printStackTrace();
			 response.setContentType("text/html");  
			 PrintWriter out = response.getWriter();  
			 out.println("<html>");
			 out.println("<body>");
			 out.println("<p>Operation couldn't be completed. Seat may be reserved!</p>");
			 out.println("</body>");
			 out.println("</html>");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
