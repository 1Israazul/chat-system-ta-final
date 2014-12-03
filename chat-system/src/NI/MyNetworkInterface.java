package NI;

import signals.*;
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
public class MyNetworkInterface{
    private int port = 4444;
    private DatagramSocket sock;
		private int lengthOfdataRcv =1024;
		private Server serve;
		private Sender send;
		private Controle c;
		private InetAddress myAddr ;
		private InetAddress broadCast;
    
    public MyNetworkInterface (){
			try{
				sock = new DatagramSocket(port, InetAddress.getLocalHost());
				myAddr = InetAddress.getByName("10.1.5.23");
				broadCast =  InetAddress.getByName("10.1.255.255");
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
		
		public void killServe(){
			serve.killMe();
		}
		
		public void sendHello(String uN){
			try{
				uN = uN.concat("@").concat(myAddr.toString().substring(1)); //Attention !!! l'adresse peu ne pas être bonne ! substring enlève le slach
				Hello h = new Hello(uN);
				byte[] mess = signals.Signal.toByteArray(h);
				
				send.send(mess, broadCast, port); //changer !!!!! metre en broadcast!
				
			}catch (Exception e){
				System.err.println("Le message Hello n'est pas parti : "+e);
			}
			
		}
		public void sendHelloOK(String uN, String remoteAddr){
				try{
				uN = uN.concat("@").concat(this.myAddr.toString().substring(1));
				HelloOK h = new HelloOK(uN);
				
				byte[] mess = signals.Signal.toByteArray(h);
					//System.out.println("sendHelloOk --- remote addr "+remoteAddr);
					// metre la bonne adress
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
		public void sendBy(String me){
			Goodbye bye = new Goodbye(me); 
			try {
				byte[] mess = signals.Signal.toByteArray(bye);
				send.send(mess,broadCast, port);
			}catch (Exception e){
				System.err.println("Le Goodbye n'est pas parti : "+e);
			}	
		}
		
		
		
		
    public void helloReceived(Signal res){
			c.helloReceived(res);
		}
		public void helloOKReceived(Signal res){
			c.helloOKReceived(res);
			
		}
		public void messageReceived(Signal res){
			c.texteMessageReceived(res);
		}
		public void byeReceived(Signal res){
			c.byeReceived(res);
		}
		

		public static InetAddress getLocalIp() throws SocketException {
        Enumeration<NetworkInterface> enumInterf = NetworkInterface.getNetworkInterfaces();
				InetAddress res; 
        while (enumInterf.hasMoreElements()) {
            Enumeration<InetAddress> enumIp = enumInterf.nextElement().getInetAddresses();
            while (enumIp.hasMoreElements()) {
							res = enumIp.nextElement();
                if(!res.isLoopbackAddress()) {
                    return enumIp.nextElement();
                }
            }
        }
        return null;       
    }

		
		
		
}



