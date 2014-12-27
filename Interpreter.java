/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mltailer;

/**
 *
 * @author cgood92
 */
public class Interpreter {
    public static String interpretLine(String line){
        if(line.contains("<error:error")){
            return "GROUP";
        }
        else if (line.contains("</error:error>")){
            return "END_GROUP";
        }
        else {
            return null;
        }
    }
}
