package bdhb.usershiro.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Menu {

	private UUID id;

	private UUID parentId;

	private String resName;

	private String url;

	private Integer qsort;

	private List<Menu> children = new ArrayList<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getQsort() {
		return qsort;
	}

	public void setQsort(Integer qsort) {
		this.qsort = qsort;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

}
