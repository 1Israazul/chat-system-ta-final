/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;
import signals.*;
import chat.system.*;
import Model.*;

import NI.NetworkInterface;

/**
 *
 * @author bardey
 */
public class Controle {
	private NetworkInterface nI;
	private ChatSystem cS;
	private RemoteUsers others;
	private Me me;
	
	public Controle (NetworkInterface nI, RemoteUsers o){
		this.nI = nI;
		this.cS = cS;
		this.others = o;
		
	}
	
	public void connect(String userName){
		if (checkUserName(userName)){
			this.me = new Me(userName);
			this.nI.sendHello(me.getUserName());
		}
	}
	
	
	private boolean checkUserName(String uN){
		return true;
	}
	
	public void helloReceived(Signal hy){
		String userName = ((Hello) hy).getUsername();
		String tmp []= userName.split("@");
		others.addRemoteUser(userName,tmp[1]);
		System.out.println("**** "+userName+" is added to the list of users at the adresse "+tmp[1]);
		try{
			nI.sendHelloOK(me.getUserName(),others.getRemoteUserAdress(userName));
		}catch (Exception e){
			System.err.println(e);
		}
		
	}
	
	public void helloOKReceived(Signal hy){
		String userName = ((HelloOK) hy).getUsername();		
		String tmp []= userName.split("@");
		others.addRemoteUser(userName,tmp[1]);
		System.out.println("**** "+userName+" is added to the list of users at the adresse "+tmp[1]);
	}
	
	public void sendMessage(String message, String remoteUser){
		String remoteAddr = others.getRemoteUserAdress(remoteUser);
		
		nI.sendTextMessage(message, remoteAddr, null);
		
	}
	
	
}
