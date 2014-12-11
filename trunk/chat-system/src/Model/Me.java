/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author bardey
 */
public class Me {
	
	private String userName;
	private String userNameWithIP;
	
	public Me (){
		
	}
	public void setUserNameWithIP(String u){
		this.userNameWithIP = u;
	}
	
	public void setUserName(String u){
		this.userName = u;
	}
	
	public String getUserNameWithIP(){
		return this.userNameWithIP;
	}
	public String getUserName(){
		return this.userName;
	}
	
}
