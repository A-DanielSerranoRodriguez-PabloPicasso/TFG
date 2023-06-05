package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GOriginImp extends GGeneral implements GOrigin {

	private static GOriginImp gestor;

	private GOriginImp() {
		table = "origin";
		try {
			conn = SQLiteDAO.getConn();
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static GOriginImp gestor() {
		if (gestor == null)
			gestor = new GOriginImp();

		return gestor;
	}

	@Override
	public String getOrigin() {
		String result = null;
		try {
			ResultSet rs = conn.createStatement().executeQuery("select * from " + table + ";");
			if (rs.next())
				result = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public boolean insert(String path) {
		try {
			return conn.createStatement().execute("insert into " + table + " values ('" + path + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void nuke() {
		try {
			conn.createStatement().execute("delete from " + table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
