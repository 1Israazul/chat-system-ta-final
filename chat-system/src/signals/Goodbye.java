package signals;

/**
 *
 * @author Alexandre
 */
public class Goodbye extends Signal {
	private String username;

    /**
     *
     * @param username
     */
    public Goodbye(String username) {
		super();
		this.username = username;
	}

    /**
     *
     * @return
     */
    public String getUsername() {
		return username;
	}

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
		this.username = username;
	}
	
}
