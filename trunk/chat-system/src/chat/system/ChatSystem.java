/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

import NI.NetworkInterface;
import java.net.*;
import Controler.*;

/**
 *
 * @author bardey
 */
public class ChatSystem {

    private static NetworkInterface nI; 
		private static Controle controle;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
			try{
				nI = new NetworkInterface();
				controle = new Controle();
				
				
				nI.sendUDPSomeShit("lala".getBytes(), InetAddress.getByName("10.255.255.255"));
				samuserAvecLeSystem();
				
				nI.closeSocket();
			}catch(Exception e){
				System.err.println("PB dans main ! "+e);
			}
        
        
    }

//geter et seter de NI et gui
		public static void samuserAvecLeSystem(){
			//on envoi un hello
			
			
			
		}

}
