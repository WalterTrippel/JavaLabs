/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vm;

/**
 *
 * @author Walter
 * @see Translator to get main idea of how commands are interpred.
 */
public class Engine {

    
    private final static int NUM_REGS = 0x9;

    public static int getNUM_REGS() {
        return NUM_REGS;
    }
    private int[] regs;
    private int[] INPUT;
    private int programCounter = 0;
    
    private int instructionNumber = 0;
    private int firstRegister = 0;
    private int secondRegister = 0;
    private int thirdRegister = 0;
    private int inputM = 0;
    
    private boolean running = true;
    
    
/**
 * This class was created to play a role of a heart of Virtual Machine(VM).
 * Operations which are present by the moment: 
 * halt             - stops execution of the programm.
 * load             - loads a constant value into a register's cell; needs a reference to a cell number and a constant value to be loaded.
 * mult             - multiplies two constant values saved into some register's cells; needs a reference to a cell number, where the new value will be loaded as well as both referenced to each constant value.
 * add              - summarizes two constant values saved into some register's cells; needs a reference to a cell number, where the new value will be loaded as well as both referenced to each constant value.
 * cmp              - compares two constante values saved into some register's cells; needs a reference to a cell number, where the result of comparation will be holded as well as both referenced to each constant value. 
 * Pay attention on the way of how cmp works: 
 * if firstValue greater than secondValue it returns 1
 * if it is less                          it returns 0
 * else                                   it return the value
 * sqrt             - extractes a square root from a constant value; needs a reference to a cell number, where the result value will be holded as well as the refernce to a cell number of a constante value to manage with.
 * sub
*/
    public Engine(int[] INPUT){
        this.INPUT = INPUT;
        regs = new int[NUM_REGS];
    }
    
    private int fetch(){
        return INPUT[programCounter++];
    }
    
    private void decode(int instructions){
        instructionNumber = (instructions & 0xF000) >> 12;
        firstRegister = (instructions & 0xF00) >> 8;
        secondRegister = (instructions & 0xF0) >> 4;
        thirdRegister = (instructions & 0xF);
        inputM = (instructions & 0xFF);
    }
    
    private void evaluate() throws TooLargeNumberException, UnknownInstructionException, TooLargeInputException{
        switch(instructionNumber){
            case 0:
                System.out.printf("halt\n");
                running = false;
                break;
            case 1:
                System.out.printf("load r%d # %d\n", firstRegister, inputM);
                regs[firstRegister] = inputM;
                if(regs[firstRegister] > 255){
                    throw new TooLargeInputException("Register cannot load a number greater than 255! ");
                }
                break;
            case 2:
                System.out.printf("add r%d r%d r%d\n", firstRegister, secondRegister, thirdRegister);
                regs[firstRegister] = regs[secondRegister] + regs[thirdRegister];
                break;
            case 3:
                System.out.printf("mult r%d r%d r%d\n", firstRegister, secondRegister, thirdRegister);
                regs[firstRegister] = regs[secondRegister] * regs[thirdRegister];
                if(regs[firstRegister] > 65535){
                    throw new TooLargeNumberException("Register can not hold a number greater than 65535!");
                }break;
            case 4:
                System.out.printf("cmp r%d r%d (?>) r%d\n", firstRegister, secondRegister, thirdRegister);
                if(regs[secondRegister] > regs[thirdRegister]){
                    regs[firstRegister] = 0x0001;
                }
                else if(regs[secondRegister] < regs[thirdRegister]){
                    regs[firstRegister] = 0x0000;
                }
                else{
                    regs[firstRegister] = regs[secondRegister];
                }
                break;
            case 5:
                System.out.printf("sqrt r%d r%d\n", firstRegister, secondRegister);
                regs[firstRegister] = (int) Math.sqrt(regs[secondRegister]);
                break;
            case 6:
                System.out.printf("sub r%d r%d r%d\n", firstRegister, secondRegister, thirdRegister);
                regs[firstRegister] = regs[secondRegister] - regs[thirdRegister];
                break;
            default: throw new UnknownInstructionException("The instruction is unknown!", instructionNumber);
        }
    }
    
    private void showRegisters(){     
        System.out.println("regs = ");
        for(int i = 0; i < NUM_REGS; ++i){
            System.out.printf("%04X ", regs[i]);
        }
        System.out.println("\n");
    }
    
    public void run() throws TooLargeNumberException, UnknownInstructionException, TooLargeInputException{
        while(running){
            showRegisters();
            int instructions = fetch();
            decode(instructions);
            evaluate();
        }
        showRegisters();
    }
}
