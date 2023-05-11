package dao;

import java.util.List;

public interface GLibrary<Library> {

	public static GLibrary<?> gestor = null;

	public Library getByPath(String path);

	public List<Library> getAll();

	public List<Library> getChildren(Library library);

	public boolean insert(Library library);

	public boolean update(Library library);

	public boolean delete(Library library);

}
