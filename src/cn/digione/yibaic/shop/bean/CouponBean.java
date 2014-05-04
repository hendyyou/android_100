package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * @ClassName: OrderBean
 * @Description: 订单对应的实体
 */
public class CouponBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String useCondition;

	private String amount;

	private String purposeText;

	private String conNo;

	private String stateText;
	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 需满足金额
	 */
	public String getUseCondition() {
		return useCondition;
	}

	public void setUseCondition(String useCondition) {
		this.useCondition = useCondition;
	}

	/**
	 * 面额
	 */
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * 用途
	 */
	public String getPurposeText() {
		return purposeText;
	}

	public void setPurposeText(String purposeText) {
		this.purposeText = purposeText;
	}

	/**
	 * 优惠码
	 */
	public String getConNo() {
		return conNo;
	}

	public void setConNo(String conNo) {
		this.conNo = conNo;
	}

	/**
	 * 状态
	 */
	public String getStateText() {
		return stateText;
	}

	public void setStateText(String stateText) {
		this.stateText = stateText;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
