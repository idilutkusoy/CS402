

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UsernameUserId
 */
@WebServlet("/UsernameUserId")
public class UsernameUserId extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectingDatabase l = null;   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsernameUserId() {
        super();
        l = new ConnectingDatabase();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html");
		 
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");

        
		
		if(name!=null && surname!=null){
	
				 ResultSet res = l.executeCommand("SELECT * FROM USER WHERE name = '" +name +"' AND surname = '" +surname+"'");

			       
				 try {
					
					 
						 	res.next();
					        out.println(res.getInt(1));
					       
						 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}	
				
		}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
