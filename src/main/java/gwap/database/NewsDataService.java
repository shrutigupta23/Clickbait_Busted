package gwap.database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import gwap.models.dao.News;


/**
 * @author shruti
 * A service class to perform news related database operations
 */
public class NewsDataService {



	/**
	 * @param noOfArticles - The number of articles to be retrieved from database
	 * @return List of news articles ids
	 */
	public synchronized static List<Integer> getNewsArticles(int... noOfArticles) {
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();

		List<Integer> newsIds = session.createCriteria(News.class)
				.setProjection(Projections.projectionList().add(Projections.property("newsId"), "newsId"))
				.addOrder(Order.asc("noOfGames")).setMaxResults(noOfArticles[0]).list();
		tr.commit();
		return newsIds;
	}

	/**
	 * @param object - News object to be updated
	 */
	public synchronized static void updateNewsArticle(Object object) {
		News article = (News) object;
		Session session = DatabaseConnection.INSTANCE.getSession();

		Transaction tr = session.beginTransaction();
		session.update(article);
		tr.commit();
		
		
	}

	/**
	 * @param newsId
	 * @return News object associated with news id
	 */
	public synchronized static News getNews(int newsId) {
		Session session = DatabaseConnection.INSTANCE.getSession();
		Transaction tr = session.beginTransaction();
		News news = (News) session.createCriteria(News.class).add(Restrictions.eq("newsId", newsId)).uniqueResult();
		tr.commit();
		return news;
	}

}
