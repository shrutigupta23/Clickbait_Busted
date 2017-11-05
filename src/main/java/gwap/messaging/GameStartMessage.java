package gwap.messaging;

import java.io.Serializable;


/**
 * @author shruti
 * Message to indicate start of game
 */
public class GameStartMessage extends Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String opponent;

	public GameStartMessage(String otherPlayer) {
		this.opponent = otherPlayer;
	}

	public String getOpponent() {
		return opponent;
	}
}
