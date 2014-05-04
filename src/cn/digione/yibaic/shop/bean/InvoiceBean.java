package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 2014/4/1
 * Time: 14:16
 */
public class InvoiceBean implements Serializable {
    private static final long serialVersionUID = -8786086375223237287L;
    private Integer id;
    private String content;

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
