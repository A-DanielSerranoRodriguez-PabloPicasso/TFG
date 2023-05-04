package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Library;

public class GLibraryImp extends GGeneral implements GLibrary<Library> {

	private static GLibraryImp gestor;

	private SQLiteDAO sqlDao;

	private GLibraryImp() {
		table = "library";
		sqlDao = new SQLiteDAO();
		try {
			conn = sqlDao.getConn();
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static GLibraryImp gestor() {
		if (gestor == null)
			gestor = new GLibraryImp();

		return gestor;
	}

	@Override
	public Library getByPath(final String id) {
		Library library = null;
		try {
			ResultSet rs = stmt.executeQuery("select path from " + table + " where path = '" + id + "'");
			if (rs.next())
				library = new Library(rs.getString(1), rs.getString(2), null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return library;
	}

	@Override
	public List<Library> getAll() {
		List<Library> result = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery("select path from " + table);
			while (rs.next())
				result.add(new Library(rs.getString(1), rs.getString(2), null));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean insert(final Library library) {
		try {
			return stmt.execute("insert into " + table + " values ('" + library.getPath() + "', '" + library.getName()
					+ "', '" + library.getCategory() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(final Library library) {
		try {
			return stmt
					.execute("update " + table + " set path = '" + library.getPath() + "', name = '" + library.getName()
							+ "', category = '" + library.getCategory() + "' where path = '" + library.getPath() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(final Library library) {
		try {
			return stmt.execute("delete from " + table + " where path = '" + library.getPath() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
