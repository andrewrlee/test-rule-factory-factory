package factory;

import static java.util.Collections.emptyList;

import java.util.Collection;

import org.junit.rules.ExternalResource;

import factory.Connection.ConnectionCreator;

public class ConnectionRule<T extends Connection> extends ExternalResource {
	
	private T connection;
	private Collection<String> addresses;
	private ConnectionCreator<T> creator;

	private ConnectionRule(ConnectionCreator<T> creator, Collection<String> addresses){
		this.creator = creator;
		this.addresses = addresses;
	}

	public static <T extends Connection> ConnectionRule<T> create(ConnectionCreator<T> creator){
		return new ConnectionRule<>(creator, emptyList());
	}
	
	public static <T extends Connection> ConnectionRule<T> create(ConnectionCreator<T> creator, Collection<String> addresses){
		return new ConnectionRule<T>(creator, addresses);
	}
	
	@Override
	protected void before() throws Throwable {
		this.connection = creator.build(addresses);	
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
	
	public Collection<String> getAddresses() {
		return connection.getAddresses();
	}
	
	
}
