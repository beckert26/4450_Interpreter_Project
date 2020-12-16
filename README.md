# 4450_Interpreter_Project

**Project Explanation**

Our project is an interpretter program that takes in a Python file with proper syntax and translates it to the Java language. While it is being translated, the code is also run, and the results produced after translation match (in Java format) the same results as if you were to run the original Python file.

Our project runs, on a basic level, by first reading in the lines from the Python file, line by line. Those lines are then saved to a "fileLines" ArrayList to be processed. We set some variables, lists, and arrays for logic purposes, and then pass our lines (along with some other variables/lists/arrays for logic purposes) to our "handleLines" function in order to begin actually sorting through the "fineLines" ArrayList, one by one.

Inside of our "handleLines" function, we grab the lines from the passed in "fileLines" list, and start going through it from top to bottom. After getting a line, the first thing we do is check to see if it's indent matches the indent we are checking for, by using our "getIndent" function (This checks the whitespace before each line unless it is empty). If it is, then we get it's "type." This is done by passing the line down to our "typeOfLine" function, which returns whether each line is: empty, a comment, a print statement, an if statement, an elif statement, an else statement, a for statement, a while statement, a break statement, and if none of the previous cases hit, a variable. The way we do this is by checking the beginning of each line to see if it starts with those previously mentioned "keywords." If it does, then we return that, and that "type" is assigned to each respective line. Then, we send the line to one of our many "handling" functions, respective to each type.

Another thing to note is that before passing down to our "fileLines" functions, we create 3 other important things. A constantly updated list of the variables "variables" (holding their names, values, and types), a boolean array "lineIfCheck" (determines if an "elif" or "else" is to be gone into), and lastly an int that represents the current indent we are checking for.

One final thing that is important to mention about our program's main functionality is that several of our "handling" functions bumps up the indent (which starts at zero). Whenever we find that the next line of the program (so long as there is one) has an indentation that is less than the current indent (unless it has a type that isn't "empty" or "comment"), then we set the current indent to that line.

**Team Members**

Our team includes:

Brett Eckert (bdem7v)

Email: bdem7v@mail.missouri.edu


Joseph Burris (jpb68d)

Email: jpb68d@umsystem.edu



Ian Anderson (ilam56)

Email: ilam56@umsystem.du

**Requirements Met (And How We Met Them)**

- Empty lines-

For this, whenever we see a line that matches "", we just skip onto the next line, since nothing is to be done with it anyways.

- Comments-

We handled this similarly to the empty lines. If the line starts with "#", then we just skip the line, since nothing is to be done with it anyways. Also handled multi-line comments by detecting line starting with "\"\"\"" and throwing away all lines until the same string appears again

- Print-

For this, the first thing we did was to see if the beginning of the line starts with "print." If that is the case, we then send the current line (along with the variables array) to the "handlePrint" function. Here, we first "trim" the line (remove the whitespace before/after) and then get the material inside of the actual print statement. Once we have determined the "meat" of the print statement, we start scanning through it, character-by-character.

If a '"' character is reached, then we begin "building" a string to be printed. This building process continues until it reaches the next '"' character, then it prints (but not with a newline) that value. However, if there isn't the case of quotes, we're constantly building a string to look for variable calls.

Whenver we reach either a "+" (meaning that statment is over) or the end of the "meat" of the print statement, we check to see if a variable exists and print it if it does through our "printVariableValue" function.

It's also important to mention that really, we exclude checking for (str() and int()). However, what we do check for is parenthesis inside of the "meat" of the line. If we run into them, then we get the material inside and check to see if it is a variable and print it, using our "printVariableValue" function.

- Conditional Statements-

For this, the first thing we did was to see if the beginning of the line starts with "if", "elif", or "else." If that is the case, then we send that line down to our "getComparison" function, along with the variables array, a blank list of "checks" (for logic purposes), and a variable "set" that is really an offset from the very left of the trimmed line (to get past the "if", "elif", or "while" part of statements where conditions are being checked.

After getting past the offset, similar to the print, we start building the "left side" of the comparison. This continues to build until it finally runs into one of the conditional operators. After it does, the left side is checked to see if it is an arithmetic expression, a "naked number", or a variable. If it is an arithmetic expression, then it is separated into 3 parts. The left side of the operation, the right side of the operation, and the operator. Those are passed to a function that returns the value of arithmetic operations, and then the "overall" left side of the comparison is set to that. If the left side is a naked number, then the "overall" left side just given a value based off of its double value. If the left side is a variable, then the variables array is scanned through, and if a match is found, the "overall" left side is given the value of that variable. After getting the "real" value of the left side of the comparison, the operator is then parsed and saved. The same method is then used to parse through (and maintain) the right side of the comparison.

Then, if the parser runs into 3 things ("and", "or", ")", or ":"), it checks the comparison, and adds the result to a boolean list of "checks." If "and" or "or" are parsed over, then then the "getComparison" function is called again with the current list of checks along with it. Then, when the parser has run into either ":" or ")", two different algorithms are used. The boolean list "checks" is parsed through, and if the comparision statement contains "and", then if one statement is false, false is returned (true if otherwise). If the comparison statement contains "or", then if ONE of the "checks" values equals true, true is returned (false if otherwise).

- If/Elif/Else Statements-

So, this part is very related to the "comparison statements" segement. Regardless, the first thing we did to handle these was check if the beginning of a line starts with "if", "elif" or "else." Before starting, it is important to mention that for each line, we maintain a boolean list "lineIfCheck", that is used to determine if, for each indent (which corresponds to each index of the list), if we should enter into an "elif" or "else" statement.

if) If the beginning of the line starts with "if", then we set the "lineIfCheck" for that indent to false (A "lineIfCheck" reset for each line since it doesn't matter if the comparision passes or not). Then, the comparison is checked. If it returns true, the "lineIfCheck" for the current line is set to true (meaning that we can't go into "elifs" or "elses" for that line specificially, until a new if statement is run into). Then, the indent that we are checking for is bumped by one to go into the if statement's logic.

elif) If the beginning of the line starts with "elif", then we check 2 things: To see if the comparison returns true, and also if the "lineIfCheck" for that indent is set to false (meaning that a previous if has failed). If those two things are true, then we set the "LinkIfCheck" for the current to true (meaning that we can't go into other "elifs" or "elses" for that line specificially, until a new if statement is run into). Then, we bump the indent and go into that elif statement's logic.

else) If the beginning of the line starts with "else", then we check to see if the "lineIfCheck" for that line is set to false. If it is, then we set the "lineIfCheck" for that line to false to reset that value (because that means that we are no longer checking for more "elif" or "else" statements). Then, we bump the current line indent we are checking for by one and enter into the else statement's logic.

- While/For loops-

These are handled separetly based on whether the beginning of a line starts with while or for. For while loops the interpretter checks to see whether the case in the interpretter is true or false if it is true it recursively calls all code with an indent greater than the indent of the while loop line. The recursive code returns the variables array and that is used to then check whether the while condition still holds up.

For for loops the project first gets the variable inside the for loop and adds it as a variable with the value being set to the first value in the range function. Then the for loop operates similarily to while loop where it checks whether the variable in the for loop is less than the second value in the range function and if it is then it recursively runs all code with indent greater than the for loop indent and increments the variable + 1 each time. 

Additonally, break statements are handled by checking whether or not a break variable in the variables array is true or false and if it is true the loop is exited.

- Variable Definitions-

First any line that doesn't contain a keyword is considered a variable line and is handled using a function. The interpretter handles variable definitions by using a variable class and an array of variables. When handling variables we determine the name of the variable and the value by checking if the operand on the right hand side is a string or not. The variable is then added to the variables array and when it needs to be used in other lines our interpretter looks through the variable array and finds the variable with the matching name in order to get the value. Additionally, if the variable already exists within the array the value on the right hand side uses the set function of the variable in order to change a variables value. We interpret the code by looking for an "=" and using character on the left as the name of the variable being assignened and the right side as what will be assigned to that value. Addiotnally, if the interpretter sees quotes at the start of the right hand side it knows to interpret that as a string" y using overloaded functions we can create the correct type of variable by calling the variable name with it's value and depending on the value our getVariableType() function in the variable class will return what type the variable is which is useful for knowing how to use the variable in other sections.  All numbers are processed as doubles since python doesn't use types for variaable definition which simplifies the airthitmetic and math operations. When printing the interpretter will check whether the number is a whole number and print it as if it is an int when the number is a whole number.

- Assignment Operations-

Assignment operators are handled within the variable definitions class since the line starts with a variable name. The interpretter checks for what kind of assignment operator is used and uses if else statements to perform the desired operation using the right hand side of the equations values.This operation works similary to how the "=" operation works except we get the value of the variable on the left hand side and perform the math with the value of the right hand side.

- Arithmetic Operations-

Arithmetic operations are handled through a function that can be called where you send the operator and left and right hand side values. Our interpretter checks for whether or not it sees an arithmetic operator and if it does it gets the variable name or number on the left and right side of the operator and then gets the respective values for variable names using the variables array and sends the operation to our arithmetic operation function. These are used in for loops, if statements, and varaible defintions. When a line contains more than one arithmetic operation the interpretter continually performs operations using orders of operations. Is does this by creating an array or operations with both their left and right values. It then sorts these operations by orders of operations. It also checks for parenthesis and accounts for that in the orders of operations. It then does the calculations on the operation with the highest precedent. When a calculation is done it updates the left and right values of the operations surrounding it. The result of the final operation is then the result of the calculation.

- Error Checking-
The error checking we have implemented probably isn't enougn for much bonus, but it is better than nothing. It essentially produces an error whenever the parser runs into an error. It displays the line of the error, the line text, and the error the parser ran into. Note that if the error occured inside a for loop then it will display an error for both the outer for loop as well as where the error occured. In such situations the line with the actual error will be the last error printed.

**How To Use/Run**

Our project can be run in any basic Java compiler. We have already included the test code that was presented on canvas as our "default" file that is to be run with the program. However, it can be easily changed by changing line#(TBD) in the code, in the area surrounded in quotes that says "python_test_code.py" and adding a new file into our "Interpretter_Project" folder.

If you have any questions about running, or the project in general, feel free to reach out to one of the specified emails above! :)
