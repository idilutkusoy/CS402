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
 * Servlet implementation class UserIdInterest
 */
@WebServlet("/UserIdInterest")
public class UserIdInterest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectingDatabase l = null;   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserIdInterest() {
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
		String user_id = request.getParameter("usr");
		String str = null;
        //out.println("SELECT * FROM USER_INTEREST WHERE user_id = " +user_id +"");
		
		if(user_id!=null){
				 ResultSet res = l.executeCommand("select I.*, U.INTEREST_RATE from INTEREST I left outer join USER_INTEREST U on U.USER_ID = "+user_id+" and I.INTEREST_ID = U.INTEREST_ID" );
				 try {
					 str = "{\"Status\" : 0 , \"interests\" : [";
					while(res.next()){
						str += "{\"id\" : " + res.getInt("INTEREST_ID") + ", \"name\" : \"" + res.getString("INTEREST_NAME") + "\", \"rate\" : " + res.getInt("INTEREST_RATE") + "}, ";
					 }
					str = str.substring(0, str.length()-2) + "]}";
				} catch (SQLException e) {
					str = "{Status: 1 }";
					e.printStackTrace();
				}		
		}else{
			str = "{Status: 1 }";
		}
		out.print(str);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}










