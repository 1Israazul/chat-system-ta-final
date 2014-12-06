package NI;

import signals.*;
import java.net.*;
import java.util.*;
import Controler.Controle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;


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
                                sock.setBroadcast(true);
                                getIpOfInterfac("wlan0");
				//myAddr = IneAddress.getByName(/*"10.1.5.106"*/"192.168.173.1");
                                //myAddr = getLocalIp();
                                System.out.println("mon ip est : "+myAddr.toString());
				//broadCast =  InetAddress.getByName(/*"10.1.255.255"*/"255.255.255.255");
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
        try {
            send = new Sender(sock);
        } catch (SocketException ex) {
            Logger.getLogger(MyNetworkInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
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
				System.out.println("1");
                                Hello h = new Hello(uN);
				System.out.println("2");
                                byte[] mess = signals.Signal.toByteArray(h);
				System.out.println("3");
				send.send(mess, broadCast, port); //changer !!!!! metre en broadcast!
                                System.out.println("hello envoyé à ");
				
			}catch (Exception e){
				System.err.println("Le message Hello n'est pas parti : "+e);
			}
			
		}
		public void sendHelloOK(String uN, InetAddress remoteAddr){
				try{
				uN = uN.concat("@").concat(this.myAddr.toString().substring(1));
				HelloOK h = new HelloOK(uN);
				
				byte[] mess = signals.Signal.toByteArray(h);
					//System.out.println("sendHelloOk --- remote addr "+remoteAddr);
					// metre la bonne adress
					send.send(mess,remoteAddr , port); //ne trouve pas l'adresse...
			}catch (Exception e){
				System.err.println("Le message HelloOK n'est pas parti : "+e);
			}			
		}
		public void sendTextMessage(String message, String me ,InetAddress to){
			TextMessage tMess = new TextMessage(message, me, null);;
			try {
				byte[] mess = signals.Signal.toByteArray(tMess);
				send.send(mess, to , port);
			}catch (Exception e){
				System.err.println("Le TextMessage n'est pas parti : "+e);
			}	
			
		}
                
                public void sendFileProposal(File file, String me, InetAddress to){
                    String fileName=file.getName();
                    long size=file.getTotalSpace();
                    
                    FileProposal fp = new FileProposal(fileName,size,me,null);
                    try{
                        byte[] proposal = signals.Signal.toByteArray(fp);
                        send.send(proposal,to,port);
                    }catch(Exception e){
                        System.err.println("File proposal pas parti : "+e);
                    }
                }
		public void sendBy(String me){
            me = me.concat("@").concat(this.myAddr.toString().substring(1));
			Goodbye bye = new Goodbye(me); 
			try {
				byte[] mess = signals.Signal.toByteArray(bye);
				send.send(mess,broadCast, port);
                                System.out.println("bye envoyé");
			}catch (Exception e){
				System.err.println("Le Goodbye n'est pas parti : "+e);
			}	
		}
		
		
		
		
    public void helloReceived(Signal res, InetAddress from){
			c.helloReceived(res, from);
			
		}
		public void helloOKReceived(Signal res, InetAddress from){
			c.helloOKReceived(res,from);
			
		}
		public void messageReceived(Signal res){
			c.texteMessageReceived(res);
		}
		public void byeReceived(Signal res){
			c.byeReceived(res);
		}
		

		/*public static InetAddress getLocalIp() throws SocketException {
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
    }*/
                
                 private void getIpOfInterfac(String inter) throws UnknownHostException {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();

                System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
                if (intf.getName().equals(inter)) {
                   /* for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        //System.out.println("        " + enumIpAddr.nextElement().toString());
                        InetAddress adressInterface = enumIpAddr.nextElement();
                        if (adressInterface.getAddress().length == 4) {
                            localIpAdress = adressInterface;
                        }
                    }*/
                    for (InterfaceAddress intAddress : intf.getInterfaceAddresses()) {
                        {
                            if (intAddress.getAddress().getAddress().length == 4) {
                                myAddr=intAddress.getAddress();
                                //System.out.println(myAddr.getHostAddress());
                                //localIpAdressString=localIpAdress.getHostAddress();
                                broadCast=intAddress.getBroadcast();
                                //broadcastString=broadcast.getHostAddress();
                               // System.out.println(intAddress.getBroadcast());
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println(" (error retrieving network interface list)");
        }

    }

		
		
		
}



