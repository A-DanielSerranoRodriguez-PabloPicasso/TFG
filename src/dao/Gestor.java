package dao;

import java.util.List;

public interface Gestor<T> {
	
	public static Gestor gestor = null;

	public T getById(String id);

	public T getByUrl(String url);

	public List<T> getAll();

	public List<T> getByNotDownloaded();

	public boolean insert(T object);

}
