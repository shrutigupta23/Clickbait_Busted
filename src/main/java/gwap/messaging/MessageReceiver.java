package gwap.messaging;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import gwap.gameplay.screens.PlayerOneScreen;
import gwap.gameplay.screens.PlayerScreen;
import gwap.gameplay.screens.PlayerTwoScreen;
import gwap.utilities.ApplicationContext;
import gwap.utilities.ScreenUpdateEngine;

/**
 * @author shruti
 * Message Receiver class that runs on the remote machine.
 * It contains a queue of requests. 
 * On every new request put in its queue, it passes the task to an execution engine.
 * This class can be plugged in with any queue - based messaging system.
 */
public class MessageReceiver {

	private ArrayWithListener requests = new ArrayWithListener();
	private PlayerScreen playerScreen;

	public void addPlayerRequest(Message message) {
		requests.add(message);
	}

	public Object getImmediateReply(Message message) throws InterruptedException, ExecutionException {
		Future result = ScreenUpdateEngine.INSTANCE.submitTask(playerScreen, message);
		while(!result.isDone()){
			
		}
		return result.get();

	}

	private void onMessageReceived(Message message) {
		if (message instanceof InstantiateOtherPlayerMessage) {
			InstantiateOtherPlayerMessage msg = (InstantiateOtherPlayerMessage) message;
			if (msg.getPlayerType() == 1){
				playerScreen = new PlayerOneScreen((String) msg.getArg());
				playerScreen.setFrame(ApplicationContext.INSTANCE.getRemoteFrame());
			}
			else{
				playerScreen = new PlayerTwoScreen((String) msg.getArg());
				playerScreen.setFrame(ApplicationContext.INSTANCE.getRemoteFrame());

			}
		} 
		else if (playerScreen != null) {
			ScreenUpdateEngine.INSTANCE.submitTask(playerScreen, message);
			requests.remove(message);

		}

	}

	private class ArrayWithListener extends CopyOnWriteArrayList<Message> {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean add(Message message) {
			boolean result = super.add(message);
			onMessageReceived(message);
			return result;

		}

	}
}
