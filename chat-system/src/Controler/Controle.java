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
import java.awt.Desktop;
import java.net.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.time.Clock;
import javax.swing.JFileChooser;

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
        private FileTransferDialog ft;
	private boolean connected = false;
        private Path path;
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

	//public void setUsername(String s) {
	//	i.getUsernameLabel().setText(s);
	//}

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
        
        public void sendFileProp(File file, String remoteUser){
            InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
            nI.sendFileProposal(file, me.getUserName(), remoteAddr);
            i.getConversationTextArea().append("Sent file transfer proposal ("+file.getName()+") to : "+remoteUser);
        }
        
        public void fileProposalReceived(Signal fp){
            String file = ((FileProposal) fp).getFileName();
            String from = ((FileProposal) fp).getFrom();
            ft = new FileTransferDialog(file,from,this);
        }
        
        public void sendFileOK(String file, String remoteUser){
            //InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
            try{
            InetAddress remoteAddr = InetAddress.getByName("192.168.1.48");
            nI.sendFileTransferAccepted(file, remoteAddr);
            }catch(Exception e){
                System.err.println(e);
            }
        }
        
        public void sendFileNOK(String file, String remoteUser){
            //InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
            try{
            InetAddress remoteAddr = InetAddress.getByName("192.168.1.48");
            nI.sendFileTransferNotAccepted(file, remoteAddr);
            }catch(Exception e){
                System.err.println(e);
            }
        }
        
        public void fileOKReceived(Signal s, InetAddress from){
            try{
               InetAddress remoteAddr = from;
               nI.sendFileTransfer(path, remoteAddr);
            }catch(Exception e){
                System.err.println("pb lors du transfert : "+e);
            }
        }
        
        public void fileNOKReceived(Signal s, InetAddress from){
            String remoteUser = others.getRemoteUserAdress(((FileTransferNotAccepted) s).getRemoteUsername()).toString();
            String fileName = ((FileTransferNotAccepted) s).getFileName();
            System.out.println(remoteUser+" a refusé le transfert du fichier "+fileName);
        }
        
        public void fileTransferReceived(Signal s){
            byte[] file = ((FileTransfer) s).getFile();
            try{
                FileOutputStream fos = new FileOutputStream("C:\\Users\\Alexandre\\Downloads\\a");
                fos.write(file);
                fos.close();
            }catch(Exception e){
                System.err.println("erreur lors du transfert du fichier : "+e);
            }

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
                
                else if (e.getSource() == i.getFileButton()){
                    final JFileChooser fc = new JFileChooser();
                    try{
                    int returnVal = fc.showOpenDialog(i);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                         File file = fc.getSelectedFile();
                         path = file.toPath();
                         i.getConversationTextArea().append("Chose: " + file.getName() + ".\n");
                         sendFileProp(file, i.getRemoteTextField().getText());
                    }
                }
                    catch(Exception exc){
                        System.err.println("Erreur lors de l'ouverture de l'explorer : "+e);
                    }
                }
                
                else if (e.getSource() == ft.getAcceptButton()){
                    sendFileOK(ft.getFileLabel().getText(), ft.getRemoteLabel().getText());
                    ft.setVisible(false);
                }
                
                else if (e.getSource() == ft.getRefuseButton()){
                    
                    sendFileNOK(ft.getFileLabel().getText(), ft.getRemoteLabel().getText());
                    ft.setVisible(false);
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
