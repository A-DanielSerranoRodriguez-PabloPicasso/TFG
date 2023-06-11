package dao;

import java.util.List;

import models.Library;

/**
 * Library manager.
 * 
 * @author Daniel Serrano Rodriguez
 */
public interface GLibrary {

	/**
	 * Obtains a library given an ID
	 * 
	 * @param id int
	 * @return Library
	 */
	public Library getById(int id);

	/**
	 * Returns a list of libraries
	 * 
	 * @return List<Library>
	 */
	public List<Library> getAll();

	/**
	 * Returns a list of sub libraries given a library
	 * 
	 * @param Library library
	 * @return List<Library>
	 */
	public List<Library> getChildren(Library library);

	/**
	 * Returns a list of the root libraries
	 * 
	 * @return List<Library>
	 */
	public List<Library> getTop();

	/**
	 * Returns a list of libraries independently if its a root library or not given
	 * a name
	 * 
	 * @param String name
	 * @return List<Library>
	 */
	public List<Library> getFromNameEverywhere(String name);

	/**
	 * Returns a list of libraries given a name
	 * 
	 * @param String name
	 * @return List<Library>
	 */
	public List<Library> getFromName(String name);

	/**
	 * Returns a list of libraries given a Libary and a name
	 * 
	 * @param Library library
	 * @param String  name
	 * @return List<Library>
	 */
	public List<Library> getFromName(Library library, String name);

	/**
	 * Inserts a library
	 * 
	 * @param Library library
	 * @return boolean
	 */
	public boolean insert(Library library);

	/**
	 * Updates a library
	 * 
	 * @param Library library
	 * @return boolean
	 */
	public boolean update(Library library);

	/**
	 * Returns a list of libraries
	 * 
	 * @param Library library
	 * @return boolean
	 */
	public boolean delete(Library library);

	/**
	 * Returns a library given a path
	 * 
	 * @param String path
	 * @return List<Library>
	 */
	public Library getByPath(String path);

}
