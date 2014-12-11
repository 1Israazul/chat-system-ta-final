package NI;

import signals.*;
import java.net.*;
import java.util.*;
import Controler.Controle;
import Model.demandeFileTrans;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author bardey
 */
public class MyNetworkInterface {

	private int port = 4444;
	private DatagramSocket sock;
	private int lengthOfdataRcv = 1024; //1024;
	private Server serve;
	private Sender send;
	private Controle c;
	private InetAddress myAddr;
	private InetAddress remoteFileSender;
	private InetAddress broadCast;
	private TCPServer serv;

	public MyNetworkInterface() {
		try {

			getIpOfInterfac("eth0");

			//myAddr = InetAddress.getByName("10.208.255.202");
			//broadCast = InetAddress.getByName("10.255.255.255");
			System.out.println("Localhost : " + InetAddress.getLocalHost().getHostAddress());
			sock = new DatagramSocket(port);
			sock.setBroadcast(true);

		} catch (Exception e) {
			System.out.println("Network interface (creating the socket) : " + e);
		}
		//Il nous faut un server UDP et un client UDP
		createUDPServer();
		//creer son socket d'envoi udp
		createUDPSender();
		runServer();

	}
	
	public String getIpString(){
		return this.myAddr.toString().substring(1);
	}

	public void setControler(Controle c) {
		this.c = c;
	}

	private void createUDPServer() {
		serve = new Server(sock, lengthOfdataRcv, this);

	}

	private void runServer() {
		this.serve.start();
	}

	public void closeSocket() {
		sock.close();
	}

	private void createUDPSender() {
		try {
			send = new Sender(sock);
		} catch (SocketException ex) {
			Logger.getLogger(MyNetworkInterface.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void sendUDPSomeShit(byte[] message, InetAddress addr) {
		send.send(message, addr, port);

	}

	public void killServe() {
		serve.killMe();
	}

	public void sendHello(String uN) {
		try {
			Hello h = new Hello(uN);
			byte[] mess = signals.Signal.toByteArray(h);
			send.send(mess, broadCast, port);
			System.out.println("hello envoyé à ");

		} catch (Exception e) {
			System.err.println("Le message Hello n'est pas parti : " + e);
		}

	}

	public void sendHelloOK(String uN, InetAddress remoteAddr) {
		try {
			uN = uN.concat("@").concat(this.myAddr.toString().substring(1));
			HelloOK h = new HelloOK(uN);

			byte[] mess = signals.Signal.toByteArray(h);
			send.send(mess, remoteAddr, port);
		} catch (Exception e) {
			System.err.println("Le message HelloOK n'est pas parti : " + e);
		}
	}

	public void sendTextMessage(String message, String me, InetAddress to) {
		TextMessage tMess = new TextMessage(message, me, null);;
		try {
			byte[] mess = signals.Signal.toByteArray(tMess);
			send.send(mess, to, port);
		} catch (Exception e) {
			System.err.println("Le TextMessage n'est pas parti : " + e);
		}

	}

	public void sendFileProposal(demandeFileTrans demande, String me, InetAddress to) {
		String fileName = demande.getFile().getName();
		long size = demande.gettaille();
		FileProposal fp = new FileProposal(fileName, size, me, null);
		try {
			byte[] proposal = signals.Signal.toByteArray(fp);
			send.send(proposal, to, port);//UDP OK
		} catch (Exception e) {
			System.err.println("File proposal pas parti : " + e);
		}
	}

	public void sendFileTransferAccepted(String file, InetAddress to) {
		FileTransferAccepted ftA = new FileTransferAccepted(file, this.myAddr.toString().substring(1));
		try {
			byte[] transferAccepted = signals.Signal.toByteArray(ftA);
			send.send(transferAccepted, to, port); //UDP ok
		} catch (Exception e) {
			System.err.println("erreur acceptation transfert : " + e);
		}
	}

	public void sendFileTransferNotAccepted(String file, String remoteUserName, InetAddress to) {
		FileTransferNotAccepted ftNA = new FileTransferNotAccepted(file, remoteUserName);
		try {
			byte[] transferNotAccepted = signals.Signal.toByteArray(ftNA);
			send.send(transferNotAccepted, to, port);
		} catch (Exception e) {
			System.err.println("erreur refus transfert : " + e);
		}
	}

	public void sendFileTransfer(demandeFileTrans demande, InetAddress to) {
		try {
			FileInputStream is = new FileInputStream(demande.getFile());
			byte[] data = new byte[(int) demande.gettaille()];//okay 
			is.read(data);

			TCPSender sendTCP = new TCPSender(data, to, port);
			//System.out.println("file transfer envoyé");

		} catch (Exception e) {
			System.err.println("le fichier n'est pas parti : " + e);
		}

	}

	public void recieveFileTransfer(demandeFileTrans demande) {

		try {
			byte[] data = new byte[(int) demande.gettaille()];
			serv = new TCPServer(data, port, (int) demande.gettaille());

			//ça doit marcher cf stackoverflow

			FileOutputStream fos = new FileOutputStream("PATH! ");
			fos.write(data);
			fos.close();

			System.out.println("file transfer reçu");
		} catch (Exception e) {
			System.err.println("le fichier n'est pas parti : " + e);
		}

	}

	public void sendBy(String me) {
		me = me.concat("@").concat(this.myAddr.toString().substring(1));
		Goodbye bye = new Goodbye(me);
		try {
			byte[] mess = signals.Signal.toByteArray(bye);
			send.send(mess, broadCast, port);
			System.out.println("bye envoyé");
		} catch (Exception e) {
			System.err.println("Le Goodbye n'est pas parti : " + e);
		}
	}

	public void helloReceived(Signal res, InetAddress from) {
		c.helloReceived(res, from);

	}

	public void helloOKReceived(Signal res, InetAddress from) {
		c.helloOKReceived(res, from);

	}

	public void messageReceived(Signal res) {
		c.texteMessageReceived(res);
	}

	public void byeReceived(Signal res) {
		c.byeReceived(res);
	}

	public void fileProposalReceived(Signal res) {
		c.fileProposalReceived(res);
	}

	public void fileTransferAcceptedReceived(Signal res, InetAddress from) {
		c.fileOKReceived(res, from);
	}

	public void fileTransferNotAcceptedReceived(Signal res, InetAddress from) {
		c.fileNOKReceived(res, from);
	}

	public void fileTransferReceived(Signal res) {
		//System.out.println("pasage dans ni");
		c.fileTransferReceived(res);
	}

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
								myAddr = intAddress.getAddress();
								//System.out.println(myAddr);
								//System.out.println(myAddr.getHostAddress());
								//localIpAdressString=localIpAdress.getHostAddress();
								broadCast = intAddress.getBroadcast();
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
