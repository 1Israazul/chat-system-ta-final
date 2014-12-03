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

/**
 *
 * @author bardey
 */
public class Controle implements ActionListener {
	private MyNetworkInterface nI;
	private ChatSystem cS;
	private RemoteUsers others;
	private Me me;
        private Interface i;
        private Accueil a;
        private boolean connected = false;
        public String username;
	
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
        
        public void setInterface(Interface i){
            this.i = i;
        }
        
        public void setAccueil(Accueil a){
            this.a = a;
        }
        
        public void setUsername(String s){
            i.getUsernameLabel().setText(s);
        }
	
	
	private boolean checkUserName(String uN){
		return true;
	}
	
	public void helloReceived(Signal hy){
		String userName = ((Hello) hy).getUsername();
		String tmp []= userName.split("@");
		others.addRemoteUser(userName,tmp[1]);
		System.out.println("**** "+userName+" is added to the list of users at the adresse "+tmp[1]);
                i.getUsersTextArea().append(userName+"\n");
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
                i.getConversationTextArea().append("Received from "+sender+" : "+message+"\n");
	} 
	public void byeReceived(Signal bye){
			String idiot = ((Goodbye) bye).getUsername();
			others.killRemoteUser(idiot);	
                        //enlever le mec de la liste des users connectés
	}
	
	
	
	public void helloOKReceived(Signal hy){
		String userName = ((HelloOK) hy).getUsername();		
		String tmp []= userName.split("@");
		others.addRemoteUser(userName,tmp[1]);
		System.out.println("**** "+userName+" is added to the list of users at the adresse "+tmp[1]);
                i.getUsersTextArea().append(userName+"\n");
	}
	
	public void sendMessage(String message, String remoteUser){
		String remoteAddr = others.getRemoteUserAdress(remoteUser);
		System.out.println("");
		nI.sendTextMessage(message, remoteAddr, null);
                i.getConversationTextArea().append("Sent to "+i.getRemoteTextField().getText()+" : "+i.getMessageTextArea().getText()+"\n");
                i.getMessageTextArea().setText("");
		
	}
	
	public void sendBye(){
		nI.sendBy(me.getUserName());
	}
        
        public boolean getConnected(){
            return this.connected;
        }
        
        public void actionPerformed(ActionEvent e){
            if(e.getSource() == a.getConnectButton()){
                connect(a.getUsernameTextArea().getText());
                username=a.getUsernameTextArea().getText();
                a.setVisible(false);
                this.connected=true;
            }
            
            else if(e.getSource() == i.getSendButton()){
                sendMessage(i.getMessageTextArea().getText(),i.getRemoteTextField().getText());  
            }
            
            else if (e.getSource() == i.getDisconnectButton()){
                //se déconnecter
            }
            
        }
}
