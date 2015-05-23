package factory;

import java.util.Collection;

public abstract class Connection{

	private Collection<String> addresses;

	private Connection(Collection<String> addresses){
		this.addresses = addresses;
	}
	
	public void close(){
		System.out.println("Closed!");
	} 
	
	public static class ConnectionA extends Connection{
		public ConnectionA(Collection<String> addresses){
			super(addresses);
		}
	}

	public static class ConnectionB extends Connection{
		public ConnectionB(Collection<String> addresses){
			super(addresses);
		}
	}
	
	public Collection<String> getAddresses() {
		return addresses;
	}
	
	@FunctionalInterface
	public interface ConnectionCreator<T extends Connection>{
		
		T build(Collection<String> addresses);
	}

}