package signals;

import java.util.ArrayList;

public class TextMessage extends Signal {
	private String message;
	private String from;
	private ArrayList<String> to;
	
	public TextMessage(String message, String from, ArrayList<String> to) {
		super();
		this.message = message;
		this.from = from;
		this.to = to;
	}

	public String getMessage() {
		return message;
	}

	public String getFrom() {
		return from;
	}

	public ArrayList<String> getTo() {
		return to;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(ArrayList<String> to) {
		this.to = to;
	}
	
	
}
