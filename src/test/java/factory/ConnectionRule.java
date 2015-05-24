package factory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.junit.rules.ExternalResource;

import factory.Connection.ConnectionCreator;


public class ConnectionRule<T extends Connection> extends ExternalResource {
	
	private final Supplier<T> connectionSupplier;
	private T connection;

	public static <T extends Connection> ConnectionRuleFactory<T> newFactory(ConnectionCreator<T> creator){
		return () -> new Builder<>(creator);
	}
	
	private ConnectionRule(Supplier<T> connectionSupplier){
		this.connectionSupplier = connectionSupplier;
	}

	@Override
	protected void before() throws Throwable {
		this.connection = connectionSupplier.get();	
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
			return new ConnectionRule<T>(() -> creator.build(name, addresses));
		}
	}
}
