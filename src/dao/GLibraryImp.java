package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GLibraryImp implements GLibrary<String> {

	private String table = "library";
	private Statement stmt;

	@Override
	public String getByPath(final String id) {
		String result = "";
		try {
			ResultSet rs = stmt.executeQuery("select path from " + table + " where path = '" + id + "'");
			rs.next();
			result = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<String> getAll() {
		List<String> result = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery("select path from " + table);
			while (rs.next())
				result.add(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean insert(final String path) {
		try {
			System.out.println("Insertando");
			return stmt.execute("insert into " + table + " values ('" + path + "')");
		} catch (SQLException e) {
			System.out.println("Algo salio mal");
			e.printStackTrace();
		}
		return false;
	}

}
