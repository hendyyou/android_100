package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * @ClassName: OrderBean
 * @Description: 订单对应的实体
 */
public class OrderBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// 订单ID
	private String orderId;
	// 订单号
	private String orderNo;
	// 下单时间
	private String orderTime;
	// 订单状态
	private String orderStatusName;
	/**
	 * 图片地址
	 */
	private String orderPic;
	/**
	 * 订单应付总金额
	 */
	private long orderAmount;

	/**
	 * 订单总价
	 */
	private long orderTotalAmount;

	/**
	 * @return orderNumber
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo
	 *            the orderNumber to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getOrderPic() {
		return orderPic;
	}

	public void setOrderPic(String orderPic) {
		this.orderPic = orderPic;
	}

	public long getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(long orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

}
