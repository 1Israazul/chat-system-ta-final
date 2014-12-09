/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;

import java.net.*;

/**
 *
 * @author bardey
 */
public class Sender {
		private DatagramSocket sock;
	public Sender(DatagramSocket s) throws SocketException{
		this.sock = s; 
                this.sock.setBroadcast(true);
	}
	
	public void send(byte[] message, InetAddress addr,int port){
		DatagramPacket sendPacket;
		
    try{
			//addr = InetAddress.getByName(/*"10.1.5.23"*/"192.168.173.41");
        
			sendPacket = new DatagramPacket(message, message.length,addr, port);
			
		  this.sock.send(sendPacket);
		
		  System.out.println("****Message envoyé à "+addr);
		}catch(Exception e){
		  System.err.println("le message n'est pas passé : "+e);
		}
	}
	
}
