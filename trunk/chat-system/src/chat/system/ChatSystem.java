/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

import NI.MyNetworkInterface;
import java.net.*;
import Controler.*;
import Model.*;
import java.util.*;
import Gui.*;

/**
 *
 * @author bardey
 */
public class ChatSystem {

	private static MyNetworkInterface nI;
	private static Controle controle;
	private static Gui gui;
	private static RemoteUsers remoteUsers;
	private static Accueil accueil;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// TODO code application logic here
		try {
			nI = new MyNetworkInterface();
			remoteUsers = new RemoteUsers();
			gui = new Gui();
			controle = new Controle(nI, remoteUsers, gui);
			gui.setControler(controle);
			nI.setControler(controle);


			gui.commencer();
		

		} catch (Exception e) {
			System.err.println("PB dans main ! " + e);
			nI.closeSocket();
		}


	}

	public Controle getControler() {
		return this.controle;
	}

//geter et seter de NI et gui
	public static void samuserAvecLeSystem() {
		//on envoi un hello
		boolean continuer = true;




		while (continuer) {
			Scanner keyboard = new Scanner(System.in);
			System.out.println("enter an integer");
			int myint = keyboard.nextInt();
			if (myint == 0) {
				continuer = false;
			} else {
				String to = remoteUsers.getRemoteUserAdressRand();
				System.out.println(to);
				controle.sendMessage("salut", to);
				System.out.println("Users in the base : " + remoteUsers.toString());;
			}





		}



	}

	public static void waitForConnection() {
		while (controle.getConnected() == false) {
			System.out.println(" ");
		}

	}
}
