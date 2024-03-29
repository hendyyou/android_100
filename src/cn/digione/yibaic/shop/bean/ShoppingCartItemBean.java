package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: youzh
 * Date: 14-3-11
 * Time: 上午11:02
 */
public class ShoppingCartItemBean implements Serializable {

    private static final long serialVersionUID = -8471565502826654721L;

    // 产品id
    private Integer id;

    //产品分类
    private Integer proClass;

    // 型号
    private String proModel;

    // 购买数量
    private Integer num;

    // 产品图片
    private String proPic;

    private String proPic2;

    private String proColor;

    // 价格(传入分为单位)
    private Integer proPrice;

    // 品牌名称
    private String proBrand;

    private Long proPreferentialPrice;

    // 产品id
    private Integer productId;
    // 产品名称
    private String proName;

    private String proRom;

    //限制购买数量
    private Integer proRes;
    /**
     * 
     */
    private int proStock;

    /**
     * 网络制式(1、TD-SCDMA，2、WCDMA)
     */
    private String proStandardName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getProPic() {
        return proPic;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic;
    }

    public String getProBrand() {
        return proBrand;
    }

    public void setProBrand(String proBrand) {
        this.proBrand = proBrand;
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Integer getProPrice() {
        return proPrice;
    }

    public void setProPrice(Integer proPrice) {
        this.proPrice = proPrice;
    }

    public String getProColor() {
        return proColor;
    }

    public void setProColor(String proColor) {
        this.proColor = proColor;
    }

    public String getProRom() {
        return proRom;
    }

    public void setProRom(String proRom) {
        this.proRom = proRom;
    }

    public Long getProPreferentialPrice() {
        return proPreferentialPrice;
    }

    public void setProPreferentialPrice(Long proPreferentialPrice) {
        this.proPreferentialPrice = proPreferentialPrice;
    }

    public Integer getProRes() {
        return proRes;
    }

    public void setProRes(Integer proRes) {
        this.proRes = proRes;
    }

    public String getProStandardName() {
        return proStandardName;
    }

    public void setProStandardName(String proStandardName) {
        this.proStandardName = proStandardName;
    }

    public Integer getProClass() {
        return proClass;
    }

    public void setProClass(Integer proClass) {
        this.proClass = proClass;
    }

    public String getProPic2() {
        return proPic2;
    }

    public void setProPic2(String proPic2) {
        this.proPic2 = proPic2;
    }

	public int getProStock() {
		return proStock;
	}

	public void setProStock(int proStock) {
		this.proStock = proStock;
	}
}
