/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;
import signals.*;
import chat.system.*;
import Model.*;

import NI.MyNetworkInterface;

/**
 *
 * @author bardey
 */
public class Controle {
	private MyNetworkInterface nI;
	private ChatSystem cS;
	private RemoteUsers others;
	private Me me;
	
	public Controle (MyNetworkInterface nI, RemoteUsers o){
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
	public void texteMessageReceived(Signal hy){
		String sender = ((TextMessage) hy).getFrom();
		String message = ((TextMessage) hy).getMessage();
		System.out.println("["+sender+"] :::: "+message);
	} 
	public void byeReceived(Signal bye){
			String idiot = ((Goodbye) bye).getUsername();
			others.killRemoteUser(idiot);		
	}
	
	
	
	public void helloOKReceived(Signal hy){
		String userName = ((HelloOK) hy).getUsername();		
		String tmp []= userName.split("@");
		others.addRemoteUser(userName,tmp[1]);
		System.out.println("**** "+userName+" is added to the list of users at the adresse "+tmp[1]);
	}
	
	public void sendMessage(String message, String remoteUser){
		String remoteAddr = others.getRemoteUserAdress(remoteUser);
		System.out.println("");
		nI.sendTextMessage(message, remoteAddr, null);
		
	}
	
	public void sendBye(){
		nI.sendBy(me.getUserName());
	}
	
	
	
	
	
}
