package models;

public class Library {

	private String name, path, category;

	public Library(String path, String name, String category) {
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
	
	public String getCategory() {
		return category;
	}

}
