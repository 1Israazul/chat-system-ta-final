package signals;

/**
 *
 * @author Alexandre
 */
public class HelloOK extends Signal {
	private String username;

    /**
     *
     * @param username
     */
    public HelloOK(String username) {
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
