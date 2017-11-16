

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
 * Servlet implementation class SıgnIn
 */
@WebServlet("/Signin")
public class Signin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectingDatabase l = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signin() {
        super();
        l= new ConnectingDatabase();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String mail = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(mail!=null && password!=null){
			
			 ResultSet res = l.executeCommand("SELECT * FROM USER WHERE mail = '" +mail +"' AND password = '" +password+"'");
			 String str = null;
			 try {
					if(res.next()){
						str =     "{Status: 0, "
									    + "UserId:"+res.getInt(1)+","
										+ "UserName:" +res.getString(2)+","
										+ "UserSurname:"+res.getString(3)+ ","
										+ "UserGender:"+res.getString(4)+ ","
										+ "UserAge:" +res.getInt(5)+ ","
										+ "UserMail:" +res.getString(6)+ "}";

					out.println(str);
				}
				else{
					str =     "{Status: 1}";
					out.println(str);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			 
	}	
	}

}
