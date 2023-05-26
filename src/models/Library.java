package models;

import dao.GLibraryImp;

public class Library {

	private String name, path, parent, namePath;
	private Library libParent;

	public Library(String path, String name) {
		this.path = path;
		this.name = name;
		namePath = name + " - " + path;
	}

	public Library(String path, String name, String parent) {
		this.path = path;
		this.name = name;
		this.parent = parent;
		namePath = name + " - " + path;
		libParent = GLibraryImp.gestor().getByPath(parent);
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

	public String getTree() {
		String tree = name;
		if (libParent != null) {
			String parentTree = libParent.getTree();
			if (!parentTree.equals(null))
				tree = parentTree + " > " + tree;
		}
		return tree;
	}

}
