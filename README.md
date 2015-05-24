# test-rule-factory-factory

Playing with making abstract test rule factories to provide a generic way of creating managed instances of different types.

```java

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
	
	```
