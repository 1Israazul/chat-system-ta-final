/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import signals.*;
import chat.system.*;
import Model.*;
import Gui.*;
import NI.*;
import java.net.*;
import java.io.File;
import java.io.FileOutputStream;

/**
 *
 * @author bardey
 */
public class Controle {

    private MyNetworkInterface nI;
    private ChatSystem cS;
    private RemoteUsers others;
    private Me me;
    private boolean connected = false;
    private demandeFileTrans demandeFile;
    private Gui gui;
    private TCPServer FilleReceiver;

	//public String username;
    /**
     * Constructeur of the classe. You must relate the controler to the a
     * network interface, a Gui and a Model (here the other users)
     *
     * @param nI network interface
     * @param o others useres of the system
     * @param g Gui of the system
     */
    public Controle(MyNetworkInterface nI, RemoteUsers o, Gui g) {
        this.nI = nI;
        this.cS = cS;
        this.others = o;
        this.gui = g;

    }

    /**
     * This function connects you to the system.
     *
     * @param userName
     */
    public void connect(String userName) {
        if (checkUserName(userName)) {
			//creer le NI ici ! 

            nI.creatServers();
            this.me = new Me();
            this.me.setUserName(userName);
            this.me.setUserNameWithIP(userName + "@" + nI.getIpString());
            this.nI.sendHello(me.getUserNameWithIP());
        }
    }

    private boolean checkUserName(String uN) {
        return true;
    }

    /**
     * Disconects you from the system.
     */
    public void disconnect() {
        sendBye();
        nI.killServe();
        nI.closeSocket();
        System.out.println("TOUS LES SOCKET SONT CLOSE !");
        //To change body of generated methods, choose Tools | Templates.
    }

    private void addNewRemotUser(String userName, InetAddress from) {

        others.addRemoteUser(userName, from);
        System.out.println("**** " + userName + " is added to the list of users at the adresse " + from);
        if (gui != null) {
            gui.addRemoteUser(userName);
        }
    }

    /**
     * Performs all the necesary actions when a Hello Signal is recieved.
     * @param hy Signal (to be treated)
     * @param from InetAdress from whom the message has been receibed
     */
    public void helloReceived(Signal hy, InetAddress from) {
        String userName = ((Hello) hy).getUsername();

        System.out.println(userName + "          " + me.getUserNameWithIP());

        if (!userName.equals(me.getUserNameWithIP())) {
            addNewRemotUser(userName, from);

            try {
                nI.sendHelloOK(me.getUserNameWithIP(), others.getRemoteUserAdress(userName));
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

    /**
     *Performs all the necesary actions when a TextMessage Signal is recieved.
     * @param hy Signal (to be treated)
     */
    public void texteMessageReceived(Signal hy) {
        String sender = ((TextMessage) hy).getFrom();
        String message = ((TextMessage) hy).getMessage();
        System.out.println("[" + sender + "] :::: " + message);
        gui.displayNewMessage(sender, message);
    }

    /**
     *Performs all the necesary actions when a Bye Signal is recieved.
     * @param bye Signal (to be treated)
     */
    public void byeReceived(Signal bye) {
        String idiot = ((Goodbye) bye).getUsername();
        others.killRemoteUser(idiot);
        System.out.println("received bye from " + idiot);
        gui.removeRemoteUser(idiot);

        //enlever le mec de la liste des users connectés
    }

    /**
     *Performs all the necesary actions when a HelloOK Signal is recieved.
     * @param hy Signal (to be treated)
     * @param from InetAdress from whom the message has been receibed
     */
    public void helloOKReceived(Signal hy, InetAddress from) {
        String userName = ((HelloOK) hy).getUsername();
        addNewRemotUser(userName, from);

    }

    /**
     *Performs all the necesary actions when you want to send a message.
     * @param message Message to be sent
     * @param remoteUser user to whom the message will be sent
     */
    public void sendMessage(String message, String remoteUser) {
        InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
        //System.out.println("");
        nI.sendTextMessage(message, me.getUserNameWithIP(), remoteAddr);

        gui.messageSent(message, remoteUser);

    }

    /**
     *Performs all the necesary actions when you want to send a Fille.
     * @param file Fille to be sent
     * @param remoteUser user to whom the message will be sent
     */
    public void sendFileProp(File file, String remoteUser) {
        //verifier que tout est bon dans la demande.
        demandeFile = new demandeFileTrans();
        demandeFile.setFile(file);
        demandeFile.setRemotUser(remoteUser);
        demandeFile.settaille(file.length());
        demandeFile.use();

        nI.sendFileProposal(demandeFile, me.getUserNameWithIP(), others.getRemoteUserAdress(demandeFile.getRemotUser()));
        gui.filePropSent(remoteUser, file.getName());

        //gerer des états 
    }

    /**
     *Performs all the necesary actions when a FilePorposal Signal is recieved.
     * @param fp File proposal signal to be interterpreted
     */
    public void fileProposalReceived(Signal fp) {
        String nameFile = ((FileProposal) fp).getFileName();
        String from = ((FileProposal) fp).getFrom();
        long length = ((FileProposal) fp).getSize();

        System.out.println(from);

        demandeFile = new demandeFileTrans();
        demandeFile.setFileName(nameFile);
        demandeFile.setRemotUser(from);
        demandeFile.settaille(length);
        demandeFile.use();

        //set la demande + new si on peu
        gui.askAuthorizationFileTransfer(nameFile, from);

        //et la faut dire oui
    }

    /**
     *Performs all the necesary actions when you accepte to receive a file. (could be inprouved : no parametres needed)
     * @param fileName fille name to be received
     * @param remoteUser remote user to send the signal
     */
    public void sendFileOK(String file, String remoteUser) {
        //InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);

        try {

            //on doit ici ouvrir le server TCP avec son propre thread ! 
            int taille = (int) demandeFile.gettaille();
            nI.lancerFileReceiver(taille);
			//on a lancer le serveur pour recevoir le fichier;

            System.out.println("Le server est parti pour recevoir et moi j'en voie le fileOK (controler)");

            InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
            nI.sendFileTransferAccepted(file, remoteAddr, me.getUserNameWithIP());

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     *Performs all the necesary actions when you refused to receive a file. (could be inprouved : no parametres needed)
     * @param fileName fille name to be received
     * @param remoteUser remote user to send the signal
     */
    public void sendFileNOK(String file, String remoteUser) {
        //InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
        try {
            InetAddress remoteAddr = others.getRemoteUserAdress(remoteUser);
            nI.sendFileTransferNotAccepted(file, remoteUser, remoteAddr);
            //la demande a été refucé, on l'efasse
            gui.fileRefused(remoteUser, file);
            demandeFile.free();
            demandeFile = null;
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     *Performs all the necesary actions when you receuve a FileOk signal. 
     * @param signal signal 
     * @param remoteUser remote user whom send the signal
     */
    public void fileOKReceived(Signal s, InetAddress from) {
        try {
			//on peut envoyer si on a une vra demande de faite

            //debug necessaire : others.getRemoteUserAdress(demandeFile.getRemotUser()) == from
            if (demandeFile.canUse()) {
                System.out.println("Le Fichier a été accepté et est encours d'envoi");
                nI.sendFileTransfer(demandeFile.getFile(), (int) demandeFile.gettaille(), from); //ok ! revoir la fonction

                //en informer la GUI
                System.out.println("Le Fichier a été envoyé.");
                gui.fileAcceptedIsSending(demandeFile.getRemotUser(), demandeFile.getFile().getName());

                demandeFile = null;
            } else {
                System.out.println("Quelqu'un vous attaque ! ");
                demandeFile = null;

            }
        } catch (Exception e) {
            demandeFile = null;
            System.err.println("pb lors du transfert : " + e);
            gui.somethingWentRong();
        }
    }

    /**
     *Performs all the necesary actions when you receuve a FileNOTOk signal. 
     * @param signal signal 
     * @param remoteUser remote user whom send the signal
     */
    public void fileNOKReceived(Signal s, InetAddress from) {
        //on kill la demande
        String remoteUserName = ((FileTransferNotAccepted) s).getRemoteUsername();

        String remoteUser = others.getRemoteUserAdress(remoteUserName).toString();
        String fileName = ((FileTransferNotAccepted) s).getFileName();
        System.out.println(remoteUser + " a refusé le transfert du fichier " + fileName);
        //notifier dans la gui		
        gui.fileRefused(demandeFile.getRemotUser(), demandeFile.getFileName());
        demandeFile.free();
        demandeFile = null;
    }

    /**
     *Performs all the necesary actions when you receuve a Received a file (from the TCP server).
     * @param size Size of received file
     * @param file Bytes of the received file
     */
    public void fileTransferReceived(long taille, byte[] file) {
        //byte[] file = new byte[(int)taille];
        try {
			//verification que ce qu'on a rçu corespond bien à ce qu'on attend.

			//on peu ajoute un folder picker pour que l'utilisateur sache ou il 
            //le fichier qu'il va recevoir
            String where = System.getProperty("user.dir");
            //ecriture du fichier
            File fichier = new File(where + "/" + demandeFile.getFileName());
            FileOutputStream fos = new FileOutputStream(fichier);
            fos.write(file);
            fos.close();
            //Informer la gui que le ficheir est bien arrivé dsur notre machine (et ou)
            gui.filleRecieved(demandeFile.getRemotUser(), demandeFile.getFileName(), where);
            //la demande a été rempli on tu sa représentation dans le programme 
            demandeFile = null;

        } catch (Exception e) {
            System.err.println("erreur de l'écriture du fichier (controler) : " + e);
        }

    }

    /**
     *Sends a GoodBye signal on the network
     */
    public void sendBye() {
        nI.sendBy(me.getUserName());
    }

    public boolean getConnected() {
        return this.connected;
    }
}
