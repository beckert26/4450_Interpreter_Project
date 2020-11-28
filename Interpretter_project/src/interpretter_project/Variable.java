package interpretter_project;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brett
 */
public class Variable {
    private String name=null;
    private String sValue;
    private int intValue;
    private double doubleValue;
    private char charValue;
    private boolean booleanValue;
    
    private boolean isString=false;
    private boolean isInt=false;
    private boolean isDouble=false;
    private boolean isChar=false;
    private boolean isBoolean=false;
    
    private Variable(String name, String sValue){
        this.name=name;
        this.sValue=sValue;
        this.isString=true;
    }
    private Variable(String name, int intValue){
        this.name=name;
        this.intValue=intValue;
        this.isInt=true;
    }
    private Variable(String name, double doubleValue){
        this.name=name;
        this.doubleValue=doubleValue;
        this.isDouble=true;
    }
    private Variable(String name, char charValue){
        this.name=name;
        this.charValue=charValue;
        this.isChar=true;
    }
    private Variable(String name, boolean booleanValue){
        this.name=name;
        this.booleanValue=booleanValue;
        this.isBoolean=true;
    }
    
    //Getters
    private String getName(){
        return this.name;
    }
    private int getIntValue(){
        return this.intValue;
    }
    private double getDoubleValue(){
        return this.doubleValue;
    }
    private char getCharValue(){
        return this.charValue;
    }
    private boolean getBooleanValue(){
        return this.booleanValue;
    }
    
    //Get Variable Type
    private String getVaraibleType(){
        if(isString==true){
            return "string";
        }
        else if(isInt==true){
            return "int";
        }
        else if(isDouble==true){
            return "int";
        }
        else if(isChar==true){
            return "char";
        }
        else if(isBoolean==true){
            return "boolean";
        }
    }
    
    //Setters
    private void setName(String name){
        this.name=name;
    }
    private void setIntValue(int intValue){
        this.intValue=intValue;
    }
    private void setDoubleValue(double doubleValue){
        this.doubleValue=doubleValue;
    }
    private void setCharValue(char charValue){
        this.charValue=charValue;
    }
    private void setBooleanValue(boolean booleanValue){
        this.booleanValue=booleanValue;
    }
    
    
    
}
