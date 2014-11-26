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
		private Server serve;
		private Sender send;
    
    public NetworkInterface (){
			try{
				sock = new DatagramSocket(port, InetAddress.getLocalHost());
			}catch (Exception e){
				System.out.println("Network interface (creating the socket) : "+e);
			}
        //Il nous faut un server UDP et un client UDP
        createUDPServer();
        //creer son socket d'envoi udp
				createUDPSender();
				runServer();
				
    }
    
    private void createUDPServer(){
			 serve = new Server(sock, lengthOfdataRcv);         
              
    }
		private void runServer(){
			this.serve.start();
		}
		public void closeSocket(){
			sock.close();
		}
		
		private void createUDPSender(){
			send = new Sender(sock);
		}
		
		
		
		public void sendUDPSomeShit(byte[] message, InetAddress addr){
       send.send(message, addr,port);
			 
		}
    
    
}
