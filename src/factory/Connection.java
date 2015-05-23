package factory;

import java.util.List;

public abstract class Connection{

	private List<String> addresses;

	private Connection(List<String> addresses){
		this.addresses = addresses;
	}
	
	public static class ConnectionA extends Connection{
		private ConnectionA(List<String> addresses){
			super(addresses);
		}
	}

	public static class ConnectionB extends Connection{
		private ConnectionB(List<String> addresses){
			super(addresses);
		}
	}

}