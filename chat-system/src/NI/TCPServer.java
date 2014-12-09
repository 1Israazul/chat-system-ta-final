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
public class TCPServer {
		ServerSocket soc;
	

	public TCPServer(byte [] data,int port, int taille){
		 int more = 0;
		 int total = 0;
		 ByteArrayOutputStream inData = new ByteArrayOutputStream();
		try{
			soc = new ServerSocket(port);
			Socket sock = soc.accept();		
			//System.out.println("connexion ok");
			byte [] tamp = new byte [taille]; 
			InputStream input = sock.getInputStream();
			
			while(total <= taille){			
				input.read(tamp);
				inData.write(tamp, 0, more);
				more = input.read(tamp);
				System.arraycopy(inData, 0, data, total, more);
				
				total = total + more;
			}
			
			//passer par un tampon fos.write et close
			sock.close();
		}catch(Exception e){
			System.out.println("Connexion échouée (serveur)"+e);
		}
	}

	
	
}
