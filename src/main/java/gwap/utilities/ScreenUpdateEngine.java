package gwap.utilities;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;

import gwap.gameplay.screens.PlayerScreen;
import gwap.messaging.Message;

/**
 * @author shruti This class is used to carry out UI related operations in its
 *         own thread. It is used in conjunction with MessageReceiver class to
 *         invoke the methods on the UI using JAVA Reflections.
 */
public enum ScreenUpdateEngine {

	INSTANCE;
	private ExecutorService engine = Executors.newSingleThreadExecutor();

	public Future submitTask(PlayerScreen playerScreen, Message message) {
		Future result = engine.submit(new ScreenOperation(playerScreen, message));
		return result;
	}

	private class ShowDialog implements Runnable {
		private JDialog dialog;
		private JFrame frame;

		public ShowDialog(JDialog dialog, JFrame frame) {
			this.dialog = dialog;
			this.frame = frame;
		}

		@Override
		public void run() {
			dialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.YELLOW, 6, true));
			dialog.setBackground(Color.RED);
			dialog.pack();
			int[] location = getCentralLocation(frame, dialog);
			dialog.setLocation(location[0], location[1]);
			if (!dialog.isVisible())
				dialog.setVisible(true);
			else
				dialog.validate();

		}
	}

	private class ScreenOperation implements Callable {
		private PlayerScreen playerScreen;
		private Message message;

		public ScreenOperation(PlayerScreen playerScreen, Message message) {
			this.playerScreen = playerScreen;
			this.message = message;
		}

		@Override
		public Object call() throws Exception {
			try {
				Class<? extends Object> classObject = playerScreen.getClass();

				if (message.getArgumentType() == null) {
					Method method = classObject.getMethod(message.getMethodName(), new Class[] {});
					return method.invoke(playerScreen, null);
				} else {
					Method method = classObject.getMethod(message.getMethodName(),
							new Class[] { message.getArgumentType() });
					return method.invoke(playerScreen, message.getArg());
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
				e.getCause().printStackTrace();
				return null;
			}
		}

	}

	public void formatAndShowDialog(JDialog dialog, JFrame frame) {
		engine.submit(new ShowDialog(dialog, frame));
	}

	public int[] getCentralLocation(JFrame frame, JDialog dialog) {
		int dialogWidth = dialog.getWidth();
		int dialogHeight = dialog.getHeight();
		int frameWidth = frame.getWidth();
		int frameHeight = frame.getHeight();
		int x = (frameWidth / 2) - (dialogWidth / 2);
		int y = (frameHeight / 2) - (dialogHeight / 2);
		int frameX = frame.getLocation().x;
		int frameY = frame.getLocation().y;
		x = frameX + x;
		y = frameY + y;
		int[] coOrdinates = { x, y };
		return coOrdinates;
	}

}
