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
	boolean isUsed;
	String fileName;
	
	
	public demandeFileTrans(){
		
	}
	
	
	public boolean canUse(){
		return isUsed;
	}
	public void use(){
		isUsed = true;
	}
	public void free(){
		isUsed = false;
	}
	
	
	public String getFileName(){
		return this.fileName;
	}
	public void setFileName(String name){
		this.fileName = name;
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
		this.fileName = theFile.getName();
	}
	public long gettaille(){
		return this.taille;
	}
	public void settaille(long taille){
		this.taille = taille;
	}
}
