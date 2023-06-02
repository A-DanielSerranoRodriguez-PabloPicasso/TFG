package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Library;
import models.Video;
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
			ResultSet rs = conn.createStatement()
					.executeQuery("select * from " + table + " where path = '" + path + "'");
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
	public List<Library> getTop() {
		List<Library> result = new ArrayList<>();
		try {
			ResultSet rs = conn.createStatement()
					.executeQuery("select * from " + table + " where parent = 0 and origin = '" + Utils.origin + "'");
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
			int idParent = rs.getInt("parent");
			ResultSet rss = conn.createStatement().executeQuery("select * from " + table + " where id = " + idParent);
			rss.next();
			return new Library(rs.getInt("id"), rs.getString("path"), rs.getString("name"), rss.getString("path"),
					idParent, rs.getString("origin"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Library> getChildren(Library library) {
		List<Library> result = new ArrayList<>();
		try {
			ResultSet rs = conn.createStatement().executeQuery("select * from " + table + " where parent = "
					+ library.getId() + " and origin = '" + Utils.origin + "'");
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
					+ name.toLowerCase() + "%' and parent = 0 and origin = '" + Utils.origin + "'");
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
			ResultSet rs = conn.createStatement().executeQuery(
					"select * from " + table + " where parent = '" + library.getPath() + "' lower(name) like '%"
							+ name.toLowerCase() + "%' and parent = 0 and origin = '" + Utils.origin + "'");
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
							+ "', '" + library.getName() + "', " + library.getIdParent() + ", '" + library.getOrigin()
							+ "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean update(final Library library) {
		boolean ok = false;
		try {
			ok = conn.createStatement()
					.execute("update " + table + " set path = '" + library.getPath() + "', name = '" + library.getName()
							+ "', parent = " + library.getIdParent() + " where id = " + library.getId() + "");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<Video> videos = GVideoImp.getGestor().getByLibrary(library);
		for (Video video : videos) {
			GVideoImp.getGestor().update(video);
		}

		List<Library> libraries = getChildren(library);

		for (Library lib : libraries) {
			lib.setPath(library.getPath() + System.getProperty("file.separator") + lib.getName());
			update(lib);
			videos = GVideoImp.getGestor().getByLibrary(library);
			for (Video video : videos) {
				GVideoImp.getGestor().update(video);
			}
		}

		return ok;
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
