package factory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;

import factory.Connection.ConnectionA;
import factory.Connection.ConnectionB;

public class ConnectionRuleTest {

	@Rule
	public ConnectionRule<ConnectionA> ruleA = ConnectionRule.create(ConnectionA::new);
	@Rule
	public ConnectionRule<ConnectionB> ruleB = ConnectionRule.create(ConnectionB::new, Collections.singleton("http://localhost"));
	
	@Test
	public void checkConnectionNotNull(){
		assertThat(ruleA.getConnection()).isNotNull();
	}
	
}
