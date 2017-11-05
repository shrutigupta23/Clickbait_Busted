package gwap.gameplay.screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import gwap.database.PlayerDataService;
import gwap.gameplay.support.GamePlay;
import gwap.models.dao.News;
import gwap.models.dao.Player;
import gwap.utilities.ApplicationContext;
import gwap.utilities.ScreenUpdateEngine;
import gwap.utilities.SoundManager;

/**
 * @author shruti An abstract class representing common operations between
 *         Player One and Player Two screen
 */
public abstract class PlayerScreen {

	protected boolean setupDone;
	protected News currentQuestion;
	protected Timer timer;

	protected JFrame frame;
	protected JTextArea instruction;
	protected JTextArea newsContent;
	protected JLabel scoreKeyword;
	protected JLabel score;
	protected JButton submit;
	protected JLabel timeKeyword;
	protected JLabel timeCounter;
	protected JLabel labelUsername;
	protected JLabel personIcon;
	protected JPanel panel;
	protected JScrollPane scrollPanel;

	protected String playerUsername;
	protected GamePlay gamePlay;
	protected TimerCounter timerObject;

	protected boolean start;

	public PlayerScreen() {

	}

	public int getScore() {
		return Integer.parseInt(score.getText());
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public PlayerScreen(String playerUsername) {
		this.playerUsername = playerUsername;
		frame = ApplicationContext.INSTANCE.getMainFrame();

	}

	public String getPlayerUsername() {
		return playerUsername;
	}

	public void startGame(String opponent) {
		JFrame mainFrame = ApplicationContext.INSTANCE.getMainFrame();
		mainFrame.getContentPane().removeAll();
		JTextArea text = new JTextArea("Opponent Found. \n" + "You will be playing against " + opponent);
		mainFrame.add(text);
		mainFrame.validate();
	}

	public void setup() {
		if (!frame.isVisible()) {
			frame.setVisible(true);
		} else {
			JFrame oldFrame = frame;
			int width = oldFrame.getWidth();
			width = ((width / 2) - 100);
			int height = oldFrame.getHeight();
			JFrame newFrame = new JFrame();
			newFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			newFrame.setSize(width, height);
			frame = newFrame;
			oldFrame.dispose();
			newFrame.setVisible(true);
		}

		instruction = new JTextArea();
		instruction.setBackground(Color.BLACK);
		instruction.setForeground(Color.WHITE);
		instruction.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		instruction.setAlignmentY(JTextArea.CENTER_ALIGNMENT);

		instruction.setFont(instruction.getFont().deriveFont(Font.BOLD));
		instruction.setBorder(BorderFactory.createLineBorder(Color.BLACK, 30));
		instruction.setEditable(false);
		instruction.setLineWrap(true);
		instruction.setWrapStyleWord(true);

		scoreKeyword = new JLabel("Score : ");
		scoreKeyword.setForeground(Color.BLACK);
		score = new JLabel("0");
		score.setForeground(Color.BLACK);

		JPanel scorePanel = new JPanel(new FlowLayout());
		scorePanel.add(scoreKeyword);
		scorePanel.add(score);
		scorePanel.setBackground(Color.RED);
		scorePanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
		scorePanel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		scorePanel.setAlignmentY(JLabel.CENTER_ALIGNMENT);

		timeKeyword = new JLabel("Time : ");
		timeKeyword.setForeground(Color.BLACK);

		timeCounter = new JLabel("30");
		timeCounter.setForeground(Color.BLACK);

		JPanel timePanel = new JPanel(new FlowLayout());
		timePanel.add(timeKeyword);
		timePanel.add(timeCounter);
		timeCounter.setVisible(false);
		timeKeyword.setVisible(false);
		timePanel.setBackground(Color.RED);
		timePanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
		timePanel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		timePanel.setAlignmentY(JLabel.CENTER_ALIGNMENT);

		JPanel timeScorePanel = new JPanel(new GridLayout(2, 1));
		timeScorePanel.add(scorePanel);
		timeScorePanel.add(timePanel);
		timeScorePanel.setBackground(Color.BLACK);
		timeScorePanel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		timeScorePanel.setAlignmentY(JLabel.CENTER_ALIGNMENT);

		submit = new JButton();
		submit.setActionCommand("submit");
		submit.setEnabled(false);
		submit.addActionListener(new SubmitListener());
		ImageIcon buttonImg = new ImageIcon(Paths.get("src", "main", "resources", "submit.png").toString());
		submit.setBackground(Color.BLACK);
		submit.setFocusPainted(false);
		submit.setBorderPainted(false);
		submit.setContentAreaFilled(false);
		submit.setIcon(buttonImg);
		submit.setAlignmentX(JButton.CENTER_ALIGNMENT);
		submit.setAlignmentY(JButton.CENTER_ALIGNMENT);

		start = true;

		labelUsername = new JLabel(playerUsername);
		labelUsername.setForeground(Color.BLACK);
		labelUsername.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		labelUsername.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		personIcon = new JLabel(ApplicationContext.INSTANCE.getPersonIcon());
		personIcon.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		personIcon.setAlignmentY(JLabel.CENTER_ALIGNMENT);

		JPanel usernamePanel = new JPanel();
		BoxLayout layout = new BoxLayout(usernamePanel, BoxLayout.Y_AXIS);
		usernamePanel.setLayout(layout);
		usernamePanel.add(personIcon);
		usernamePanel.add(labelUsername);
		usernamePanel.setBackground(Color.RED);
		usernamePanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));

		newsContent = new JTextArea();
		newsContent.setBackground(new Color(248, 236, 194));
		newsContent.setFont(newsContent.getFont().deriveFont(Font.BOLD));
		newsContent.setWrapStyleWord(true);
		newsContent.setLineWrap(true);
		newsContent.setBorder(BorderFactory.createLineBorder(new Color(248, 236, 194), 20));
		scrollPanel = new JScrollPane(newsContent);
		scrollPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		scrollPanel.setBackground(Color.BLACK);

		JPanel topRow = new JPanel(new BorderLayout());
		topRow.add(usernamePanel, BorderLayout.WEST);
		topRow.add(timeScorePanel, BorderLayout.EAST);
		topRow.add(instruction, BorderLayout.CENTER);
		topRow.add(Box.createRigidArea(new Dimension(topRow.getWidth(), 10)), BorderLayout.SOUTH);
		topRow.setBackground(Color.BLACK);

		timerObject = new TimerCounter(30);
		timer = new Timer(1000, timerObject);
		timer.setInitialDelay(0);

		panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 20));
		panel.setBackground(Color.BLACK);
		panel.setLayout(new BorderLayout());
		panel.add(topRow, BorderLayout.NORTH);
		panel.add(scrollPanel, BorderLayout.CENTER);

	}

	abstract public void displayQuestion(Integer newsId);

	abstract public void opponentMoved(String answer);

	abstract public void answerReceived();

	public void setGamePlay(GamePlay gamePlay) {
		this.gamePlay = gamePlay;
	}

	public void startTimer() {

		timerObject.setCounter(30);
		timeKeyword.setVisible(true);
		timeCounter.setVisible(true);
		timer.start();
		SoundManager.INSTANCE.playDuringGameSound();

	}

	public void stopTimer() {
		timer.stop();
		SoundManager.INSTANCE.stopDuringGameSound();
		timeKeyword.setVisible(true);
		timeCounter.setVisible(false);
	}

	public void timeUp() {
		stopTimer();
		notifyPlayerMoved("");
		showMessage("Oops! Time's up. No answer received", 2000);
	}

	public void showMessage(String msg, int showTime) {

		JDialog message = new JDialog(frame);
		JLabel msgL = new JLabel(msg);
		msgL.setForeground(Color.BLACK);
		msgL.setBackground(Color.RED);
		msgL.setOpaque(true);
		msgL.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		message.add(msgL);
		message.setTitle(playerUsername);
		message.setAutoRequestFocus(true);
		ScreenUpdateEngine.INSTANCE.formatAndShowDialog(message, frame);


		disposeDialog(message, showTime);
	}

	private void disposeDialog(JDialog dialog, int showTime) {
		Timer timer = new Timer(showTime, new DisposeDialog(dialog));
		timer.setRepeats(false);
		timer.start();

	}

	public void stopGame() {

		showMessage("Game over. Your score is " + score.getText(), 5000);
	}

	public abstract void notifyPlayerMoved(String answer);

	public void updateScore(int awardedPoints) {
		int currentScore = Integer.parseInt(score.getText());
		currentScore += awardedPoints;
		score.setText(String.valueOf(currentScore));
	}

	public void onIncorrect() {
		start = false;
		SoundManager.INSTANCE.playIncorrectSound();
		showMessage("Oops. Wrong", 2000);
		nextQuestion();
	}

	public void nextQuestion() {
		while (start) {

		}
		gamePlay.showNextQuestion();

	}

	public void onCorrect() {
		start = false;
		SoundManager.INSTANCE.playCorrectSound();
		updateScore(100);
		showMessage("Awesome. Correct. You score 100 points", 2000);
		nextQuestion();

	}

	protected class TimerCounter implements ActionListener {
		Integer counter;

		public TimerCounter(int counter) {

			this.counter = counter;
		}

		public void setCounter(int newValue) {
			synchronized (counter) {

				this.counter = newValue;
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (start) {
				if (counter > 0) {
					timeCounter.setText(String.valueOf(counter));
					counter--;
				} else if (counter == 0) {
					timeUp();
				}

			}
		}

	}

	protected class DisposeDialog implements ActionListener {
		private JDialog dialog;

		public DisposeDialog(JDialog dialog) {

			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.setVisible(false);
			dialog.dispose();
			start = true;

		}

	}

	private class SubmitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("submit")) {
				answerReceived();

			}

		}

	}

	public void showLeaderboard() {
		int oldScore = PlayerDataService.getHighestScore(playerUsername);
		int currentScore = Integer.parseInt(score.getText());
		PlayerDataService.updateScore(playerUsername, currentScore);

		List<Player> topTen = PlayerDataService.getTopTenPlayers();

		JPanel leaderboard = new JPanel(new GridLayout(11, 2, 5, 20));
		leaderboard.setBackground(Color.BLACK);
		leaderboard.setOpaque(true);
		leaderboard.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
		leaderboard.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		leaderboard.setAlignmentY(JPanel.CENTER_ALIGNMENT);

		JLabel ul = new JLabel("username");
		ul.setBackground(Color.red);
		ul.setForeground(Color.black);
		ul.setOpaque(true);

		JLabel pl = new JLabel("points");
		pl.setBackground(Color.red);
		pl.setForeground(Color.black);
		pl.setOpaque(true);

		leaderboard.add(ul);
		leaderboard.add(pl);

		for (Player player : topTen) {
			JLabel usernameLabel = new JLabel(player.getUsername());
			usernameLabel.setBackground(Color.red);
			usernameLabel.setForeground(Color.black);
			usernameLabel.setOpaque(true);
			leaderboard.add(usernameLabel);

			JLabel pointsLabel = new JLabel(String.valueOf(player.getHighestScore()));
			pointsLabel.setBackground(Color.red);
			pointsLabel.setForeground(Color.black);
			pointsLabel.setOpaque(true);
			leaderboard.add(pointsLabel);
			if (player.getUsername().equalsIgnoreCase(playerUsername)) {
				usernameLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
				pointsLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));

			}

		}

		JLabel message = new JLabel();
		message.setBackground(Color.red);
		message.setForeground(Color.black);
		message.setOpaque(true);
		message.setHorizontalAlignment(JLabel.CENTER);
		message.setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
		if (currentScore > oldScore)
			message.setText("Awesome! You scored a new high score of " + currentScore + " points ");

		else
			message.setText("Too bad. You couldn't beat your old high score of " + oldScore + " points ");

		JPanel display = new JPanel();
		display.setLayout(new BoxLayout(display, BoxLayout.Y_AXIS));
		display.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		display.add(message);
		display.add(Box.createRigidArea(new Dimension(leaderboard.getWidth(), 20)));
		display.add(leaderboard);
		frame.getContentPane().removeAll();
		frame.add(display);
		frame.pack();
		frame.validate();
		SoundManager.INSTANCE.playBackgroundSound();

	}

}
