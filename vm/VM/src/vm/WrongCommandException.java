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
public class WrongCommandException extends MyException{
    
    public WrongCommandException(){
        super();
    }
    
    public WrongCommandException(String msg){
        super(msg);
    }
    
    public WrongCommandException(String msg, String command){
        super(msg);
        System.err.println(command);
    }
}
