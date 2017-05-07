package au.edu.unsw.soacourse.model;

public class Review {
	private int _reviewID;
	private int _appID;
	private String ReviewDetails;
	private String Comments;
	private String Decision;
	public int get_reviewID() {
		return _reviewID;
	}
	public void set_reviewID(int _reviewID) {
		this._reviewID = _reviewID;
	}
	public int get_appID() {
		return _appID;
	}
	public void set_appID(int _appID) {
		this._appID = _appID;
	}
	public String getReviewDetails() {
		return ReviewDetails;
	}
	public void setReviewDetails(String reviewDetails) {
		ReviewDetails = reviewDetails;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	public String getDecision() {
		return Decision;
	}
	public void setDecision(String decision) {
		Decision = decision;
	}
	
}
