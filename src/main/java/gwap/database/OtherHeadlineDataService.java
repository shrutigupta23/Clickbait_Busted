package gwap.database;
import org.hibernate.Session;
import org.hibernate.Transaction;

import gwap.database.DatabaseConnection;
import gwap.models.dao.OtherHeadline;

/**
 * @author shruti
 * A service class to obtain other headlines related to a news article
 */
public class OtherHeadlineDataService {
	
	public static void addNewHeadline(Object object){
		OtherHeadline otherHeadline = (OtherHeadline) object;
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();
		session.save(otherHeadline);
		tr.commit();

	}
}
