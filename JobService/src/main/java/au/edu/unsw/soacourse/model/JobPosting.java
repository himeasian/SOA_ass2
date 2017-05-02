package au.edu.unsw.soacourse.model;

public class JobPosting {
	
	private int _jobID;
	private String CompanyName;
	private String SalaryRate;
	private String PositionType;
	private String Location;
	private String JobDescription;
	private String Status;
	private String Classification;
	public int get_jobID() {
		return _jobID;
	}
	public void set_jobID(int _jobID) {
		this._jobID = _jobID;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getSalaryRate() {
		return SalaryRate;
	}
	public void setSalaryRate(String salaryRate) {
		SalaryRate = salaryRate;
	}
	public String getPositionType() {
		return PositionType;
	}
	public void setPositionType(String positionType) {
		PositionType = positionType;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getJobDescription() {
		return JobDescription;
	}
	public void setJobDescription(String jobDescription) {
		JobDescription = jobDescription;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getClassification() {
		return Classification;
	}
	public void setClassification(String classification) {
		Classification = classification;
	}
	
	
	
	
}
