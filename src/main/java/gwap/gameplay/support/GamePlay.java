package gwap.gameplay.support;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import gwap.database.NewsDataService;
import gwap.gameplay.screens.PlayerScreen;
import gwap.models.dao.Game;
import gwap.utilities.DBExecutionEngine;

/**
 * @author shruti This class represents a Game and is responsible for
 *         orchestrating between player one and player two
 */
public class GamePlay {

	private PlayerScreen localPlayer;
	private PlayerProxyScreen remotePlayer;
	private List<Integer> articles;
	private int currentArticle;

	public GamePlay(PlayerScreen localPlayer, PlayerScreen remotePlayer) {

		this.localPlayer = localPlayer;
		this.localPlayer.setGamePlay(this);

		this.remotePlayer = new PlayerProxyScreen(remotePlayer);
		this.remotePlayer.setGamePlay(this);

	}

	public void launch() {
		// Let the other player know game has started
		remotePlayer.startGame(localPlayer.getPlayerUsername());
		// Get the news articles for the game
		articles = NewsDataService.getNewsArticles(6);

		currentArticle = 0;
		// Display first question for local player
		localPlayer.displayQuestion(articles.get(currentArticle));
		// Display first question for remote player
		remotePlayer.displayQuestion(articles.get(currentArticle));

	}

	public void showNextQuestion() {
		currentArticle++;

		if (currentArticle < articles.size()) {
			int newsId = articles.get(currentArticle);
			System.out.println(currentArticle + "\t" + newsId);
			// Display question for local player
			localPlayer.displayQuestion(newsId);
			// Display question for remote player
			remotePlayer.displayQuestion(newsId);

		} else {
			stopGame();

		}
	}

	public void notifyPlayerOneMoved(String opponentAnswer) {
		remotePlayer.opponentMoved(opponentAnswer);
	}

	public void notifyPlayerTwoMoved(String opponentAnswer) {
		localPlayer.opponentMoved(opponentAnswer);
	}

	public void stopGame() {
		localPlayer.stopGame();
		remotePlayer.stopGame();
		saveGame();
		Timer timerOne = new Timer(5000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				localPlayer.showLeaderboard();
				remotePlayer.showLeaderboard();
			}
		});
		timerOne.setRepeats(false);
		timerOne.start();

	}

	private void saveGame() {

		Game game = new Game();
		game.setPlayerOne(localPlayer.getPlayerUsername());
		game.setPlayerTwo(remotePlayer.getPlayerUsername());
		int localPlayerScore = localPlayer.getScore();
		int remotePlayerScore = remotePlayer.getScore();
		game.setPlayerOneScore(localPlayerScore);
		game.setPlayerTwoScore(remotePlayerScore);
		DBExecutionEngine.INSTANCE.submitTask("GameDataService", "saveNewGame", game);

	}

}
