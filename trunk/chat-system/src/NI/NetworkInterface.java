package NI;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.*;
import java.util.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author bardey
 */
public class NetworkInterface{
    private int port = 4444;
    private DatagramSocket sock;
		private int lengthOfdataRcv =250;
    
    public NetworkInterface (){
        //Il nous faut un server UDP et un client UDP
        createUDPServer();
        //creer son socket d'envoi udp
    }
    
    private void createUDPServer(){
			try{
				sock = new DatagramSocket(port, InetAddress.getLocalHost());
			}catch (Exception e){
				System.out.println("Network interface (creating the socket) : "+e);
			}               
              
    }
		
		public void closeSocket(){
			sock.close();
		}
		
		public String getRecv(){
			String res = "";
			//voir stak over flow pour les infos.
			
			try {
        byte[] receiveData = new byte[lengthOfdataRcv];
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
			
			return res;
		}
		
		public void sendUDP(byte[] message, InetAddress addr){
       DatagramPacket sendPacket = new DatagramPacket(message, message.length,
                   addr, port);
       try{
				 this.sock.send(sendPacket);
				 System.out.println("Message envoyé à "+addr);
			 }catch(Exception e){
				 System.err.println("le message n'est pas passé : "+e);
			 }
			 
		}
    
    
}
