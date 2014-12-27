/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mltailer;

import org.apache.commons.io.input.TailerListenerAdapter;
import java.util.ArrayList;
 
public class MyTailerListener extends TailerListenerAdapter {
    Console console = new Console();
    ArrayList<String> groupString = new ArrayList<String>();
    boolean in_group = false;
    
    public void handle(String line) {
        if("GROUP".equals(Interpreter.interpretLine(line))){
            groupString.clear();
            groupString.add(line);
            in_group = true;
        }
        else if("END_GROUP".equals(Interpreter.interpretLine(line))){
            groupString.add(line);
            in_group = false;
            ErrorBlock errorBlock = new ErrorBlock(groupString);
            console.makeNewCollapsible(errorBlock);
        }
        else if(in_group){
            groupString.add(line);
        }
        console.consoleOutputLine(line);
    }
}