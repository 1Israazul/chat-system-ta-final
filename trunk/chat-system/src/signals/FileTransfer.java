package signals;

public class FileTransfer extends Signal {
        private byte[] file;

	public FileTransfer(byte [] file) {
		super();
		this.file = file;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
}
