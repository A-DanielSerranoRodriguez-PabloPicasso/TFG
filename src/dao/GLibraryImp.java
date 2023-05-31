package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Library;
import utils.Utils;

public class GLibraryImp extends GGeneral implements GLibrary<Library> {

	private static GLibraryImp gestor;

	private GLibraryImp() {
		table = "library";
		try {
			conn = SQLiteDAO.getConn();
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static GLibraryImp getGestor() {
		if (gestor == null)
			gestor = new GLibraryImp();

		return gestor;
	}

	@Override
	public Library getById(final int id) {
		Library library = null;
		try {
			ResultSet rs = conn.createStatement().executeQuery("select * from " + table + " where id = '" + id + "'");
			if (rs.next())
				library = constructLibrary(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return library;
	}
	
	@Override
	public Library getByPath(final String path) {
		Library library = null;
		try {
			ResultSet rs = conn.createStatement().executeQuery("select * from " + table + " where path = '" + path + "'");
			if (rs.next())
				library = constructLibrary(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return library;
	}

	@Override
	public List<Library> getAll() {
		List<Library> result = new ArrayList<>();
		try {
			ResultSet rs = conn.createStatement()
					.executeQuery("select * from " + table + " where origin = '" + Utils.origin + "';");
			while (rs.next()) {
				result.add(constructLibrary(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Library> getChildless() {
		List<Library> result = new ArrayList<>();
		try {
			ResultSet rs = conn.createStatement().executeQuery(
					"select * from " + table + " where parent is 'null' and origin = '" + Utils.origin + "'");
			while (rs.next()) {
				result.add(constructLibrary(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	private Library constructLibrary(ResultSet rs) {
		try {
			return new Library(rs.getInt("id"), rs.getString("path"), rs.getString("name"), rs.getString("parent"),
					rs.getString("origin"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Library> getChildren(Library library) {
		List<Library> result = new ArrayList<>();
		try {
			ResultSet rs = conn.createStatement().executeQuery("select * from " + table + " where parent = '"
					+ library.getPath() + "' and origin = '" + Utils.origin + "'");
			while (rs.next())
				result.add(constructLibrary(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Library> getFromNameEverywhere(final String name) {
		List<Library> result = new ArrayList<>();
		try {
			ResultSet rs = conn.createStatement().executeQuery("select * from " + table + " where lower(name) like '%"
					+ name.toLowerCase() + "%' and origin = '" + Utils.origin + "'");
			while (rs.next())
				result.add(constructLibrary(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Library> getFromName(final String name) {
		List<Library> result = new ArrayList<>();
		try {
			ResultSet rs = conn.createStatement().executeQuery("select * from " + table + " where lower(name) like '%"
					+ name.toLowerCase() + "%' and parent is 'null' and origin = '" + Utils.origin + "'");
			while (rs.next())
				result.add(constructLibrary(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Library> getFromName(Library library, final String name) {
		List<Library> result = new ArrayList<>();
		try {
			ResultSet rs = conn.createStatement()
					.executeQuery("select * from " + table + " where parent = '" + library.getPath()
							+ "' lower(name) like '%" + name.toLowerCase() + "%' and parent is 'null' and origin = '"
							+ Utils.origin + "'");
			while (rs.next())
				result.add(constructLibrary(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean insert(final Library library) {
		try {
			return conn.createStatement()
					.execute("insert into " + table + "(path, name, parent, origin) values ('" + library.getPath()
							+ "', '" + library.getName() + "', '" + library.getParent() + "', '" + library.getOrigin()
							+ "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(final Library library) {
		try {
			return conn.createStatement()
					.execute("update " + table + " set path = '" + library.getPath() + "', name = '" + library.getName()
							+ "', parent = '" + library.getParent() + "' where id = " + library.getId() + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(final Library library) {
		try {
			return conn.createStatement().execute("delete from " + table + " where id = " + library.getId() + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
