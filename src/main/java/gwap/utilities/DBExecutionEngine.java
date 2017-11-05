package gwap.utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shruti
 * Allows to carry out each DBOperation in a new thread using a single threaded
 * executor service
 */
public enum DBExecutionEngine {

	INSTANCE;
	private ExecutorService engine = Executors.newSingleThreadExecutor();

	public void submitTask(String name, String methodName, Object... argument) {
		engine.submit(new DBOperation(name,methodName,argument));
	}

		private class DBOperation implements Runnable {
		private String name;
		private String methodName;
		private Object[] arguments;

		public DBOperation(String name, String methodName, Object... arguments) {
			this.name = name;
			this.methodName = methodName;
			this.arguments = arguments;
		}

		@Override
		public void run() {
			String className = "gwap.database." + name;
			try {
				Class classObject = Class.forName(className);
				Method method = classObject.getMethod(methodName, new Class[]{Object.class});
				method.invoke(null, arguments[0]);
			} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				e.getCause().printStackTrace();
			}

		}

	}

}
