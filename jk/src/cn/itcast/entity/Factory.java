package cn.itcast.entity;

import java.util.Date;

/**
 * @Description: 
 * @Author: nutony
 * @CreateTime: 2013-11-25
 */
public class Factory {
	private String id;
	private Integer ctype;
	private String fullName;
	private String factoryName;
	private String contractor;
	private String phone;
	private String mobile;
	private String fax;
	private String cnote;
	private String state;
	private String inspector;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the ctype
	 */
	public Integer getCtype() {
		return ctype;
	}
	/**
	 * @param ctype the ctype to set
	 */
	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}
	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * @return the factoryName
	 */
	public String getFactoryName() {
		return factoryName;
	}
	/**
	 * @param factoryName the factoryName to set
	 */
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	/**
	 * @return the contractor
	 */
	public String getContractor() {
		return contractor;
	}
	/**
	 * @param contractor the contractor to set
	 */
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * @return the cnote
	 */
	public String getCnote() {
		return cnote;
	}
	/**
	 * @param cnote the cnote to set
	 */
	public void setCnote(String cnote) {
		this.cnote = cnote;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the inspector
	 */
	public String getInspector() {
		return inspector;
	}
	/**
	 * @param inspector the inspector to set
	 */
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	/**
	 * @return the orderNo
	 */
	public Integer getOrderNo() {
		return orderNo;
	}
	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * @return the createDept
	 */
	public String getCreateDept() {
		return createDept;
	}
	/**
	 * @param createDept the createDept to set
	 */
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	private Integer orderNo;
	private String createBy;
	private String createDept;
	private Date createTime;
	
}
