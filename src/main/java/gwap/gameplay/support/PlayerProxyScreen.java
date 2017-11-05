package gwap.gameplay.support;

import java.util.concurrent.ExecutionException;

import gwap.exceptions.RemoteTaskException;
import gwap.gameplay.screens.PlayerOneScreen;
import gwap.gameplay.screens.PlayerScreen;
import gwap.messaging.InstantiateOtherPlayerMessage;
import gwap.messaging.Message;
import gwap.messaging.MessageSender;

/**
 * @author shruti This is a proxy class that represents screen of the player on
 *         the remote JVM On every method call, it uses the Messaging Service to
 *         pass the method invocation to the remote object
 */
public class PlayerProxyScreen extends PlayerScreen {

	private PlayerScreen remotePlayer;

	public PlayerProxyScreen(PlayerScreen remotePlayer) {
		this.remotePlayer = remotePlayer;
		int playerType = remotePlayer instanceof PlayerOneScreen ? 1 : 2;
		InstantiateOtherPlayerMessage msg = new InstantiateOtherPlayerMessage();
		msg.setPlayerType(playerType);
		msg.setArg(remotePlayer.getPlayerUsername());
		MessageSender.sendMessage(msg);
	}

	@Override
	public String getPlayerUsername() {
		return remotePlayer.getPlayerUsername();
	}

	@Override
	public void displayQuestion(Integer newsId) {
		MessageSender.sendMessage(new Message("displayQuestion", Integer.class, newsId));

	}

	@Override
	public void setGamePlay(GamePlay gamePlay) {
		MessageSender.sendMessage(new Message("setGamePlay", GamePlay.class, gamePlay));

	}

	@Override
	public void stopGame() {
		MessageSender.sendMessage(new Message("stopGame", null, null));

	}

	@Override
	public void showLeaderboard() {
		MessageSender.sendMessage(new Message("showLeaderboard", null, null));

	}

	@Override
	public void startGame(String opponent) {
		MessageSender.sendMessage(new Message("startGame", String.class, opponent));
	}

	@Override
	public void opponentMoved(String answer) {
		MessageSender.sendMessage(new Message("opponentMoved", String.class, answer));

	}

	@Override
	public void answerReceived() {
		MessageSender.sendMessage(new Message("answerReceived", null, null));

	}

	@Override
	public void notifyPlayerMoved(String answer) {
		MessageSender.sendMessage(new Message("notifyPlayerMoved", String.class, answer));

	}

	@Override
	public int getScore() {
		Object result = null;
		try {
			result = MessageSender.sendAndGetReply(new Message("getScore", null, null));
		} catch (InterruptedException | ExecutionException e) {
			throw new RemoteTaskException(e);
		}
		return (Integer) result;

	}

}
