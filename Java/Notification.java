

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Notification
 */
@WebServlet("/Notification")
public class Notification extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ConnectingDatabase l = null;  
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Notification() {
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
		String settings = request.getParameter("set");

		Date date2 = new Date();   // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
		calendar.setTime(date2);   // assigns calendar to given date 


		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		if(hour >12 && hour<13){
			java.util.Date dt = new java.util.Date();

			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String currentTime = sdf.format(dt);
			out.println("900;Yemekhane;YemekYe;" +currentTime+ ";41.031815;29.259163");
		}
		if(user_id!=null && settings!=null){
			int set =Integer.parseInt(settings);
			if(set == 1){
				String sql = "select * from EVENT where activity_date > current_timestamp order by ACTIVITY_DATE LIMIT 1";
				ResultSet rs = l.executeCommand(sql);
				try {
					while(rs.next()){
						String eventId= rs.getString(1);
						String eventVenue = rs.getString(2);
						String eventName = rs.getString(3);
						String date= rs.getString(4);
						ResultSet rs2 = l.executeCommand("SELECT VENUE_LAT, VENUE_LON FROM VENUE WHERE VENUE_NAME = '" + eventVenue + "'");
						if(rs2.next()){
							double latitude = rs2.getDouble(1);
							double longitude = rs2.getDouble(2);
							out.println(eventId + ";" +eventVenue + ";"  +  eventName + ";" + date + ";" +latitude+ ";" +longitude);
						}

					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if (set==2){
				String latitude = request.getParameter("lat");
				String longitude = request.getParameter("lon");
				ResultSet rs = l.executeCommand("select * from (select event.event_id as id, event.event_name as name, event.venue as venue, event.activity_date as date, venue.venue_lat as lat, venue.venue_lon  as lon from event  inner join venue on event.venue = venue.venue_name where event.activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5) as tab " +
						"where sqrt(pow("+latitude+ " - tab.lat,2) + pow(" + longitude+ "- tab.lon,2)) = (select min(sqrt(pow("+latitude+ " - tab2.lat,2) + pow(" + longitude+ "- tab2.lon,2))) from (select event.event_id as id, venue.venue_lat as lat, venue.venue_lon  as lon from event  inner join venue on event.venue = venue.venue_name where event.activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5) as tab2)");

				try {
					while(rs.next()){
						String eventId= rs.getString(1);
						String eventName = rs.getString(2);
						String eventVenue = rs.getString(3);
						String date= rs.getString(4);
						String lat= rs.getString(5);
						String lon= rs.getString(6);

						out.println(eventId + ";" +eventVenue + ";"  +  eventName + ";" + date + ";" +lat+ ";" +lon); 
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if(set==3){
				ResultSet res = l.executeCommand("SELECT * FROM USER_INTEREST WHERE user_id = " +user_id +"" );


				try {

					String list = "(";
					while(res.next()){
						list+= res.getInt(1) + ",";

					}


					list = list.substring(0,list.length()-1);
					list += ")";
					ResultSet rs2 =l.executeCommand("SELECT * FROM EVENT_INTEREST WHERE INTEREST_ID in "+list);
					list = "(";
					while(rs2.next()){
						list+= rs2.getInt(2) + ",";

					}
					list = list.substring(0,list.length()-1);
					list += ")";
					rs2 = l.executeCommand("SELECT * FROM EVENT WHERE EVENT_ID in " +list + " and activity_date > current_timestamp and timestampdiff(hour,current_timestamp, activity_date)<=5");
					while(rs2.next()){

						String eventId= rs2.getString(1);
						String eventName = rs2.getString(3);
						String eventVenue = rs2.getString(2);
						String date= rs2.getString(4);
						ResultSet rs3 = l.executeCommand("SELECT VENUE_LAT, VENUE_LON FROM VENUE WHERE VENUE_NAME = '" + eventVenue + "'");
						if(rs3.next()){

							double latitude = rs3.getDouble(1);
							double longitude = rs3.getDouble(2);
							out.println(eventId + ";" +eventVenue + ";"  +  eventName + ";" + date + ";" +latitude+ ";" +longitude);
						}
					}

				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
