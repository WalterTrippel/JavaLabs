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
public class UnknownInstructionException extends MyException{
    
    public UnknownInstructionException(){
        super();
    }
    
    public UnknownInstructionException(String msg, int instruction){
        super(msg);
        System.err.println(instruction);
    }
    
}
