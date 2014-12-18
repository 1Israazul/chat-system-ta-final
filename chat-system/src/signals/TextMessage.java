package signals;

import java.util.ArrayList;

/**
 *
 * @author Alexandre
 */
public class TextMessage extends Signal {
	private String message;
	private String from;
	private ArrayList<String> to;
	
    /**
     *
     * @param message
     * @param from
     * @param to
     */
    public TextMessage(String message, String from, ArrayList<String> to) {
		super();
		this.message = message;
		this.from = from;
		this.to = to;
	}

    /**
     *
     * @return
     */
    public String getMessage() {
		return message;
	}

    /**
     *
     * @return
     */
    public String getFrom() {
		return from;
	}

    /**
     *
     * @return
     */
    public ArrayList<String> getTo() {
		return to;
	}

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
		this.message = message;
	}

    /**
     *
     * @param from
     */
    public void setFrom(String from) {
		this.from = from;
	}

    /**
     *
     * @param to
     */
    public void setTo(ArrayList<String> to) {
		this.to = to;
	}
	
	
}
