package factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.rules.ExternalResource;

import factory.Connection.ConnectionCreator;

public class ConnectionRule<T extends Connection> extends ExternalResource {
	
	private final Collection<String> addresses;
	private final ConnectionCreator<T> creator;
	private final String name;
	private T connection;

	public static <T extends Connection> ConnectionRuleFactory<T> newFactory(ConnectionCreator<T> creator){
		return () -> new Builder<>(creator);
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
		public Builder<T> build();
		public default ConnectionRule<T> create(){
			return build().create();
		}
	}
	
	public static class Builder<T extends Connection>{
		private String name = "default";
		private final ConnectionCreator<T> creator;
		private final List<String> addresses = new ArrayList<>();

		private Builder(ConnectionCreator<T> creator){
			this.creator = creator;
		}
		
		public Builder<T> name(String name){
			this.name = name;
			return this;
		}
		
		public Builder<T> addresses(List<String> addresses){
			this.addresses.addAll(addresses);
			return this;
		}
		
		public Builder<T> withAddress(String address) {
			this.addresses.add(address);
			return this;
		}
		
		public ConnectionRule<T> create(){
			return new ConnectionRule<T>(creator, name, addresses);
		}
	}
}
