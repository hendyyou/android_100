package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Simpson
 * Date: 2014/4/13
 * Time: 16:00
 */
public class OrderCouponJiaCoinOutBean implements Serializable {
    private Long preferentialAmt;
    private Long jiaCurrencyAmt;
    private Long orderAmount;

    public OrderCouponJiaCoinOutBean() {

    }

    public OrderCouponJiaCoinOutBean(Long preferentialAmt, Long jiaCurrencyAmt, Long orderAmount) {
        this.preferentialAmt = preferentialAmt;
        this.jiaCurrencyAmt = jiaCurrencyAmt;
        this.orderAmount = orderAmount;
    }

    public Long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Long orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getPreferentialAmt() {
        return preferentialAmt;
    }

    public void setPreferentialAmt(Long preferentialAmt) {
        this.preferentialAmt = preferentialAmt;
    }

    public Long getJiaCurrencyAmt() {
        return jiaCurrencyAmt;
    }

    public void setJiaCurrencyAmt(Long jiaCurrencyAmt) {
        this.jiaCurrencyAmt = jiaCurrencyAmt;
    }
}
