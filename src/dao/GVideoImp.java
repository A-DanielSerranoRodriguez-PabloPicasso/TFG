//package dao;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//
//import models.Video;
//
//public class GVideoImp implements GVideo<Video> {
//
//	public static GVideo<Video> gestor;
//
//	private Connection conn;
//
//	private GVideoImp() {
//		try {
//			conn = new SQLiteDAO().getConn();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public GVideo<Video> getGestor() {
//		if (gestor == null)
//			gestor = new GVideoImp();
//
//		return gestor;
//	}
//
//	@Override
//	public Video getById(String id) {
//		Video general = null;
//
//		try {
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("select url, downloaded from general where id = " + Integer.parseInt(id));
//			rs.next();
//
//			general = new Video(rs.getString(1), rs.getInt(2));
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return general;
//	}
//
//	public Video getByUrl(String url) {
//		Video general = null;
//
//		try {
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("select url, downloaded from general where url = " + url);
//			rs.next();
//
//			general = new Video(rs.getString(1), rs.getInt(2));
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return general;
//	}
//
//	@Override
//	public List<Video> getAll() {
//		List<Video> generales = new ArrayList<>();
//
//		try {
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("select url, downloaded from general");
//
//			while (rs.next())
//				generales.add(new Video(rs.getString(1), rs.getInt(2)));
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return generales;
//	}
//
//	public List<Video> getByNotDownloaded() {
//		List<Video> generales = new ArrayList<>();
//
//		try {
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("select url from general where downloaded = 0");
//
//			while (rs.next())
//				generales.add(new Video(rs.getString(1)));
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return generales;
//	}
//
//	@Override
//	public boolean insert(Video video) {
//		boolean ok = false;
//
//		try {
//			Statement stmt = conn.createStatement();
//			ok = stmt.execute("insert into general(url) values ('" + object.getUrl() + "');");
//		} catch (SQLException e) {
//			System.err.println("URL already exists");
//		}
//
//		return ok;
//	}
//
//}
