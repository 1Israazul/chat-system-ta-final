package signals;

import signals.Signal;

public class Hello extends Signal {
	private String username;

	public Hello(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
