package factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;

import factory.Connection.ConnectionA;
import factory.Connection.ConnectionB;

public class ConnectionRuleTest {

	@Rule
	public ConnectionRule<ConnectionA> ruleA1 = ConnectionRule.create(ConnectionA::new);
	@Rule
	public ConnectionRule<ConnectionA> ruleA2 = ConnectionRule.create(ConnectionA::new, Collections.singleton("http://localhost"));
	@Rule
	public ConnectionRule<ConnectionB> ruleB1 = ConnectionRule.create(ConnectionB::new);
	@Rule
	public ConnectionRule<ConnectionB> ruleB2 = ConnectionRule.create(ConnectionB::new, Collections.singleton("http://localhost"));
	
	@Test
	public void checkConnectionNotNull(){
		assertThat(ruleA1.getConnection()).isNotNull();
		assertThat(ruleA2.getConnection()).isNotNull();
		assertThat(ruleB1.getConnection()).isNotNull();
		assertThat(ruleB2.getConnection()).isNotNull();
	}	
	
}
