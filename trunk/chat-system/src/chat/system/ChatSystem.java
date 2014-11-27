/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

import NI.NetworkInterface;
import java.net.*;
import Controler.*;
import Model.*;
import java.util.*;

/**
 *
 * @author bardey
 */
public class ChatSystem {

    private static NetworkInterface nI; 
		private static Controle controle;
		private static RemoteUsers remoteUsers;
		
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
			try{
				nI = new NetworkInterface();
				remoteUsers = new RemoteUsers();
				controle = new Controle(nI, remoteUsers);
				nI.setControler(controle);
				
				
				//nI.sendUDPSomeShit("lala".getBytes(), InetAddress.getByName("10.1.5.49"));
				
				
				
				
				
				controle.connect("les BG");
				
				
				
				
				
				
				
				samuserAvecLeSystem();
				
				nI.closeSocket();
			}catch(Exception e){
				System.err.println("PB dans main ! "+e);
				nI.closeSocket();
			}
        
        
    }
		
		public Controle getControler(){
			return this.controle;
		}

//geter et seter de NI et gui
		public static void samuserAvecLeSystem() {
			//on envoi un hello
			boolean continuer = true;
			
			while (continuer){
				Scanner keyboard = new Scanner(System.in);
				System.out.println("enter an integer");
				int myint = keyboard.nextInt();
				
				if (myint == 0){
					continuer = false;
				}else{
					String to = remoteUsers.getRemoteUserAdressRand();
					controle.sendMessage("salut", to);
				}
			
								
				
				
				
			}
			
			
			
		}

}
