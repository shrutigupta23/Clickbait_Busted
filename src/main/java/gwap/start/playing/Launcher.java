package gwap.start.playing;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import gwap.database.DatabaseConnection;
import gwap.login.screens.LoginScreen;
import gwap.utilities.ApplicationContext;
import gwap.utilities.SoundManager;

/**
 * @author shruti
 * Main Class of Game
 *
 */
public class Launcher {

	static {

		DatabaseConnection.INSTANCE.load();
		ApplicationContext.INSTANCE.load();
		SoundManager.INSTANCE.load();
	}

	public static void main(String[] args) throws InterruptedException {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		JFrame mainFrame = new JFrame("Clickbait_Busted. Brought to you by Fake News Corporation");
		ApplicationContext.INSTANCE.setMainFrame(mainFrame);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setSize(screenSize.width, screenSize.height);

		LoginScreen loginScreen = new LoginScreen();
		loginScreen.launch();

	}
}
