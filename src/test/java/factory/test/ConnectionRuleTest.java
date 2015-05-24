package factory.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;

import factory.Connection;
import factory.ConnectionRule;
import factory.Connection.ConnectionA;
import factory.Connection.ConnectionB;
import factory.ConnectionRule.ConnectionRuleFactory;

public class ConnectionRuleTest {

	public static ConnectionRuleFactory<ConnectionA> connectionARuleFactory = ConnectionRule.newFactory(ConnectionA::new);
	public static ConnectionRuleFactory<ConnectionB> connectionBRuleFactory = ConnectionRule.newFactory(ConnectionB::new);
	
	@Rule
	public ConnectionRule<ConnectionA> ruleA1 = connectionARuleFactory.create();
	@Rule
	public ConnectionRule<ConnectionA> ruleA2 = connectionARuleFactory.build()
																		.name("customA")
																		.withAddress("http://localhost/a")
																		.create();
	@Rule
	public ConnectionRule<ConnectionB> ruleB1 = connectionBRuleFactory.create();
	@Rule
	public ConnectionRule<ConnectionB> ruleB2 = connectionBRuleFactory.build()
																		.name("customB")
																		.withAddress("http://localhost/b")
																		.create();
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
	
	Verify checkConnection(Connection connection){
		return new Verify(connection);
	}
	
	static class Verify{
		Connection connection;
		Verify(Connection connection) {
			this.connection = connection;
		}
		Verify hasName(String name){
			assertThat(connection.getName()).isEqualTo(name);
			return this;
		}
		Verify hasNoAddresses(){
			assertThat(connection.getAddresses()).isEmpty();
			return this;
		}
		Verify hasAddresses(String... addresses){
			assertThat(connection.getAddresses()).contains(addresses);
			return this;
		}
	}
}
