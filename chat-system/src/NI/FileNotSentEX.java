/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NI;

/**
 *
 * @author bardey
 */
public class FileNotSentEX extends Exception{

	public FileNotSentEX(Exception res) {
		super("trace" + res);
	}
	
	
	
}
