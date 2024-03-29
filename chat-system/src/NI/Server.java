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
import javax.sound.midi.Soundbank;

/**
 * UDP listener that notifies our system when a message is received.
 * @author bardey and dauriac
 */
public class Server extends Thread {
	private DatagramSocket sock;
	private int lengthMax;
	private MyNetworkInterface nI;
	private boolean canGo = true;
	
    /**
     *Constructor, sets up every needed items
     * @param sock socket, managed by the NI
     * @param lengthMax my lenght of the packet received
     * @param nI link to the NI
     */
    public Server(DatagramSocket sock, int lengthMax, MyNetworkInterface nI){
		this.sock = sock;
		this.lengthMax = lengthMax;
		this.nI = nI;
	}
	
    /**
     *Starts the server to listen and notify our system.
     */
    public void run(){
		InetAddress from; 
		Signal res ;
			//voir stak over flow pour les infos.
			
			canGo = true;
			try {
        


        while(canGo)
        {
          byte[] receiveData = new byte[lengthMax];
          res = null;
          DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
					System.out.println("waiting");
          this.sock.receive(receivePacket);
					System.out.println("recieved packet from : "+receivePacket.getAddress());
          res = Signal.fromByteArray(receiveData);
					from = receivePacket.getAddress();
          System.out.println("**** Recieved : " + res.getClass()+"/// from : "+receivePacket.getAddress()); 
					
					
					if(res instanceof Hello){
						nI.helloReceived(res, from);
					}else if(res instanceof HelloOK){
						nI.helloOKReceived(res,from);
					}else if (res instanceof TextMessage){
						nI.messageReceived(res);
					}else if (res instanceof Goodbye){
						System.out.println("on a un truc !!!! ma tete ! "+res);
						nI.byeReceived(res);          
					}else if (res instanceof FileProposal){
						nI.fileProposalReceived(res);
					}else if (res instanceof FileTransferAccepted){
						nI.fileTransferAcceptedReceived(res,from);
					}else if (res instanceof FileTransferNotAccepted){
						nI.fileTransferNotAcceptedReceived(res,from);
					}
					
				}
      } catch (Exception e) {
         System.out.println("erreur dans server : "+e);
      }		
				
		
	}
	
    /**
     *Stops the thread 
     */
    public void killMe(){
		this.canGo = false;
	}
	
}
