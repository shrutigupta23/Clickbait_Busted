package gwap.database;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author shruti
 *	This class is responsible for connecting to the Database.
 */
public enum DatabaseConnection {


	INSTANCE;

	private SessionFactory sessionFactory;
	private StandardServiceRegistry serviceRegistry;
	private Session session;



	private DatabaseConnection() {
		Configuration config = new Configuration();
		config.configure();
		Properties props = config.getProperties();

		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(props).build();
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();

	}
	
	public Session getSession() {
		return session;
	}


		
	/**
	 * Dummy function to facilitate eager loading of database connection
	 */
	public void load() {

	}
	

}
