package au.edu.unsw.soacourse.foundit.model;

public class Poll {
	private int _pId;
	private String title;
	private String description;
	private String type;
	private String options;
	private String comments;
	private String finalChoice;
	public int get_pId() {
		return _pId;
	}
	public void set_pId(int _pId) {
		this._pId = _pId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOptions() {
		return options;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getFinalChoice() {
		return finalChoice;
	}
	public void setFinalChoice(String finalChoice) {
		this.finalChoice = finalChoice;
	}
	
	
}
