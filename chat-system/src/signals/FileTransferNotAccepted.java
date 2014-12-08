/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

/**
 *
 * @author gb
 */
public class FileTransferNotAccepted extends Signal {
    private String fileName;
    private String remoteUsername;

    public FileTransferNotAccepted(String fileName, String remoteUsername) {
        this.fileName = fileName;
        this.remoteUsername = remoteUsername;
    }

    public String getFileName() {
        return fileName;
    }

    public String getRemoteUsername() {
        return remoteUsername;
    }
}
