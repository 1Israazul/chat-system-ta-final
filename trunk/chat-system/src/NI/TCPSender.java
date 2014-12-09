/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;

import java.io.*;
import java.net.Socket;
import java.net.*;

/**
 *
 * @author bardey
 */
public class TCPSender {
	
	
	public TCPSender(byte [] data,InetAddress forWhom, int port){
	
		try{
			
		Socket socket = new Socket(forWhom,port);
		//System.out.println("SOCKET = " + socket);	
		BufferedWriter write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		PrintWriter pred = new PrintWriter(write);
		pred.println(data) ;
		pred.close();
		socket.close();

		
		}catch(Exception e){
			System.out.println("Connexion échouée (côté client)"+e);
		}
	}

	
}
