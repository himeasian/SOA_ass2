package au.edu.unsw.soacourse.model;

/**
 * Model for Poll type
 * @author trungisme
 *
 */
public class PollModel {
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
	public String getTitle() {
		return title;
	}
	public String getType() {
		return type;
	}
	public String getOptions() {
		return options;
	}
	public String getComments() {
		return comments;
	}
	public String getFinalChoice() {
		return finalChoice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void set_pId(int _pId) {
		this._pId = _pId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setOptions(String options) {
		this.options = options;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setFinalChoice(String final_choice) {
		this.finalChoice = final_choice;
	}
	
}
