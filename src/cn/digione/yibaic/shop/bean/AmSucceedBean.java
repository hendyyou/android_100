package cn.digione.yibaic.shop.bean;
/**
 * 预约成功后的返回值 
 *
 */
public class AmSucceedBean {
	/**
	 * 是否支持预约付款
	 */
	private String isPrepaid;
	
	/**
	 * 产品ID
	 */
	private String proId;

	public String getIsPrepaid() {
		return isPrepaid;
	}

	public void setIsPrepaid(String isPrepaid) {
		this.isPrepaid = isPrepaid;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

}
