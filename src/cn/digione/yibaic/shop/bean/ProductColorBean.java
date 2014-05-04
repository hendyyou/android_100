package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-3-6 Time: 下午2:44
 */
public class ProductColorBean implements Serializable {

	private static final long serialVersionUID = -8479966818938258714L;
	// 产品id
	private Integer id;
	// 是否售罄
	private Integer isSoldout;
	// 分类
	private Integer proClass;
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
	private Integer proPrice;

	private String proColor;

	private String proRom;

	private String proRam;

	private String publishName;

	private String sellType;

	private String jcodeSellType;
	/**
	 * 是否支持预付款。1：不支持 2：支持
	 */
	private String isPrepaid; 
	/**
	 * 预约ID。仅当sellType为0的时候，本字段才有效。
	 */
	private String appointmentID;

	/**
	 * 网络制式
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

	public Integer getProPrice() {
		return proPrice;
	}

	public void setProPrice(final Integer proPrice) {
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

	/**
	 * 销售类型： 0：可预约、1：可购买、2：已售罄
	 */
	public String getSellType() {
		return sellType;
	}

	public void setSellType(String sellType) {
		this.sellType = sellType;
	}

	/**
	 * 销售类型： 1：可购买、2：已售罄
	 */
	public String getJcodeSellType() {
		return jcodeSellType;
	}

	public void setJcodeSellType(String jcodeSellType) {
		this.jcodeSellType = jcodeSellType;
	}

	/**
	 * 预约ID。仅当sellType为0的时候，本字段才有效。
	 */

	public String getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(String appointmentID) {
		this.appointmentID = appointmentID;
	}

	/**
	 * 网络制式
	 * 
	 * @return
	 */
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

	public String getIsPrepaid() {
		return isPrepaid;
	}

	public void setIsPrepaid(String isPrepaid) {
		this.isPrepaid = isPrepaid;
	}
	
}
