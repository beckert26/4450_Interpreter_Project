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
        String file=readFile("test.py", StandardCharsets.US_ASCII);
        
        System.out.println("Characters in file:\n");
        for(int i=0; i<=file.length()-1; i++) {
            if(file.charAt(i)=='\n'){
                System.out.print("NEW LINE HERE");
            }
            if(checkTab(file, i)){
                System.out.print("TAB HERE");
            } 
            System.out.println(file.charAt(i));
	}
        
        System.out.println("\nOriginal File:\n" +file);
        
        ArrayList<String> fileLines = new ArrayList<>();
        fileLines=parseFileLines(file);
        System.out.println("\n\nFile separated");
        for(int i=0; i<fileLines.size(); i++){
            System.out.println(fileLines.get(i));
        }
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
    
}

