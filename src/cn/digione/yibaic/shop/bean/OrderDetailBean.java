package cn.digione.yibaic.shop.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 订单详情实体
 * 
 * @author Administrator
 * 
 */
public class OrderDetailBean implements Serializable {
	private String orderId;
	/**
	 * 订单最终总费用
	 */
	private long orderAmount;

	/**
	 * 商品总价(不包含运费和优惠)
	 */
	private long productPriceAmt;
	/**
	 * 订单状态(10未付款、20等待发货、40已发货、50已收货)
	 */
	private int orderStatus;
	/**
	 * 订单状态(已付款、未付款)
	 */
	private String orderStatusName;
	/**
	 * 运费
	 */
	private long transCost;

	/**
	 * 优惠金额
	 */
	private long preferential;

	/**
	 * 下单时间
	 */
	private String orderTime;

	/**
	 * 订单编号
	 */
	private String orderNo;

	/**
	 * 收货地址
	 */
	private String consigneeAddress;

	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 发票类型
	 */
	private String invoiceTypeName;

	/**
	 * 能否取消
	 */
	private int canCancel;
	/**
	 * 优惠
	 */
	private long preferentialAmt;
	/**
	 * 商品数量
	 */
	private int productCount;
	/**
	 * 订单中的产品
	 */
	private ArrayList<OrderProductBean> orderItemVOs;

	public long getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(long orderAmount) {
		this.orderAmount = orderAmount;
	}

	public long getProductPriceAmt() {
		return productPriceAmt;
	}

	public void setProductPriceAmt(long productPriceAmt) {
		this.productPriceAmt = productPriceAmt;
	}

	public long getTransCost() {
		return transCost;
	}

	public void setTransCost(long transCost) {
		this.transCost = transCost;
	}

	public long getPreferential() {
		return preferential;
	}

	public void setPreferential(long preferential) {
		this.preferential = preferential;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	public int getCanCancel() {
		return canCancel;
	}

	public void setCanCancel(int canCancel) {
		this.canCancel = canCancel;
	}

	public long getPreferentialAmt() {
		return preferentialAmt;
	}

	public void setPreferentialAmt(long preferentialAmt) {
		this.preferentialAmt = preferentialAmt;
	}

	public ArrayList<OrderProductBean> getOrderItemVOs() {
		return orderItemVOs;
	}

	public void setOrderItemVOs(ArrayList<OrderProductBean> orderItemVOs) {
		this.orderItemVOs = orderItemVOs;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
}
