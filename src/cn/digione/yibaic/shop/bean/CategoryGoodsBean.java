package cn.digione.yibaic.shop.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: youzh Date: 14-3-6 Time: 下午2:44
 */
public class CategoryGoodsBean implements Serializable {

	private static final long serialVersionUID = 6754707090754725394L;
	// 分类ID
	private int id;
	// 分类商品图片地址
	private String classPic;
	// 分类名称
	private String className;
	/**
	 * 是否V6，如果是1，则从分类直接跳到专题页。如果为0，则前进到型号列表
	 */
	private int isV6;
	/**
	 * 代表该类别中的某个产品ID。用于查看详情页
	 */
	private int proId;
	
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassPic() {
		return classPic;
	}

	public void setClassPic(String classPic) {
		this.classPic = classPic;
	}

	public int getIsV6() {
		return isV6;
	}

	public void setIsV6(int isV6) {
		this.isV6 = isV6;
	}

	public int getProId() {
		return proId;
	}

	public void setProId(int proId) {
		this.proId = proId;
	}
}
