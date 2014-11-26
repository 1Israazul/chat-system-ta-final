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
				controle = new Controle(nI);
				nI.setControler(controle);
				
				//nI.sendUDPSomeShit("lala".getBytes(), InetAddress.getByName("10.1.5.49"));
				
				
				
				samuserAvecLeSystem();
				
				controle.connect("les BG");
				
				
				
				
				Thread.sleep(1000);
				
				
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
			
			
			
		}

}
