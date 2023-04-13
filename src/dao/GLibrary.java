package dao;

import java.util.List;

public interface GLibrary<T> {

	public static GLibrary<?> gestor = null;

	public T getByPath(T path);

	public List<T> getAll();

	public boolean insert(T path);

}
