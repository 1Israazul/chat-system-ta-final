/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;
import java.net.*;
import java.io.*;

/**
 *TCP server used to received files
 * @author bardey and dauriac
 */
public class TCPServer extends Thread {
		Socket sock;
		ServerSocket soc;
		int taille ;
		byte[] data;
		MyNetworkInterface nI;

    /**
     *Simple constructeur use to initialise some variables 
     * @param port on to wait for a file
     * @param taille size of the file to be expected
     * @param pere linl to the NetWork management interface
     */
    public TCPServer(int port, int taille, MyNetworkInterface pere){
		 this.taille = taille; 
		 this.nI = pere;
		try{
			soc = new ServerSocket(port);
				
			//System.out.println("connexion ok");
		}catch(Exception e){
			System.out.println("Connexion échouée (serveur)"+e);
		}
	}
			
    /**
     *Receive the file 
     */
    public synchronized void run(){
		int more = 0;
		int total = 0;
		ByteArrayOutputStream inData = new ByteArrayOutputStream();
		data = new byte[taille];
		
			
		try{
			
			sock = soc.accept();
			byte [] tamp = new byte [taille]; 
			InputStream input = sock.getInputStream();
			
			while(total < taille){	
				
				System.out.println("ServeTCP is recceing");				
				more = input.read(tamp);
				/*
				System.out.println("taille : "+taille);
				System.out.println("TAMP ("+tamp.length+"): "+tamp);
				System.out.println("DATA ("+data.length+"): "+data);
				System.out.println("total = "+total+" more : "+more);
				*/
				System.arraycopy(tamp, 0, data, total, more);
				
				total = total + more;
				
			}
			//passer par un tampon fos.write et close
			sock.close();
			nI.fichierRecuParTCPServer(data);
			
		}catch(Exception e){
			System.out.println("Connexion échouée (serveur)"+e);
			e.printStackTrace();
		}
	}

	
	
}
