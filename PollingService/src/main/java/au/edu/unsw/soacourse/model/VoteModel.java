package au.edu.unsw.soacourse.model;

/**
 * Model for Vote type
 * @author trungisme
 *
 */
public class VoteModel {
	private int _voteId;
	private int _pId;
	private String participantName;
	private String chosenOption;
	public int get_voteId() {
		return _voteId;
	}
	public int get_pId() {
		return _pId;
	}
	public String getParticipantName() {
		return participantName;
	}
	public String getChosenOption() {
		return chosenOption;
	}
	public void set_voteId(int _voteId) {
		this._voteId = _voteId;
	}
	public void set_pId(int _pId) {
		this._pId = _pId;
	}
	public void setParticipantName(String participantName) {
		this.participantName = participantName;
	}
	public void setChosenOption(String chosenOption) {
		this.chosenOption = chosenOption;
	}
	
}
