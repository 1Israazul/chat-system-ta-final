/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import Controler.*;
import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author Tristan
 */
public class Gui implements ActionListener, WindowListener, MouseListener {

	private Controle c;
	private Accueil a;
	private Interface i;
	private FileTransferDialog ft;

	public Gui() {
	}

	public void setControler(Controle c) {
		this.c = c;
	}

	public void commencer(){
		a = new Accueil(this);
	}
	
	public void askAuthorizationFileTransfer(String nameFile,String from){
		ft = new FileTransferDialog(nameFile, from, this);
	
	}
		
	
	public void addRemoteUser(String userName){
		i.addRemoteUser(userName);
	}
	public void displayNewMessage(String sender, String message){
		i.getConversationTextArea().append("Received from " + sender + " : " + message + "\n");
	}
	public void removeRemoteUser(String idiot){
		i.removeRemoteUser(idiot);
	}
	public void messageSent(String message, String remoteUser){
		i.getConversationTextArea().append("Sent to " + remoteUser + " : " + message + "\n");
		i.getMessageTextArea().setText("");
		
	}
	public void filePropSent(String remoteU, String fileName){
		i.getConversationTextArea().append("Sent file transfer proposal (" + fileName+ ") to : " + remoteU);

	}
					
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == a.getConnectButton()) {
			String myName = a.getUsernameTextArea().getText();
			a.dispose();
			i = new Interface(this);
			c.connect(myName);

		} else if (e.getSource() == i.getSendButton()) {
			c.sendMessage(i.getMessageTextArea().getText(), i.getRemoteTextField().getText());

			//System.out.println(i.getUserSelected());
		} else if (e.getSource() == i.getDisconnectButton()) {
			c.disconnect();
			i.dispose();
			//System.out.println("disco called (controle)");
			// à relier sur la gui.

		} else if (e.getSource() == i.getFileButton()) {
			final JFileChooser fc = new JFileChooser();
			try {
				int returnVal = fc.showOpenDialog(i);

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					c.sendFileProp(file, i.getRemoteTextField().getText());
					i.getConversationTextArea().append("Chose: " + file.getName() + ".\n");
				}
			} catch (Exception exc) {
				System.err.println("Erreur lors de l'ouverture de l'explorer : " + e);
			}
		} else if (e.getSource() == ft.getAcceptButton()) {
			c.sendFileOK(ft.getFileLabel().getText(), ft.getRemoteLabel().getText());
			//a changer
			
			ft.setVisible(false);
			ft.dispose();
		} else if (e.getSource() == ft.getRefuseButton()) {

			c.sendFileNOK(ft.getFileLabel().getText(), ft.getRemoteLabel().getText());
			ft.setVisible(false);
			ft.dispose();
		}

	}

	

	@Override
	public void windowOpened(WindowEvent e) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowClosing(WindowEvent e) {
		c.disconnect();
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
		if ((e.getSource() == i.getUsersList()) && (i.getUserSelected() != null)) {
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
