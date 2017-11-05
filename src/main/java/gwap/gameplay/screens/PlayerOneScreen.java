package gwap.gameplay.screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import gwap.database.NewsDataService;
import gwap.models.dao.OtherHeadline;
import gwap.utilities.DBExecutionEngine;
import gwap.utilities.ScreenUpdateEngine;
import gwap.utilities.SoundManager;

/**
 * @author shruti
 * The screen for player one
 */
public class PlayerOneScreen extends PlayerScreen {

	private JTextField input;
	private JDialog afterReply;
	private JLabel betweenQuestions;

	public PlayerOneScreen(String playerUsername) {
		super(playerUsername);
	}

	@Override
	public void displayQuestion(Integer newsId) {
		if (!setupDone)
			setup();

		currentQuestion = NewsDataService.getNews(newsId);
		newsContent.setText(currentQuestion.getContent());
		input.setText("");
		submit.setEnabled(true);
		startTimer();
	}

	@Override
	public void setup() {
		super.setup();
		instruction.setText("Read the article and enter an appropriate headline");
	
		betweenQuestions = new JLabel("");
		betweenQuestions.setForeground(Color.BLACK);
		betweenQuestions.setBackground(Color.RED);
		betweenQuestions.setOpaque(true);
		betweenQuestions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		afterReply = new JDialog(frame);
		afterReply.setTitle(playerUsername);
		afterReply.add(betweenQuestions);
		


		input = new JTextField();
		input.setPreferredSize(new Dimension((frame.getWidth() - 153), 20));
		input.setText("");

		
		FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 10, 0);
		layout.setHgap(5);
		JPanel inputPanel = new JPanel(layout);
		JLabel inputLabel = new JLabel("Answer");
		inputLabel.setForeground(Color.BLACK);
		inputPanel.add(inputLabel);
		inputPanel.setOpaque(true);
		
		inputPanel.add(input);
		inputPanel.setBackground(Color.RED);
		inputPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW,5));
		
		JPanel bottomRow = new JPanel();
		bottomRow.setLayout(new BoxLayout(bottomRow, BoxLayout.Y_AXIS));
		bottomRow.setBorder(BorderFactory.createLineBorder(Color.BLACK, 20));
		Component blankArea = Box.createRigidArea(new Dimension(newsContent.getWidth(), 20));

		bottomRow.add(blankArea);
		bottomRow.add(inputPanel);
		Component blankSecondArea = Box.createRigidArea(new Dimension(newsContent.getWidth(), 80));
		bottomRow.add(blankSecondArea);
		bottomRow.add(submit);
		bottomRow.setBackground(Color.BLACK);

		panel.add(bottomRow, BorderLayout.SOUTH);

		panel.setBackground(Color.BLACK);
		frame.add(panel);
		frame.setLocation(0, 0);
		frame.setAutoRequestFocus(true);
		frame.setTitle("Player 1 Screen : " + playerUsername);
		frame.validate();

		setupDone = true;
	}

	@Override
	public void answerReceived() {
		submit.setEnabled(false);
		stopTimer();
		String answer = input.getText();
		SoundManager.INSTANCE.playPopupSound();
		betweenQuestions.setText("Your answer is \"" + answer + "\" Waiting for opponent's reply");
		ScreenUpdateEngine.INSTANCE.formatAndShowDialog(afterReply , frame);
		notifyPlayerMoved(answer);
		currentQuestion.setPlayer_one_headline(answer);
		OtherHeadline oh = currentQuestion.addNewHeadline(answer);
		DBExecutionEngine.INSTANCE.submitTask("OtherHeadlineDataService", "addNewHeadline", oh);
	}
	public JTextArea getNewsContentArea(){
		return newsContent;
	}

	@Override
	public void opponentMoved(String opponentAnswer) {
		afterReply.setVisible(false);
		if (opponentAnswer.equals(currentQuestion.getPlayer_one_headline())) {
			onCorrect();
		} else
			onIncorrect();

	}

	@Override
	public void notifyPlayerMoved(String answer) {
		gamePlay.notifyPlayerOneMoved(answer);
	}

}
