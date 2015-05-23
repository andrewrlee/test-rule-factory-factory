package factory;

import java.util.Collections;
import java.util.List;

public class ConnectionRule<T extends Connection> {
	
	private T connection;

	private ConnectionRule(List<String> addresses){
		this.connection = null; // Create this;	
	}

	public static <T extends Connection> ConnectionRule<T> create(){
		return new ConnectionRule<>(Collections.emptyList());
	}
	
	public static <T extends Connection> ConnectionRule<T> create(List<String> addresses){
		return new ConnectionRule<T>(Collections.emptyList());
	}
	
	public T getConnection() {
		return connection;
	}
}
