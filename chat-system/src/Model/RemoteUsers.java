/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.net.InetAddress;
import java.util.*;

/**
 *The remotes users are stored here in a HashMap. You can access the need informations with the preseted messages.
 * @author bardey
 */
public class RemoteUsers {
	
	private HashMap <String , InetAddress> others;
	/**
         * Constructeur of the class, intenciats a empty HashMap
         */
	public RemoteUsers(){
		others = new HashMap<String, InetAddress>();
	}
	/**
         * Adds a User in the Registory.
         * @param uN
         * @param addr 
         */
	public void addRemoteUser(String uN, InetAddress addr){
		this.others.put(uN,addr);
		//System.out.println(this.others.toString());;
	}
	/**
         * InnetAdress a RemoteUser
         * @param UserName
         * @return InnetAdresse of the Remote User
         */
	public InetAddress getRemoteUserAdress(String Un){
		return this.others.get(Un);		
	}
	/**
         * Used in testing and debug to get a remote user adresse randomly in the registory.
         * @return 
         */
	public String getRemoteUserAdressRand(){
		Random generator = new Random();
		Object[] keys = others.keySet().toArray();
		String randomValue = (String) keys[generator.nextInt(keys.length)];
		return randomValue;
	}
	/**
         * Deletes a Remote User in the registory.
         * @param idiot 
         */
	public void killRemoteUser(String idiot){
		this.others.remove(idiot);
	}
	/**
         * Debug function to inspecte the Registory.
         * @return 
         */
	public String toString(){
		return "Hasmap cotient : "+others.size();
	}
	
}
