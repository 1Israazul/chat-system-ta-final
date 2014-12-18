/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *Decribes yourself into the system.
 * @author bardey
 */
public class Me {
	
	private String userName;
	private String userNameWithIP;
	/**
         * Simple constructor of the class;
         */
	public Me (){
		
	}
        /**
         * Stores your username with your IP address in this format "user@333.333.333.333".
         * @param u 
         */
	public void setUserNameWithIP(String u){
		this.userNameWithIP = u;
	}
	/**
         * Stores your username.
         * @param u 
         */
	public void setUserName(String u){
		this.userName = u;
	}
	/**
         * Finds your username with your IP address in this format "user@333.333.333.333".
         * @return 
         */
	public String getUserNameWithIP(){
		return this.userNameWithIP;
	}
        /**
         * Finds your username.
         * @return 
         */
	public String getUserName(){
		return this.userName;
	}
	
}
