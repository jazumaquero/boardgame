package com.zcia.boardgame.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

public class Item {

	private long id;
	private String name;
	private float x;
	private float y;
	private boolean visible;
	private boolean enable;
	private List<Resource> resources;

	@PostConstruct
	public void init() {
		resources = new ArrayList<Resource>();
	}

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

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public void addResource(Resource resource) {
		resources.add(resource);
	}
}
