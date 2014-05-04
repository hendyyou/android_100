package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

public class JiaCoinBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7659124756675267011L;
	/**
	 * 0 加币 1 优惠券
	 */
	private String accBookType;
	/**
	 * 余额
	 */
	private long balance;
	private int id;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 0 正常 1 冻结
	 */
	private int state;
	/**
	 * 用户ID
	 */
	private String userId;

	public String getAccBookType() {
		return accBookType;
	}

	public void setAccBookType(String accBookType) {
		this.accBookType = accBookType;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
