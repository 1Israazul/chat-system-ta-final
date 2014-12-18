package signals;

/**
 *
 * @author Alexandre
 */
public class FileTransfer extends Signal {
        private byte[] file;

    /**
     *
     * @param file
     */
    public FileTransfer(byte [] file) {
		super();
		this.file = file;
	}

    /**
     *
     * @return
     */
    public byte[] getFile() {
		return file;
	}

    /**
     *
     * @param file
     */
    public void setFile(byte[] file) {
		this.file = file;
	}
}
