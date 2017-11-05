package gwap.gameplay.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

import gwap.exceptions.PlayerNotFoundException;
import gwap.gameplay.support.GamePlay;
import gwap.models.dto.PlayerDTO;
import gwap.utilities.ApplicationContext;
import gwap.utilities.ScreenUpdateEngine;
import gwap.utilities.SoundManager;

/**
 * @author shruti The first screen a player sees after login
 */
public class GameStartScreen {

	private PlayerDTO player;
	private JFrame mainFrame;

	public GameStartScreen(PlayerDTO player) {
		this.player = player;
	}

	public void launch() throws InterruptedException {

		mainFrame = ApplicationContext.INSTANCE.getMainFrame();
		mainFrame.getContentPane().removeAll();
		ImageIcon newsIcon = ApplicationContext.INSTANCE.getNewsIcon(mainFrame.getWidth(), mainFrame.getHeight());

		JLabel output = new JLabel("Please wait. Finding an opponent...", newsIcon, JLabel.CENTER);
		output.setForeground(Color.RED);
		output.setFont(output.getFont().deriveFont(64.0f));
		output.setHorizontalTextPosition(JLabel.CENTER);
		output.setVerticalTextPosition(JLabel.CENTER);
		output.setHorizontalAlignment(JLabel.CENTER);
		output.setVerticalAlignment(JLabel.CENTER);

		mainFrame.add(output);
		mainFrame.validate();
		simulateSearching();

	}

	private void onOpponentFound() {

		int playerNo = playerNo();
		String opponent = findOpponent();

		JLabel person = new JLabel();
		person.setIcon(ApplicationContext.INSTANCE.getPersonIcon());
		person.setHorizontalAlignment(JLabel.CENTER);
		person.setVerticalAlignment(JLabel.CENTER);

		JTextArea text = new JTextArea(
				"Opponent Found.\nYou will be playing against " + opponent + ".\nYou will start as Player " + playerNo);
		text.setBackground(Color.RED);
		text.setForeground(Color.BLACK);
		text.setFont(text.getFont().deriveFont(Font.BOLD));

		JDialog dialog = new JDialog(mainFrame, "Opponent found", true);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(person);
		panel.add(text);
		panel.setBackground(Color.RED);
		panel.setBorder(BorderFactory.createLineBorder(Color.RED, 20));
		dialog.add(panel);

		ScreenUpdateEngine.INSTANCE.formatAndShowDialog(dialog, mainFrame);
		SoundManager.INSTANCE.playBackgroundSound();

		player.setPlayerNo(playerNo);
		player.setPlaying(true);
		PlayerScreen localPlayer = playerNo == 1 ? new PlayerOneScreen(player.getUsername())
				: new PlayerTwoScreen(player.getUsername());

		PlayerScreen remotePlayer = playerNo != 1 ? new PlayerOneScreen(opponent) : new PlayerTwoScreen(opponent);

		Timer timer = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
				new GamePlay(localPlayer, remotePlayer).launch();

			}
		});
		timer.setRepeats(false);
		timer.start();

	}

	private String findOpponent() {
		System.out.println("opponent ran");
		PlayerDTO opponent = ApplicationContext.INSTANCE.getOnlinePlayer(player.getUsername());
		if (opponent != null) {
			opponent.setPlaying(true);
			return opponent.getUsername();
		} else
			throw new PlayerNotFoundException();
	}

	private int playerNo() {
		// int number = new Random().nextInt((1 - 0) + 1) + 0;
		// return (number == 0 ? 1 : 2);
		return 1;
	}

	private void simulateSearching() {
		Timer timer = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onOpponentFound();

			}
		});
		timer.setRepeats(false);
		timer.start();
	}
}
