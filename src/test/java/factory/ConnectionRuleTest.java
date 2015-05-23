package factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;

import factory.Connection.ConnectionA;
import factory.Connection.ConnectionB;
import factory.ConnectionRule.ConnectionRuleFactory;

public class ConnectionRuleTest {

	public static ConnectionRuleFactory<ConnectionA> connectionARuleFactory = ConnectionRule.create(ConnectionA::new);
	public static ConnectionRuleFactory<ConnectionB> connectionBRuleFactory = ConnectionRule.create(ConnectionB::new);
	
	@Rule
	public ConnectionRule<ConnectionA> ruleA1 = connectionARuleFactory.create();
	@Rule
	public ConnectionRule<ConnectionA> ruleA2 = connectionARuleFactory.create("customA", Collections.singleton("http://localhost/a"));
	@Rule
	public ConnectionRule<ConnectionB> ruleB1 = connectionBRuleFactory.create();
	@Rule
	public ConnectionRule<ConnectionB> ruleB2 = connectionBRuleFactory.create("customB", Collections.singleton("http://localhost/b"));
	
	@Test
	public void checkConnectionARules(){
		checkConnection(ruleA1.getConnection()).hasNoAddresses().hasName("default");
		checkConnection(ruleA2.getConnection()).hasAddresses("http://localhost/a").hasName("customA");
	}	
	
	@Test
	public void checkConnectionBRules(){
		checkConnection(ruleB1.getConnection()).hasNoAddresses().hasName("default");
		checkConnection(ruleB2.getConnection()).hasAddresses("http://localhost/b").hasName("customB");
	}
	
	public Verify checkConnection(Connection connection){
		return new Verify(connection);
	}
	
	private static class Verify{
		private Connection connection;
		private Verify(Connection connection) {
			this.connection = connection;
		}
		private Verify hasName(String name){
			assertThat(connection.getName()).isEqualTo(name);
			return this;
		}
		private Verify hasNoAddresses(){
			assertThat(connection.getAddresses()).isEmpty();
			return this;
		}
		private Verify hasAddresses(String... addresses){
			assertThat(connection.getAddresses()).contains(addresses);
			return this;
		}
	}
}
