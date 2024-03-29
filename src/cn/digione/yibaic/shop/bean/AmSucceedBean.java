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
	/**
	 * 引导支付的文案
	 */
	private String appointmentTip;
	
	/**
	 * 分享的文案
	 */
	private String successTip;
	
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

	public String getAppointmentTip() {
		return appointmentTip;
	}

	public void setAppointmentTip(String appointmentTip) {
		this.appointmentTip = appointmentTip;
	}

	public String getSuccessTip() {
		return successTip;
	}

	public void setSuccessTip(String successTip) {
		this.successTip = successTip;
	}

}
