/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.Ksiega_Inwentarzowa.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wanted
 */
public class Log {
    private static BufferedWriter output;
    public static void logUpdate(String tekst){
        try {
            output = new BufferedWriter(new FileWriter("log.txt", true));
            output.append(tekst + "\ttime: "+ LocalDateTime.now() +  "\n");
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
