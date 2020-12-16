package interpretter_project;
import java.util.ArrayList;

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
    
    public Variable(String name, String sValue){
        this.name=name;
        this.sValue=sValue;
        this.isString=true;
    }
    public Variable(String name, int intValue){
        this.name=name;
        this.intValue=intValue;
        this.isInt=true;
    }
    public Variable(String name, double doubleValue){
        this.name=name;
        this.doubleValue=doubleValue;
        this.isDouble=true;
    }
    public Variable(String name, char charValue){
        this.name=name;
        this.charValue=charValue;
        this.isChar=true;
    }
    public Variable(String name, boolean booleanValue){
        this.name=name;
        this.booleanValue=booleanValue;
        this.isBoolean=true;
    }
    

    
    //Getters
    public String getName(){
        return this.name;
    }
    public String getStringValue(){
        return this.sValue;
    }
    public int getIntValue(){
        return this.intValue;
    }
    public double getDoubleValue(){
        return this.doubleValue;
    }
    public char getCharValue(){
        return this.charValue;
    }
    public boolean getBooleanValue(){
        return this.booleanValue;
    }
    
    //Get Variable Type
    public String getVariableType(){
        if(isString==true){
            return "string";
        }
        else if(isInt==true){
            return "int";
        }
        else if(isDouble==true){
            return "double";
        }
        else if(isChar==true){
            return "char";
        }
        else if(isBoolean==true){
            return "boolean";
        }
        return "failure";
    }
    
    //Setters
    public void setName(String name){
        this.name=name;
    }
  
    public void setStringValue(String sValue){
        this.sValue=sValue;
    }
    public void setIntValue(int intValue){
        this.intValue=intValue;
    }
    public void setDoubleValue(double doubleValue){
        this.doubleValue=doubleValue;
    }
    public void setCharValue(char charValue){
        this.charValue=charValue;
    }
    public void setBooleanValue(boolean booleanValue){
        this.booleanValue=booleanValue;
    }
    
    
    
}
