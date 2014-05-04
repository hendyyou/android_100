package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * 省份 /城市、地区
 *
 */
public class AreaBean implements Serializable {
	private String id;//本身ID
	private String hasChildren;
	private String text;
	private String areaType;
	private String pid;//上一级ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(String hasChildren) {
		this.hasChildren = hasChildren;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
		
}
