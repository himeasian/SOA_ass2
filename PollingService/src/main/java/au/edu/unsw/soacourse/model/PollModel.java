package au.edu.unsw.soacourse.model;

/**
 * Model for Poll type
 * @author trungisme
 *
 */
public class PollModel {
	private int _pId;
	private String title;
	private String type;
	private String options;
	private String comments;
	private String final_choice;
	
	
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
	public String getFinal_choice() {
		return final_choice;
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
	public void setFinal_choice(String final_choice) {
		this.final_choice = final_choice;
	}
	
}
