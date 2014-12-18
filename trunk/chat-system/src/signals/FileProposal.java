package signals;

import java.util.ArrayList;

/**
 *
 * @author Alexandre
 */
public class FileProposal extends Signal {
	private String fileName;
	private long size;
	private String from;
	private ArrayList<String> to;
	
    /**
     *
     * @param fileName
     * @param size
     * @param from
     * @param to
     */
    public FileProposal(String fileName, long size, String from, ArrayList<String> to) {
		super();
		this.fileName = fileName;
		this.size = size;
		this.from = from;
		this.to = to;
	}
	
    /**
     *
     * @return
     */
    public String getFileName() {
		return fileName;
	}

    /**
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
		this.fileName = fileName;
	}

    /**
     *
     * @return
     */
    public long getSize() {
		return size;
	}

    /**
     *
     * @param size
     */
    public void setSize(long size) {
		this.size = size;
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
     * @param from
     */
    public void setFrom(String from) {
		this.from = from;
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
     * @param to
     */
    public void setTo(ArrayList<String> to) {
		this.to = to;
	}

	
}
