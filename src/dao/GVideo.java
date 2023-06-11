package dao;

import java.util.List;

import models.Library;
import models.Video;

public interface GVideo {

	/**
	 * Obtains a video by an id
	 * 
	 * @param id String
	 * @return Video
	 */
	public Video getById(String id);

	/**
	 * Obtains a video by an URL
	 * 
	 * @param url String
	 * @return Video
	 */
	public Video getByUrl(String url);

	/**
	 * Obtains all videos
	 * 
	 * @return List<Video>
	 */
	public List<Video> getAll();

	/**
	 * Obtain the videos given a library
	 * 
	 * @param library Library
	 * @return List<Video>
	 */
	public List<Video> getByLibrary(Library library);

	/**
	 * Obtain the most recent videos
	 * 
	 * @param amount int
	 * @return List<Video>
	 */
	public List<Video> getRecent(int amount);

	/**
	 * Obtain the videos not downloaded
	 * 
	 * @return List<Video>
	 */
	public List<Video> getByNotDownloaded();

	/**
	 * Inserts a vide
	 * 
	 * @param video Video
	 * @return boolean
	 */
	public boolean insert(Video video);

	/**
	 * Updates the vide
	 * 
	 * @param video Video
	 * @return boolean
	 */
	public boolean update(Video video);

	/**
	 * Deletes the vide
	 * 
	 * @param video Video
	 * @return boolean
	 */
	public boolean delete(Video video);

	/**
	 * Obtains the videos given a library id and a file name
	 * 
	 * @param idLibrary int
	 * @param fileName  String
	 * @return Video
	 */
	public Video getByLibrary(int idLibrary, String fileName);

	/**
	 * Updates the videos given a library
	 * 
	 * @param library Library
	 * @param videos  List<Video>
	 * @return boolean
	 */
	public boolean update(Library library, List<Video> videos);

	/**
	 * Obtains the videos given a library id and the order
	 * 
	 * @param idLibrary int
	 * @param asc       boolean
	 * @return List<Video>
	 */
	public List<Video> getByLibraryOrderedName(int idLibrary, boolean asc);

	/**
	 * Obtains the videos given a library id and the order
	 * 
	 * @param idLibrary int
	 * @param asc       boolean
	 * @return List<Video>
	 */
	public List<Video> getByLibraryOrderedDate(int idLibrary, boolean asc);

}
