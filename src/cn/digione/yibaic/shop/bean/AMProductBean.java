package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 2014/3/28
 * Time: 16:05
 */
public class AMProductBean implements Serializable {
    private static final long serialVersionUID = -7300670491343400183L;
    private Integer id;
    private String amTime; //预约时间
    private Integer amState; //预约状态
    private BookBean appointmentVO;
    private ProductColorBean productVO;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmTime() {
        return amTime;
    }

    public void setAmTime(String amTime) {
        this.amTime = amTime;
    }

    public Integer getAmState() {
        return amState;
    }

    public void setAmState(Integer amState) {
        this.amState = amState;
    }

    public BookBean getAppointmentVO() {
        return appointmentVO;
    }

    public void setAppointmentVO(BookBean appointmentVO) {
        this.appointmentVO = appointmentVO;
    }

    public ProductColorBean getProductVO() {
        return productVO;
    }

    public void setProductVO(ProductColorBean productVO) {
        this.productVO = productVO;
    }
}
