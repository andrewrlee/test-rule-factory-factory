package factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;

import factory.Connection.ConnectionA;
import factory.Connection.ConnectionB;
import factory.ConnectionRuleFactoryFactory.ConnectionRuleFactory;

public class ConnectionRuleTest {

	public static ConnectionRuleFactory<ConnectionA> ConnectionARuleFactory = 
			ConnectionRuleFactoryFactory.create(ConnectionA::new);
	
	public static ConnectionRuleFactory<ConnectionB> ConnectionBRuleFactory = 
			ConnectionRuleFactoryFactory.create(ConnectionB::new);
	
	@Rule
	public ConnectionRule<ConnectionA> ruleA1 = ConnectionARuleFactory.create();
	@Rule
	public ConnectionRule<ConnectionA> ruleA2 = ConnectionARuleFactory.create(Collections.singleton("http://localhost/a"));
	@Rule
	public ConnectionRule<ConnectionB> ruleB1 = ConnectionBRuleFactory.create();
	@Rule
	public ConnectionRule<ConnectionB> ruleB2 = ConnectionBRuleFactory.create(Collections.singleton("http://localhost/b"));
	
	@Test
	public void checkConnectionARules(){
		assertThat(ruleA1.getConnection()).isNotNull();
		assertThat(ruleA1.getAddresses()).isEmpty();
		
		assertThat(ruleA2.getConnection()).isNotNull();
		assertThat(ruleA2.getAddresses()).containsExactly("http://localhost/a");
	}	
	
	@Test
	public void checkConnectionBRules(){
		assertThat(ruleB1.getConnection()).isNotNull();
		assertThat(ruleB1.getAddresses()).isEmpty();
		
		assertThat(ruleB2.getConnection()).isNotNull();
		assertThat(ruleB2.getAddresses()).containsExactly("http://localhost/b");
	}	
	
}
