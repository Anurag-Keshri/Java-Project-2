import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDB {
	public static void main(String[] args) {
		// Set database connection parameters
		String url = "jdbc:sqlite:users.db";

		// Create new database file
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
			System.out.println("Connected to database!");
			Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT, password TEXT)";
			stmt.executeUpdate(sql);
			System.out.println("Table created!");
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
}
