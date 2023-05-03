package models;

public class Library {

	private String name, path;
	private Category category;

	public Library(String path, String name, Category category) {
		this.path = path;
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public Category getCategory() {
		return category;
	}

}
