package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 2014/4/1
 * Time: 14:16
 */
public class PaymentBean implements Serializable {
    private static final long serialVersionUID = -8878082574786255131L;
    private  Integer id;
    private  String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
