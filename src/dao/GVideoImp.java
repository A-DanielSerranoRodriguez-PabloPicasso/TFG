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

	public static GVideo<Video> gestor;

	private Connection conn;

	private GVideoImp() {
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

	@Override
	public Video getById(String id) {
		Video video = null;

		try {
			ResultSet rs = stmt.executeQuery("select * from video where id = " + Integer.parseInt(id));
			rs.next();
			Library library = GLibraryImp.gestor().getByPath(rs.getString("library"));

			video = new Video(rs.getInt("id"), rs.getString("name"), rs.getString("file_name"), library,
					rs.getString("url"), rs.getBoolean("downloaded"), rs.getLong("last_date"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return video;
	}

//	public Video getByUrl(String url) {
//		Video video = null;
//
//		try {
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("select url, downloaded from video where url = " + url);
//			rs.next();
//
//			video = new Video(rs.getString(1), rs.getInt(2));
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return video;
//	}

	@Override
	public List<Video> getAll() {
		List<Video> generales = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from video");

			while (rs.next()) {
				Library library = GLibraryImp.gestor().getByPath(rs.getString("library"));
				generales.add(new Video(rs.getInt("id"), rs.getString("name"), rs.getString("file_name"), library,
						rs.getString("url"), rs.getBoolean("downloaded"), rs.getLong("last_date")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generales;
	}

	@Override
	public List<Video> getRecent(int amount) {
		List<Video> videos = new ArrayList<>();

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from video");
			int i = 0;

			System.out.println("existo");
			while (rs.next() && i <= amount) {
				Library library = GLibraryImp.gestor().getByPath(rs.getString("library"));
				videos.add(new Video(rs.getInt("id"), rs.getString("name"), rs.getString("file_name"), library,
						rs.getString("url"), rs.getBoolean("downloaded"), rs.getLong("last_date")));
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

			while (rs.next()) {
				Library library = GLibraryImp.gestor().getByPath(rs.getString("library"));
				generales.add(new Video(rs.getInt("id"), rs.getString("name"), rs.getString("file_name"), library,
						rs.getString("url"), rs.getBoolean("downloaded"), rs.getLong("last_date")));
			}
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
					+ video.getName() + "', '" + video.getUrl() + "', '" + video.getLibrary().getPath() + "', " + 1
					+ ", " + Instant.now().getEpochSecond() + ");");
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
			ok = stmt.execute("update video set name = '" + video.getName() + "', library = '"
					+ video.getLibrary().getPath() + "' where id = " + video.getId() + ";");
		} catch (SQLException e) {
			System.err.println("URL already exists");
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

}
