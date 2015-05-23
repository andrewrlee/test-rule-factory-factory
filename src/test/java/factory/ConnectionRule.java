package factory;

import java.util.Collections;
import java.util.List;

import org.junit.rules.ExternalResource;

public class ConnectionRule<T extends Connection> extends ExternalResource {
	
	private T connection;
	private List<String> addresses;

	private ConnectionRule(List<String> addresses){
		this.addresses = addresses;
	}

	public static <T extends Connection> ConnectionRule<T> create(){
		return new ConnectionRule<>(Collections.emptyList());
	}
	
	public static <T extends Connection> ConnectionRule<T> create(List<String> addresses){
		return new ConnectionRule<T>(Collections.emptyList());
	}
	
	@Override
	protected void before() throws Throwable {
		this.connection = null; // Create this;	
	}

	@Override
	protected void after() {
		if(connection != null){
			connection.close();
		}
	}
	
	public T getConnection() {
		return connection;
	}
	
	
}
