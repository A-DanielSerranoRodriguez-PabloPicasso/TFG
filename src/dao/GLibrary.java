package dao;

import java.util.List;

public interface GLibrary<Library> {

	public static GLibrary<?> gestor = null;

	public Library getByPath(String path);

	public List<Library> getAll();

	public boolean insert(Library library);

	public boolean update(Library library);

	public boolean delete(Library library);

}
