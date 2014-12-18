package NI;

import signals.*;
import java.net.*;
import java.util.*;
import Controler.Controle;
import Model.demandeFileTrans;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import NI.FileNotSentEX;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This class implements all the necessary signals and messages needed to
 * interact with the local network. You can performe a connection, send a
 * message, a file...
 *
 * @author Bardey and Dauriac
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
	private byte[] filebytes;

	/**
	 * Constructor of the class no need of any parameter.
	 */
	public MyNetworkInterface() {
	}

	/**
	 * This is a crucial function, it will initialize all the sockets.
	 */
	public void creatServers() {
		try {
			getIPs();

			//myAddr = InetAddress.getByName("10.208.255.202");
			//broadCast = InetAddress.getByName("10.255.255.255");
			//System.out.println("Localhost : " + InetAddress.getLocalHost().getHostAddress());
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

	/**
	 * Sends back a string representation of the ip address you are using.
	 *
	 * @return Your IP address
	 */
	public String getIpString() {
		return this.myAddr.toString().substring(1);
	}

	/**
	 * Your network interface needs to be related to your controler. This function
	 * creates the link.
	 *
	 * @param c (controler)
	 */
	public void setControler(Controle c) {
		this.c = c;
	}

	/**
	 * Creates the UDPserver that will receive all the sent signals.
	 */
	private void createUDPServer() {
		serve = new Server(sock, lengthOfdataRcv, this);
	}

	/**
	 * Starts the UDPServer, it will then starts functionning.
	 */
	private void runServer() {
		this.serve.start();
	}

	/**
	 * Closes the recieving socket.
	 */
	public void closeSocket() {
		sock.close();
	}

	/**
	 * Stars the TCP server that will receive the sent files.
	 *
	 * @param taille Size of the file you want to receive
	 */
	public void lancerFileReceiver(int taille) {
		serv = new TCPServer(port, taille, this);
		serv.start();
		serv = null;//Sebastien Neumann m'a dit qu'il fallait mettre ça
		System.out.println("Le Server TCT a été lancé (NI)");
	}

	/**
	 * When your TCP has received the file it will call this function to send the
	 * data back to the controler.
	 *
	 * @param data bytes of the file you want to send
	 */
	public void fichierRecuParTCPServer(byte[] data) {

		try {
			c.fileTransferReceived(port, data);

			System.out.println("file transfer reçu (NI)");
		} catch (Exception e) {
			System.err.println("le fichier n'est pas parti (NI): " + e);
		}
	}

	/**
	 * This function is essential. It creates all the necessary items you need to
	 * send.
	 */
	private void createUDPSender() {
		try {
			send = new Sender(sock);
		} catch (SocketException ex) {
			Logger.getLogger(MyNetworkInterface.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Sends anything with the UDP sender module.
	 *
	 * @param message message you want to send
	 * @param addr address of the remote user you want to send this to
	 */
	public void sendUDPSomeShit(byte[] message, InetAddress addr) {
		send.send(message, addr, port);

	}

	/**
	 * Closes the UDP server. The network is not listening anymore.
	 */
	public void killServe() {
		serve.killMe();
	}

	/**
	 * Sends the Hello signal on the network.
	 *
	 * @param uN Your very own user name (respecting the convention)
	 */
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

	/**
	 * Sends the HelloOk Signal to a specific remote user.You need to inform the
	 * function of you username and of the destination IP address.
	 *
	 * @param uN Your very own user name (respecting the convention)
	 * @param remoteAddr adress of the remote user you will send this to
	 */
	public void sendHelloOK(String uN, InetAddress remoteAddr) {
		try {
			//uN = uN.concat("@").concat(this.myAddr.toString().substring(1));
			HelloOK h = new HelloOK(uN);

			byte[] mess = signals.Signal.toByteArray(h);
			send.send(mess, remoteAddr, port);
		} catch (Exception e) {
			System.err.println("Le message HelloOK n'est pas parti : " + e);
		}
	}

	/**
	 * Sends the TexteMessage Signal to a specific remote user.
	 *
	 * @param message message you want to send to your friend
	 * @param me Your very own user name (respecting the convention)
	 * @param to adress of the remote user you will send this to
	 */
	public void sendTextMessage(String message, String me, InetAddress to) {
		TextMessage tMess = new TextMessage(message, me, new ArrayList());;
		try {
			byte[] mess = signals.Signal.toByteArray(tMess);
			send.send(mess, to, port);
		} catch (Exception e) {
			System.err.println("Le TextMessage n'est pas parti : " + e);
		}

	}

	/**
	 * Sends a FileProposal signal to a specific remote user.
	 *
	 * @param demande class containing all the needed informations
	 * @param me Your very own user name (respecting the convention)
	 * @param to adress of the remote user you will send this to
	 */
	public void sendFileProposal(demandeFileTrans demande, String me, InetAddress to) {
		String fileName = demande.getFile().getName();
		long size = demande.gettaille();
		FileProposal fp = new FileProposal(fileName, size, me, new ArrayList());
		try {
			byte[] proposal = signals.Signal.toByteArray(fp);
			send.send(proposal, to, port);//UDP OK
		} catch (Exception e) {
			System.err.println("File proposal pas parti : " + e);
		}
	}

	/**
	 * Sends a fileTransferAccepted signal to a specific remote user.
	 *
	 * @param file file name of the file you accepted
	 * @param me Your very own user name (respecting the convention)
	 * @param to adress of the remote user you will send this to
	 */
	public void sendFileTransferAccepted(String file, InetAddress to, String me) {
		FileTransferAccepted ftA = new FileTransferAccepted(file, me); //chack that shit bro
		try {
			byte[] transferAccepted = signals.Signal.toByteArray(ftA);
			send.send(transferAccepted, to, port);
		} catch (Exception e) {
			System.err.println("erreur acceptation transfert : " + e);
		}
	}

	/**
	 * Sends a FileTransferNotAccepted signal to a specific remote user.
	 *
	 * @param file file name of the file you refused
	 * @param remoteUserName adress of the remote user you will send this to
	 * @param to adress of the remote user you will send this to
	 */
	public void sendFileTransferNotAccepted(String file, String remoteUserName, InetAddress to) {
		FileTransferNotAccepted ftNA = new FileTransferNotAccepted(file, remoteUserName);
		try {
			byte[] transferNotAccepted = signals.Signal.toByteArray(ftNA);
			send.send(transferNotAccepted, to, port);
		} catch (Exception e) {
			System.err.println("erreur refus transfert : " + e);
		}
	}

	/**
	 * Sends the file on the network.
	 *
	 * @param fichier file you are willing to sending
	 * @param taille size of the file you are sending
	 * @param to adress of the remote user you will send this to
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public void sendFileTransfer(File fichier, int taille, InetAddress to) throws FileNotFoundException, Exception {
		try {
			FileInputStream is = new FileInputStream(fichier);
			byte[] data = new byte[taille];//okay 
			is.read(data);
			TCPSender sendTCP = new TCPSender(data, to, port);
			System.out.println("file transfer envoyé");
		} catch (Exception e) {
			System.err.println("le fichier n'est pas parti (NI): " + e);
		}
	}

	/**
	 * Sends a GoodBye signal on the network.
	 *
	 * @param me Your very own user name (respecting the convention)
	 */
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

	/**
	 * Informs the controler that a Hello signal has been received.
	 *
	 * @param res signal you received
	 * @param from from whom you received the signal
	 */
	public void helloReceived(Signal res, InetAddress from) {
		c.helloReceived(res, from);

	}

	/**
	 * Informs the controler that a HelloOK signal has been received.
	 *
	 * @param res signal you received
	 * @param from from whom you received the signal
	 */
	public void helloOKReceived(Signal res, InetAddress from) {
		c.helloOKReceived(res, from);

	}

	/**
	 * Informs the controler that a TextMessage signal has been received.
	 *
	 * @param res signal you received
	 */
	public void messageReceived(Signal res) {
		c.texteMessageReceived(res);
	}

	/**
	 * Informs the controler that a GoodBye signal has been received.
	 *
	 * @param res signal you received
	 */
	public void byeReceived(Signal res) {
		c.byeReceived(res);
	}

	/**
	 * Informs the controler that a FileProposal signal has been received.
	 *
	 * @param res signal you received
	 */
	public void fileProposalReceived(Signal res) {
		c.fileProposalReceived(res);
	}

	/**
	 * Informs the controler that a TransferAcceptedReceived signal has been
	 * received.
	 *
	 * @param res signal you received
	 * @param from from whom you received the signal
	 */
	public void fileTransferAcceptedReceived(Signal res, InetAddress from) {
		c.fileOKReceived(res, from);
	}

	/**
	 * Informs the controler that a FileTransferNotAccepted signal has been
	 * received.
	 *
	 * @param res signal you received
	 * @param from from whom you received the signam
	 */
	public void fileTransferNotAcceptedReceived(Signal res, InetAddress from) {
		c.fileNOKReceived(res, from);
	}

	
	//Thanks to sebastien we can work on most platforms
	// function that find our @ip and the broadcast@ depending on our network
	public void getIPs() {
		boolean notFound = true;
		InetAddress addrIP = null;
		InetAddress broadcast = null;
		String os = System.getProperty("os.name");

		try {
// if we have Mac os X or Windows the following works
			if (os.contains("Windows") || os.contains("Mac")) {
// we find our @ip
				boolean found = false;
				addrIP = InetAddress.getLocalHost();
				NetworkInterface i = NetworkInterface.getByInetAddress(addrIP);

// we enumerate interface's @ip to find the broadcast one
				List<InterfaceAddress> list = i.getInterfaceAddresses();
				Iterator<InterfaceAddress> it = list.iterator();
				Enumeration en = i.getInetAddresses();
				while (en.hasMoreElements() && !found) {
					InterfaceAddress ia = it.next();
					InetAddress addr = (InetAddress) en.nextElement();

// we choose the ipv4 one and its @broadcast associated
					if (addr instanceof Inet4Address) {
						found = true;
						broadcast = ia.getBroadcast();
						break;
					}
				}
// else several Ubuntu versions doesn't fit with the previous lines so we need to do this :
			} else if (os.contains("Linux")) {

//enumeration of the interface we have
				Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();
				while (ni.hasMoreElements() && notFound) {
					NetworkInterface i = (NetworkInterface) ni.nextElement();
// if the interface we are checking is eth0 or wlan0 then we suppose it's found
// note : we suppose wlan0 and eth0 or not active in the same time
					if ((i.getName().equals("eth0")) || (i.getName().equals("wlan0"))) {
						notFound = false;
						List<InterfaceAddress> list = i.getInterfaceAddresses();
						Iterator<InterfaceAddress> it = list.iterator();

//we enumerate each @IP of the interface , we can have ipv6 and ipv4
						for (Enumeration en = i.getInetAddresses(); en.hasMoreElements();) {
							InterfaceAddress ia = it.next();
							InetAddress addr = (InetAddress) en.nextElement();

// we choose the ipv4 one and its @broadcast associated
							if (addr instanceof Inet4Address) {
								addrIP = addr;
								broadcast = ia.getBroadcast();

							}
						}
					}
				}
			}
		} catch (IOException e) {
			System.err.println("error");
		}

		if (addrIP == null) {
			System.err.println("DEBUG *** Network setIP : could not find any localIP ***");
			 
		} else {
			myAddr = addrIP ;
			broadCast = broadcast;
		}
	}
}
