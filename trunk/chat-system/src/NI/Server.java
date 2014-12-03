/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import signals.*;
import chat.system.*;

/**
 *
 * @author bardey
 */
public class Server extends Thread {
	private DatagramSocket sock;
	private int lengthMax;
	private MyNetworkInterface nI;
	private boolean canGo = true;
	
	
	public Server(DatagramSocket sock, int lengthMax, MyNetworkInterface nI){
		this.sock = sock;
		this.lengthMax = lengthMax;
		this.nI = nI;
	}
	
	public void run(){
		
		Signal res ;
			//voir stak over flow pour les infos.
			
			canGo = true;
			try {
        byte[] receiveData = new byte[lengthMax];


        while(canGo)
        {
					res = null;
					DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
          this.sock.receive(receivePacket);
          res = Signal.fromByteArray(receiveData);
          System.out.println("**** Recieved : " + res.getClass()+"/// from : "+receivePacket.getAddress()); 
					if(res instanceof Hello){
						nI.helloReceived(res);
					}else if(res instanceof HelloOK){
						//System.out.println("on a un truc !!!! ma tete ! "+res);
						nI.helloOKReceived(res);
					}else if (res instanceof TextMessage){
						nI.messageReceived(res);
					}else if (res instanceof Goodbye){
						nI.byeReceived(res);
					}
					
				}
      } catch (Exception e) {
         System.out.println(e);
      }		
				
		
	}
	
	
	public void killMe(){
		this.canGo = false;
	}
	
}
