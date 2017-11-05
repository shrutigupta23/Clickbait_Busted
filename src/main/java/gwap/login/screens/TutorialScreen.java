package gwap.login.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;

/**
 * @author shruti
 * Screen to show game instructions to player
 */
public class TutorialScreen {

	private Window window;

	public TutorialScreen(Window window) {
		this.window = window;
	}

	public Window launch() {

		JTextArea info = new JTextArea(""
				+ "Player 1 : Read the article and enter an appropriate headline.\n"
				+ "Player 2: Choose the appropriate headline for the article amongst the given options.\n"
				+ "If player2 enters the headline entered by player1, both players score 100 points.\n"
				+ "Hint : If player 2 chooses the most popular headline, player2 scores 200 points");
		info.setFont(info.getFont().deriveFont(Font.BOLD));
		info.setForeground(Color.BLACK);
		info.setBackground(Color.RED);
		info.setBorder(BorderFactory.createLineBorder(Color.RED, 20));

		window.add(info);
		window.setBackground(Color.BLACK);

		return window;
	}

	}
