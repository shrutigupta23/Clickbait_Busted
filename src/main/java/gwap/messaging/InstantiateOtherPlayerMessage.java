package gwap.messaging;


/**
 * @author shruti
 * Message to indicate instantiation of other player
 */
public class InstantiateOtherPlayerMessage extends Message {

	private int playerType;

	public void setPlayerType(int playerType) {
		this.playerType = playerType;
	}

	public int getPlayerType() {
		return playerType;
	}


	private static final long serialVersionUID = 1L;

}
