package au.edu.unsw.soacourse.model;

public class Application {

	private int _appID;
	private int _jobID;
	private String CompanyName;
	private String SalaryRate;
	private String PositionType;
	private String Location;
	private String JobDescription;
	private String Status;
	private String Classification;
	
	public int getJobID(){
		return _jobID;
	}
	
	public int getAppID(){
		return _appID;
	}
	
	public void setJobId(int jobid){
		this._jobID=jobid;
	}
	
	public String getCompanyName(){
		return CompanyName;
	}
	public void setCompanyName(String companyname){
		this.CompanyName=companyname;
	}
	
	public String getSalaryRate(){
		return SalaryRate;
	}
	
	public void setSalaryRate(String salaryrate){
		this.SalaryRate=salaryrate;
	}
	
	public String getPositionType(){
		return PositionType;
	}
	
	public void setPositionType(String positiontype){
		this.PositionType=positiontype;
	}
	
	public String getLocation(){
		return Location;
	}
	
	public void setLocation(String location){
		this.Location=location;
	}
	
	public String getJobDescription(){
		return JobDescription;
	}
	
	public void setJobDescription(String jobdescription){
		this.JobDescription=jobdescription;
	}
	
	public String getStatus(){
		return Status;
	}
	
	public void setStatus(String status){
		this.Status=status;
	}
	
	public String getClassification(){
		return Classification;
	}
	
	public void setClassification(String classification){
		this.Classification=classification;
	}
	
}
