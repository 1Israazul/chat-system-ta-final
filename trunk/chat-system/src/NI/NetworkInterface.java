package NI;

import signals.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import Controler.Controle;


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
		private Controle c;
    
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
		public void setControler(Controle c){
			this.c = c;
		}
    
    private void createUDPServer(){
			 serve = new Server(sock, lengthOfdataRcv, this);         
              
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
		
		public void sendHello(Hello h){
			try{
				InetAddress addr =  InetAddress.getByName("10.1.5.49");
				byte[] mess = signals.Signal.toByteArray(h);
				send.send(mess, addr, port);
			}catch (Exception e){
				System.err.println("Le message Hello n'est pas parti : "+e);
			}
			
		}
		public void sendHelloOK(HelloOK h, InetAddress addr){
				try{
				byte[] mess = signals.Signal.toByteArray(h);
				send.send(mess, addr, port);
			}catch (Exception e){
				System.err.println("Le message Hello n'est pas parti : "+e);
			}			
		}
		
		
		
		
    public void helloReceived(Signal res){
			c.helloReceived(res);
		}
		
    
}
