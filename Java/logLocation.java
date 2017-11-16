import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class logLocation
 */
@WebServlet("/logLocation")
public class logLocation extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ConnectingDatabase l = null;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logLocation() {
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
		 String user_id = request.getParameter("usr");
		 if(user_id!=null){
			 String latitude = request.getParameter("lat");
			 String longitude = request.getParameter("lon");
			 if(latitude!=null && longitude!=null){
				 java.util.Date dt = new java.util.Date();

				 java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				 String currentTime = sdf.format(dt);
				 l.executeCommand("insert into location_log (user_id, tme, latitude, longitude) values ('"+user_id+"', '"+currentTime+"' ,'"+latitude+"', '"+longitude+"')");
				 
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
