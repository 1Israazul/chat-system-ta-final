/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.*;


/**
 *
 * @author bardey
 */
public class demandeFileTrans {
	
	String remoteUser ;
	File filetoSend;
	long taille;
	
	
	public demandeFileTrans(){
		
	}
	
	
	public String getRemotUser(){
		return this.remoteUser;
	}
	public void setRemotUser(String user){
		this.remoteUser = user;
	}
	public File getFile(){
		return this.filetoSend;
	}
	public void setFile(File theFile){
		this.filetoSend = theFile;
	}
	public long gettaille(){
		return this.taille;
	}
	public void settaille(long taille){
		this.taille = taille;
	}
}
