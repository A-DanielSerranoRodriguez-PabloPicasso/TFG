package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Category;

public class GCategoryImp extends GGeneral implements GCategory<Category> {

	public GCategoryImp() {
		table = "category";
		try {
			conn = SQLiteDAO.getConn();
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Category getByPath(final String id) {
		Category category = null;
		try {
			ResultSet rs = stmt.executeQuery("select name from " + table + " where name = '" + id + "'");
			if (rs.next())
				category = new Category(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return category;
	}

	@Override
	public List<Category> getAll() {
		List<Category> result = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery("select name from " + table);
			while (rs.next())
				result.add(new Category(rs.getString(1)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean insert(final Category category) {
		try {
			return stmt.execute("insert into " + table + " values ('" + category.getName() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(final Category category) {
		try {
			return stmt.execute("delete from " + table + " where name = '" + category.getName() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
