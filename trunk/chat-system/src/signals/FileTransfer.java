package signals;

import java.io.File;

public class FileTransfer extends Signal {
	private File file;

	public FileTransfer(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}
