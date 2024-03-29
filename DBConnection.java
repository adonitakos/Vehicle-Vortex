/* Project: Vehicle Vortex
*  Class: DBConnection.java
*  Author: Antonios Takos, Summer Snyder, Teuta Elezaj, Christian Felix, Tahir Buksh, Jayden Kuprel
*  Date: April 16th, 2023 
*  This program creates and returns a database connection.
*/

import java.sql.*;

public class DBConnection {
	private static Connection conn = null;
	private static String dbString = "jdbc:mysql://localhost:3306/vehicle_vortex";
	private static String userName = "root";
	private static String password = "password";// Change to personal system password
	public static String winflag = "?useSSL=false";

	/**
	 * initialize with the name of the database the rest of the connection
	 * information is hardcoded
	 * 
	 * @param dbn database name
	 * @throws ClassNotFoundException
	 */
	public static void init() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}

	/**
	 * return a database connection
	 * 
	 * @return
	 * @throws SQLException if the connection cannot be created
	 */
	public static Connection getMyConnection() throws SQLException {
		if (conn == null) {
			String fullDBString = dbString + winflag;
			conn = DriverManager.getConnection(fullDBString, userName, password);
		}
		return conn;
	} // <--- getMyConnection() method ends here

} // <--- DBConncetion{} class ends here