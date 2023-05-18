package models;

public class Library {

	private String name, path, parent, namePath;
	private Category category;

	public Library(String path, String name, Category category) {
		this.path = path;
		this.name = name;
		this.category = category;
		namePath = name + " - " + path;
	}

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

	public Category getCategory() {
		return category;
	}

}
