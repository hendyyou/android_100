package cn.digione.yibaic.shop.bean;

import java.io.Serializable;
import java.util.List;

public class JsonPagerEntityBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1068699941823816547L;

	/**
	 * 总数据条数
	 */
	private Integer totalCount;
	/**
	 * 当前页码
	 */
	private Integer currentPage;
	/**
	 * 每页条数
	 */
	private Integer pageSize;
	/**
	 * 返回页数
	 */
	private Integer pageCount;
	/**
	 * 订单列表
	 */
	private List<T> resultList;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(final List<T> resultList) {
		this.resultList = resultList;
	}
}
