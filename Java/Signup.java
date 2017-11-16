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
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectingDatabase l = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
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
		String name= request.getParameter("name");
		String surname=request.getParameter("surname");
		String gender=request.getParameter("gender");
		int age= Integer.parseInt(request.getParameter("age"));
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String faculty= request.getParameter("faculty");
		int studentclass= Integer.parseInt(request.getParameter("studentclass"));
		
		ResultSet res = l.executeCommand("SELECT * FROM USER WHERE mail = '" +mail+"'");
		String str = null;
		try {
			if(res.next()){
				str =     "{Status: 1}";
				out.println(str);
			}else{
				l.executeCommand("INSERT INTO USER (name, surname, gender, age, mail, password, faculty, class) VALUES ('" +name + "' , '" +surname + "' ,'" +gender + "' , '" +age + "' , '" +mail +"' , '" +password+"' , '" +faculty + "' , '" +studentclass + "')");
				 
				 res = l.executeCommand("SELECT * FROM USER WHERE mail = '" +mail +"' AND password = '" +password+"'");
				 
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
				
			}
		} catch (SQLException e) {
			str =     "{Status: 1}";
			out.println(str);
		}
			 
	}

}
