package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

public class SimpleProductBean implements Serializable {

	private static final long serialVersionUID = 1L;
	// 产品ID
	private Integer productId;
	/**
	 * 产品类别。 1:手机 2:手机配件 
	 */
	private Integer dictId1;
	// 产品详情链接URL
	private String productDetailUrl;
	private String picPath1Url;
	// 图片5链接URL
	private String picPath5Url;
	// 品牌名称
	private String brandName;
	// 产品型号
	private String pattern;
	// 产品颜色
	private String colorSpec;

	private Integer isSpecial;

	private Integer isForesee;

	public SimpleProductBean() {
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getDictId1() {
		return dictId1;
	}

	public void setDictId1(Integer dictId1) {
		this.dictId1 = dictId1;
	}

	public String getProductDetailUrl() {
		return productDetailUrl;
	}

	public void setProductDetailUrl(String productDetailUrl) {
		this.productDetailUrl = productDetailUrl;
	}

	public String getPicPath1Url() {
		return picPath1Url;
	}

	public void setPicPath1Url(String picPath1Url) {
		this.picPath1Url = picPath1Url;
	}

	public String getPicPath5Url() {
		return picPath5Url;
	}

	public void setPicPath5Url(String picPath5Url) {
		this.picPath5Url = picPath5Url;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getColorSpec() {
		return colorSpec;
	}

	public void setColorSpec(String colorSpec) {
		this.colorSpec = colorSpec;
	}

	public Integer getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Integer getIsForesee() {
		return isForesee;
	}

	public void setIsForesee(Integer isForesee) {
		this.isForesee = isForesee;
	}
}
