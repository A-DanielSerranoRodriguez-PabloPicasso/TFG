package models;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import dao.GLibrary;
import dao.GLibraryImp;
import dao.GVideo;
import dao.GVideoImp;

public class Library {

	private int id, idParent;
	private String name, path, parent, namePath, origin;
	private Library libParent;
	private GLibrary<Library> gLibrary;
	private GVideo<Video> gVideo;

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

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public String getParent() {
		return parent;
	}

	public String getNamePath() {
		return namePath;
	}

	public Library getLibParent() {
		return libParent;
	}

	public int getIdParent() {
		return idParent;
	}

	public String getOrigin() {
		return origin;
	}

	public String getTree() {
		String tree = name;
		if (libParent != null) {
			String parentTree = libParent.getTree();
			if (!parentTree.equals(null))
				tree = parentTree + " > " + tree;
		}
		return tree;
	}

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
