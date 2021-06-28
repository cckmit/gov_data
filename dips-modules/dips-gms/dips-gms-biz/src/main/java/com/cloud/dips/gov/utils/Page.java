package com.cloud.dips.gov.utils;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

public class Page<T> {

	private Boolean isPage = true;
	/**
	 * 页码，默认是第一页
	 */
	private Integer pageNum = 1;
	/**
	 *每页显示的记录数，默认是10
	 */
	private Integer pageSize = 10;

	private Integer startRow;
	/**
	 * 信息资源待审核数量
	 */
	private Integer auditCount;
	/**
	 * 信息资源已退回数量
	 */
	private Integer returnCount;
	/**
	 * 基础设施建设待审核数量
	 */
	private Integer reCount;
	/**
	 * 基础设施建设已退回数量
	 */
	private Integer buildReturnCount;

	private Integer sysReCount;

	private Integer sysReturnCount;

	private Integer endRow;

	private Map<String, Object> params = Maps.newHashMap();

	private String obj;

	private String startDate;

	private String endDate;

	private String queryParams;

	private String tableName;
	/**
	 * 是否授权 ：0否 1是
	 */
	private String authorize;
	/**
	 * 总记录数
	 */
	private Long total;
	/**
	 * 对应的当前页记录
	 */
	private List<T> rows;
	/**
	 * 数据目录概览-权责清单和信息项统计
	 */
	private Map<String, Object> countMap;

	public Page() {

	}

	public Map<String, Object> getCountMap() {
		return countMap;
	}

	public void setCountMap(Map<String, Object> countMap) {
		this.countMap = countMap;
	}

	public Integer getReCount() {
		return reCount;
	}

	public void setReCount(Integer reCount) {
		this.reCount = reCount;
	}

	public Integer getSysReCount() {
		return sysReCount;
	}

	public void setSysReCount(Integer sysReCount) {
		this.sysReCount = sysReCount;
	}

	public Integer getSysReturnCount() {
		return sysReturnCount;
	}

	public void setSysReturnCount(Integer sysReturnCount) {
		this.sysReturnCount = sysReturnCount;
	}

	public Integer getBuildReturnCount() {
		return buildReturnCount;
	}

	public void setBuildReturnCount(Integer buildReturnCount) {
		this.buildReturnCount = buildReturnCount;
	}

	public Boolean getIsPage() {
		return isPage;
	}

	public void setIsPage(Boolean isPage) {
		this.isPage = isPage;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void startPage() {
		this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
		this.endRow = pageSize * pageNum;
	}

	public Integer getAuditCount() {
		return auditCount;
	}

	public void setAuditCount(Integer auditCount) {
		this.auditCount = auditCount;
	}

	public Integer getReturnCount() {
		return returnCount;
	}

	public void setReturnCount(Integer returnCount) {
		this.returnCount = returnCount;
	}

	public String getAuthorize() {
		return authorize;
	}

	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}

}
