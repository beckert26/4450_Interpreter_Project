/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpretter_project;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author brett
 */
public class Interpretter_project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //read file in
        String file=readFile("python_test_code.py", StandardCharsets.US_ASCII);
        
//        System.out.println("Characters in file:\n");
//        for(int i=0; i<=file.length()-1; i++) {
//            if(file.charAt(i)=='\n'){
//                System.out.print("NEW LINE HERE");
//            }
//            if(checkTab(file, i)){
//                System.out.print("TAB HERE");
//            } 
//            System.out.println(file.charAt(i));
//	}
        
        //System.out.println("\nOriginal File:\n" +file);
        
        ArrayList<String> fileLines = new ArrayList<>();
        
        fileLines=parseFileLines(file);
        System.out.println("\n\nFile separated:");
        
        //array list for variables
        ArrayList<Variable> varaibles = new ArrayList<>();
        String line="";
        String type="";
        for(int i=0; i<fileLines.size(); i++){
            line=fileLines.get(i);
            type=typeOfLine(line);
            
            //System.out.println(line);
            //System.out.println(type);
            
            if(type.equals("comment")){
                continue;
            }

            if(type.equals("print")){
                handlePrint(line);
            }
        }
        
        
        
        //function to see if line of code is variable declaration
    }
    
    static String readFile(String path, Charset encoding) throws IOException{
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
    }
    
    static boolean checkTab(String file, int i){
        if(i>=3){
            if(file.charAt(i)==' ' && file.charAt(i-1)==' ' && file.charAt(i-2)==' ' && file.charAt(i-3)==' '){
                return true;
            }
        }
        return false;
    }
    
    static ArrayList<String> parseFileLines(String file){
        ArrayList<String> fileList = new ArrayList<>();
        
        String line = "";
        char c=' ';
        for(int i=0; i<=file.length()-1; i++) {
            c=file.charAt(i);
            if(c!='\n'){
                line+=Character.toString(c);
            }
            else if(c=='\n'){
                fileList.add(line);
                line="";
            }
	}
        fileList.add(line);
        return fileList;
    }
    
     static String typeOfLine(String line){
        //System.out.println(line);
        line=line.trim();
        if(line.equals(""))
            return "empty";
        else if(line.substring(0,1).equals("#"))
            return "comment";
        else if(line.substring(0,5).equals("print"))
            return "print";
        else if(line.substring(0,2).equals("if"))
            return "if";
        else if(line.substring(0,4).equals("elif"))
            return "elif";
        else if(line.substring(0,4).equals("else"))
            return "else";
        else if(line.substring(0,3).equals("for"))
            return "for";
        else if(line.substring(0,5).equals("while"))
            return"while";
        else
            return"variable";
        
            

    }

    static void handlePrint(String line) {
        line=line.trim();
        String meat = line.substring(6,line.length()-1);
        for(int i = 0; i< meat.length(); i++) {
            // Prints strings inside of print statements with " as the char
            if(meat.charAt(i)=='"') {
                i++;
                String part = "";
                while(meat.charAt(i)!='"') {
                    part += meat.charAt(i);
                    i++;
                }
                System.out.print(part);
            }
            if(meat.charAt(i)=='(') {
                /*int endIndex = 0;
                String varPart = meat.substring(i+4);
                for(int a = 0; a<varPart.length();a++) {
                    if(varPart.charAt(a)!=')') {
                        i++;
                        endIndex++;
                    }
                }
                String varName = varPart.substring(0,endIndex);
                System.out.print(varName);*/
                i++;
                String varName = "";
                while(meat.charAt(i) != ')') {
                    varName += meat.charAt(i);
                    i++;
                }
                System.out.print(varName);
            }
        }
        System.out.println("");
    }
    
}

