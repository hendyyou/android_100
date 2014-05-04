package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

public class HomeBean implements Serializable {
	private String model;
	private String indexUrl;
	/**
	 * 专题页面对应的产品类别
	 */
	private int proClass;
	/**
	 * 是否购买（1为购买,2为售罄，0为预约）
	 * @return
	 */
	private int sellType;
	/**
	 * 专题页对应的产品型号
	 * @return
	 */
	public String getModel() {
		return model;
	}	
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * 专题页的页面地址
	 * @return
	 */
	public String getIndexUrl() {
		return indexUrl;
	}
	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}
	/**
	 * 是否购买（1为购买,2为售罄，0为预约）
	 * @return
	 */
	public int getSellType() {
		return sellType;
	}
	public void setSellType(int sellType) {
		this.sellType = sellType;
	}
	/**
	 * 专题页面对应的产品类别
	 */
	public int getProClass() {
		return proClass;
	}
	public void setProClass(int proClass) {
		this.proClass = proClass;
	}
	
	
}
