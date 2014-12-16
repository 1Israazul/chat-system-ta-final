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
 *This class descripse all the possible signals that a user can send to the differents windows of the system. Thos windows will not be described.
 * @author bardey and dauriac
 */
public class Gui implements ActionListener, WindowListener, MouseListener {
	private Controle c;
	private Accueil a;
	private Interface i;
	private FileTransferDialog ft;

    /**
     *Simple constructer of the classe.
     */
    public Gui() {
	}

    /**
     *The gui need to be related to your controler. Therefore you need to set the controler that the Gui is going to use.
     * @param c Controler you are using.
     */
    public void setControler(Controle c) {
		this.c = c;
	}

    /**
     *Displays the Welcome window of our application.
     */
    public void commencer(){
	a = new Accueil(this);
    }
	
    /**
     * Displays a dialog box to know if the user wants to accepte the fille or not.
     * @param nameFile Name of the file you want to transfer
     * @param from Remote user that is sending the proposal
     */
    public void askAuthorizationFileTransfer(String nameFile,String from){
		ft = new FileTransferDialog(nameFile, from, this);
	
    }	
    /**
     *Adds a New remote user in the Remote users list in the main window.
     * @param userName Remote user name to be displayed. 
     */
    public void addRemoteUser(String userName){
		i.addRemoteUser(userName);
    }
    /**
     * Display the message you've  sent an other user in the main window.
     * @param sender personne whom to you have sent the message.
     * @param message Message
     */
    public void displayNewMessage(String sender, String message){
		i.getConversationTextArea().append("Received from " + sender + " : " + message + "\n");
	}

    /**
     *Removes a user in the list of the users in the users list of the main window.
     * @param userName User that will be removed from the liste
     */
    public void removeRemoteUser(String idiot){
		i.removeRemoteUser(idiot);
	}

    /**
     *  Display the message you've recieved an other user in the main window.
     * @param message personne from whom you've received a message
     * @param remoteUser Message
     */
    public void messageSent(String message, String remoteUser){
		i.getConversationTextArea().append("Sent to " + remoteUser + " : " + message + "\n");
		i.getMessageTextArea().setText("");
		
	}

    /**
     *  Informes the user in the main window that you've sent a file proposal.
     * @param remoteU remote user that has sent a file proposal to
     * @param fileName file name of the proposal
     */
    public void filePropSent(String remoteU, String fileName){
		i.getConversationTextArea().append("Sent file transfer proposal (" + fileName+ ") to : " + remoteU);
    }
    /**
     * Informes the user in the main window that the file transfer has been refused.
     * @param remoteU user that refused the file
     * @param fileName name of the file that has been refused
     */
    public void fileRefused(String remoteU, String fileName){
		i.getConversationTextArea().append(remoteU+" has REFUSED the transfere, "+ fileName);
	}

    /**
     * Informes the user in the main window that the file transfer has been Accepted.
     * @param remoteU user that accepted the file
     * @param fileName name of the file that has been accepted
     */
    public void fileAcceptedIsSending(String remoteU, String fileName){
		i.getConversationTextArea().append(remoteU+" has accepted the transfere, "+ fileName+" has been sent");
	}

    /**
     * Informes the user in the main window that something went wrong. (for any reason)
     */
    public void somethingWentRong(){
		i.getConversationTextArea().append("SOMETHING WHEN WRONG DURING THE TRANSFERE");
	}

    /**
     *Informes the user in the main window that he has received a file.
     * @param remoteU user that sent the file
     * @param fileName name of the file that has been sent
     * @param where folder where the file has been sent.
     */
    public void filleRecieved(String remoteU, String fileName, String where){
		i.getConversationTextArea().append("You recieved a fille in : "+where+" from "+remoteU);
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
			a = new Accueil(this);
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
