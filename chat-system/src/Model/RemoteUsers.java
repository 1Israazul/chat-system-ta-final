/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.net.InetAddress;
import java.util.*;

/**
 *The remote users are stored here in a HashMap. You can access the needed informations with the presetted messages.
 * @author bardey
 */
public class RemoteUsers {
	
	private HashMap <String , InetAddress> others;
	/**
         * Constructor of the class, instantiates an empty HashMap.
         */
	public RemoteUsers(){
		others = new HashMap<String, InetAddress>();
	}
	/**
         * Adds a User in the Registry.
         * @param uN
         * @param addr 
         */
	public void addRemoteUser(String uN, InetAddress addr){
		this.others.put(uN,addr);
		//System.out.println(this.others.toString());;
	}
	/**
         * InetAddress a RemoteUser
         * @param Un
         * @return InetAddress of the Remote User.
         */
	public InetAddress getRemoteUserAdress(String Un){
		return this.others.get(Un);		
	}
	/**
         * Used in testing and debugging to get a remote user address randomly in the registry.
         * @return 
         */
	public String getRemoteUserAdressRand(){
		Random generator = new Random();
		Object[] keys = others.keySet().toArray();
		String randomValue = (String) keys[generator.nextInt(keys.length)];
		return randomValue;
	}
	/**
         * Deletes a Remote User in the registry.
         * @param idiot 
         */
	public void killRemoteUser(String idiot){
		this.others.remove(idiot);
	}
	/**
         * Debug function to inspect the Registry.
         * @return 
         */
	public String toString(){
		return "Hasmap cotient : "+others.size();
	}
	
}
