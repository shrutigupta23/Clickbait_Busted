package gwap.login.screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gwap.utilities.ApplicationContext;
import gwap.utilities.ScreenUpdateEngine;
import gwap.utilities.SoundManager;

/**
 * @author shruti
 * Homepage of the game
 */
public class LoginScreen {

	private JFrame mainFrame;

	public LoginScreen() {
		this.mainFrame = ApplicationContext.INSTANCE.getMainFrame();
	}

	public void launch() {
		
		ImageIcon bgImg = new ImageIcon(Paths.get("src", "main", "resources", "cover.jpg").toString());
		Image scaledImg = bgImg.getImage().getScaledInstance(mainFrame.getWidth(), (mainFrame.getHeight()) / 2, 0);
		bgImg.setImage(scaledImg);
		JLabel image = new JLabel(bgImg);

		JButton button1 = new JButton();
		button1.setActionCommand("Sign In / Sign Up");
		ImageIcon button1Img = new ImageIcon(Paths.get("src", "main", "resources", "signup.png").toString());
		button1.setBackground(Color.BLACK);
		button1.setFocusPainted(false);
		button1.setBorderPainted(false);
		button1.setContentAreaFilled(false);
		button1.setIcon(button1Img);

		JButton button2 = new JButton();
		button2.setActionCommand("Play as Guest");
		ImageIcon button2Img = new ImageIcon(Paths.get("src", "main", "resources", "guestplay.png").toString());
		button2.setBackground(Color.BLACK);
		button2.setFocusPainted(false);
		button2.setBorderPainted(false);
		button2.setContentAreaFilled(false);
		button2.setIcon(button2Img);

		JButton button3 = new JButton();
		button3.setActionCommand("Tutorial");
		ImageIcon button3Img = new ImageIcon(Paths.get("src", "main", "resources", "tutorial.png").toString());
		button3.setBackground(Color.BLACK);
		button3.setFocusPainted(false);
		button3.setBorderPainted(false);
		button3.setContentAreaFilled(false);
		button3.setIcon(button3Img);

		ButtonClickListener clickListener = new ButtonClickListener();

		button1.addActionListener(clickListener);
		button2.addActionListener(clickListener);
		button3.addActionListener(clickListener);

		JPanel panel1 = new JPanel();
		panel1.add(button1);
		panel1.add(button2);
		panel1.add(button3);
		panel1.setBackground(Color.BLACK);
		panel1.setBorder(BorderFactory.createEmptyBorder());

		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());
		panel.add(image, BorderLayout.CENTER);
		panel.setBackground(Color.BLACK);
		panel.add(panel1, BorderLayout.SOUTH);
		mainFrame.add(panel);
		mainFrame.setBackground(Color.BLACK);

		mainFrame.setVisible(true);
		SoundManager.INSTANCE.playBackgroundSound();

	}

	private class ButtonClickListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {

			String command = event.getActionCommand();
			if (command.equals("Sign In / Sign Up")) {
				launchSignInScreen();

			} else if (command.equals("Play as Guest")) {
				launchGuestScreen();
			} else if (command.equals("Tutorial")) {
				launchTutorial();
			}

		}

	}

	private void launchSignInScreen() {
		JDialog dialog = new JDialog(mainFrame, "Sign In / Sign Up", true);
		dialog = (JDialog) new SignInScreen(dialog).launch();
		ScreenUpdateEngine.INSTANCE.formatAndShowDialog(dialog , mainFrame);

	}

	private void launchGuestScreen() {
		JDialog dialog = new JDialog(mainFrame, "Logging as Guest", true);
		dialog = (JDialog) new GuestScreen(dialog).launch();
		ScreenUpdateEngine.INSTANCE.formatAndShowDialog(dialog , mainFrame);

	}

	private void launchTutorial() {
		JDialog dialog = new JDialog(mainFrame, "Tutorial", true);
		dialog = (JDialog) new TutorialScreen(dialog).launch();
		ScreenUpdateEngine.INSTANCE.formatAndShowDialog(dialog , mainFrame);
	}

}
