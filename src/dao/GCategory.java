package dao;

import java.util.List;

import models.Category;

public interface GCategory<category> {

	public static GCategory<?> gestor = null;

	public Category getByPath(String path);

	public List<Category> getAll();

	public boolean insert(Category catepory);

	public boolean delete(Category category);

}
