package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDAO {

	private Connection conn;

	public SQLiteDAO() throws SQLException {
		File file = new File("/db/db.db");
		System.out.println(file.exists());
		if (file.exists()) {
			String url = "jdbc:sqlite://" + file.getAbsolutePath();

			conn = DriverManager.getConnection(url);
		}
	}

	public Connection getConn() throws SQLException {
		return conn;
	}

	public void closeConn() throws SQLException {
		conn.close();
	}
}
