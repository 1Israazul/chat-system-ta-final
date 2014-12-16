/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;

import java.io.*;
import java.net.Socket;
import java.net.*;

/**
 * TCP socket that will send the file. (or anything)
 * @author bardey and dauriac
 */
public class TCPSender {
	
    /**
     *Everything is done here
     * @param data byte you are sending (the file)
     * @param forWhom adress to whom you want to send the data
     * @param port source port to use
     */
    public TCPSender(byte [] data,InetAddress forWhom, int port){
	
		try{
			
		Socket socket = new Socket(forWhom,port);
		//System.out.println("SOCKET = " + socket);	
		/*BufferedWriter write = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		PrintWriter pred = new PrintWriter(write);
		pred.println(data) ;
		pred.close();
		socket.close();*/
		OutputStream writter = socket.getOutputStream();
		writter.write(data);
		writter.flush();
		writter.close();
		
		
		}catch(Exception e){
			System.out.println("Connexion échouée (côté client)"+e);
			
		}
	}

	
}
