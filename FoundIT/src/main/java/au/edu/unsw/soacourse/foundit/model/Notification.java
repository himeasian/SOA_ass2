package au.edu.unsw.soacourse.foundit.model;

public class Notification {
	private int _nid;
	private String email;
	private String message;
	private String from;
	public int get_nid() {
		return _nid;
	}
	public String getEmail() {
		return email;
	}
	public String getMessage() {
		return message;
	}
	public void set_nid(int _nid) {
		this._nid = _nid;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
}
