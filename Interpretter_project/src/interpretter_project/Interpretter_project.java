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
import java.util.Scanner;

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
        //String file=readFile("Joey_test.py", StandardCharsets.US_ASCII);

        
        
        ArrayList<String> fileLines = new ArrayList<>();
        
        fileLines=parseFileLines(file);
        
        //array list for variables
        ArrayList<Variable> variables = new ArrayList<>();
        String line="";
        String type="";
        String nextLine="";
        boolean[] lineIfCheck = new boolean[100];
        for(int c = 0; c<lineIfCheck.length;c++) {
            lineIfCheck[c] = false;
        }
        int indent = 0;
        handleLines(fileLines, variables, lineIfCheck, indent);
//        for(int i=0; i<fileLines.size(); i++){
//            line=fileLines.get(i);
//            type=typeOfLine(line);
//            if(i<fileLines.size()-1) {
//                nextLine=fileLines.get(i+1);
//            }
//            //System.out.println(i+1 + " : " + indent);
//            //System.out.println(i+1 + " : " + getIndent(line));
//            
//            if (indent == getIndent(line)) {
//    //            System.out.println(line);
//    //            System.out.println(type);
//
//                if(type.equals("comment")){
//                    continue;
//                }
//
//                else if(type.equals("print")){
//
//                    handlePrint(line, variables);
//                }
//
//                
//                else if(type.equals("variable")){
//                    variables=handleVariable(line,variables);
//                }
//                
//                
//                else if(type.equals("if")) {
//                    lineIfCheck[indent] = false;
//                    ArrayList<Boolean> checks =  new ArrayList<Boolean>();
//                    //Set to 3 for it, set to 5
//                    if(getComparison(line,variables,checks,3)==true) {
//                        //System.out.println(getComparison(line,variables,checks,3));
//                        //System.out.println("Go in");
//                        //System.out.println("hitting");
//                        lineIfCheck[indent] = true;
//                        //bump indent and go into if statement
//                        indent++;
//                        
//                    }
//                    else {
//                        
//                    }
//                }
//                else if(type.equals("elif")) {
//                    ArrayList<Boolean> checks =  new ArrayList<Boolean>();
//                    //Set to 3 for it, set to 5
//                    //System.out.println(lineIfCheck[indent]);
//                    if(getComparison(line,variables,checks,5)==true && lineIfCheck[indent] == false) {
//                        //System.out.println("Go In");
//                        lineIfCheck[indent] = true;
//                        //bump indent and go into else
//                        indent++;
//                    }
//                    else {
//                        //System.out.println("Skip");
//                    }
//                }
//                else if(type.equals("else")) {
//                    ArrayList<Boolean> checks =  new ArrayList<Boolean>();
//                    //Set to 3 for it, set to 5
//                    if(lineIfCheck[indent] == false) {
//                        //System.out.println("Go in");
//                        lineIfCheck[indent] = false;
//                        //bump indent and go into else
//                        indent++;
//                    }
//                    else {
//                        //System.out.println("Skip");
//                    }
//                }
//                else if(type.equals("while")) {
//                    ArrayList<Boolean> checks =  new ArrayList<Boolean>();
//                    //Set to 3 for it, set to 5
//                    if(getComparison(line,variables,checks,6)==true) {
//                        System.out.println("Go in");
//                        indent++;
//                    }
//                    else {
//                        //System.out.println("Skip");
//                    }
//                }
//                else if(type.equals("for")){
//                    //get whether condition is true or false
//                    //get variable value
//                    String varName=getForVariableName(line);
//                    //checks if variable is in variables if not adds it in and sets value to the beginning value of range
//                    //if it is then it increments the number
//                    variables=handleForVariable(line, variables, varName);
//                    //check if variable is < last number in range
//                    boolean forBool=handleForCondition(line, variables, varName);
//                    
//                }
//                if(getIndent(nextLine) < indent && !typeOfLine(nextLine).equals("empty") && !typeOfLine(nextLine).equals("comment") && i<fileLines.size()-1 ) {
//                    indent = getIndent(nextLine);
//                }
//                
//            }
            /*else {
                indent--;
            }*/
//        }
        //prints all variables and their values
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
    
    
    static ArrayList<Variable> handleLines(ArrayList<String> fileLines, ArrayList<Variable> variables, boolean[] lineIfCheck, int indent) {
        
        
        //array list for variables
        String line="";
        String type="";
        String nextLine="";
        
        for(int i=0; i<fileLines.size(); i++){
            
            line=fileLines.get(i);
            type=typeOfLine(line);
            if(i<fileLines.size()-1) {
                nextLine=fileLines.get(i+1);
            }
//            System.out.println(i+1 + " : " + indent);
//            System.out.println(i+1 + " : " + getIndent(line));
//            System.out.println((i+1)+": "+indent);
            if (indent == getIndent(line)) {
               
//                System.out.println(line);
    //            System.out.println(type);

                if(type.equals("comment")){
                    continue;
                }

                else if(type.equals("print")){

                    handlePrint(line, variables);
                }

                
                else if(type.equals("variable")){
                    variables=handleVariable(line,variables);
                }
                
                
                else if(type.equals("if")) {
                    lineIfCheck[indent] = false;
                    ArrayList<Boolean> checks =  new ArrayList<Boolean>();
                    //Set to 3 for it, set to 5
                    if(getComparison(line,variables,checks,3)==true) {
                        //System.out.println(getComparison(line,variables,checks,3));
                        //System.out.println("Go in");
                        //System.out.println("hitting");
                        lineIfCheck[indent] = true;
                        //bump indent and go into if statement
                        indent++;
                        
                    }
                    else {
                        
                    }
                }
                else if(type.equals("elif")) {
                    ArrayList<Boolean> checks =  new ArrayList<Boolean>();
                    //Set to 3 for it, set to 5
                    
                    if(getComparison(line,variables,checks,5)==true && lineIfCheck[indent] == false) {
                        //System.out.println("Go In");
                        lineIfCheck[indent] = true;
                        //bump indent and go into else
                        indent++;
                    }
                    else {
                        //System.out.println("Skip");
                    }
                }
                else if(type.equals("else")) {
                    ArrayList<Boolean> checks =  new ArrayList<Boolean>();
                    //Set to 3 for it, set to 5
                    if(lineIfCheck[indent] == false) {
                        //System.out.println("Go in");
                        lineIfCheck[indent] = false;
                        //bump indent and go into else
                        indent++;
                    }
                    else {
                        //System.out.println("Skip");
                    }
                }
                else if(type.equals("while")) {
                    ArrayList<Boolean> checks =  new ArrayList<Boolean>();
                    //Set to 3 for it, set to 5
                    ArrayList<String> whileFileLines=new ArrayList<String>();
                    int j=i+1;
                        String whileLine="";
                        int whileIndent=indent;
                        int checkIndent=getIndent(nextLine);
                        while(true){
//                            System.out.println("whileindent: "+ whileIndent);
//                            System.out.println("checkIndent: "+ checkIndent);
                            if(whileIndent<checkIndent && j<fileLines.size()){
//                                System.out.println("nextline " + getIndent(nextLine));
                                whileLine=fileLines.get(j);
                                whileFileLines.add(whileLine);
//                                System.out.println(whileLine);
                                j++;
                                if(j<fileLines.size()) {
                                    nextLine=fileLines.get(j);
                                }

                                checkIndent = getIndent(nextLine);
                                
                            }
                            else{
                                break;
                            }
                    }
                    //fix values
                    while(getComparison(line,variables,checks,6)==true) {
                            variables=handleLines(whileFileLines, variables, lineIfCheck, whileIndent+1);
    //                        for(int m=0; m<variables.size(); m++){
    //                             Variable v=variables.get(m);
    //                             System.out.println(v.getName()+": "+v.getDoubleValue());
    //                         }
    //                         System.out.println();
                    }
                    i+=whileFileLines.size();
                    indent++;
                    
                }
                else if(type.equals("for")){
                    //get whether condition is true or false
                    //get variable value
                    String varName=getForVariableName(line);
                    //checks if variable is in variables if not adds it in and sets value to the beginning value of range
                    //if it is then it increments the number
                    variables=handleForVariable(line, variables, varName);
                    //check if variable is < last number in range
                    boolean forBool=handleForCondition(line, variables, varName);
                    
                }
                
                
            }
            if(getIndent(nextLine) < indent && !typeOfLine(nextLine).equals("empty") && !typeOfLine(nextLine).equals("comment") && i<fileLines.size()-1 ) {
                    indent = getIndent(nextLine);
                }
    }
//       for(int i=0; i<variables.size(); i++){
//           Variable v=variables.get(i);
//           System.out.println(v.getName()+": "+v.getDoubleValue());
//       }
//       System.out.println();
        return variables;
   }
    
    static String getForVariableName(String line){
        String name="";
        char c=' ';
        for(int i=4; i<=line.length()-1; i++) {
            c=line.charAt(i);
            if(c!=' '){
                name+=Character.toString(c);
            }
            if(c==' '){
                break;
            }
        }
        return name;
    }
    static ArrayList<Variable> handleForVariable(String line, ArrayList<Variable> variables, String varName){
        
        double variable=0;
        char c=' ';
        boolean afterRange=false;
        String beginString="";
        double beginValue=0;
        
        //check if varName is in variables array
        boolean inVariables=false;
        for(int i=0; i<variables.size(); i++){
            Variable v=variables.get(i);
            if(v.getName().equals(varName)){
                inVariables=true;
            }
        }
        //if it's not get beginning value in range and set it to new variable in array with varName
        if(inVariables==false){
            
            //get begin value
            for(int i=0; i<=line.length()-1; i++) {
                c=line.charAt(i);
                if(afterRange){
                    if(c!=','){
                        beginString+=Character.toString(c);
                    }
                    else{
                        break;
                    }
                }
                if(c=='r' && line.charAt(i+1)=='a' && line.charAt(i+2)=='n' && line.charAt(i+3)=='g' && line.charAt(i+4)=='e' && line.charAt(i+5)=='('){ 
                    afterRange=true;
                    i=i+5;
                }
            }
            
            //handle whether begin string is number or variable
            //check if begin is in variable arrray if it is not must be a number
            boolean beginInVariables=false;
            for(int i=0; i<variables.size(); i++){
                Variable v=variables.get(i);
                if(v.getName().equals(beginString)){
                    //increment value
                    beginInVariables=true;
                    beginValue=v.getDoubleValue();
                    Variable vNew=new Variable(varName, beginValue);
                    variables.add(vNew);
                    break;
                }
            }
            if(beginInVariables==false){
                //check for int function
                
                boolean intFunction=false;
                 int intindex=0;
                 for(int k=0; k<beginString.length(); k++){
                    char cVal=beginString.charAt(k);
                    String opVal="";
                    String left="";
                    String right="";
                    String restValue="";
                    double leftVal=0;
                    double rightVal=0;
                    
                    if(cVal=='i' && beginString.charAt(k+1)=='n' && beginString.charAt(k+2)=='t' && beginString.charAt(k+3)=='('){
                        intindex=k;
                        k=k+3;
                        intFunction=true;
                        
                    }
                    if(cVal=='+' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^' || (cVal=='-' || cVal==')' && k!=0) ){
                        opVal=Character.toString(cVal);
                        int p=0;
                        for(int m=0; m<beginString.length(); m++){

                            char cVal2=beginString.charAt(m);
                            if(cVal2=='i' && beginString.charAt(m+1)=='n' && beginString.charAt(m+2)=='t' && beginString.charAt(m+3)=='('){
                                intindex=m;
                                m=m+3;
                                intFunction=true;
                                
                                continue;
                            }

                            if((p==0 && cVal2!=' ' && cVal2!='+' && cVal2!='-' && cVal2!='*' && cVal2!='/' && cVal2!='%' && cVal2!='^' && cVal2!=')') || (cVal2=='-' && right.equals("") && p==0)){
                                left+=Character.toString(cVal2);
                            }
                            else if((p==1 && cVal2!='-' && cVal2!=' ' && cVal2!='+' && cVal2!='*' && cVal2!='/' && cVal2!='%' && cVal2!='^' && cVal2!=')') || (cVal2=='-' && right.equals("") && p==1)){
                                right+=Character.toString(cVal2);
                            }
                            else if(cVal2=='+' || cVal2=='-' || cVal2=='*' || cVal2=='/' || cVal2=='%' || cVal2=='^' || cVal2==')'){
                                p++;
                            }
                            if(p>=2){
                                restValue+=Character.toString(cVal2);
                            }
                        }
//                        System.out.println(left);
//                        System.out.println(right);
                        //perform int function when parenthesis is closed
                        if(cVal==')' && intFunction==true){
                            char intC=' ';
                            String intStringVal="";
                            int j=k-1;
                            while(intC!='('){
                                intC=beginString.charAt(j);
                                //to fix reverse order
                                if(intC!='(')
                                    intStringVal=Character.toString(intC)+intStringVal;
                                j--;
                            }
                            double intVal=(int)Double.parseDouble(intStringVal);
                            intStringVal=Double.toString(intVal);
                            beginString=intStringVal+restValue;
                            intFunction=false;
//                            restValue=restValue.substring(1);
                        }
                        else{
                            //check if leftVal is in variables array if it is get value else just parse
                            boolean leftInVariable=false;
                            for(int i=0; i<variables.size(); i++){
                                Variable vLeft=variables.get(i);
                                if(vLeft.getName().equals(left)){
                                    //increment value
                                    leftInVariable=true;
                                    leftVal=vLeft.getDoubleValue();
                                    break;
                                }
                            }
                            if(leftInVariable==false){
                                leftVal=Double.parseDouble(left);
                            }

                            boolean rightInVariable=false;
                            for(int i=0; i<variables.size(); i++){
                                Variable vRight=variables.get(i);
                                if(vRight.getName().equals(right)){
                                    //increment value
                                    rightInVariable=true;
                                    rightVal=vRight.getDoubleValue();
                                    break;
                                }
                            }
                            if(rightInVariable==false){
                                rightVal=Double.parseDouble(right);
                            }

                            if(intFunction==true){
                                    beginString="int("+Double.toString(arithmeticOperation(leftVal, rightVal, opVal))+restValue;
                            }
                            else{
                                beginString=Double.toString(arithmeticOperation(leftVal, rightVal, opVal))+restValue;
                            }
                        }
                        
                        k=0;
                    }
                   
                }
                
                
                beginValue=Double.parseDouble(beginString);
                Variable vNew=new Variable(varName, beginValue);
                variables.add(vNew);
            }
            
        }
            
        else{
            for(int i=0; i<variables.size(); i++){
                Variable v=variables.get(i);
                if(v.getName().equals(varName)){
                    //increment value
                    v.setDoubleValue(v.getDoubleValue()+1);
                }
            }
        }
        
        return variables;
        
    }
    
    static boolean handleForCondition(String line, ArrayList<Variable> variables, String varName ){
        double varValue=0.0;
        //get variables value to be checked against
        for(int i=0; i<variables.size(); i++){
            Variable v=variables.get(i);
            if(v.getName().equals(varName)){
                varValue=v.getDoubleValue();
            }
        }
        
        //get end String
        String endString="";
        char c=' ';
        boolean afterComma=false;
        for(int i=0; i<line.length(); i++) {
            c=line.charAt(i);
            if(afterComma){
                if(c!=')' || line.charAt(i+1)!=':'){
                   endString+=Character.toString(c);
                }
                else{
                    break;
                }
            }
            if(c==','){ 
                afterComma=true;
                i=i+1;
            }
        }
        
        //get end value
        double endValue=0;
        
        boolean endInVariables=false;
            for(int i=0; i<variables.size(); i++){
                Variable v=variables.get(i);
                if(v.getName().equals(endString)){
                    //increment value
                    endInVariables=true;
                    endValue=v.getDoubleValue();
                    break;
                }
            }
            
            if(endInVariables==false){
                //check for int function and arithmetic and perform those operations
                 boolean intFunction=false;
                 int intindex=0;
                 for(int k=0; k<endString.length(); k++){
                    char cVal=endString.charAt(k);
                    String opVal="";
                    String left="";
                    String right="";
                    String restValue="";
                    double leftVal=0;
                    double rightVal=0;
                    
                    if(cVal=='i' && endString.charAt(k+1)=='n' && endString.charAt(k+2)=='t' && endString.charAt(k+3)=='('){
                        intindex=k;
                        k=k+3;
                        intFunction=true;
                        
                    }
                    if(cVal=='+' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^' || (cVal=='-' || cVal==')' && k!=0) ){
                        opVal=Character.toString(cVal);
                        int p=0;
                        for(int m=0; m<endString.length(); m++){

                            char cVal2=endString.charAt(m);
                            if(cVal2=='i' && endString.charAt(m+1)=='n' && endString.charAt(m+2)=='t' && endString.charAt(m+3)=='('){
                                intindex=m;
                                m=m+3;
                                intFunction=true;
                                
                                continue;
                            }

                            if((p==0 && cVal2!=' ' && cVal2!='+' && cVal2!='-' && cVal2!='*' && cVal2!='/' && cVal2!='%' && cVal2!='^' && cVal2!=')') || (cVal2=='-' && right.equals("") && p==0)){
                                left+=Character.toString(cVal2);
                            }
                            else if((p==1 && cVal2!='-' && cVal2!=' ' && cVal2!='+' && cVal2!='*' && cVal2!='/' && cVal2!='%' && cVal2!='^' && cVal2!=')') || (cVal2=='-' && right.equals("") && p==1)){
                                right+=Character.toString(cVal2);
                            }
                            else if(cVal2=='+' || cVal2=='-' || cVal2=='*' || cVal2=='/' || cVal2=='%' || cVal2=='^' || cVal2==')'){
                                p++;
                            }
                            if(p>=2){
                                restValue+=Character.toString(cVal2);
                            }
                        }
//                        System.out.println(left);
//                        System.out.println(right);
                        //perform int function when parenthesis is closed
                        if(cVal==')' && intFunction==true){
                            char intC=' ';
                            String intStringVal="";
                            int j=k-1;
                            while(intC!='('){
                                intC=endString.charAt(j);
                                //to fix reverse order
                                if(intC!='(')
                                    intStringVal=Character.toString(intC)+intStringVal;
                                j--;
                            }
                            double intVal=(int)Double.parseDouble(intStringVal);
                            intStringVal=Double.toString(intVal);
                            endString=intStringVal+restValue;
                            intFunction=false;
//                            restValue=restValue.substring(1);
                        }
                        else{
                            //check if leftVal is in variables array if it is get value else just parse
                            boolean leftInVariable=false;
                            for(int i=0; i<variables.size(); i++){
                                Variable vLeft=variables.get(i);
                                if(vLeft.getName().equals(left)){
                                    //increment value
                                    leftInVariable=true;
                                    leftVal=vLeft.getDoubleValue();
                                    break;
                                }
                            }
                            if(leftInVariable==false){
                                leftVal=Double.parseDouble(left);
                            }

                            boolean rightInVariable=false;
                            for(int i=0; i<variables.size(); i++){
                                Variable vRight=variables.get(i);
                                if(vRight.getName().equals(right)){
                                    //increment value
                                    rightInVariable=true;
                                    rightVal=vRight.getDoubleValue();
                                    break;
                                }
                            }
                            if(rightInVariable==false){
                                rightVal=Double.parseDouble(right);
                            }

                            if(intFunction==true){
                                    endString="int("+Double.toString(arithmeticOperation(leftVal, rightVal, opVal))+restValue;
                            }
                            else{
                                endString=Double.toString(arithmeticOperation(leftVal, rightVal, opVal))+restValue;
                            }
                        }
                        
                        k=0;
                    }
                   
                }
                 
                 endValue=Double.parseDouble(endString);
                
            }
        if(varValue<endValue){
            return true;
        }
        else{
            return false;
        }
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
            if(c!='"' && c!='=' && part==1){
                if(isString==true)
                    value+=Character.toString(c);
                else if(c!=' '){
                    value+=Character.toString(c);
                }
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
            //is the variable is a string
            else if(c=='"'){
                isString=true;
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
                if(variables.get(varIndex).getVariableType()=="double"){

                    variables.get(varIndex).setDoubleValue(Double.parseDouble(value));
                }
                else
                    variables.get(varIndex).setStringValue(value);
            }
            //perform according operation
            else if(operator.equals("+=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getDoubleValue()+variables.get(assignmentIndex).getDoubleValue());
            }
            else if(operator.equals("-=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getDoubleValue()-variables.get(assignmentIndex).getDoubleValue());
            }
            else if(operator.equals("*=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getDoubleValue()*variables.get(assignmentIndex).getDoubleValue());
            }
            else if(operator.equals("/=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getDoubleValue()/variables.get(assignmentIndex).getDoubleValue());
            }
            else if(operator.equals("^=")){
                variables.get(varIndex).setDoubleValue((int)variables.get(varIndex).getDoubleValue()^(int)variables.get(assignmentIndex).getDoubleValue());
            }
            else if(operator.equals("%=")){
                variables.get(varIndex).setDoubleValue(variables.get(varIndex).getDoubleValue()%variables.get(assignmentIndex).getDoubleValue());
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
                printVariableValue(varName, variables);
            }
            if(i == meat.length()-1 && !buildString.equals("")) {
                printVariableValue(buildString, variables);
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
                    //print integers properly
                    if((int)v.getDoubleValue()==v.getDoubleValue())
                        System.out.print((int)v.getDoubleValue());
                    else
                        System.out.print(v.getDoubleValue());
                else
                    System.out.print(v.getStringValue());
            }
        }
    }

    static boolean getComparison(String line, ArrayList<Variable> variables, ArrayList<Boolean> checks, int set) {

        line = line.trim();
        //System.out.println("hi");
        //System.out.println(line);
        int number = 1;
        String comparitor = "";
        String buildString = "";
        String secondString = "";
        for(int i = set; i<line.length();i++) {
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
                //New stuff
                String check = containsOperation(buildString);
                boolean checkBool = check.equals("");
                //End new stuff
                double firstDub = 0;
                double secondDub = 0;
                if(checkBool == false) {
                    String firstVar = separateFirstVariable(buildString, check);
                    String secondVar = separateSecondVariable(buildString, check);
                    double first = getDoubleVar(firstVar,variables);
                    double second = getDoubleVar(secondVar,variables);
                    firstDub = arithmeticOperation(first, second, check);
                }
                else if(Character.isDigit(buildString.charAt(0))) {
                    firstDub = Double.parseDouble(buildString);
                }
                else {
                    firstDub = getDoubleVar(buildString,variables);
                }
                check = containsOperation(secondString);
                checkBool = check.equals("");
                if(checkBool == false) {
                    String firstVar = separateFirstVariable(buildString, check);
                    String secondVar = separateSecondVariable(buildString, check);
                    double first = getDoubleVar(firstVar,variables);
                    double second = getDoubleVar(secondVar,variables);
                    secondDub = arithmeticOperation(first, second, check);
                }
                else if(Character.isDigit(secondString.charAt(0))) {
                    secondDub = Double.parseDouble(secondString);
                }
                else {
                    secondDub = getDoubleVar(secondString,variables);
                }
                
                /*
                System.out.print(firstDub);
                System.out.print(" ");
                System.out.print(comparitor);
                System.out.print(" ");
                System.out.print(secondDub);
                System.out.println("");
                */
                
                if(comparitor.equals(">=")) {
                    if(firstDub >= secondDub) {
                        checks.add(true);
                    }
                    else {
                        checks.add(false);
                    }
                }
                else if(comparitor.equals("<=")) {
                    if(firstDub <= secondDub) {
                        checks.add(true);
                    }
                    else {
                        checks.add(false);
                    }
                }
                else if(comparitor.equals(">")) {
                    if(firstDub > secondDub) {
                        checks.add(true);
                    }
                    else {
                        checks.add(false);
                    }
                }
                else if(comparitor.equals("<")) {
                    if(firstDub < secondDub) {
                        checks.add(true);
                    }
                    else {
                        checks.add(false);
                    }
                }
                else if(comparitor.equals("==")) {
                    if(firstDub == secondDub) {
                        checks.add(true);
                    }
                    else {
                        checks.add(false);
                    }
                }
                else if(comparitor.equals("!=")) {
                    if(firstDub != secondDub) {
                        checks.add(true);
                    }
                    else {
                        checks.add(false);
                    }
                }
            }
            if(line.substring(i).length() > 4) {
                if(i>0 && line.charAt(i-1)==' ' && line.charAt(i)=='a' && line.charAt(i+1)=='n' && line.charAt(i+2)=='d' && line.charAt(i+3)==' ') {
                    String check = containsOperation(buildString);
                    boolean checkBool = check.equals("");
                    double firstDub = 0;
                    double secondDub = 0;
                    if(checkBool == false) {
                        String firstVar = separateFirstVariable(buildString, check);
                        String secondVar = separateSecondVariable(buildString, check);
                        double first = getDoubleVar(firstVar,variables);
                        double second = getDoubleVar(secondVar,variables);
                        firstDub = arithmeticOperation(first, second, check);
                    }
                    else if(Character.isDigit(buildString.charAt(0))) {
                        firstDub = Double.parseDouble(buildString);
                    }
                    else {
                        firstDub = getDoubleVar(buildString,variables);
                    }
                    check = containsOperation(secondString);
                    checkBool = check.equals("");
                    if(checkBool == false) {
                        String firstVar = separateFirstVariable(buildString, check);
                        String secondVar = separateSecondVariable(buildString, check);
                        double first = getDoubleVar(firstVar,variables);
                        double second = getDoubleVar(secondVar,variables);
                        secondDub = arithmeticOperation(first, second, check);
                    }
                    else if(Character.isDigit(secondString.charAt(0))) {
                        secondDub = Double.parseDouble(secondString);
                    }
                    else {
                        secondDub = getDoubleVar(secondString,variables);
                    }
                    /*
                    System.out.print(firstDub);
                    System.out.print(" ");
                    System.out.print(comparitor);
                    System.out.print(" ");
                    System.out.print(secondDub);
                    System.out.println("");
                    */
                    if(comparitor.equals(">=")) {
                        if(firstDub >= secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals("<=")) {
                        if(firstDub <= secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals(">")) {
                        if(firstDub > secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals("<")) {
                        if(firstDub < secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals("==")) {
                        if(firstDub == secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals("!=")) {
                        if(firstDub != secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    getComparison(line.substring(i+1),variables,checks,3);
                    break;
                }
            }
            
            if(line.substring(i).length() > 3) {
                if(i>0 && line.charAt(i-1)==' ' && line.charAt(i)=='o' && line.charAt(i+1)=='r' && line.charAt(i+2)==' ') {
                    String check = containsOperation(buildString);
                    boolean checkBool = check.equals("");
                    double firstDub = 0;
                    double secondDub = 0;
                    if(Character.isDigit(buildString.charAt(0))) {
                        firstDub = Double.parseDouble(buildString);
                    }
                    else if(checkBool == false) {
                        String firstVar = separateFirstVariable(buildString, check);
                        String secondVar = separateSecondVariable(buildString, check);
                        double first = getDoubleVar(firstVar,variables);
                        double second = getDoubleVar(secondVar,variables);
                        firstDub = arithmeticOperation(first, second, check);
                    }
                    else {
                        firstDub = getDoubleVar(buildString,variables);
                    }
                    check = containsOperation(secondString);
                    checkBool = check.equals("");
                    if(Character.isDigit(secondString.charAt(0))) {
                        secondDub = Double.parseDouble(secondString);
                    }
                    else if(checkBool == false) {
                        String firstVar = separateFirstVariable(buildString, check);
                        String secondVar = separateSecondVariable(buildString, check);
                        double first = getDoubleVar(firstVar,variables);
                        double second = getDoubleVar(secondVar,variables);
                        secondDub = arithmeticOperation(first, second, check);
                    }
                    else {
                        secondDub = getDoubleVar(secondString,variables);
                    }
                    /*
                    System.out.print(firstDub);
                    System.out.print(" ");
                    System.out.print(comparitor);
                    System.out.print(" ");
                    System.out.print(secondDub);
                    System.out.println("");
                    */
                    if(comparitor.equals(">=")) {
                        if(firstDub >= secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals("<=")) {
                        if(firstDub <= secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals(">")) {
                        if(firstDub > secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals("<")) {
                        if(firstDub < secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals("==")) {
                        if(firstDub == secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    else if(comparitor.equals("!=")) {
                        if(firstDub != secondDub) {
                            checks.add(true);
                        }
                        else {
                            checks.add(false);
                        }
                    }
                    getComparison(line.substring(i),variables,checks,3);
                    break;
                }
            }
            if(line.charAt(i)!=' ' && line.charAt(i)!='(') {
                if(number == 1) {
                    buildString+=line.charAt(i);
                }
                else {
                    secondString+=line.charAt(i);
                }
            }
        }
        
        //FINAL CHECK!!
        if(line.contains(" and ")) {
            for(int j = 0; j<checks.size();j++) {
                if(checks.get(j) == false) {
                    //System.out.println(checks.get(j));
                    return false;
                }
            }
            return true;
        }
        else if(line.contains(" or ")) {
            for(int j = 0; j<checks.size();j++) {
                if(checks.get(j) == true) {
                    return true;
                }
            }
            return false;
        }
        else {
            if(checks.get(0) == true) {
                return true;
            }
            else {
                return false;
            }
        }
    }
    
    static double getDoubleVar(String s, ArrayList<Variable> variables) {
        if(Character.isDigit(s.charAt(0))) {
            return Double.parseDouble(s);
        }
        for(int j=0; j<variables.size(); j++){
            Variable v=variables.get(j);
            String vtype=v.getVariableType();
            if(v.getName().equals(s)){
               return v.getDoubleValue();
            }
        }
        return 0;
    }
    
    static String containsOperation(String s) {
        //(+, -, *, /, %, ^)
        if(s.contains("+")) {
            return "+";
        }
        else if(s.contains("-")) {
            return "-";
        }
        else if(s.contains("*")) {
            return "*";
        }
        else if(s.contains("/")) {
            return "/";
        }
        else if(s.contains("%")) {
            return "%";
        }
        else if(s.contains("^")) {
            return "^";
        }
        else {
            return "";
        }
    }
    
    static String separateFirstVariable(String s, String op) {
        int opIndex = s.indexOf(op);
        return s.substring(0,opIndex).trim();
    }
    
    static String separateSecondVariable(String s, String op) {
        int opIndex = s.indexOf(op);
        return s.substring(opIndex+1).trim();
    }
    
    static int getIndent(String line) {
        //System.out.println(line);
        if(line.equals("")) {
            return 0;
        }
        int tabs = 0;
        for(int i = 0;line.charAt(i)==' ';i++) {
            tabs++;
        }
        //System.out.println(tabs/4);
        return tabs/4;
    }
    
}

