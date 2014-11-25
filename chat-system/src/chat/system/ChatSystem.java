/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

import NI.NetworkInterface;
import java.net.*;

/**
 *
 * @author bardey
 */
public class ChatSystem {

    private static NetworkInterface nI; 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
			try{
				nI = new NetworkInterface();
				nI.sendUDP("lala".getBytes(), InetAddress.getByName("10.255.255.255"));
				nI.closeSocket();
			}catch(Exception e){
				System.err.println("PB dans main ! "+e);
			}
        
        
    }

//geter et seter de NI et gui


}
