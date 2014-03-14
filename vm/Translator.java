/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 *
 * @author Walter
 */
public class Translator {
    
    private LinkedList<String> list;
    private String[] buffer;
    
    /**
     * Initializes array of String's, which play role of special kinda interpreter,
     * one the other hand, creates an instance of <code> Translator </code>.
     */
    public Translator(LinkedList<String> list){
        this.list = list;
        this.buffer = new String[list.size()];
        for(int i = 0; i < buffer.length; ++i){
            buffer[i] = "00";
        }
    }
    
    /**
     * Method is introduced to get the current value (command) to be traslated to "machine" language.
     * 
     */ 
    private LinkedList<String> myStringHash(StringBuffer sb){
        String buffer = sb.toString();
        LinkedList<String> list = new LinkedList<>();
        Scanner scanner = new Scanner(buffer);
        while(scanner.hasNext()){
            list.push(scanner.next());
        }
        return list;
    }
    
    /**
     * The main "idea", so called "body" of the translater. It provides a totally interpretation
     * of the "alpha" command into "machine" code (based on registers primitives). Example :
     * load r0 # 100 -> will be translated into : 0x1064
     * load r1 # 12  -> will be translated into : 0x110C
     * mult r2 r1 r0 -> will be translated into : 0x3210
     * halt          -> will be translated into : 0x0000
     */
    public String[] translate() throws RegistersOutOfBoundsException, TooLargeInputException, WrongCommandException{
        int i = 0;
        Collections.reverse(list); 
        while(!list.isEmpty()){
            Queue<String> thisList = myStringHash(new StringBuffer(list.pop()));
            Collections.reverse((List<?>) thisList);
            while(!thisList.isEmpty()){
                String tmp = thisList.poll();
                if(!tmp.contains("\n")){
                    switch(tmp){
                        case "halt":{
                            buffer[i] += "0000";
                        }break;
                        case "load":{
                            buffer[i] += "1";
                            tmp = thisList.poll();
                            inCase(tmp, i);
                        }break;
                        case "add":{
                            buffer[i] += "2";
                            tmp = thisList.poll();
                            mainCase(tmp, i, thisList);
                        }break;
                        case "mult":{
                            buffer[i] += "3";
                            tmp = thisList.poll();
                            mainCase(tmp, i, thisList);
                        }break;
                        case "#":{
                            tmp = thisList.poll();
                            if(Integer.parseInt(tmp) < 16 && Integer.parseInt(tmp) > 0){
                                buffer[i] += "0";
                                buffer[i] += Integer.toHexString(Integer.parseInt(tmp));
                            } else {
                                buffer[i] += Integer.toHexString(Integer.parseInt(tmp));
                            }
                            if(Integer.parseInt(tmp) > 255){
                                throw new TooLargeInputException("The register can not load a number greater than 255! ");
                            }
                        }break; 
                        case "cmp":{
                            buffer[i] += "4";
                            tmp = thisList.poll();
                            mainCase(tmp, i, thisList);
                        }break; 
                        case "sqrt":{
                            buffer[i] += "5";
                            switchRegister(thisList, i);
                            buffer[i] += "0";
                            break;
                        }
                        case "sub":{
                            buffer[i] += "6";
                            tmp = thisList.poll();
                            mainCase(tmp, i, thisList); 
                        } break;
                        default: throw new WrongCommandException("Unknown command reported! ", tmp);
                    }
                }
            }
            ++i;
    }
        return buffer;
    }
    
    private void switchRegister(Queue<?> instruction, int j) throws RegistersOutOfBoundsException{
        String tmp = (String) instruction.poll();
        for(int i = 0 ; i < Engine.getNUM_REGS(); ++i){
            if(tmp.equals("r" +i)){
                buffer[j] += i;
                tmp= (String) instruction.poll();
                inCase(tmp, j);
                return;
            }
        }
        throw new RegistersOutOfBoundsException(tmp);
    }
    
    private void inCase(String tmp, int j) throws RegistersOutOfBoundsException{
        for(int i = 0; i < Engine.getNUM_REGS(); ++i){
            if(tmp.equals("r" + i)){
                buffer[j] += i;
                return;
            }
        }
        throw new RegistersOutOfBoundsException(tmp);
    }
    
    private void mainCase(String tmp, int j, Queue<?> thisList ) throws RegistersOutOfBoundsException{
        for(int i = 0; i < Engine.getNUM_REGS(); ++i){
            if(tmp.equals("r" + i)){
                buffer[j] += i;
                switchRegister((Queue<String>) thisList, j);
                return;
            }
        }
        throw new RegistersOutOfBoundsException(tmp);
    }
}
