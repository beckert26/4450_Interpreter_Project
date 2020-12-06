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
//        String file=readFile("test.py", StandardCharsets.US_ASCII);
        
        
        ArrayList<String> fileLines = new ArrayList<>();
        
        fileLines=parseFileLines(file);
        
        //array list for variables
        ArrayList<Variable> variables = new ArrayList<>();
        String line="";
        String type="";
        int indent = 0;
        for(int i=0; i<fileLines.size(); i++){
            line=fileLines.get(i);
            type=typeOfLine(line);
            
            if (indent == getIndent(line)) {


            else if(type.equals("print")){
                handlePrint(line, variables);
            }
//                System.out.println(i+1 + " : " + indent);
    //            System.out.println(line);
    //            System.out.println(type);

                if(type.equals("comment")){
                    continue;
                }

                else if(type.equals("print")){
                    handlePrint(line);
                }

                /*
                else if(type.equals("variable")){
                    variables=handleVariable(line,variables);
                }
                */
                
                else if(type.equals("if")) {
                    getComparison(line,0);
                }
            }
            /*else {
                indent--;
            }*/
        }
        
//        for(int j=0; j<variables.size(); j++){
//            Variable v=variables.get(j);
//            System.out.println(v.getName());
//            String vtype=v.getVariableType();
//            if(vtype=="double")
//                System.out.println(v.getDoubleValue());
//            else
//                System.out.println(v.getStringValue());
//        }
        
        
        
        //function to see if line of code is variable declaration
    }
    
    static ArrayList<Variable> handleVariable(String line, ArrayList<Variable> va ){
        ArrayList<Variable> variables=va;
        line=line.trim();
        String name="";
        int part=0;
        char c=' ';
        String value="";
        Variable v;
        String operator="";
        boolean isString=false;
        for(int i=0; i<=line.length()-1; i++) {
            c=line.charAt(i);
            
            //add them to value
            if(c!='"' && c!='=' && c!=' ' && part==1){
                value+=Character.toString(c);
            }
            //get variable name
            if(c!='=' && c!='+' && c!='-' && c!='*' && c!='/' && c!='^' && c!='%' && c!=' ' && part==0){
                name+=Character.toString(c);
            }
            //HANDLE OPERATORS
            //set part to 1 after = sign
            else if(c=='='){
                part++;
                operator="=";
            }
            else if(c=='+' && line.charAt(i+1)=='='){
                part++;
                operator="+=";
                i++;
            }
            else if(c=='-' && line.charAt(i+1)=='='){
                part++;
                operator="-=";
                i++;
            }
            else if(c=='*' && line.charAt(i+1)=='='){
                part++;
                operator="*=";
                i++;
            }
            else if(c=='/' && line.charAt(i+1)=='='){
                part++;
                operator="/=";
                i++;
            }
            else if(c=='^' && line.charAt(i+1)=='='){
                part++;
                operator="^=";
                i++;
            }
            else if(c=='%' && line.charAt(i+1)=='='){
                part++;
                operator="%=";
                i++;
            }
            //let know the variable is a string
            else if(c=='"'){
                isString=true;
                i++;
            }
            
	}
        //check if variable already exists
        boolean exists=false;
        int varIndex=0;
        for(int j=0; j<variables.size(); j++){
            Variable vExist=variables.get(j);
            if(name.equals(vExist.getName())){
                exists=true;
                varIndex=j;
            }
        }
        
        //second value
        //check whether right hand side has arthimetic and handle the arthimetic
        for(int k=0; k<value.length(); k++){
            char cVal=value.charAt(k);
            String opVal="";
            String left="";
            String right="";
            String restValue="";
            double leftVal=0;
            double rightVal=0;
            if(cVal=='+' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^' || (cVal=='-' && k!=0) ){
                opVal=Character.toString(cVal);
                
                int p=0;
                for(int m=0; m<value.length(); m++){
                    
                    char cVal2=value.charAt(m);
                    
                    if((p==0 && cVal2!=' ' && cVal2!='+' && cVal2!='-' && cVal2!='*' && cVal2!='/' && cVal2!='%' && cVal2!='^') || (cVal2=='-' && right.equals("") && p==0)){
                        left+=Character.toString(cVal2);
                    }
                    else if((p==1 && cVal2!='-' && cVal2!=' ' && cVal2!='+' && cVal2!='*' && cVal2!='/' && cVal2!='%' && cVal2!='^') || (cVal2=='-' && right.equals("") && p==1)){
                        right+=Character.toString(cVal2);
                    }
                    else if(cVal2=='+' || cVal2=='-' || cVal2=='*' || cVal2=='/' || cVal2=='%' || cVal2=='^' ){
                        p++;
                    }
                    if(p>=2){
                        restValue+=Character.toString(cVal2);
                    }
                }
                
                leftVal=Double.parseDouble(left);
                rightVal=Double.parseDouble(right);
                value=Double.toString(arithmeticOperation(leftVal, rightVal, opVal))+restValue;
                k=0;
            }
        }
        
        
        //check for assignment
        int assignmentIndex=0;
        for(int j=0; j<variables.size(); j++){
            Variable vSecond=variables.get(j);
            if(value.equals(vSecond.getName())){
                
                assignmentIndex=j;
            }
        }
        if(exists==false){
        //figure out if value is an equation and do that first
            if(isString==true){
                System.out.println("NAME: "+value);
                v=new Variable(name, value);
                variables.add(v);
            }
            else{
                v=new Variable(name, Double.parseDouble(value));
                variables.add(v);
            }
        }
        //handle operations with existing variable
        else{
            if(operator.equals("=")){
                if(variables.get(varIndex).getVariableType()=="int")
                    variables.get(varIndex).setDoubleValue(Integer.parseInt(value));
                else
                    variables.get(varIndex).setStringValue(value);
            }
            //perform according operation
            else if(operator.equals("+=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getIntValue()+variables.get(assignmentIndex).getIntValue());
            }
            else if(operator.equals("-=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getIntValue()-variables.get(assignmentIndex).getIntValue());
            }
            else if(operator.equals("*=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getIntValue()*variables.get(assignmentIndex).getIntValue());
            }
            else if(operator.equals("/=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getIntValue()/variables.get(assignmentIndex).getIntValue());
            }
            else if(operator.equals("^=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getIntValue()^variables.get(assignmentIndex).getIntValue());
            }
            else if(operator.equals("%=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getIntValue()%variables.get(assignmentIndex).getIntValue());
            }
            
        }
        return variables;
    }
    
    static double arithmeticOperation(double left, double right, String operation){
        if(operation.equals("+")){
            return left+right;
        }
        else if(operation.equals("-")){
            return left-right;
        }
        else if(operation.equals("*")){
            return left*right;
        }
        else if(operation.equals("/")){
            return left/right;
        }
        else if(operation.equals("%")){
            return left%right;
        }
        else{
            return (int)left^(int)right;
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
        else if(line.substring(0,5).equals("break"))
            return "break";
        else
            return"variable";
    }

    static void handlePrint(String line, ArrayList<Variable> variables) {
        //System.out.println(line);
        line=line.trim();
        String meat = line.substring(6,line.length()-1);
        String buildString = "";
        for(int i = 0; i< meat.length(); i++) {
            // Prints strings inside of print statements with " as the char
            if(meat.charAt(i)=='+') {
                //check if buildString equals a var name currently saved
                printVariableValue(buildString, variables);
//                if(buildString.equals("name") || buildString.equals("beginning") || buildString.equals("end")) {
//                    System.out.print(buildString);
//                }
                buildString = "";
            }
            buildString += meat.charAt(i);
            if(meat.charAt(i)=='"') {
                buildString = "";
                i++;
                String part = "";
                while(meat.charAt(i)!='"') {
                    part += meat.charAt(i);
                    i++;
                }
                System.out.print(part);
            }
            if(meat.charAt(i)=='(') {
                buildString = "";
                i++;
                String varName = "";
                while(meat.charAt(i) != ')') {
                    varName += meat.charAt(i);
                    i++;
                }
                //HERE
                printVariableValue(varName, variables);
                //Check to see if variable name is in variable name list
            }
            if(i == meat.length()-1 && !buildString.equals("")) {
                //Check to see if variable name is in variable name list
                //HERE
                printVariableValue(buildString, variables);
//                if(buildString.equals("beginning") || buildString.equals("end")) {
//                    System.out.print(buildString);
//                }
            }
            
        }
        System.out.println("");
    }
    

    static void printVariableValue(String buildString, ArrayList<Variable> variables){
        for(int j=0; j<variables.size(); j++){
            Variable v=variables.get(j);
            String vtype=v.getVariableType();
            if(v.getName().equals(buildString)){
                if(vtype=="double")
                    System.out.print(v.getDoubleValue());
                else
                    System.out.print(v.getStringValue());
            }
        }

    static boolean getComparison(String line, int number) {
        line = line.trim();
        ArrayList<Boolean> checks =  new ArrayList<Boolean>();
        number = 1;
        String comparitor = "";
        String buildString = "";
        String secondString = "";
        for(int i = 3; i<line.length();i++) {
            if(line.charAt(i)=='<' && line.charAt(i+1)=='=') {
                comparitor = "<=";
                i=i+2;
                number = 2;
            }
            if(line.charAt(i)=='>' && line.charAt(i+1)=='=') {
                comparitor = ">=";
                i=i+2;
                number = 2;
            }
            if(line.charAt(i)=='<' && line.charAt(i+1)!='=') {
                comparitor = "<";
                i=i+1;
                number = 2;
            }
            if(line.charAt(i)=='>' && line.charAt(i+1)!='=') {
                comparitor = ">";
                i=i+1;
                number = 2;
            }
            if(line.charAt(i)=='=' && line.charAt(i+1)=='=') {
                comparitor = "==";
                i=i+2;
                number = 2;
            }
            if(line.charAt(i)=='!' && line.charAt(i+1)=='=') {
                comparitor = "!=";
                i=i+2;
                number = 2;
            }
            if(line.charAt(i)==')' || line.charAt(i)==':') {
                if(line.charAt(i)==')') {
                    i++;
                }
                System.out.print(buildString);
                System.out.print(" ");
                System.out.print(comparitor);
                System.out.print(" ");
                System.out.print(secondString);
            }
            if(line.charAt(i)!=' ') {
                if(number == 1) {
                    buildString+=line.charAt(i);
                }
                else {
                    secondString+=line.charAt(i);
                }
            }
        }
        
        System.out.println("");
        return true;
    }
    
    static int getIndent(String line) {
        if(line.equals("")) {
            return 0;
        }
        int tabs = 0;
        for(int i = 0;line.charAt(i)==' ';i++) {
            tabs++;
        }
        return tabs/4;
    }
    
}

