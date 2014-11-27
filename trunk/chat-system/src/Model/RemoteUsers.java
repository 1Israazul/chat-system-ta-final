/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.*;

/**
 *
 * @author bardey
 */
public class RemoteUsers {
	
	private HashMap <String , String> others;
	
	public RemoteUsers(){
		others = new HashMap<String, String>();
	}
	
	public void addRemoteUser(String uN, String addr){
		this.others.put(uN,addr);
		//System.out.println(this.others.toString());;
	}
	
	public String getRemoteUserAdress(String Un){
		return this.others.get(Un);		
	}
	
	public String getRemoteUserAdressRand(){
		Random generator = new Random();
		Object[] keys = others.keySet().toArray();
		String randomValue = (String) keys[generator.nextInt(keys.length)];
		return randomValue;
	}
	
	public void killRemoteUser(String idiot){
		this.others.remove(idiot);
	}
	
	public String toString(){
		return "Hasmap cotient : "+others.size();
	}
	
}
