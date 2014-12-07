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
		InetAddress from; 
		Signal res ;
			//voir stak over flow pour les infos.
			
			canGo = true;
			try {
        byte[] receiveData = new byte[lengthMax];


        while(canGo)
        {
            System.out.println("1");
            res = null;
            System.out.println("2");
            DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
            System.out.println("3");
          this.sock.receive(receivePacket);
          System.out.println("4");
          res = Signal.fromByteArray(receiveData);
          System.out.println("5");
					from = receivePacket.getAddress();
                                        System.out.println("6");
          System.out.println("**** Recieved : " + res.getClass()+"/// from : "+receivePacket.getAddress()); 
          System.out.println("7");
					if(res instanceof Hello){
						nI.helloReceived(res, from);
					}else if(res instanceof HelloOK){
						//System.out.println("on a un truc !!!! ma tete ! "+res);
						nI.helloOKReceived(res,from);
					}else if (res instanceof TextMessage){
						nI.messageReceived(res);
					}else if (res instanceof Goodbye){
						nI.byeReceived(res);
                                        }else if (res instanceof FileProposal){
						nI.fileProposalReceived(res);
					}else if (res instanceof FileTransferAccepted){
						nI.fileTransferAcceptedReceived(res,from);
					}else if (res instanceof FileTransferNotAccepted){
						nI.fileTransferNotAcceptedReceived(res,from);
					}else if (res instanceof FileTransfer){
                                            System.out.println("passage dans server");
						nI.fileTransferReceived(res);
					}
					
				}
      } catch (Exception e) {
         System.out.println("erreur dans server : "+e);
      }		
				
		
	}
	
	
	public void killMe(){
		this.canGo = false;
	}
	
}
