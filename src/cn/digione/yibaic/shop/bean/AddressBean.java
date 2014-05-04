package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * 收货地址
 */
public class AddressBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 地址ID
	 */
	private String id;
	
	/**
	 * 具体地址
	 */
	private String address;
	/**
	 * 城市
	 */
	private String cityName;
	/**
	 * 城市ID
	 */
	private String cityId; 
	/**
	 * 收货人
	 */
	private String name;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 区
	 */
	private String countyName;
	/**
	 * 区ID
	 */
	private String countyId;
	/**
	 * 省份
	 */
	private String provinceName;
	/**
	 * 省份ID
	 */
	private String provinceId;
	/**
	 * 移动电话
	 */
	private String mobile;
	/**
	 * 固定电话
	 */
	private String phone;
	/**
	 * 邮编
	 */
	private String postcode;
			
	/**
	 * 是否默认(0：非默认地址 1：默认地址)
	 */
	private int isDefault;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
