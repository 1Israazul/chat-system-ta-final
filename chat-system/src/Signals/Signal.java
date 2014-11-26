/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author gb
 */
public abstract class Signal implements Serializable {
    
    public final static int MAX_SIZE = 1024;
    
    public static byte[] toByteArray(Signal signal) throws SignalTooBigException, IOException {

        byte[] result = new byte[MAX_SIZE];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        
        oos.writeObject(signal);
        
        if (baos.size() > MAX_SIZE) {
            throw new SignalTooBigException("Signal too big");
        }
        
        result = baos.toByteArray();
        return result;
    }

    public static Signal fromByteArray(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        
        return ((Signal) ois.readObject());
    }
}
