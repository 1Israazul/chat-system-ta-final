/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author bardey
 */
public class Server extends Thread {
	private DatagramSocket sock;
	private int lengthMax;
	public Server(DatagramSocket sock, int lengthMax){
		this.sock = sock;
		this.lengthMax = lengthMax;
	}
	
	public void run(){
		
		String res = "";
			//voir stak over flow pour les infos.
			
			try {
        byte[] receiveData = new byte[lengthMax];
        DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);

        while(true)
        {
          this.sock.receive(receivePacket);
          res = new String( receivePacket.getData(), 0, receivePacket.getLength());
          System.out.println("Recieved : " + res+"/// from : "+receivePacket.getAddress()); 
        }
      } catch (Exception e) {
         System.out.println(e);
      }		
				
		
	}
	
	
}
