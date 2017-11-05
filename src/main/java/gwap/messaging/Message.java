package gwap.messaging;

import java.io.Serializable;


/**
 * @author shruti
 * Used by messaging sender and receiver service to communicate uniformly with each other
 * Encapsulates a method signature such that it can be invoked by JAVA reflections
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String methodName;
	private Class argumentType;
	private Object argument;
	private Class returnType;
	
	
	public Message(){
		
	}
	public Message(String methodName, Class argumentType, Object arg) {
		this.methodName = methodName;
		this.argumentType = argumentType;
		this.argument = arg;
	}
	
	public Message(String methodName, Class argumentType, Object arg, Class returnType) {
		this(methodName,argumentType,arg);
		this.returnType = returnType;
		
	}
	
	public void setArgumentType(Class argumentType) {
		this.argumentType = argumentType;
	}
	
	public Class getArgumentType() {
		return argumentType;
	}
	
	public void setArg(Object arg) {
		this.argument = arg;
	}
	
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	
	public Object getArg() {
		return argument;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	

}
