package gwap.database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.GenericJDBCException;

import gwap.exceptions.DatabaseOperationException;
import gwap.models.dao.Player;

/**
 * @author shruti A service class to perform player related database operations
 */
public class PlayerDataService {

	public static void insertPlayer(String username, String password) throws GenericJDBCException {
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();
		Player player = new Player(username, password, 0);
		session.save(player);
		tr.commit();

	}

	public static List<String> getPlayers() {
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();

		List<String> usernames = session.createCriteria(Player.class).add(Restrictions.ne("password", ""))
				.setProjection(Projections.projectionList().add(Projections.property("username"), "username")).list();
		tr.commit();
		return usernames;

	}

	public static boolean authenticatePlayer(String username, String password) {
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();
		Player result = (Player) session.createCriteria(Player.class).add(Restrictions.eq("username", username))
				.uniqueResult();
		tr.commit();
		if (result != null && result.getPassword().equals(password))
			return true;
		else
			return false;

	}

	public static String generateGuestPlayer() {
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();
		int count = session.createCriteria(Player.class).add(Restrictions.eq("password", "")).list().size();
		count++;
		String uname = "Guest" + count;
		tr.commit();
		try {
			insertPlayer(uname, "");
		} catch (GenericJDBCException e) {
			throw new DatabaseOperationException(e);
		}
		return uname;

	}

	public synchronized static void updateScore(String playerName, int newScore) {

		// Get current score for player . if score > current score then update
		// score to newScore
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();

		Player player = (Player) session.createCriteria(Player.class).add(Restrictions.eq("username", playerName))
				.uniqueResult();
		if (player.getHighestScore() < newScore) {
			player.setHighestScore(newScore);
		}
		session.save(player);
		tr.commit();

	}

	public synchronized static List<Player> getTopTenPlayers() {
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();
		List<Player> players = session.createCriteria(Player.class).addOrder(Order.desc("highestScore"))
				.setMaxResults(10).list();
		tr.commit();
		return players;

	}

	public synchronized static int getHighestScore(String playerUsername) {
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();
		int score = (Integer) session.createCriteria(Player.class).add(Restrictions.eq("username", playerUsername))
				.setProjection(Projections.projectionList().add(Projections.property("highestScore"), "highestScore"))
				.uniqueResult();
		tr.commit();
		return score;
	}

}
