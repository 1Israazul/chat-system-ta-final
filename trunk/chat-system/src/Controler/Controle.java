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

/**
 *
 * @author bardey
 */
public class Controle implements ActionListener, WindowListener,MouseListener {

	private MyNetworkInterface nI;
	private ChatSystem cS;
	private RemoteUsers others;
	private Me me;
	private Interface i;
	private Accueil a;
	private boolean connected = false;
	//public String username;

	public Controle(MyNetworkInterface nI, RemoteUsers o) {
		this.nI = nI;
		this.cS = cS;
		this.others = o;

	}

	public void connect(String userName) {
		if (checkUserName(userName)) {
			this.me = new Me(userName);
			this.nI.sendHello(me.getUserName());
		}
	}

	public void setInterface(Interface i) {
		this.i = i;
	}

	public void setAccueil(Accueil a) {
		this.a = a;
	}

	public void setUsername(String s) {
		i.getUsernameLabel().setText(s);
	}

	private boolean checkUserName(String uN) {
		return true;
	}

	private void addNewRemotUser(String userName, InetAddress from){
		
		others.addRemoteUser(userName, from);
		System.out.println("**** " + userName + " is added to the list of users at the adresse " + from);
		if (i != null) {// a t on ouver la fenètre ?
			//Son passe ici (j'ai testé)
			i.addRemoteUser(userName);
		}
	}
	
	
	public void helloReceived(Signal hy, InetAddress from) {
		String userName = ((Hello) hy).getUsername();
		//System.out.println("helloReceived controler");
		addNewRemotUser(userName,from);

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
		i.getConversationTextArea().append("Received from " + sender + " : " + message + "\n");
	}

	public void byeReceived(Signal bye) {
		String idiot = ((Goodbye) bye).getUsername();
		others.killRemoteUser(idiot);
                System.out.println("received bye from "+idiot);
                i.removeRemoteUser(idiot);
		
		//enlever le mec de la liste des users connectés
	}

	public void helloOKReceived(Signal hy, InetAddress from) {
		String userName = ((HelloOK) hy).getUsername();
		addNewRemotUser(userName, from);
		
	}

	public void sendMessage(String message, String remoteUser) {
		InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
		//System.out.println("");
		nI.sendTextMessage(message, me.getUserName() , remoteAddr);
		i.getConversationTextArea().append("Sent to " + i.getRemoteTextField().getText() + " : " + i.getMessageTextArea().getText() + "\n");
		i.getMessageTextArea().setText("");

	}

	public void sendBye() {
		nI.sendBy(me.getUserName());
	}

	public boolean getConnected() {
		return this.connected;
	}

	////////////////////////////////////////////////:
	////////////////////////////////////////////////:
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == a.getConnectButton()) {
			String myName = a.getUsernameTextArea().getText();
			a.dispose();
			i = new Interface(this);
			connect(myName);
			//username=a.getUsernameTextArea().getText();
			

		} else if (e.getSource() == i.getSendButton()) {
			sendMessage(i.getMessageTextArea().getText(), i.getRemoteTextField().getText() );
			
			
			//System.out.println(i.getUserSelected());
		} else if (e.getSource() == i.getDisconnectButton()) {
			disconnect();
			// à relier sur la gui.
			
		}

	}
	
	
	private void disconnect(){
		sendBye();
		nI.killServe();
		nI.closeSocket();
		i.dispose();		
		System.out.println("TOUT EST BIEN FINI !");
		//To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowOpened(WindowEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowClosing(WindowEvent e) {
		disconnect();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowIconified(WindowEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowActivated(WindowEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if ((e.getSource() == i.getUsersList()) && (i.getUserSelected() != null) ){
			
			i.setRemoteTextField(i.getUserSelected());
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
