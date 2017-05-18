/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nc.pilot.lib;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author travis
 */
public class GlobalData {
    public static String X = "0.000";
    public static String Y = "0.000";
    public static String Z = "0.000";
    public static String F = "0.000";
    
    public static Boolean KeycodeExecute = false;
    public static Boolean AltPressed = false;
    public static Boolean GCodeWindowFocused = false;
    
    public static Boolean TorchOn = false;
    
    public static String NCFile;
    public static String NC_Code;
    public static Integer NC_Lines;
    
    public static Boolean Auto = false;
    public static Boolean Jogging = false;
    
    public static int LinePosition = 0;
    public static int FreeBuffers = 0;
    public static boolean PlannerReady = false;
    public static boolean SendOnce = true;
    
    public static String readFile(String path) throws IOException 
    {
       byte[] encoded = Files.readAllBytes(Paths.get(path));
       return new String(encoded);
    }
}
