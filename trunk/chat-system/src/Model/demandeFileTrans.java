/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.*;


/**
 *When a fille transfere resquest is been treted this classe helps you keep track of the stat of this request.This can be user ethear for files to be sent and to be recieved (will not precise it each time, we take into account that you understand that you can use it in anyways).
 * @author bardey and dauriac
 */
public class demandeFileTrans {
	
	String remoteUser ;
	File filetoSend;
	long taille;
	boolean isUsed;
	String fileName;
	
	/**
         * Simple constructer.
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
         * Get the name of the fille that is curently been transfered.
         * @return name of the file
         */
	public String getFileName(){
		return this.fileName;
	}
        /**
         * Set the name of the fille that is going to be transfered.
         * @param name 
         */
	public void setFileName(String name){
		this.fileName = name;
	}
        /**
         * Get the name of the remote User to whom the is curently been transfered.
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
         * Get the fille that is going to be transfered.
         * @return file that is been sent
         */
	public File getFile(){
		return this.filetoSend;
	}
        /**
         * Set the fille that is going to be transfered.
         * @param File that is been sent
         */
	public void setFile(File theFile){
		this.filetoSend = theFile;
		this.fileName = theFile.getName();
	}
        /**
         * Get the size of the fille that is going to be transfered.
         * @return size of the fille to be sent
         */
	public long gettaille(){
		return this.taille;
	}
        /**
         * Set the size of the fille that is going to be transfered.
         * @param taille size of the file
         */
	public void settaille(long taille){
		this.taille = taille;
	}
}
