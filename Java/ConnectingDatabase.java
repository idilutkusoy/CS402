import java.sql.*;
import java.util.Scanner;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class ConnectingDatabase {
	

	static final String database_url = "jdbc:mysql://localhost:3306/Ozyegin";
	static final String user_name = "root";
	static final String user_password = "123456";
	Connection conn;
	Statement statement;
	Scanner scanner = new Scanner(System.in);

	public ConnectingDatabase() {
		
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			conn = (Connection) DriverManager.getConnection(database_url, user_name, user_password);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public ResultSet executeCommand(String command){
		
		try {
			statement = (Statement) conn.createStatement();			
			statement.execute(command);
			return (ResultSet) statement.getResultSet();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
}