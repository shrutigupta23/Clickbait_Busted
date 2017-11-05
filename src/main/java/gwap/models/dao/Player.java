package gwap.models.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author shruti
 * Player model class for Hibernate - ORM
 */
@Entity
@Table(name = "players")
public class Player {

	private String username;
	private String password;
	private int highestScore;

	public Player() {

	}

	public Player(String username, String password, int highestScore) {
		this.username = username;
		this.password = password;
		this.highestScore = highestScore;
	}

	@Id
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public int getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}


}
