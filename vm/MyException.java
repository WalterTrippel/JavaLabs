/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vm;

/**
 *
 * @author Walter
 */
public class MyException extends Exception {

    /**
     * Creates a new instance of <code>MyException</code> without detail
     * message.
     * To tell the truth, it's been created only like a buffer to get some lulz).
     * As I see now it has not provided any. But, according to the Sun documentation 
     * I have already faced with, neither of them have. 
     */
    public MyException() {
        super();
    }

    /**
     * Constructs an instance of <code>MyException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public MyException(String msg) {
        super(msg);
    }
    
    public MyException(int index){
       // super(index);
    }
}
