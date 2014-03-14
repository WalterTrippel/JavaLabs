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
public class TooLargeNumberException extends MyException{
    
    public TooLargeNumberException(){
        super();
    }
    
    public TooLargeNumberException(String msg){
        super(msg);
    }
    
}
