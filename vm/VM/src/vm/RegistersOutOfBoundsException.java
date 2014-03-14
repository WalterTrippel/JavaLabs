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
public class RegistersOutOfBoundsException extends MyException{
    
    public RegistersOutOfBoundsException(){
        super();
    }
    
    public RegistersOutOfBoundsException(String msg){
        super(msg);
    }
    
    public RegistersOutOfBoundsException(int index){
        super(index);
    }
    
}
