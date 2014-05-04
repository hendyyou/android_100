package cn.digione.yibaic.shop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 2014/4/1
 * Time: 14:11
 */
public class ShoppingCartOrderBean implements Serializable {
    private static final long serialVersionUID = -1154810621809458597L;

    private Long preferentialAmt;        //订单优惠金额--整单优惠的情况
    private Long productPriceAmt;    //	产品总额
    private Integer productCount;//	产品数量
    private Long orderAmount;    //订单总额即应付金额，等于产品总额+运费-订单优惠金额
    private Long transCost;//订单运费
    private List<ShoppingCartItemBean> shoppingCartVOList;
    private AddressBean addressVO;
    private List<InvoiceBean> invoiceVO;
    private List<DeliveryBean> deliveryVO;
    private List<PaymentBean> paymentVO;

    public Long getPreferentialAmt() {
        return preferentialAmt;
    }

    public void setPreferentialAmt(Long preferentialAmt) {
        this.preferentialAmt = preferentialAmt;
    }

    public Long getProductPriceAmt() {
        return productPriceAmt;
    }

    public void setProductPriceAmt(Long productPriceAmt) {
        this.productPriceAmt = productPriceAmt;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getTransCost() {
        return transCost;
    }

    public void setTransCost(Long transCost) {
        this.transCost = transCost;
    }

    public List<ShoppingCartItemBean> getShoppingCartVOList() {
        return shoppingCartVOList;
    }

    public void setShoppingCartVOList(List<ShoppingCartItemBean> shoppingCartVOList) {
        this.shoppingCartVOList = shoppingCartVOList;
    }

    public AddressBean getAddressVO() {
        return addressVO;
    }

    public void setAddressVO(AddressBean addressVO) {
        this.addressVO = addressVO;
    }

    public List<InvoiceBean> getInvoiceVO() {
        return invoiceVO;
    }

    public void setInvoiceVO(List<InvoiceBean> invoiceVO) {
        this.invoiceVO = invoiceVO;
    }

    public List<DeliveryBean> getDeliveryVO() {
        return deliveryVO;
    }

    public void setDeliveryVO(List<DeliveryBean> deliveryVO) {
        this.deliveryVO = deliveryVO;
    }

    public List<PaymentBean> getPaymentVO() {
        return paymentVO;
    }

    public void setPaymentVO(List<PaymentBean> paymentVO) {
        this.paymentVO = paymentVO;
    }
}
