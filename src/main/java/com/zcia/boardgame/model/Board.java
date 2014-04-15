package com.zcia.boardgame.model;

public class Board {

	private long id;
	private String name;
	private String version;
	private BoardSize size;
	private Resource background;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public BoardSize getSize() {
		return size;
	}

	public void setSize(BoardSize size) {
		this.size = size;
	}

	public Resource getBackground() {
		return background;
	}

	public void setBackground(Resource background) {
		this.background = background;
	}

}
