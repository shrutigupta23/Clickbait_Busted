package gwap.login.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.Timer;

import gwap.database.PlayerDataService;
import gwap.login.support.GameStartListener;

/**
 * @author shruti
 * Screen if player wants to play as guest
 */
public class GuestScreen {

	private Window window;
	private String guestUsername;

	public GuestScreen(Window window) {
		this.window = window;
	}

	public Window launch() {

		this.guestUsername = getGuestUsername();
		JTextArea info = new JTextArea("Playing as " + guestUsername + "\nNote: You can't save scores in guest mode");
		info.setFont(info.getFont().deriveFont(Font.BOLD));
		info.setForeground(Color.BLACK);
		info.setBackground(Color.RED);
		info.setBorder(BorderFactory.createLineBorder(Color.RED, 20));

		window.add(info);
		window.setBackground(Color.BLACK);

		Timer timer = new Timer(3000, new GameStartListener(window, guestUsername));
		timer.setRepeats(false);
		timer.start();
		return window;
	}

	private String getGuestUsername() {

		String guestUsername = PlayerDataService.generateGuestPlayer();
		return guestUsername;

	}
}
