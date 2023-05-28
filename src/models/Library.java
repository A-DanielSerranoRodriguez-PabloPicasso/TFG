package models;

import dao.GLibraryImp;

public class Library {

	private String name, path, parent, namePath, origin;
	private Library libParent;

	public Library(String path, String name, String parent, String origin) {
		this.path = path;
		this.name = name;
		this.parent = parent;
		this.origin = origin;
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

}
