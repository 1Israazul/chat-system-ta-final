/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;
import java.net.*;
import java.io.*;

/**
 *
 * @author bardey
 */
public class TCPServer extends Thread {
		Socket sock;
		ServerSocket soc;
		int taille ;
		byte[] data;
		MyNetworkInterface nI;

	public TCPServer(int port, int taille, MyNetworkInterface pere){
		 this.taille = taille; 
		 this.nI = pere;
		try{
			ServerSocket soc = new ServerSocket(port);
				
			//System.out.println("connexion ok");
		}catch(Exception e){
			System.out.println("Connexion échouée (serveur)"+e);
		}
	}
			
	public void run(){
		int more = 0;
		int total = 0;
		ByteArrayOutputStream inData = new ByteArrayOutputStream();
		data = new byte[taille];
		
			
		try{
			sock = soc.accept();
			
			byte [] tamp = new byte [taille]; 
			InputStream input = sock.getInputStream();
			
			while(total <= taille){	
				
				System.out.println("ServeTCP is recceing");
				input.read(tamp);
				inData.write(tamp, 0, more);
				more = input.read(tamp);
				System.arraycopy(inData, 0, data, total, more);
				
				total = total + more;
			}
			//passer par un tampon fos.write et close
			sock.close();
			nI.fichierRecuParTCPServer(data);
			
		}catch(Exception e){
			System.out.println("Connexion échouée (serveur)"+e);
		}
	}

	
	
}
