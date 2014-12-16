/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chat.system;

import NI.MyNetworkInterface;
import Controler.Controle;
import Model.*;
import java.util.*;
import Gui.Gui;

/**
 * Main class Entry point of the Application
 *
 * @author bardey and dauriac
 */
public class ChatSystem {

    private static MyNetworkInterface nI;
    private static Controle controle;
    private static Gui gui;
    private static RemoteUsers remoteUsers;


    /**
     * Public static void main methode (Here starts the fireworcks, enjoy)
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            nI = new MyNetworkInterface();
            remoteUsers = new RemoteUsers();
            gui = new Gui();
            controle = new Controle(nI, remoteUsers, gui);
            gui.setControler(controle);
            nI.setControler(controle);
			//plus rien sur le NI ici ! pas besion ! 

            //pourais être fait par la controler
            gui.commencer();

        } catch (Exception e) {
            System.err.println("PB dans main ! " + e);
            nI.closeSocket();
        }

    }
    //is sury not used, never.
    public Controle getControler() {
        return this.controle;
    }

    
//debug method does not concern any one
    public static void samuserAvecLeSystem() {
        //on envoi un hello
        boolean continuer = true;

        while (continuer) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("enter an integer");
            int myint = keyboard.nextInt();
            if (myint == 0) {
                continuer = false;
            } else {
                String to = remoteUsers.getRemoteUserAdressRand();
                System.out.println(to);
                controle.sendMessage("salut", to);
                System.out.println("Users in the base : " + remoteUsers.toString());;
            }

        }

    }

    
}
