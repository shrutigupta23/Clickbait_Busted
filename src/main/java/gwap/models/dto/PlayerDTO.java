package gwap.models.dto;

/**
 * @author shruti
 * Convenience class for finding online players
 */
public class PlayerDTO {

	private String username;
	private boolean isPlaying;
	private int playerNo;
	
	public PlayerDTO(String username){
		this.username = username;
	}
	
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	public String getUsername() {
		return username;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}
	
	public int getPlayerNo() {
		return playerNo;
	}
}
