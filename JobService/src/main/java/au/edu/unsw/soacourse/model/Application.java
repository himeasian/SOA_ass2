package au.edu.unsw.soacourse.model;

public class Application {

	private int _appID;
	private int _jobID;
	private String CandidatesDetails;
	private String CoverLetter;
	private String Status;
	private String Attachment1;
	private String Attachment2;
	public int get_appID() {
		return _appID;
	}
	public void set_appID(int _appID) {
		this._appID = _appID;
	}
	public int get_jobID() {
		return _jobID;
	}
	public void set_jobID(int _jobID) {
		this._jobID = _jobID;
	}
	public String getCandidatesDetails() {
		return CandidatesDetails;
	}
	public void setCandidatesDetails(String candidatesDetails) {
		CandidatesDetails = candidatesDetails;
	}
	public String getCoverLetter() {
		return CoverLetter;
	}
	public void setCoverLetter(String coverLetter) {
		CoverLetter = coverLetter;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getAttachment1() {
		return Attachment1;
	}
	public void setAttachment1(String attachment1) {
		Attachment1 = attachment1;
	}
	public String getAttachment2() {
		return Attachment2;
	}
	public void setAttachment2(String attachment2) {
		Attachment2 = attachment2;
	}
	
	
	
	
}
