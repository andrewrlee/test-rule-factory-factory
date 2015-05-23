package factory;

import java.util.Collection;
import java.util.Collections;

import org.junit.rules.ExternalResource;

import factory.Connection.ConnectionCreator;

public class ConnectionRule<T extends Connection> extends ExternalResource {
	
	private final Collection<String> addresses;
	private final ConnectionCreator<T> creator;
	private final String name;
	private T connection;

	public static <T extends Connection> ConnectionRuleFactory<T> create(ConnectionCreator<T> creator){
		return (name, addresses) ->  new ConnectionRule<T>(creator, name, addresses);
	}
	
	private ConnectionRule(ConnectionCreator<T> creator, String name, Collection<String> addresses){
		this.creator = creator;
		this.addresses = addresses;
		this.name = name;
	}

	@Override
	protected void before() throws Throwable {
		this.connection = creator.build(name, addresses);	
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
	
	@FunctionalInterface
	public static interface ConnectionRuleFactory<T extends Connection>{
		public ConnectionRule<T> create(String name, Collection<String> addresses);
		public default ConnectionRule<T> create(){
			return create("default", Collections.emptyList());
		}
		public default ConnectionRule<T> create(String name){
			return create(name, Collections.emptyList());
		}
	}
}
