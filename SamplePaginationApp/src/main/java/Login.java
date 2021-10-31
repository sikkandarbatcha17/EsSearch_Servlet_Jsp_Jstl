

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		if(username.equals("admin") && password.equals("admin"))
		{
			HttpSession session=request.getSession();
			session.setAttribute("username", username);
			response.sendRedirect("welcome.jsp");	
		}
		else {
			response.sendRedirect("Login.jsp");
		}
		
	}

	

}
