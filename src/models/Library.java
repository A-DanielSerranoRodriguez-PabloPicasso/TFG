package models;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import dao.GLibrary;
import dao.GLibraryImp;
import dao.GVideo;
import dao.GVideoImp;

/**
 * Represents a library of the databse
 * 
 * @author Daniel Serrano Rodriguez
 */
public class Library {

	private int id, idParent;
	private String name, path, parent, namePath, origin;
	private Library libParent;
	private GLibrary gLibrary;
	private GVideo gVideo;

	/**
	 * Constructor of a library
	 * 
	 * @param id       int
	 * @param path     String
	 * @param name     String
	 * @param parent   String
	 * @param idParent int
	 * @param origin   String
	 */
	public Library(int id, String path, String name, String parent, int idParent, String origin) {
		this.id = id;
		this.path = path;
		this.name = name;
		this.parent = parent;
		this.idParent = idParent;
		this.origin = origin;
		namePath = name + " - " + path;
		gLibrary = GLibraryImp.getGestor();
		gVideo = GVideoImp.getGestor();
		libParent = gLibrary.getByPath(parent);
	}

	/**
	 * Constructor of a library without id
	 * 
	 * @param path     String
	 * @param name     String
	 * @param parent   String
	 * @param idParent int
	 * @param origin   String
	 */
	public Library(String path, String name, String parent, int idParent, String origin) {
		this.path = path;
		this.name = name;
		this.parent = parent;
		this.idParent = idParent;
		this.origin = origin;
		namePath = name + " - " + path;
		gLibrary = GLibraryImp.getGestor();
		gVideo = GVideoImp.getGestor();
		libParent = gLibrary.getByPath(parent);
	}

	/**
	 * Returns the id
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the name
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the path
	 * 
	 * @return String
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Returns the parent
	 * 
	 * @return String
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * Returns the name + path
	 * 
	 * @return String
	 */
	public String getNamePath() {
		return namePath;
	}

	/**
	 * Returns the parent library
	 * 
	 * @return Library
	 */
	public Library getLibParent() {
		return libParent;
	}

	/**
	 * Returns the id of the parent library
	 * 
	 * @return int
	 */
	public int getIdParent() {
		return idParent;
	}

	/**
	 * Returns the origin folder
	 * 
	 * @return String
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * Returns a tree from the first parent to this library
	 * 
	 * @return String
	 */
	public String getTree() {
		String tree = name;
		if (libParent != null) {
			String parentTree = libParent.getTree();
			if (!parentTree.equals(null))
				tree = parentTree + " > " + tree;
		}
		return tree;
	}

	/**
	 * Changes the name of the library
	 * 
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
		String oldPath = path;
		String[] parts = path.split(System.getProperty("file.separator"));
		int l = parts.length;
		parts[l - 1] = name;
		path = "";
		for (int i = 0; i < l; i++) {
			path += parts[i];
			if (i < l - 1)
				path += System.getProperty("file.separator");
		}

		try {
			Files.move(new File(oldPath), new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		gLibrary.update(this);
		gVideo.update(this, gVideo.getByLibrary(this));
	}

	/**
	 * Sets the new path of the library
	 * 
	 * @param path String
	 */
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "Library [id=" + id + ", name=" + name + ", path=" + path + ", parent=" + parent + ", namePath="
				+ namePath + ", origin=" + origin + ", libParent=" + libParent + ", gLibrary=" + gLibrary + ", gVideo="
				+ gVideo + "]";
	}

}
