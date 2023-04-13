package dao;

import java.util.List;

public interface GVideo<T> {

	public static GVideo<?> gestor = null;

	public T getById(String id);

	public T getByUrl(String url);

	public List<T> getAll();

	public List<T> getByNotDownloaded();

	public boolean insert(T object);

}
