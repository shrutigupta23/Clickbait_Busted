package gwap.database;

import org.hibernate.Session;
import org.hibernate.Transaction;

import gwap.models.dao.Game;

/**
 * @author shruti
 * A service class to perform game related database operations
 */
public class GameDataService {

	public static void saveNewGame(Object object) {
		Game game = (Game) object;
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();
		session.save(game);
		tr.commit();

	}

}
