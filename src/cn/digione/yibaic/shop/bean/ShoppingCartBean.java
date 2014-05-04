package cn.digione.yibaic.shop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 14-3-11
 * Time: 上午11:02
 */
public class ShoppingCartBean implements Serializable {
    private static final long serialVersionUID = 6017470962479793559L;

    private Long preferentialAmt;		//订单优惠金额--整单优惠的情况
    private Long productPriceAmt;	//	产品总额
    private Integer productCount;//	产品数量
    private Long orderAmount;	//订单总额即应付金额，等于产品总额+运费-订单优惠金额
    private Long transCost;//订单运费
    private List<ShoppingCartItemBean> shoppingCartVOList;

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
}
