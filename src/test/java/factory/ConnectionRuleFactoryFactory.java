package factory;

import java.util.Collection;
import java.util.Collections;

import factory.Connection.ConnectionCreator;

public class ConnectionRuleFactoryFactory {
	
	public static <T extends Connection> ConnectionRuleFactory<T> create(ConnectionCreator<T> creator){
		return (addresses) ->  ConnectionRule.create(creator, addresses);
	}
	
	public static interface ConnectionRuleFactory<T extends Connection>{
		public ConnectionRule<T> create(Collection<String> addresses);
		public default ConnectionRule<T> create(){
			return create(Collections.emptyList());
		}
	}
}
