package dao;

import java.util.List;

import models.Library;

public interface GVideo<Video> {

	public static GVideo<?> gestor = null;

	public Video getById(String id);

//	public Video getByUrl(String url);

	public List<Video> getAll();

	public List<Video> getByLibrary(Library library);

	public List<Video> getRecent(int amount);

	public List<Video> getByNotDownloaded();

	public boolean insert(Video video);

	public boolean update(Video video);

	public boolean delete(Video video);

	public Video getByLibrary(int idLibrary, String fileName);
	
	public boolean update(Library library, List<Video> videos);

}
