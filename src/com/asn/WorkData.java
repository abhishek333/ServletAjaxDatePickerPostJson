/**
 * 
 */
package com.asn;

import java.util.Date;

/**
 * A class to hold WorkData information
 * 
 * @author Abhishek
 *
 */
public class WorkData {
	
	private Long pId;
	private Date date;
	private String name;
	private String address;
	private Integer dayHours;
	private Integer dayMinutes;
	private Float allDayHours;
	private Integer kmDistanceAddr;
	private Float timeToTravel;
		
	public WorkData(Long pId, Date date, String name, String address,
			Integer dayHours, Integer dayMinutes, Float allDayHours,
			Integer kmDistanceAddr, Float timeToTravel) {
		super();
		this.pId = pId;
		this.date = date;
		this.name = name;
		this.address = address;
		this.dayHours = dayHours;
		this.dayMinutes = dayMinutes;
		this.allDayHours = allDayHours;
		this.kmDistanceAddr = kmDistanceAddr;
		this.timeToTravel = timeToTravel;
	}
	
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getDayHours() {
		return dayHours;
	}
	public void setDayHours(Integer dayHours) {
		this.dayHours = dayHours;
	}
	public Integer getDayMinutes() {
		return dayMinutes;
	}
	public void setDayMinutes(Integer dayMinutes) {
		this.dayMinutes = dayMinutes;
	}
	public Float getAllDayHours() {
		return allDayHours;
	}
	public void setAllDayHours(Float allDayHours) {
		this.allDayHours = allDayHours;
	}
	public Integer getKmDistanceAddr() {
		return kmDistanceAddr;
	}
	public void setKmDistanceAddr(Integer kmDistanceAddr) {
		this.kmDistanceAddr = kmDistanceAddr;
	}
	public Float getTimeToTravel() {
		return timeToTravel;
	}
	public void setTimeToTravel(Float timeToTravel) {
		this.timeToTravel = timeToTravel;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkData [pId=");
		builder.append(pId);
		builder.append(", date=");
		builder.append(date);
		builder.append(", name=");
		builder.append(name);
		builder.append(", address=");
		builder.append(address);
		builder.append(", dayHours=");
		builder.append(dayHours);
		builder.append(", dayMinutes=");
		builder.append(dayMinutes);
		builder.append(", allDayHours=");
		builder.append(allDayHours);
		builder.append(", kmDistanceAddr=");
		builder.append(kmDistanceAddr);
		builder.append(", timeToTravel=");
		builder.append(timeToTravel);
		builder.append("]");
		return builder.toString();
	}

}
