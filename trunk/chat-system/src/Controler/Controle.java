/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;
import signals.*;
import chat.system.*;
import java.net.*;

import NI.NetworkInterface;

/**
 *
 * @author bardey
 */
public class Controle {
	private NetworkInterface nI;
	private ChatSystem cS;
	
	public Controle (NetworkInterface nI){
		this.nI = nI;
		this.cS = cS;
		
	}
	
	public void connect(String userName){
		if (checkUserName(userName)){
			this.nI.sendHello(new Hello(userName));
		}
	}
	
	
	private boolean checkUserName(String uN){
		return true;
	}
	
	public void helloReceived(Signal hy){
		String userName = ((Hello) hy).getUsername();
		System.out.println(userName);
		try{
			nI.sendHelloOK(new HelloOK("Nous"), InetAddress.getByName("10.1.5.49"));
		}catch (Exception e){
			System.err.println(e);
		}
		
	} 
}
