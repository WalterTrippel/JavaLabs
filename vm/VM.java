/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Walter
 * @see Engine
 * @see Translator
 */
public class VM {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws vm.RegistersOutOfBoundsException
     * @throws vm.TooLargeInputException
     * @throws vm.WrongCommandException
     * @throws vm.TooLargeNumberException
     * @throws vm.UnknownInstructionException
     */
    
    /**
     * That's a simple implemenation of Virtual Machine written as the first lab in
     * java programming language. 
     * To get the full imagination of what surely it is:
     * 
     * P.S. The output stream is redirected to the OUTPUT file, provided with the project.
     * To redirect it back, you will have to comment the line number 43.
     * 
     * May the force be with you.
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, RegistersOutOfBoundsException, TooLargeInputException, WrongCommandException, TooLargeNumberException, UnknownInstructionException {
        // TODO code application logic here
        PrintStream out = new PrintStream(new FileOutputStream("C:\\Users\\user\\Documents\\NetBeansProjects\\VM\\src\\vm\\OUTPUT"));
        System.setOut(out); 
        
        Scanner scanner = new Scanner(new File("C:\\Users\\user\\Documents\\NetBeansProjects\\VM\\src\\vm\\INPUT"));
        
        LinkedList<String> list = new LinkedList<>();
        while(scanner.hasNext()){
            list.push(scanner.nextLine());
        }
        Translator translator = new Translator(list);
        int[] INPUT = new int[translator.translate().length];
        for(int i = 0; i < translator.translate().length; ++i){
            INPUT[i] = Integer.parseInt(translator.translate()[i], 16);
        }
        Engine engineOfVM = new Engine(INPUT);
        engineOfVM.run(); 
    }
    
}
