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
import java.util.Collections;
import java.util.Scanner;


/**
 *
 * @author brett
 */


class Operation {
    public Integer pVal = 0;
    public Character operator;
    public Integer pos;
    public String left = "";
    public String right = "";
    public boolean exist = true;
    public boolean isString = false;
    public String content = "";
}


public class Interpretter_project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //read file in
      
        String file=readFile("python_test_code.py", StandardCharsets.US_ASCII);
//        String file=readFile("test.py", StandardCharsets.US_ASCII);
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
        //String test ="This is some test \"test to see what happens\"" + " hello there";

        try{
            handleLines(fileLines, variables, lineIfCheck, indent);
        }
        catch(Exception e ){
            System.out.print("Exception: " + e + "\n");
        }
        String test ="This is some test \"test to see what happens\"" + " hello there";
     
        String test2= "\"This is Some Test\" + \" of adding strings\"";
     
        //try{
        /*
            String test3= "int(\"remove \") + int(int(\"nleek\" + \"2\"))";
            System.out.println(removeInt(test3,variables));
            String test4= "\"remove \" + str(str(\"nleek\") + str(test))";
            System.out.println(removeStr(test4,variables));
        */
        //}
        //catch(Exception e){
            
        //}

    }
    
    
    static ArrayList<Variable> handleLines(ArrayList<String> fileLines, ArrayList<Variable> variables, boolean[] lineIfCheck, int indent) throws Exception{
        
        
        //array list for variables
        String line="";
        String type="";
        String nextLine="";
        
        
        //for breaks
        Variable vBreak=new Variable("break", "false");
        variables.add(vBreak);
        boolean cont = true;
        for(int i=0; i<fileLines.size() && cont == true; i++){
            line=fileLines.get(i);
            try{

                if(!type.equals("in-comment"))
                    type=typeOfLine(line);
                if(i<fileLines.size()-1) {
                    nextLine=fileLines.get(i+1);
                }

                if (indent == getIndent(line)) {



                    if(type.equals("comment")){
                        continue;
                    }

                    else if(type.equals("multi-line")){
                        type = "in-comment";
                        continue;
                    }

                    else if(type.equals("in-comment")){
                        if(line.contains("\"\"\"")){
                            type = "comment";
                            continue;
                        }
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
        
                            lineIfCheck[indent] = true;
                            //bump indent and go into else
                            indent++;
                        }
                        else {
           
                        }
                    }
                    else if(type.equals("else")) {
                        ArrayList<Boolean> checks =  new ArrayList<Boolean>();
                        //Set to 3 for it, set to 5
                        if(lineIfCheck[indent] == false) {
             
                            lineIfCheck[indent] = false;
                            //bump indent and go into else
                            indent++;
                        }
                        else {
                 
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
                                if(whileIndent<checkIndent && j<fileLines.size()){
                                    whileLine=fileLines.get(j);
                                    whileFileLines.add(whileLine);
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
    //                    boolean forBool=handleForCondition(line, variables, varName);


                        ArrayList<Boolean> checks =  new ArrayList<Boolean>();
                        //Set to 3 for it, set to 5
                        ArrayList<String> forFileLines=new ArrayList<String>();
                        int j=i+1;
                            String forLine="";
                            int forIndent=indent;
                            int checkIndent=getIndent(nextLine);
                            while(true){
    //     
                                if(forIndent<checkIndent && j<fileLines.size()){
    //            
                                    forLine=fileLines.get(j);
                                    forFileLines.add(forLine);
    //               
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
                        boolean breakFor=false;
                        while(handleForCondition(line, variables, varName)==true && breakFor==false) {

                                variables=handleLines(forFileLines, variables, lineIfCheck, forIndent+1);
                                for(int n=0; n<variables.size(); n++){
                                    Variable v=variables.get(n);
                                    if(v.getName().equals(varName)){
                                        v.setDoubleValue(v.getDoubleValue()+1);
                                    }
                                    if(v.getName().equals("break")){
                                        if(v.getStringValue()=="true"){
                                            v.setStringValue("false");
                                            breakFor=true;
                                        }
                                    }
                                }
                        }
                        i+=forFileLines.size();
                        indent++;

                    }
                    else if(type.equals("break")){
                        for(int n=0; n<variables.size(); n++){
                            Variable v=variables.get(n);
                            if(v.getName().equals("break")){
                                v.setStringValue("true");
                            }
                        }
    //                    break;
                    }


                }
                if(getIndent(nextLine) < indent && !typeOfLine(nextLine).equals("empty") && !typeOfLine(nextLine).equals("comment") && !typeOfLine(nextLine).equals("multi-line") && !typeOfLine(nextLine).equals("in-comment") && i<fileLines.size()-1 ) {
                        indent = getIndent(nextLine);
                }
            }
            
            catch(Exception e){
                throw new Exception ("\nException at line " + i + "\nLine: " + line + "\nParserException: " + e + "\n");
            }
        }
        for(int i=0; i<variables.size(); i++){
           Variable v=variables.get(i);
//         
       }
//      
        return variables;
   }
    
    static String getForVariableName(String line) throws Exception {
        try{
        line=line.trim();
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
        catch(Exception e){
            throw new Exception ("Variable name exception \nParserException: " + e + "\n");
        }
    }
    static ArrayList<Variable> handleForVariable(String line, ArrayList<Variable> variables, String varName) throws Exception{
        try{
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
            //System.out.println("hello there beg " + beginString);
            beginString = removeInt(beginString,variables);
            //System.out.println("hello there beg " + beginString);
            beginString = handleArithmetic(beginString,variables);
            //System.out.println("hello there beg " + beginString);
            /*
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
                    if(beginString.length()>5){
 
                        if(cVal=='i' && beginString.charAt(k+1)=='n' && beginString.charAt(k+2)=='t' && beginString.charAt(k+3)=='('){
                            intindex=k;
                            k=k+3;
                            intFunction=true;

                        }
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
                     
                            boolean intInVariables=false;
                            double intVal = 0;
                            for(int i=0; i<variables.size(); i++){
                                Variable v=variables.get(i);
                                if(v.getName().equals(intStringVal)){
                                    //increment value
                                    intInVariables=true;
                                    intVal=v.getDoubleValue();
                                    break;
                                }
                            }
                            if(!intInVariables){
                                intVal=(int)Double.parseDouble(intStringVal);
                             
                            }
                            
                           
       
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
            */
            //if it's not get beginning value in range and set it to new variable in array with varName

            if(inVariables==false){

                if(beginInVariables==false){
                    //check for int function
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
                        v.setDoubleValue(Double.parseDouble(beginString));
                    }
                }
            }

            return variables;
        }
        catch (Exception e){
              throw new Exception ("Variable exception \nParserException: " + e + "\n");  
        }

    }
    
    static ArrayList<Integer> createRange(int start, int end){
      ArrayList<Integer> range = new ArrayList<Integer>();
      for(int i=start;i<=end;i++){
          range.add(i);
      }
      return range;
    }
    
    static boolean handleForCondition(String line, ArrayList<Variable> variables, String varName ) throws Exception {
        try{
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
            boolean wasPara = false;
            for(int i=0; i<line.length(); i++) {
                c=line.charAt(i);
                if(afterComma){
                    if(c!=')' || line.charAt(i+1)!=':'){
                       endString+=Character.toString(c);
                       if(c==')')
                           wasPara=true;
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
                    //System.out.println("wwwww1 " + endString);
                    endString = removeInt(endString,variables);
                    //System.out.println("wwwww1 " + endString);
                    endString = handleArithmetic(endString,variables);
                    
                    //System.out.println("wwwww2 " + endString);
                    /*
                    //check for int function and arithmetic and perform those operations
                     boolean intFunction=false;
                     for(int k=0; k<endString.length(); k++){
                        char cVal=endString.charAt(k);


                        if(cVal=='i' && endString.charAt(k+1)=='n' && endString.charAt(k+2)=='t' && endString.charAt(k+3)=='('){
      
                            k=k+3;
                            intFunction=true;

                        }
                        
                        if(cVal=='+' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^' || ((cVal=='-' || cVal==')') && k!=0 && (intFunction == false || k!=4) )){
                            for(int m=0; m<endString.length(); m++){

                                char cVal2=endString.charAt(m);
                           
                                
                                
                                if(cVal2=='i' && endString.charAt(m+1)=='n' && endString.charAt(m+2)=='t' && endString.charAt(m+3)=='('){
                              
                                    m=m+3;
                                    intFunction=true;

                                    continue;
                                }
                                
                

                            }*/
    //           
                            //perform int function when parenthesis is closed
                
                            /*
                            if(cVal==')' && intFunction==true){
                                char intC=' ';
                                String intStringVal="";
                                int j=k-1;
                                while(intC!='('){
                                    intC=endString.charAt(j);
                                    //to fix reverse order
                                    if(intC!='('){
                                
                                        intStringVal=Character.toString(intC)+intStringVal;
                                    }
                                    j--;
                                }
                               
                                boolean intInVariables=false;
                                double intVal = 0;
                                for(int i=0; i<variables.size(); i++){
                                    Variable v=variables.get(i);
                                    if(v.getName().equals(intStringVal)){
                                        //increment value
                                        intInVariables=true;
                                        intVal=v.getDoubleValue();
                                        break;
                                    }
                                }
                                if(!intInVariables){
                                    intVal=(int)Double.parseDouble(intStringVal);

                                }
                                
                                intStringVal=Double.toString(intVal);
                                //endString=intStringVal+restValue;
                                intFunction=false;
    //                            restValue=restValue.substring(1);
                            }
                            */
                            //{
                                //check if leftVal is in variables array if it is get value else just parse
                                /*
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
                                */
                                /*
                          
                                if(intFunction==true){
                                        String endInner = endString.substring(4,endString.length()-1);
                                        //System.out.println("test4: " + end);
                                        //System.out.println("test: " + endString);
                                        //System.out.println("test: " + left+opVal+right+restValue.substring(0,restValue.length()-1));
                                        endString=handleArithmetic(endInner, variables);
                                        //System.out.println("test3: " + endString);
                                }
                                else{
                                    //System.out.println("test3: " + endString);
                                    endString = handleArithmetic(endString, variables);
                                    //System.out.println("test3: " + endString);
                                }
                                */
                            //}

                            //k=0; 
                        //}

                    //}
                    //System.out.println("bloopp2 " + varValue);
                    //System.out.println("bloopp " + endString);
                    endValue=Double.parseDouble(endString);

                }
            if(varValue<endValue){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            throw new Exception ("Condition exception \nParserException: " + e + "\n");
        }
    }
    
    static ArrayList<Variable> handleVariable(String line, ArrayList<Variable> va ) throws Exception{
        try{
            
            ArrayList<Variable> variables=va;
            line=line.trim();
            line = removeInt(line,variables);
            //System.out.println("testing " + line);
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
            String result = handleArithmetic(value,variables);
            //if(!result.equals(""))
            value = result;
            value = trimWhite(value);
  


                    /*
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
                */
            /*
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
                    value= Double.toString(arithmeticOperation(leftVal, rightVal, opVal))+restValue;
                    k=0;
                }
            }

            */
            //check for assignment
            int assignmentIndex=0;
            for(int j=0; j<variables.size(); j++){
                Variable vSecond=variables.get(j);
                if(value.equals(vSecond.getName())){
                    if(vSecond.getVariableType().equals("string")){
                        value = vSecond.getStringValue();
                    }
                    else{
                        value = String.valueOf(vSecond.getDoubleValue());
                    }
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
        catch(Exception e){
            throw new Exception ("Variable exception \nParserException: " + e + "\n");
        }
    }
    
    static String removeInt(String value, ArrayList<Variable> variables){
       // try{
            //System.out.println("test" + value);
            String newVal = value;
            for (int k=0; k<value.length()-4; k++){
                char cVal = value.charAt(k);
                
                if(cVal=='"'){
                    for(int a=k;a<value.length();a++){
                        if(value.charAt(a)==('"')){
                          
                            k=a+1;
                            break;
                        }
                        k=a;
                    }
                }
                
                if(cVal=='i' && value.charAt(k+1)=='n' && value.charAt(k+2)=='t' && value.charAt(k+3)=='('){
                    
                    int paracount = 1;
                    int start = k+4;
                    int end = 0;
                    for(int i=k+4;i<value.length();i++){
                        cVal = value.charAt(i);
                        //System.out.println("hello cval " + cVal);
                        //System.out.println("hello para " + paracount);
                        if(cVal=='('){
                            paracount++;
                        }
                        if(cVal==')'){
                            paracount--;
                        }
                        if(paracount==0){
                           
                     
                            end = i;
                            //newVal =  value.substring(0,start-4) + value.substring(i+1,value.length());
                            String temp = value.substring(start,i);
                            //System.out.println("this is temp " + temp);
                            String temp2 = "";
                            char cPrev = 'x';
                            int paracount2 = 0;
                            boolean inFunc = false;
                            for(int j=0;j<temp.length();j++){
                                if(cVal=='('){
                                    paracount2++;
                                }
                               
                                cVal=temp.charAt(j);
                           
                                //if(j>0){
                                    boolean q = false;
                                    if(cVal=='"'){
                                        q = !q;
                                        //temp2+= cVal;
                                        for(int a=j+1;a<temp.length();a++){
                                         
                                            if(temp.charAt(a)=='"' && q){
                                                //q=!q;
                                                continue;
                                            }
                                            else if(temp.charAt(a)=='"' && !q){
                                                q=!q;
                                                temp2+=temp.charAt(a);
                                            }
                                            else{
                                                temp2+= temp.charAt(a);
                                            }
                                            /*
                                            if(temp.charAt(a)=='"'){
                                                j=a+1;
                                                break;
                                            }
                                            else{
                                                j=a;
                                            }
                                            */
                                            j=a+1;
                                        }
                                    }
                                    if(j>=temp.length()){
                                        break;
                                    }
                                //}

                                cVal=temp.charAt(j);
                                if(j+4<temp.length()){
                                   
                                    if((cVal=='i' && temp.charAt(j+1)=='n' && temp.charAt(j+2)=='t' && temp.charAt(j+3)=='(')||(cVal=='s' && temp.charAt(j+1)=='t' && temp.charAt(j+2)=='r' && temp.charAt(j+3)=='(')){
                                     
                                        paracount2++;
                                        for(int p=j;p<temp.length();p++){
                                            char cVal2 = temp.charAt(p);
                                            //System.out.println("paracountc " + cVal2);
                                            //System.out.println("paracount " + paracount2);
                                            temp2 += temp.charAt(p);
                                            //System.out.println("Hi there3 " + temp2);
                                            if(cVal2=='('){
                                                paracount2++;
                                            }
                                            else if(cVal2==')'){
                                                paracount2--;
                                                if(paracount2==0)
                                                    temp2 = temp2.substring(0,temp2.length()-1);
                                                //System.out.println("Hi there2 " + temp2);
                                            }
                                            if(paracount2==0){
                                                j=p+1;
                                             
                                                break;
                                            }
                                            j=p;
                                        }
                                        j++;
                                    }
                                    //System.out.println("Hi there1 " + temp2);
                                    if(j>=temp.length()){
                                        break;
                                    }
                                    cVal=temp.charAt(j);
                                }
                                if(j>0){
                                    cPrev = temp.charAt(j-1);
                                }
                              
                                /*
                                if(j+1<temp.length()){
                                    
                                    if(temp.charAt(j+1)=='"' && temp.charAt(j)=='\\'){
                                        j++; 
                                        j++;
                                    }
                                }
                                */
                            
                              
                                if(temp.charAt(j)!='"'){
                             
                                    temp2+= Character.toString(temp.charAt(j));
                                }
                                
                            //System.out.println("Hi there " + temp2);
                            }
                            if(temp2.charAt(0)=='"'){
                             
                            }
      
                            //System.out.println("test" + temp2);
                            /*
                            else{
                                Variable tempV = handleArtVar(newVal, variables);
                                if(tempV.getVariableType().equals("string")){
                                    newVal = tempV.getStringValue();
                                }
                                if(tempV.getVariableType().equals("char")){
                                    newVal = Character.toString(tempV.getCharValue());
                                }
                                if(tempV.getVariableType().equals("int")){
                                    newVal = String.valueOf(tempV.getIntValue());
                                }
                                if(tempV.getVariableType().equals("double")){
                                    newVal = String.valueOf(tempV.getDoubleValue());
                                }
                                if(tempV.getVariableType().equals("boolean")){
                                    newVal = Boolean.toString(tempV.getBooleanValue());
                                }
                         
                            }
                            */
                            newVal = value.substring(0,start-4) + temp2 + value.substring(i+1,value.length());

                            break;
                        }
                        k=i;
                    }
                }
            }
            //System.out.println("baadsf" +newVal);
            if(newVal.equals(value)){
                return value;
            }
            else{
                return removeInt(newVal,variables);
            }
        //}
        /*
        catch(Exception e){
            
            throw new Exception("test");
        }
        */
    }
    

    
    static String makeString(String value, ArrayList<Variable> variables){
        if(value.charAt(0)=='"'){
            return value;
        }
        String newVal = value;
        for(int i=0;i<variables.size();i++){
            if(variables.get(i).getName().equals(value)){
                Variable temp = variables.get(i);
                if(temp.getVariableType().equals("string")){
                    newVal = temp.getStringValue();
                }
                if(temp.getVariableType().equals("char")){
                    newVal = Character.toString(temp.getCharValue());
                }
                if(temp.getVariableType().equals("int")){
                    newVal = String.valueOf(temp.getIntValue());
                }
                if(temp.getVariableType().equals("double")){
                    newVal = String.valueOf(temp.getDoubleValue());
                }
                if(temp.getVariableType().equals("boolean")){
                    newVal = Boolean.toString(temp.getBooleanValue());
                }
                
            }
        }
        newVal = "\"" + newVal + "\"";
        return newVal;
    }
    
    static String removeStr(String value, ArrayList<Variable> variables){
        //try{
            String newVal = value;
            for (int k=0; k<value.length()-4; k++){
                char cVal = value.charAt(k);
                if(cVal=='s' && value.charAt(k+1)=='t' && value.charAt(k+2)=='r' && value.charAt(k+3)=='('){
                    //System.out.println("test");
                    int paracount = 1;
                    int start = k+4;
                    for(int i=k+4;i<value.length();i++){
                        cVal = value.charAt(i);
                        if(cVal=='('){
                            paracount++;
                        }
                        if(cVal==')'){
                            paracount--;
                        }
                        //System.out.println(paracount);
                        if(paracount==0){
                               
                            //System.out.println("test");
                            //end = i;
                            //newVal =  value.substring(0,start-4) + value.substring(i+1,value.length());
                            String temp = value.substring(start,i);
                            //System.out.println(temp);
                            String temp2 = "";
                            char cPrev = 'x';
                            int paracount2 = 0;
                            //boolean inFunc = false;
                            for(int j=0;j<temp.length();j++){
                                if(j+4<temp.length()){
                                    if((cVal=='i' && temp.charAt(j+1)=='n' && temp.charAt(j+2)=='t' && temp.charAt(j+3)=='(')||(cVal=='s' && temp.charAt(j+1)=='t' && temp.charAt(j+2)=='r' && temp.charAt(j+3)=='(')){
                                        //System.out.println(temp);
                                        paracount2=1;
                                        for(int p=j+4;p<temp.length();p++){
                                            char cVal2 = temp.charAt(p);
                                            if(cVal2=='('){
                                                paracount2++;
                                            }
                                            else if(cVal2==')'){
                                                paracount2--;
                                            }
                                            if(paracount2==0){
                                                j=p+1;
                                                break;
                                            }
                                            j=p;
                                        }
                                    }
                                    cVal=temp.charAt(j);
                                }
                                if(j>0){
                                    cPrev = temp.charAt(j-1);
                                }
                                //System.out.println(temp.charAt(j));
                                /*
                                if(j+1<temp.length()){
                                    
                                    if(temp.charAt(j+1)=='"' && temp.charAt(j)=='\\'){
                                        j++; 
                                        j++;
                                    }
                                }
                                */
                                if(j<temp.length()){
                                    //System.out.println(temp.charAt(j));
                                    if(temp.charAt(j)!='"'){
                                        //System.out.println(temp.charAt(j));
                                        temp2+= Character.toString(temp.charAt(j));
                                    }
                                }
        
                            }
                            if(temp2.charAt(0)=='"'){
                                //System.out.println("error");
                            }
                            newVal =  value.substring(0,start-4) + value.substring(start,i) + value.substring(i+1,value.length());
                            newVal = makeString(newVal,variables);
                            //System.out.println(newVal);
                            /*
                            if(newVal.charAt(0)!= '"'){
                                Variable temp = handleArtVar(newVal, variables);
                                if(temp.getVariableType().equals("string")){
                                    newVal = temp.getStringValue();
                                }
                                if(temp.getVariableType().equals("char")){
                                    newVal = Character.toString(temp.getCharValue());
                                }
                                if(temp.getVariableType().equals("int")){
                                    newVal = String.valueOf(temp.getIntValue());
                                }
                                if(temp.getVariableType().equals("double")){
                                    newVal = String.valueOf(temp.getDoubleValue());
                                }
                                if(temp.getVariableType().equals("boolean")){
                                    newVal = Boolean.toString(temp.getBooleanValue());
                                }
                                if(newVal.charAt(0)!='"')
                                    newVal = "\"" + newVal + "\"";
                            }
                            */
                            break;
                        }
                        k=i;
                    }
                }
            }
            //System.out.println(newVal);
            if(newVal.equals(value)){
                return value;
            }
            else{
                return removeStr(newVal,variables);
            }
        /*
        }
        catch(Exception e){
            throw new Exception("test");
        }
        */
    }
    
    
    /*
    static Variable handleArtVar(String value, ArrayList<Variable> variables) throws Exception{
        Variable returnVar = new Variable("test","test");
        try{
            value = removeStr(value,variables);
            value = removeInt(value,variables);
            ArrayList<Operation> ops;
            ops = new ArrayList<Operation>();
            ArrayList<Operation> ops2 = new ArrayList<Operation>();
            Integer pVal = 0;
            //determine type
            String newVal;
            boolean isString = false;
            if(value.charAt(0)== '"'){
                isString = true;
            }
            
            for(int k=0; k<value.length();k++){
                boolean inString=false;
                String stringTemp = "";
                char cVal = value.charAt(k);
                if(cVal=='"'){
                    inString = true;
                    for(int j=k+1;j<value.length();j++){
                        cVal = value.charAt(j);
                        if(cVal!='"'){
                            stringTemp += Character.toString(cVal);
                            newVal+= Character.toString(cVal);
                            k=j;
                        }
                        else {
                            k++;
                            break;
                        }
                    }
                Operation tempOp = new Operation();
                tempOp.content = stringTemp;
                tempOp.isString= true;
                ops.add(tempOp);
                }
                if(cVal=='i' && value.charAt(k+1)=='n' && value.charAt(k+2)=='t' && value.charAt(k+3)=='('){
                    if(value.charAt(k+4)=='"'){
                        for
                        
                    }
                    int paracount = 1;
                    for(int i=k+4;i<value.length();i++){
                        cVal=value.charAt(i);
                        if(cVal=='"'){
                            inString = true;
                            for(int j=k+1;j<value.length();j++){
                                cVal = value.charAt(j);
                                if(cVal!='"'){
                                    stringTemp += Character.toString(cVal);
                                    k=j;
                                }
                                else {
                                    k++;
                                    break;
                                }
                            }
                        Operation tempOp = new Operation();
                        tempOp.content = stringTemp;
                        tempOp.isString= true;
                        ops.add(tempOp);
                        }
                        k=i;
                        if(cVal=='('){
                            paracount++;
                        }
                        else if(cVal==')'){
                            paracount--;
                        }
                        if(paracount==0){
                            break;
                        }
                        
                    }
                }
            }

            for(int k=0; k<value.length(); k++){
                char cVal=value.charAt(k);
                char cPrev = 'x';
                if(k>0)
                    cPrev = value.charAt(k-1);
                if(cVal=='+' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^' || (cVal=='-' && !(k==0 || (cPrev=='+' || cPrev =='-' || cPrev =='*' || cPrev =='/' || cPrev == '^')))){
                    Operation newOp = new Operation();
                    newOp.operator = cVal;
                    newOp.pos = k;
                    newOp.pVal = pVal;
                    ops.add(newOp);
                    ops2.add(newOp);
                }
                else if (cVal =='('){
                    pVal++;
                }
                else if (cVal == ')' && pVal > 0){
                    pVal--;
                }
            }

            Collections.reverse(ops);
            Collections.reverse(opsInd);
            for(int i=0; i<ops.size()-1; i++){
                for(int j=0;j<ops.size()-i-1;j++)
                    if(opGreater(ops.get(j+1),ops.get(j))){
                        Collections.swap(ops,j,j+1);
                    }

            }



            for(int i=0; i<ops.size(); i++){
                Integer ind = ops.get(i).pos;
                Character op = ops.get(i).operator;
                String left="";
                String right="";
                for(int k=ind+1;k<value.length();k++){
                    char cVal = value.charAt(k);
                    if(((cVal=='+' || cVal=='-' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^')&& !(cVal=='-' && right=="")) ){
                        break;
                    }
                    else if(cVal=='(' || cVal==')'){
                        continue;
                    }
                    else{
                        right+= Character.toString(cVal);
                    }
                }
                int p = 0;
                for(int k=ind-1;k>=0;k--){
                    char cVal = value.charAt(k);
                    if(cVal=='-' && p==0){
                        left= Character.toString(cVal) + left;
                        p++;
                    }
                    else if(cVal=='(' || cVal==')'){
                        continue;
                    }
                    else if(((cVal=='+' || cVal=='-' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^')&& !(cVal=='-' && p==0)) ){
                        break;
                    }
                    else{
                        left= Character.toString(cVal) + left;
                    }
                }
                ops.get(i).left = left;
                ops.get(i).right = right;

            }
            String result = "";
            for(int i=0; i<ops.size(); i++){
                Operation op = ops.get(i);
  
                
                double leftVal = 0;
                double rightVal = 0;
                boolean leftExists=false;
                boolean rightExists=false;
         
                for(int j=0; j<variables.size(); j++){
                    Variable vExist=variables.get(j);
                    if(op.left.equals(vExist.getName())){
                        leftExists=true;
                        leftVal=vExist.getDoubleValue();
                    }
                    if(op.right.equals(vExist.getName())){
                        rightExists=true;
                        rightVal=vExist.getDoubleValue();
                    }
                }
                if(!leftExists)
                    leftVal = Double.parseDouble(op.left);
                if(!rightExists)
                    rightVal = Double.parseDouble(op.right);
                result = Double.toString(arithmeticOperation(leftVal,rightVal,Character.toString(op.operator)));
                Integer smallestL = 99999;
                Integer smallestR = 99999;
                int smallestLInd = 0;
                int smallestRInd = 0;
                for(int j=0;j<ops.size();j++){
                    if(ops.get(j).exist){
                        Integer diff = ops.get(i).pos - ops.get(j).pos;
                        Integer diff2 = -diff;
                        if(diff > 0 && diff < smallestL){
                            smallestL = diff;
                            smallestLInd = j;
                        }
                        if(diff2 > 0 && diff2 < smallestR){
                            smallestR = diff2;
                            smallestRInd = j;
                        }
                    }
                }
                op.exist = false;
                //System.out.print("result: " + result + "\n");
                ops.get(smallestLInd).right = result;
                ops.get(smallestRInd).left = result;
            }

            value = result;
            return result;
        }
        catch(Exception e){
            throw new Exception ("Arithmatic exception \nParserException: " + e + "\n");
        }
        
        return returnVar;
    }
 */
    
    static String trimWhite(String value){
        String newVal = "";
        //System.out.println(value);
        for(int i=0;i<value.length();i++){
            char cVal = value.charAt(i);
            char cPrev = 'x';
            if(cVal == '"'){
                
                newVal+= cVal;
                for(int k=i+1;k<value.length();k++){
                    cPrev = value.charAt(k-1);
                    cVal = value.charAt(k);
                    newVal+= cVal;
                    if(cVal == '"' && cPrev != '\\'){
                        break;
                    }
                    i=k;
                }
                i++;
                continue;
            }
            else if(cVal !=' '){
                newVal += cVal;
            }
        }
        
        return newVal;
    }
    
    
    static String handleStrings(String value){
        value = trimWhite(value);
        String newString="";
        //boolean inString;
        char previous = '+';
        int paraCount = 0;
        for(int i=0;i<value.length();i++){
            char cPrev = 'x';
            if(i>0){
                cPrev = value.charAt(i-1);
            }
            if(paraCount<0 || previous != '+'){
                newString = value;
                break;
            }
            if(value.charAt(i)=='"' && cPrev != '\\' && previous=='+' && paraCount >=0){
                for(int k=i+1; k<value.length();k++){
                    if(value.charAt(k)=='"' && value.charAt(k-1)!='\\'){
                        i=k;
                        break;
                    }
                    else{
                        newString+= value.charAt(k);
                    }
                    i=k;
                }
            }
            else if(value.charAt(i)=='+'){
                previous = value.charAt(i);
                //continue;
            }
            else if(value.charAt(i) == '('){
                paraCount++;
            }
            else if(value.charAt(i) == '}'){
                paraCount--;
            }
            else{
                previous = value.charAt(i);
            }
            
        }
        
        return newString;
    }
    
    
    
    static String handleArithmetic(String value, ArrayList<Variable> variables) throws Exception{
        try{
            //System.out.println("hello there" + value);
            ArrayList<Operation> ops;
            ArrayList<Integer> opsInd = new ArrayList<Integer>();
            ops = new ArrayList<Operation>();
            ArrayList<Operation> ops2 = new ArrayList<Operation>();
            Integer pVal = 0;
            //System.out.println(value);
            for(int k=0; k<value.length(); k++){
                char cVal=value.charAt(k);
                char cPrev = 'x';
                if(k>0)
                    cPrev = value.charAt(k-1);
                if(cVal=='+' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^' || (cVal=='-' && !(k==0 || (cPrev=='+' || cPrev =='-' || cPrev =='*' || cPrev =='/' || cPrev == '^' || cPrev =='(')))){
                    Operation newOp = new Operation();
                    newOp.operator = cVal;
                    newOp.pos = k;
                    newOp.pVal = pVal;
                    ops.add(newOp);
                    ops2.add(newOp);
                }
                else if (cVal =='('){
                    pVal++;
                }
                else if (cVal == ')' && pVal > 0){
                    pVal--;
                }
            }
            //System.out.println(value);
            Collections.reverse(ops);
            Collections.reverse(opsInd);
            for(int i=0; i<ops.size()-1; i++){
                for(int j=0;j<ops.size()-i-1;j++)
                    if(opGreater(ops.get(j+1),ops.get(j))){
                        Collections.swap(ops,j,j+1);
                    }

            }


            //System.out.println(value);
            for(int i=0; i<ops.size(); i++){
                //System.out.println("ops " + ops.get(i).operator);
                Integer ind = ops.get(i).pos;
                Character op = ops.get(i).operator;
                String left="";
                String right="";
                for(int k=ind+1;k<value.length();k++){
                    char cVal = value.charAt(k);
                    if(((cVal=='+' || cVal=='-' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^')&& !(cVal=='-' && right=="")) ){
                        break;
                    }
                    else if(cVal=='(' || cVal==')'){
                        continue;
                    }
                    else{
                        right+= Character.toString(cVal);
                    }
                }
                int p = 0;
                for(int k=ind-1;k>=0;k--){
                    char cVal = value.charAt(k);
                    //System.out.println("hello" + cVal);
                    if(cVal=='-' && p==0){
                        left= Character.toString(cVal) + left;
                        p++;
                    }
                    else if(cVal=='(' || cVal==')'){
                        continue;
                    }
                    else if(((cVal=='+' || cVal=='-' || cVal=='*' || cVal=='/' || cVal=='%' || cVal=='^')&& !(cVal=='-' && p==0)) ){
                        break;
                    }
                    else{
                        left= Character.toString(cVal) + left;
                        //System.out.println("hello2 " + cVal);
                        //System.out.println("helloi2 " + i);
                    }
                }
                ops.get(i).left = left;
                ops.get(i).right = right;

            }
            //System.out.println(value);
            String result = "";
            for(int i=0; i<ops.size(); i++){
                //System.out.println("testing" + i);
                
                Operation op = ops.get(i);
                //System.out.println("hello there2 " + op.right);
                //System.out.println("hello there2 " + op.left);
                /*
                System.out.print("left: " + op.left + "\n");
                System.out.print("right: " + op.right + "\n");
                System.out.print("op: " + op.operator + "\n");
                System.out.print("p: " + op.pVal + "\n");
                */
                
                double leftVal = 0;
                double rightVal = 0;
                boolean leftExists=false;
                boolean rightExists=false;
         
                for(int j=0; j<variables.size(); j++){
                    Variable vExist=variables.get(j);
                    if(op.left.equals(vExist.getName())){
                        leftExists=true;
                        leftVal=vExist.getDoubleValue();
                    }
                    if(op.right.equals(vExist.getName())){
                        rightExists=true;
                        rightVal=vExist.getDoubleValue();
                    }
                }
                if(!leftExists)
                    leftVal = Double.parseDouble(op.left);
                if(!rightExists)
                    rightVal = Double.parseDouble(op.right);
                //System.out.println("hello there2 " + op.right);
                //System.out.println("hello there2 " + op.left);
                result = Double.toString(arithmeticOperation(leftVal,rightVal,Character.toString(op.operator)));
                //System.out.println("hello there2 " + result);
                Integer smallestL = 99999;
                Integer smallestR = 99999;
                int smallestLInd = 0;
                int smallestRInd = 0;
                for(int j=0;j<ops.size();j++){
                    if(ops.get(j).exist){
                        Integer diff = ops.get(i).pos - ops.get(j).pos;
                        Integer diff2 = -diff;
                        if(diff > 0 && diff < smallestL){
                            smallestL = diff;
                            smallestLInd = j;
                        }
                        if(diff2 > 0 && diff2 < smallestR){
                            smallestR = diff2;
                            smallestRInd = j;
                        }
                    }
                }
                op.exist = false;
                //System.out.print("result: " + result + "\n");
                ops.get(smallestLInd).right = result;
                ops.get(smallestRInd).left = result;
            }
            //System.out.println("test" + result);
            //System.out.println(value);
            if(!result.equals(""))
                value = result;
            //System.out.println("this is a test: " + value);
            return value;
        }
        catch(Exception e){
            throw new Exception ("Arithmatic exception \nParserException: " + e + "\n");
        }
    }
    
    static double arithmeticOperation(double left, double right, String operation) throws Exception{
        
        try{
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
        catch (Exception e){
            throw new Exception ("Arithmatic exception \nParserException: " + e + "\n");
        }
    }
    
    static String readFile(String path, Charset encoding) throws IOException{
      byte[] encoded = Files.readAllBytes(Paths.get(path));
      return new String(encoded, encoding);
    }
    
    static boolean checkTab(String file, int i) {

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
        else if(line.substring(0,3).equals("\"\"\""))
            return "multi-line";
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

    static void handlePrint(String line, ArrayList<Variable> variables) throws Exception {
        //System.out.println(line);
        try{
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
                if(meat.charAt(i)=='\'') {
                    buildString = "";
                    i++;
                    String part = "";
                    while(meat.charAt(i)!='\'') {
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
        catch(Exception e){
            throw new Exception ("Print exception \nParserException: " + e + "\n");
        }
    }
    

    static void printVariableValue(String buildString, ArrayList<Variable> variables) throws Exception {
        try{
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
        catch(Exception e){
            throw new Exception ("Print variable exception \nParserException: " + e + "\n");
        }
    }

    static boolean getComparison(String line, ArrayList<Variable> variables, ArrayList<Boolean> checks, int set) throws Exception{
        try{
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
        catch (Exception e){
            throw new Exception ("Comparison exception \nParserException: " + e + "\n");
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
    
    static boolean opGreater(Operation op1, Operation op2){
        Character char1 = op1.operator;
        Character char2 = op2.operator;
        

        
        if(op1.pVal > op2.pVal){
            return true;
        }
        else if(op1.pVal < op2.pVal){
            return false;
        }
        
        
        else if(char1.equals('^')){
            if(char2.equals('^'))
                return false;
            else
                return true;
        }
        else if(char1.equals('*') || char1.equals('/')){
            if(char2.equals('*') || char2.equals('/') || char2.equals('^'))
                return false;
            else{
                return true;
            }
        }
        
        return false;
    }
    

}

