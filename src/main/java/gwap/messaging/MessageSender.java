package gwap.messaging;

import java.util.concurrent.ExecutionException;
/**
 * @author shruti
 * Message Sender class that runs on the source machine.
 * Its job is to put requests in the Message Receiver's queue
 * This class can be plugged in with any queue - based messaging system.
 */
public class MessageSender {

	private static MessageReceiver receiver = new MessageReceiver();

	public static void sendMessage(Message message) {
		// This would be replaced by networking code for 
		// actually sending message to receiver
		receiver.addPlayerRequest(message);
	}

	public static Object sendAndGetReply(Message message) throws InterruptedException, ExecutionException {
		Object result = receiver.getImmediateReply(message);
		return result;

	}

}
