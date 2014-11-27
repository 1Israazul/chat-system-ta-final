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
	}
	
	public String getRemoteUserAdress(String Un){
		return this.others.get(Un);		
	}
	
	public String getRemoteUserAdressRand(){
		Random generator = new Random();
		Object[] values = others.values().toArray();
		String randomValue = (String) values[generator.nextInt(values.length)];
		return randomValue;
	}
	
}
