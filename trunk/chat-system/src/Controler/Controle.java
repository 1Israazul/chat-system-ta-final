/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import signals.*;
import chat.system.*;
import Model.*;
import Gui.*;

import java.awt.event.*;
import NI.MyNetworkInterface;
import java.net.*;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;

/**
 *
 * @author bardey
 */
public class Controle  {

	private MyNetworkInterface nI;
	private ChatSystem cS;
	private RemoteUsers others;
	private Me me;
	private boolean connected = false;
	private demandeFileTrans demandeFile;
	private Gui gui;

	//public String username;
	public Controle(MyNetworkInterface nI, RemoteUsers o, Gui g) {
		this.nI = nI;
		this.cS = cS;
		this.others = o;
		this.gui = g;

	}

	public void connect(String userName) {
		if (checkUserName(userName)) {
			this.me = new Me();
			this.me.setUserName(userName);
			this.me.setUserNameWithIP(userName+"@"+nI.getIpString());
			this.nI.sendHello(me.getUserNameWithIP());
		}
	}

	
	private boolean checkUserName(String uN) {
		return true;
	}

	public void disconnect() {
		sendBye();
		nI.killServe();
		nI.closeSocket();
		System.out.println("TOUT EST BIEN FINI !");
		//To change body of generated methods, choose Tools | Templates.
	}

	private void addNewRemotUser(String userName, InetAddress from) {

		others.addRemoteUser(userName, from);
		System.out.println("**** " + userName + " is added to the list of users at the adresse " + from);
		if (gui != null) {
			gui.addRemoteUser(userName);
		}
	}

	public void helloReceived(Signal hy, InetAddress from) {
		String userName = ((Hello) hy).getUsername();
		addNewRemotUser(userName, from);

		try {
			nI.sendHelloOK(me.getUserName(), others.getRemoteUserAdress(userName));
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	public void texteMessageReceived(Signal hy) {
		String sender = ((TextMessage) hy).getFrom();
		String message = ((TextMessage) hy).getMessage();
		System.out.println("[" + sender + "] :::: " + message);
		gui.displayNewMessage(sender, message);
	}

	public void byeReceived(Signal bye) {
		String idiot = ((Goodbye) bye).getUsername();
		others.killRemoteUser(idiot);
		System.out.println("received bye from " + idiot);
		gui.removeRemoteUser(idiot);

		//enlever le mec de la liste des users connectés
	}

	public void helloOKReceived(Signal hy, InetAddress from) {
		String userName = ((HelloOK) hy).getUsername();
		addNewRemotUser(userName, from);

	}

	public void sendMessage(String message, String remoteUser) {
		InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
		//System.out.println("");
		nI.sendTextMessage(message, me.getUserName(), remoteAddr);
		
		gui.messageSent(message, remoteUser);
		

	}

	public void sendFileProp(File file, String remoteUser) {
		//verifier que tout est bon dans la demande.
		demandeFile = new demandeFileTrans();
		demandeFile.setFile(file);
		demandeFile.setRemotUser(remoteUser);
		demandeFile.settaille(file.length());
		demandeFile.use();

		nI.sendFileProposal(demandeFile, me.getUserNameWithIP(), others.getRemoteUserAdress(demandeFile.getRemotUser()));
		gui.filePropSent(remoteUser, file.getName());
		
		//gerer des états 
	}

	public void fileProposalReceived(Signal fp) {
		String nameFile = ((FileProposal) fp).getFileName();
		String from = ((FileProposal) fp).getFrom();
		long length = ((FileProposal) fp).getSize();
		
		System.out.println(from);


		demandeFile = new demandeFileTrans();
		demandeFile.setFileName(nameFile);
		demandeFile.setRemotUser(from);
		demandeFile.settaille(length);
		demandeFile.use();

		//set la demande + new si on peu
		gui.askAuthorizationFileTransfer(nameFile, from);
		
		

		//et la faut dire oui
	}

	public void sendFileOK(String file, String remoteUser) {
		//InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);

		try {

			InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
			nI.sendFileTransferAccepted(file, remoteAddr);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void sendFileNOK(String file, String remoteUser) {
		//InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
		try {
			InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
			nI.sendFileTransferNotAccepted(file ,remoteUser,remoteAddr );
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void fileOKReceived(Signal s, InetAddress from) {
		try {
			//on peut envoyer si on a une vra demande de faite

			//debug necessaire : others.getRemoteUserAdress(demandeFile.getRemotUser()) == from
			if (demandeFile.canUse()) {
				System.out.println("Le Fichier a été accepté et est encours d'envoi");
				nI.sendFileTransfer(demandeFile, from); //ok ! revoir la fonction

				//en informer la GUI
				System.out.println("Le Fichier a été envoyé.");
			} else {
				System.out.println("L'ordre d'envoie n'as pas était fait ! ");
			}


		} catch (Exception e) {
			System.err.println("pb lors du transfert : " + e);
		}
	}

	public void fileNOKReceived(Signal s, InetAddress from) {
		//on kill la demande
		String remoteUserName = ((FileTransferNotAccepted) s).getRemoteUsername();
		
		System.out.println(remoteUserName);
		
		String remoteUser = others.getRemoteUserAdress(remoteUserName).toString();
		String fileName = ((FileTransferNotAccepted) s).getFileName();
		System.out.println(remoteUser + " a refusé le transfert du fichier " + fileName);
		
		demandeFile.free();
		demandeFile = null;
		
	}

	public void fileTransferReceived(Signal s) {
		byte[] file = ((FileTransfer) s).getFile();
		try {
			FileOutputStream fos = new FileOutputStream("C:\\Users\\Alexandre\\Downloads\\a"); //NONONONONONONONONONONONON
			fos.write(file);
			fos.close();
		} catch (Exception e) {
			System.err.println("erreur lors du transfert du fichier : " + e);
		}

	}

	public void sendBye() {
		nI.sendBy(me.getUserName());
	}

	public boolean getConnected() {
		return this.connected;
	}
	////////////////////////////////////////////////:
	////////////////////////////////////////////////:
}
