package gwap.models.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author shruti
 * Game model class for Hibernate - ORM
 */
@Entity
@Table(name = "games")
public class Game {

	private int gameId;
	private String playerOne;
	private String playerTwo;
	
	private int playerOneScore;
	private int playerTwoScore;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getGameId() {
		return gameId;
	}

	public String getPlayerOne() {
		return playerOne;
	}

	public String getPlayerTwo() {
		return playerTwo;
	}


	public void setGameId(int gameId) {
		this.gameId = gameId;
	}


	public void setPlayerOne(String playerOne) {
		this.playerOne = playerOne;
	}

	public void setPlayerTwo(String playerTwo) {
		this.playerTwo = playerTwo;
	}

	
	public void setPlayerOneScore(int playerOneScore) {
		this.playerOneScore = playerOneScore;
	}
	
	public int getPlayerOneScore() {
		return playerOneScore;
	}
	
	public void setPlayerTwoScore(int playerTwoScore) {
		this.playerTwoScore = playerTwoScore;
	}
	
	public int getPlayerTwoScore() {
		return playerTwoScore;
	}
}
