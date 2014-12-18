/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.*;


/**
 *When a file transfer resquest has been treated this class helps you keep track of the state of this request.This can be used either for files to be sent or to be recieved (will not precise it each time, we take into account that you understand that you can use it in any way).
 * @author bardey and dauriac
 */
public class demandeFileTrans {
	
	String remoteUser ;
	File filetoSend;
	long taille;
	boolean isUsed;
	String fileName;
	
	/**
         * Simple constructor.
         */
	public demandeFileTrans(){
		
	}
	/**
         * Check the canUse semaphore.
         * @return 
         */
	public boolean canUse(){
		return isUsed;
	}
        /**
         * take the canUse semaphore.
         */
	public void use(){
		isUsed = true;
	}
        /**
         * Let go the canUse Semaphore.
         */
	public void free(){
		isUsed = false;
	}
        /**
         * Get the name of the file that is currently transferred.
         * @return name of the file
         */
	public String getFileName(){
		return this.fileName;
	}
        /**
         * Set the name of the file that is going to be transferred.
         * @param name 
         */
	public void setFileName(String name){
		this.fileName = name;
	}
        /**
         * Get the name of the remote User to whom the file is currently transferred.
         * @return remote user
         */
	public String getRemotUser(){
		return this.remoteUser;
	}
        /**
         * Set the name of the remote User to whom the is curently been transfered.
         * @param user 
         */
	public void setRemotUser(String user){
		this.remoteUser = user;
	}
        /**
         * Get the file that is going to be transferred.
         * @return file that has been sent
         */
	public File getFile(){
		return this.filetoSend;
	}
        /**
         * Set the file that is going to be transfered.
         * @param theFile that has been sent
         */
	public void setFile(File theFile){
		this.filetoSend = theFile;
		this.fileName = theFile.getName();
	}
        /**
         * Get the size of the file that is going to be transferred.
         * @return size of the file to be sent.
         */
	public long gettaille(){
		return this.taille;
	}
        /**
         * Set the size of the file that is going to be transferred.
         * @param taille size of the file
         */
	public void settaille(long taille){
		this.taille = taille;
	}
}
