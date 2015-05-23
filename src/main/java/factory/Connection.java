package factory;

import java.util.Collection;

public interface Connection{

	void close();
	String getName();
	Collection<String> getAddresses();
	
	public static class ConnectionA implements Connection{
		private Collection<String> addresses;
		private String name;

		public ConnectionA(String name, Collection<String> addresses){
			this.addresses = addresses;
			this.name = name;
		}

		@Override
		public void close() {
			System.out.println("Closing connection A");
		}

		@Override
		public Collection<String> getAddresses() {
			return addresses;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}

	public static class ConnectionB implements Connection{
		private Collection<String> addresses;
		private String name;

		public ConnectionB(String name, Collection<String> addresses){
			this.addresses = addresses;
			this.name = name;
		}
		
		@Override
		public void close() {
			System.out.println("Closing connection B");
		}
		
		public Collection<String> getAddresses() {
			return addresses;
		}
		
		@Override
		public String getName() {
			return name;
		}
	}
	
	@FunctionalInterface
	public interface ConnectionCreator<T extends Connection>{
		T build(String name, Collection<String> addresses);
	}
}