/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

/**
 *
 * @author gb
 */
public class SignalTooBigException extends Exception {

    /**
     *
     * @param signal_too_big
     */
    public SignalTooBigException(String signal_too_big) {
        super(signal_too_big);
    }
    
}
