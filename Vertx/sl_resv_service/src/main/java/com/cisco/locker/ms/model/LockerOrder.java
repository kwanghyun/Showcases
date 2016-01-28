package com.cisco.locker.ms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LockerOrder {

	@JsonProperty("_id")
	String id;
	String site;
	String bank;
	String size;
	String orderDate;
	String packageId;
	String expectedDepositDate;
	String depositReleaseCode;
	String pickupReleaseCode;
	int orderType;

	@JsonProperty("_id")
	public String getId() {
		return id;
	}

	@JsonProperty("_id")
	public void setId(String id) {
		this.id = id;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getExpectedDepositDate() {
		return expectedDepositDate;
	}

	public void setExpectedDepositDate(String expectedDepositDate) {
		this.expectedDepositDate = expectedDepositDate;
	}

	public String getDepositReleaseCode() {
		return depositReleaseCode;
	}

	public void setDepositReleaseCode(String depositReleaseCode) {
		this.depositReleaseCode = depositReleaseCode;
	}

	public String getPickupReleaseCode() {
		return pickupReleaseCode;
	}

	public void setPickupReleaseCode(String pickupReleaseCode) {
		this.pickupReleaseCode = pickupReleaseCode;
	}

	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

}
