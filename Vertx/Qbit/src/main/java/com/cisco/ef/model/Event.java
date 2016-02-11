package com.cisco.ef.model;

/**
 * Created by kwjang on 2/6/16.
 */
public class Event {
    long packageId;
    String site;
    String bank;
    int lockerId;
    int size;
    long orderDate;

    public Event(long packageId, String site, String bank, int lockerId, int size, long orderDate) {
        this.packageId = packageId;
        this.site = site;
        this.bank = bank;
        this.lockerId = lockerId;
        this.size = size;
        this.orderDate = orderDate;
    }

    public int getLockerId() {
        return lockerId;
    }

    public void setLockerId(int lockerId) {
        this.lockerId = lockerId;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    @Override
    public String toString() {
        return "Event{" +
                "packageId=" + packageId +
                ", site='" + site + '\'' +
                ", bank='" + bank + '\'' +
                ", lockerId=" + lockerId +
                ", size=" + size +
                ", orderDate=" + orderDate +
                '}';
    }
}
