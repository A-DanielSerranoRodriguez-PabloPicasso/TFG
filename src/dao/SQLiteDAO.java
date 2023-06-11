package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.Utils;

/**
 * Creates a connection to the database
 * 
 * @author Daniel Serrano Rodriguez
 */
public class SQLiteDAO {

	private static Connection conn;

	private static SQLiteDAO dao;

	/**
	 * Constructor
	 */
	private SQLiteDAO() {
		File file = new File(Utils.dbFilePath);

		if (file.exists()) {
			String url = "jdbc:sqlite://" + file.getAbsolutePath();

			try {
				conn = DriverManager.getConnection(url);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns the DAO object
	 * 
	 * @return SQLiteDAO
	 */
	public static SQLiteDAO getDao() {
		if (dao == null)
			dao = new SQLiteDAO();

		return dao;
	}

	/**
	 * Returns a connection to the database
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConn() throws SQLException {
		getDao();
		return conn;
	}

	/**
	 * Closes the connection
	 * 
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		conn.close();
	}
}
