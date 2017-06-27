package com.nt.open.discron.common.pager;

import java.io.Serializable;

/**
 * 
 * @author bjfulianqiu
 *
 */
public class PageCondition implements Serializable {

	public static final String FIELD_PAGE_START = "start";

	public static final String FIELD_PAGE_SIZE = "pageSize";
	
	public static final String FIELD_PAGE_INDEX = "pageIndex";

	/**
	 * 
	 */
	private static final long serialVersionUID = 5358613174358872174L;

	/**
	 * 当前页码
	 */
	private Integer pageIndex;
	/**
	 * 每页记录数
	 */
	private Integer pageSize = 10;
	/**
	 * 结果集总数
	 */
	private Integer rowCount;
	/**
	 * 结果集页数
	 */
	private Integer pageCount;

	public PageCondition(Integer pageIndex, Integer pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}

	public PageCondition(Integer pageIndex) {
		super();
		this.pageIndex = pageIndex;
	}

	public PageCondition() {
		super();
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	/**
	 * 设置总记录数时，会自动计算总页码数
	 * 
	 * @param rowCount
	 */
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
		if (rowCount == 0) {
			this.pageCount = 0;
		} else {
			if (this.rowCount % this.pageSize == 0) {
				this.pageCount = this.rowCount / this.pageSize;
			} else {
				this.pageCount = this.rowCount / this.pageSize + 1;
			}
		}
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 根据页码以及每页记录数获取分页的开始位置
	 * 
	 * @return
	 */
	public Integer getStart() {
		Integer start = 0;
		if (this.pageIndex == null) {
			this.pageIndex = 0;
		}
		if (this.getPageIndex() > 0) {
			start = (this.getPageIndex() - 1) * this.getPageSize();
		}
		return start;
	}

	@Override
	public String toString() {
		return ",pageIndex=" + this.pageIndex + ",pageSize=" + this.pageSize + ",rowCount=" + this.rowCount + ",pageCount="
				+ this.pageCount;
	}
}
