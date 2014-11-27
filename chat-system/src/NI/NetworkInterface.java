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
		private InetAddress myAddr;
    
    public NetworkInterface (){
			try{
				sock = new DatagramSocket(port, InetAddress.getLocalHost());
				myAddr = InetAddress.getLocalHost();
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
		
		public void sendHello(String uN){
			try{
				uN = uN.concat("@").concat(myAddr.toString()); //Attention !!! l'adresse peu ne pas être bonne !
				Hello h = new Hello(uN);
				byte[] mess = signals.Signal.toByteArray(h);
				InetAddress addrBroacats = InetAddress.getByName("10.1.5.20");
				send.send(mess, addrBroacats, port); //changer !!!!! metre en broadcast!
				
			}catch (Exception e){
				System.err.println("Le message Hello n'est pas parti : "+e);
			}
			
		}
		public void sendHelloOK(String uN, String remoteAddr){
				try{
				uN = uN.concat("@").concat(this.myAddr.toString());
				HelloOK h = new HelloOK(uN);
				
				byte[] mess = signals.Signal.toByteArray(h);
					//System.out.println("sendHelloOk --- remote addr "+remoteAddr);
					remoteAddr = "10.1.5.20";// metre la bonne adress
					send.send(mess,InetAddress.getByName(remoteAddr) , port); //ne trouve pas l'adresse...
			}catch (Exception e){
				System.err.println("Le message HelloOK n'est pas parti : "+e);
			}			
		}
		public void sendTextMessage(String message, String me,String to){
			TextMessage tMess = new TextMessage(message, me, null);
			String remoteAddr = to;
			try {
				byte[] mess = signals.Signal.toByteArray(tMess);
				send.send(mess,InetAddress.getByName(remoteAddr) , port);
			}catch (Exception e){
				System.err.println("Le TextMessage n'est pas parti : "+e);
			}	
			
		}
		
		
		
    public void helloReceived(Signal res){
			c.helloReceived(res);
		}
		public void helloOKReceived(Signal res){
			c.helloOKReceived(res);
		}
		
		
    
}
