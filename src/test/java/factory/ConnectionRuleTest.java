package factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;

import factory.Connection.ConnectionA;
import factory.Connection.ConnectionB;

public class ConnectionRuleTest {

	@Rule
	public ConnectionRule<ConnectionA> ruleA1 = ConnectionARuleFactory.create();
	@Rule
	public ConnectionRule<ConnectionA> ruleA2 = ConnectionARuleFactory.create(Collections.singleton("http://localhost"));
	@Rule
	public ConnectionRule<ConnectionB> ruleB1 = ConnectionBRuleFactory.create();
	@Rule
	public ConnectionRule<ConnectionB> ruleB2 = ConnectionBRuleFactory.create(Collections.singleton("http://localhost"));
	
	@Test
	public void checkConnectionNotNull(){
		assertThat(ruleA1.getConnection()).isNotNull();
		assertThat(ruleA2.getConnection()).isNotNull();
		assertThat(ruleB1.getConnection()).isNotNull();
		assertThat(ruleB2.getConnection()).isNotNull();
	}	
	
	public static class ConnectionARuleFactory {
		public static ConnectionRule<ConnectionA> create(){
			return ConnectionRule.create(ConnectionA::new);
		}
		public static ConnectionRule<ConnectionA> create(Collection<String> addresses){
			return  ConnectionRule.create(ConnectionA::new, addresses);
		}
	} 

	public static class ConnectionBRuleFactory {
		public static ConnectionRule<ConnectionB> create(){
			return ConnectionRule.create(ConnectionB::new);
		}
		public static ConnectionRule<ConnectionB> create(Collection<String> addresses){
			return  ConnectionRule.create(ConnectionB::new, addresses);
		}
	} 
}
