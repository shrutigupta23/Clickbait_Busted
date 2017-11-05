package gwap.login.screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;

import gwap.database.PlayerDataService;
import gwap.login.support.GameStartListener;

/**
 * @author shruti
 * Screen if players wants to sign up / sign in
 */
public class SignInScreen {

	private Window window;
	private JTextField username;
	private JPasswordField password;
	private JLabel message;

	public SignInScreen(Window window) {
		this.window = window;
	}

	public Window launch() {
		
		JLabel uname = new JLabel("username : ");
		JLabel passwd = new JLabel("password : ");
		uname.setForeground(Color.BLACK);
		passwd.setForeground(Color.BLACK);

		

		username = new JTextField("");
		password = new JPasswordField("");
		username.setColumns(20);
		password.setColumns(20);

		
		JPanel panelUname = new JPanel();
		panelUname.setLayout(new FlowLayout());
		panelUname.add(uname);
		panelUname.add(username);
		panelUname.setBackground(Color.RED);
		
		
	
		JPanel panelPasswd = new JPanel();
		panelPasswd.setLayout(new FlowLayout());
		panelPasswd.add(passwd);
		panelPasswd.add(password);
		panelPasswd.setBackground(Color.RED);

		
		JButton signIn = new JButton("Sign In");
		signIn.setBackground(Color.BLACK);
		signIn.setForeground(Color.YELLOW);
		signIn.setFocusPainted(false);

		JButton signUp = new JButton("Sign Up");
		signUp.setBackground(Color.BLACK);
		signUp.setForeground(Color.YELLOW);

		
		JPanel buttons = new JPanel();
		buttons.setSize(window.getWidth(), buttons.getHeight());
		buttons.setLayout(new FlowLayout());
		buttons.add(signIn);
		buttons.add(signUp);
		buttons.setBackground(Color.red);



		ButtonClickListener buttonClickListener = new ButtonClickListener();
		signIn.addActionListener(buttonClickListener);
		signUp.addActionListener(buttonClickListener);

		window.addWindowFocusListener(new WindowFocusChanger(signIn));

		message = new JLabel("");
		message.setVisible(false);
		message.setSize(50, 10);

		Component gapComponent = Box.createRigidArea(new Dimension(0, 10));
		JPanel combined = new JPanel();
		combined.setLayout(new BoxLayout(combined, BoxLayout.Y_AXIS));
		combined.add(panelUname);
		combined.add(gapComponent);
		combined.add(panelPasswd);
		combined.add(gapComponent);
		combined.add(message);
		combined.add(gapComponent);
		combined.add(buttons);
		combined.add(gapComponent);
		combined.setBackground(Color.RED);
		combined.setBorder(BorderFactory.createLineBorder(Color.RED, 20));

		
		
		window.add(combined);

		return window;

	}

	private class WindowFocusChanger extends WindowAdapter {
		private Component component;

		public WindowFocusChanger(Component component) {
			this.component = component;
		}

		public void windowGainedFocus(WindowEvent e) {
			component.requestFocusInWindow();
		}

	}

	private class ButtonClickListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (isInputValid()) {
				if (e.getActionCommand().equals("Sign Up")) {
					boolean successful = insertNewPlayer(username.getText(), String.valueOf(password.getPassword()));
					if (successful) {
						displayMessage(Color.GREEN, "Signed up successfully");
						goToGameStartScreen();

					} else {
						displayMessage(Color.YELLOW, "Username already exists. Please try again");
						username.setText("");
						password.setText("");
					}

				} else if (e.getActionCommand().equals("Sign In")) {
					boolean authorized = authenticatePlayer(username.getText(), String.valueOf(password.getPassword()));
					if (authorized) {
						displayMessage(Color.GREEN, "Signed in successfully");
						goToGameStartScreen();
					} else {
						displayMessage(Color.YELLOW, "Incorrect username/password. Please try again");
						password.setText("");
					}

				}
			}
		}

	}

	private boolean insertNewPlayer(String username, String password) {
		try{
		PlayerDataService.insertPlayer(username, password);
		return true;
		}
		catch(org.hibernate.exception.GenericJDBCException e){
			e.printStackTrace();
			
			return false;
		}

	}

	private void goToGameStartScreen() {

		Timer timer = new Timer(3000, new GameStartListener(window, username.getText()));
		timer.setRepeats(false);
		timer.start();
	}

	private boolean authenticatePlayer(String username, String password) {

			return PlayerDataService.authenticatePlayer(username, password);
		
	}

	private void displayMessage(Color color, String msg) {
		message.setForeground(color);
		message.setText(msg);
		message.setVisible(true);
	}

	private boolean isInputValid() {
		if (message.isVisible())
			message.setVisible(false);
		if (username.getText().isEmpty() || String.valueOf(password.getPassword()).isEmpty()) {
			displayMessage(Color.RED, "Username/Password is empty!!!");
			return false;
		} else
			return true;
	}
}
