/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpretter_project;

/**
 *
 * @author Ian
 */
public class Syntax {
    
}
/*
    static Variable handleArtVar(String value, ArrayList<Variable> variables) throws Exception{
        Variable returnVar = new Variable("test","test");
        try{
            value = removeStr(value,variables);
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