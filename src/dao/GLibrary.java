package dao;

import java.util.List;

public interface GLibrary<Library> {

	public static GLibrary<?> gestor() {
		return null;
	}

	public Library getById(int id);

	public List<Library> getAll();

	public List<Library> getChildren(Library library);

	public List<Library> getChildless();

	public List<Library> getFromNameEverywhere(String name);

	public List<Library> getFromName(String name);

	public List<Library> getFromName(Library library, String name);

	public boolean insert(Library library);

	public boolean update(Library library);

	public boolean delete(Library library);

	models.Library getByPath(String path);

}
