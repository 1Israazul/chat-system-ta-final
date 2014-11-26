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
	public Sender(DatagramSocket s){
		this.sock = s;
	}
	
	public void send(byte[] message, InetAddress addr,int port){
		DatagramPacket sendPacket;
		sendPacket = new DatagramPacket(message, message.length,addr, port);
    try{
		  this.sock.send(sendPacket);
		  System.out.println("Message envoyé à "+addr);
		}catch(Exception e){
		  System.err.println("le message n'est pas passé : "+e);
		}
	}
	
}
