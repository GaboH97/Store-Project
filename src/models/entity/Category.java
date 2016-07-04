package model.entity;

public enum Category {
	BEAUTY, BOOKS, CLOTHING, ELECTRONIC, ENTERTAINMENT, GARDERING, GROCERY, HEALTH, HOME, HOUSEWORK, JEWELRY, OTHERS, OUTDOORS, SEARCH, SPORTS, TOOLS, TECHNOLOGY;

	@Override
	public String toString() {
		String name = name().replaceAll("_", " ").toLowerCase();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}
}
