package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-3-6 Time: 下午2:44
 */
public class ProductBean implements Serializable {

	private static final long serialVersionUID = 564047238940036996L;
	// 产品id
	private Integer id;
	// 是否售罄
	private Integer isSoldout;

	// 产品图片
	private String pic;
	// 品牌名称
	private String proBrand;
	// 卖点
	private String proDesc;
	// 型号
	private String proModel;
	// 产品名称
	private String proName;
	// 价格(传入分为单位)
	private Long proPrice;

    private String proRam;

    private String proRom;

    private String  publishName;

    /**
     * 网络制式(1、TD-SCDMA，2、WCDMA)
     */
    private String proStandardName;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getIsSoldout() {
		return isSoldout;
	}

	public void setIsSoldout(final Integer isSoldout) {
		this.isSoldout = isSoldout;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(final String pic) {
		this.pic = pic;
	}

	public String getProBrand() {
		return proBrand;
	}

	public void setProBrand(final String proBrand) {
		this.proBrand = proBrand;
	}

	public String getProDesc() {
		return proDesc;
	}

	public void setProDesc(final String proDesc) {
		this.proDesc = proDesc;
	}

	public String getProModel() {
		return proModel;
	}

	public void setProModel(final String proModel) {
		this.proModel = proModel;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(final String proName) {
		this.proName = proName;
	}

	public Long getProPrice() {
		return proPrice;
	}

	public void setProPrice(final Long proPrice) {
		this.proPrice = proPrice;
	}

    public String getProRom() {
        return proRom;
    }

    public void setProRom(String proRom) {
        this.proRom = proRom;
    }

    public String getProRam() {
        return proRam;
    }

    public void setProRam(String proRam) {
        this.proRam = proRam;
    }

    public String getPublishName() {
        return publishName;
    }

    public void setPublishName(String publishName) {
        this.publishName = publishName;
    }

    public String getProStandardName() {
        return proStandardName;
    }

    public void setProStandardName(String proStandardName) {
        this.proStandardName = proStandardName;
    }
}
