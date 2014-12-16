/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *Decribse your self in to the system.
 * @author bardey
 */
public class Me {
	
	private String userName;
	private String userNameWithIP;
	/**
         * Simple constructeur of the class;
         */
	public Me (){
		
	}
        /**
         * Stors your user name with your IP adresse in this format "user@333.333.333.333".
         * @param u 
         */
	public void setUserNameWithIP(String u){
		this.userNameWithIP = u;
	}
	/**
         * Stors your user name.
         * @param u 
         */
	public void setUserName(String u){
		this.userName = u;
	}
	/**
         * Find your user name with your IP adresse in this format "user@333.333.333.333".
         * @return 
         */
	public String getUserNameWithIP(){
		return this.userNameWithIP;
	}
        /**
         * Find your User Name.
         * @return 
         */
	public String getUserName(){
		return this.userName;
	}
	
}
