package gwap.gameplay.screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import gwap.database.NewsDataService;
import gwap.utilities.DBExecutionEngine;
import gwap.utilities.SoundManager;

/**
 * @author shruti
 * The screen for player two
 */
public class PlayerTwoScreen extends PlayerScreen {

	private JLabel status;
	private JRadioButton optionOne;
	private JRadioButton optionTwo;
	private JRadioButton optionThree;
	private ButtonGroup allOptions;
	private JLabel secondCounter;
	private Timer secondTimer;
	private SecondTimerCounter secondTimerobj;
	private JLabel hint;

	public PlayerTwoScreen(String playerUsername) {
		super(playerUsername);
	}

	@Override
	public void setup() {
		super.setup();

		instruction.setText("You have 30 seconds to read the article."
				+ "\n\nPost that you will have 10 seconds to choose the appropriate headline");

		status = new JLabel("Waiting for opponent's answer...", SwingConstants.CENTER);
		status.setBackground(Color.RED);
		status.setForeground(Color.BLACK);
		status.setOpaque(true);
		status.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));

		// status.setBorder(BorderFactory.createLineBorder(Color.RED, 2));



		secondCounter = new JLabel("10", SwingConstants.CENTER);
		secondCounter.setForeground(Color.RED);
		secondCounter.setFont(secondCounter.getFont().deriveFont(Font.BOLD));
	

		JPanel submitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		submitPanel.add(submit);
		submitPanel.setBackground(Color.BLACK);


		optionOne = new JRadioButton();
		optionOne.setBackground(Color.CYAN);
		optionOne.setForeground(Color.BLACK);
		optionOne.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		optionOne.setVisible(false);


		optionTwo = new JRadioButton();
		optionTwo.setBackground(Color.CYAN);
		optionTwo.setForeground(Color.BLACK);
		optionTwo.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		optionTwo.setVisible(false);


		optionThree = new JRadioButton();
		optionThree.setBackground(Color.CYAN);
		optionThree.setForeground(Color.BLACK);
		optionThree.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		optionThree.setVisible(false);

		allOptions = new ButtonGroup();
		allOptions.add(optionOne);
		allOptions.add(optionTwo);
		allOptions.add(optionThree);

		GridLayout l2 = new GridLayout(6, 1);
		l2.setVgap(10);
		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(l2);
		optionsPanel.setOpaque(true);
		optionsPanel.setBackground(Color.BLACK);

		hint = new JLabel("Hint : You score bonus points if you guess the most popular headline");
		hint.setFont(hint.getFont().deriveFont(Font.BOLD));
		hint.setForeground(Color.YELLOW);
		hint.setVisible(false);
		hint.setHorizontalAlignment(JLabel.CENTER);

		optionsPanel.add(status);
		optionsPanel.add(optionOne);
		optionsPanel.add(optionTwo);
		optionsPanel.add(optionThree);
		optionsPanel.add(secondCounter);
		optionsPanel.add(hint);

		optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		secondTimerobj = new SecondTimerCounter(10);
		secondTimer = new Timer(1000, secondTimerobj);
		secondTimer.setInitialDelay(0);

		

		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.Y_AXIS));
		

		bottomRow.add(scrollPanel);
		
		bottomRow.add(optionsPanel);
		
		bottomRow.add(submitPanel);

		bottomRow.setBackground(Color.BLACK);
		bottomRow.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		bottomRow.setAlignmentY(JPanel.CENTER_ALIGNMENT);

		panel.add(bottomRow, BorderLayout.SOUTH);


		frame.add(panel);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width / 2), 0);
		frame.setTitle("Player 2 Screen : " + playerUsername);

		frame.validate();

		setupDone = true;
	}

	@Override
	public void displayQuestion(Integer newsId) {
		if (!setupDone)
			setup();

		currentQuestion = NewsDataService.getNews(newsId);
		status.setText("Waiting for opponent's answer...");
		newsContent.setText(currentQuestion.getContent());
		optionOne.setVisible(false);
		optionTwo.setVisible(false);
		optionThree.setVisible(false);
		secondCounter.setVisible(false);
		hint.setVisible(false);
		submit.setEnabled(true);
		startTimer();
	}

	@Override
	public void answerReceived() {
		// Read answer
		submit.setEnabled(false);
		stopTimer();
		stopSecondTimer();
		JRadioButton selected = null;
		if (optionOne.isVisible() && optionOne.isSelected())
			selected = optionOne;
		else if (optionTwo.isVisible() && optionTwo.isSelected())
			selected = optionTwo;
		else if (optionThree.isVisible() && optionThree.isSelected())
			selected = optionThree;
		notifyPlayerMoved(selected.getText());

		if (selected.getText().equals(currentQuestion.getPlayer_one_headline())) {
			onCorrect();
			currentQuestion.incrementFalseScore();
		} else if (selected.getText().equals(currentQuestion.getHeadline())) {
			if (currentQuestion.orgHeadlinePopularity())
				onBonus();
			else {
				onIncorrect();
			}
		} else {
			onIncorrect();
			currentQuestion.incrementFalseScore();
		}

		currentQuestion.incrementGameCount();
		DBExecutionEngine.INSTANCE.submitTask("NewsDataService", "updateNewsArticle", currentQuestion);

	}

	private void onBonus() {
		start = false;
		SoundManager.INSTANCE.playBonusSound();
		updateScore(200);
		showMessage("Bonus!You score 200 points", 2000);
		nextQuestion();
	}

	private JRadioButton getNthRadio(int n) {
		Enumeration<AbstractButton> buttons = allOptions.getElements();
		int count = 0;
		JRadioButton option = null;
		while (buttons.hasMoreElements()) {
			option = (JRadioButton) buttons.nextElement();
			if (count == n)
				break;
			count++;
		}
		return option;

	}

	@Override
	public void opponentMoved(String answer) {
		status.setText("Opponent's answer received");
		currentQuestion.setPlayer_one_headline(answer);
		int number = new Random().nextInt((2 - 0) + 1) + 0;

		JRadioButton opponentAnswer = getNthRadio(number);
		opponentAnswer.setText(answer);
		opponentAnswer.setSelected(true);

		int no = number == 0 ? (number == 1 ? 0 : 2) : (number == 2 ? 1 : 0);
		JRadioButton originalHeadline = getNthRadio(no);
		originalHeadline.setText(currentQuestion.getHeadline());

		int nbr = 3 - (number + no);

		JRadioButton randomHeadline = getNthRadio(nbr);
		String rhl = currentQuestion.obtainRandomHeadline();

		if (rhl != null && !rhl.isEmpty()) {
			randomHeadline.setText(rhl);
			randomHeadline.setVisible(true);

		}

		if (!answer.isEmpty()) {
			opponentAnswer.setVisible(true);

		}

		originalHeadline.setVisible(true);
		hint.setVisible(true);
		SoundManager.INSTANCE.playDuringGameSound();

	}

	public void stopSecondTimer() {
		secondTimer.stop();
		SoundManager.INSTANCE.stopDuringGameSound();
		SoundManager.INSTANCE.stopSecondCounterSound();
		secondCounter.setVisible(false);
	}

	@Override
	public void timeUp() {
		stopTimer();
		status.setText("Time for reading up. You have 10 seconds to choose a headline");
		startSecondCounter();

	}

	protected class SecondTimerCounter implements ActionListener {
		Integer counter;

		public SecondTimerCounter(int counter) {

			this.counter = counter;
		}

		public void setCounter(int newValue) {
			synchronized (counter) {
				this.counter = newValue;

			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (counter > 0) {
				secondCounter.setText(String.valueOf(counter));
				counter--;
			} else if (counter == 0) {
				secondCounterTimeUp();
			}

		}

	}

	public void startSecondCounter() {
		SoundManager.INSTANCE.stopDuringGameSound();

		secondTimerobj.setCounter(10);
		secondCounter.setVisible(true);
		SoundManager.INSTANCE.playSecondCounterSound();
		secondTimer.start();
	}

	@Override
	public void notifyPlayerMoved(String answer) {
		gamePlay.notifyPlayerTwoMoved(answer);
	}

	public void secondCounterTimeUp() {
		stopSecondTimer();
		notifyPlayerMoved("");
		showMessage("Oops! Time's up. No answer received", 2000);

	}

	@Override
	public void nextQuestion() {
		// DO NOTHING IN CASE OF SAME JVM
	}
}
