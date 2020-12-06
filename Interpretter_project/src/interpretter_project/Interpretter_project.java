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
//        String file=readFile("python_test_code.py", StandardCharsets.US_ASCII);
        String file=readFile("test.py", StandardCharsets.US_ASCII);
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
        ArrayList<Variable> variables = new ArrayList<>();
        String line="";
        String type="";
        for(int i=0; i<fileLines.size(); i++){
            line=fileLines.get(i);
            type=typeOfLine(line);
            
//            System.out.println(line);
//            System.out.println(type);
            
            if(type.equals("comment")){
                continue;
            }
            else if(type.equals("variable")){
                //check if variable already exists in variables then do this
                Variable v=handleVariableDefinition(line);
                variables.add(v);
            }
        }
        
        for(int j=0; j<variables.size(); j++){
            Variable v=variables.get(j);
            System.out.println(v.getName());
            String vtype=v.getVariableType();
            if(vtype=="int")
                System.out.println(v.getIntValue());
            else
                System.out.println(v.getStringValue());
        }
        
        
        
        //function to see if line of code is variable declaration
    }
    
    static Variable handleVariableDefinition(String line){
        line=line.trim();
        String name="";
        int part=0;
        char c=' ';
        String value="";
        Variable v;
        boolean isString=false;
        for(int i=0; i<=line.length()-1; i++) {
            c=line.charAt(i);
            //get variable name
            if(c!='=' && c!=' ' && part==0){
                name+=Character.toString(c);
            }
            //set part to 1 after = sign
            else if(c=='=')
                part++;
            //let know the variable is a string
            else if(c=='"'){
                isString=true;
            }
            //add them to value
            if(c!='"' && c!='=' && c!=' ' && part==1){
                value+=Character.toString(c);
            }
	}
        if(isString==true){
            v=new Variable(name, value);
        }
        else{
            v=new Variable(name, Integer.parseInt(value));
        }
        return v;
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
    
}

