package factory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;

import factory.Connection.ConnectionA;

public class ConnectionRuleTest {

	@Rule
	public ConnectionRule<ConnectionA> rule = ConnectionRule.<ConnectionA>create();
	
	@Test
	public void checkConnectionNotNull(){
		assertThat(rule.getConnection()).isNotNull();
	}
	
}
