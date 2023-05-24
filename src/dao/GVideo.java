package dao;

import java.util.List;

public interface GVideo<Video> {

	public static GVideo<?> gestor = null;

	public Video getById(String id);

//	public Video getByUrl(String url);

	public List<Video> getAll();

	public List<Video> getRecent(int amount);

	public List<Video> getByNotDownloaded();

	public boolean insert(Video video);
	
	public boolean update(Video video);
	
	public boolean delete(Video video);

}
