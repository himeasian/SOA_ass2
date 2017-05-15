package au.edu.unsw.soacourse.foundit.bean;

import au.edu.unsw.soacourse.foundit.model.Application;

public class JobApplication extends Application {
	
	private int phoneNo;
	private String positionType;
	private String companyName;
	
	public int getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(int phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getPositionType() {
		return positionType;
	}
	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
