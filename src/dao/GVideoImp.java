package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import models.Library;
import models.Video;

public class GVideoImp extends GGeneral implements GVideo<Video> {

	private static GVideo<Video> gestor;

	private Connection conn;
	private GLibrary<Library> gLibrary;

	private GVideoImp() {
		gLibrary = GLibraryImp.getGestor();

		try {
			conn = SQLiteDAO.getConn();
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static GVideo<Video> getGestor() {
		if (gestor == null)
			gestor = new GVideoImp();

		return gestor;
	}

	private Video constructVideo(ResultSet rs) {
		Video video = null;
		Library library;

		try {
			library = gLibrary.getById(rs.getInt("library"));
			video = new Video(rs.getInt("id"), rs.getString("name"), rs.getString("file_name"), library,
					rs.getString("url"), rs.getBoolean("downloaded"), rs.getLong("last_date"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return video;
	}

	@Override
	public Video getById(String id) {
		Video video = null;

		try {
			ResultSet rs = stmt.executeQuery("select * from video where id = " + Integer.parseInt(id));
			rs.next();
			video = constructVideo(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return video;
	}

	public Video getByUrl(String url) {
		Video video = null;

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from video where url = '" + url + "'");
			if (rs.next())
				video = constructVideo(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return video;
	}

	@Override
	public List<Video> getAll() {
		List<Video> generales = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from video");

			while (rs.next())
				generales.add(constructVideo(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generales;
	}

	@Override
	public List<Video> getByLibrary(Library library) {
		List<Video> videos = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from video where library = " + library.getId() + "");

			while (rs.next())
				videos.add(constructVideo(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return videos;
	}

	@Override
	public List<Video> getRecent(int amount) {
		List<Video> videos = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from video");
			int i = 0;

			while (rs.next() && i <= amount) {
				videos.add(constructVideo(rs));
				i++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return videos;
	}

	public List<Video> getByNotDownloaded() {
		List<Video> generales = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from video where downloaded = 0");

			while (rs.next())
				generales.add(constructVideo(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generales;
	}

	@Override
	public boolean insert(Video video) {
		boolean ok = false;

		try {
			Statement stmt = conn.createStatement();
			ok = stmt.execute("insert into video(name, file_name, url, library, downloaded, last_date) values ('"
					+ video.getName() + "','" + video.getFileName() + "', '" + video.getUrl() + "', '"
					+ video.getLibrary().getId() + "', " + 1 + ", " + Instant.now().getEpochSecond() + ");");
		} catch (SQLException e) {
			System.err.println("URL already exists");
		}

		return ok;
	}

	@Override
	public boolean update(Video video) {
		boolean ok = false;

		try {
			Statement stmt = conn.createStatement();
			ok = stmt.execute("update video set name = '" + video.getName() + "', file_name = '" + video.getFileName()
					+ "', library = " + video.getLibrary().getId() + " where id = " + video.getId() + ";");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ok;
	}

	@Override
	public boolean delete(Video video) {
		boolean ok = false;

		try {
			Statement stmt = conn.createStatement();
			ok = stmt.execute("delete from video where id = " + video.getId() + ";");
		} catch (SQLException e) {
			System.err.println("URL already exists");
		}

		return ok;
	}

	@Override
	public Video getByLibrary(int idLibrary, String fileName) {
		Video video = null;

		try {
			ResultSet rs = stmt.executeQuery(
					"select * from video where library = " + idLibrary + " and file_name = '" + fileName + "'");
			rs.next();
			video = constructVideo(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return video;
	}

	@Override
	public List<Video> getByLibraryOrderedName(int idLibrary, boolean asc) {
		List<Video> videos = new ArrayList<>();
		String order = asc ? "asc" : "desc";

		try {
			ResultSet rs = stmt
					.executeQuery("select * from video where library = " + idLibrary + " order by name " + order);
			while (rs.next())
				videos.add(constructVideo(rs));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return videos;
	}

	@Override
	public List<Video> getByLibraryOrderedDate(int idLibrary, boolean asc) {
		List<Video> videos = new ArrayList<>();
		String order = asc ? "desc" : "asc";

		try {
			ResultSet rs = stmt
					.executeQuery("select * from video where library = " + idLibrary + " order by last_date " + order);
			while (rs.next())
				videos.add(constructVideo(rs));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return videos;
	}

	@Override
	public boolean update(Library library, List<Video> videos) {
		boolean ok = false;

		try {
			Statement stmt = conn.createStatement();
			for (Video video : videos) {
				stmt.addBatch("update video set library = " + library.getId() + " where id = " + video.getId() + ";");
			}
			ok = stmt.executeBatch().length > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ok;
	}

}
