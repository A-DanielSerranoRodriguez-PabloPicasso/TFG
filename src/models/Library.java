package models;

public class Library {

	private String name, path, parent;
	private Category category;

	public Library(String path, String name, Category category) {
		this.path = path;
		this.name = name;
		this.category = category;
	}

	public Library(String path, String name) {
		this.path = path;
		this.name = name;
	}

	public Library(String path, String name, String parent) {
		this.path = path;
		this.name = name;
		this.parent = parent;
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

	public Category getCategory() {
		return category;
	}

}
