package au.edu.unsw.soacourse.model;

/**
 * Model for Vote type
 * @author trungisme
 *
 */
public class VoteModel {
	private int _voteId;
	private int _pId;
	private String participant_name;
	private String chosen_option;
	
	public int get_voteId() {
		return _voteId;
	}
	public int get_pId() {
		return _pId;
	}
	public String getParticipant_name() {
		return participant_name;
	}
	public String getChosen_option() {
		return chosen_option;
	}
	public void set_voteId(int _voteId) {
		this._voteId = _voteId;
	}
	public void set_pId(int _pId) {
		this._pId = _pId;
	}
	public void setParticipant_name(String participant_name) {
		this.participant_name = participant_name;
	}
	public void setChosen_option(String chosen_option) {
		this.chosen_option = chosen_option;
	}
}
