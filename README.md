# 4450_Interpreter_Project

**Project Explanation**

Our project is an interpretter program that takes in a Python file with proper syntax and translates it to the Java language. While it is being translated, the code is also run, and the results produced after translation match (in Java format) the same results as if you were to run the original Python file.

Our project runs, on a basic level, by first reading in the lines from the Python file, line by line. Those lines are then saved to a "fileLines" ArrayList to be processed. We set some variables, lists, and arrays for logic purposes, and then pass our lines (along with some other variables/lists/arrays for logic purposes) to our "handleLines" function in order to begin actually sorting through the "fineLines" ArrayList, one by one.

Inside of our "handleLines" function, we grab the lines from the passed in "fileLines" list, and start going through it from top to bottom. After getting a line, the first thing we do is check to see if it's indent matches the indent we are checking for, by using our "getIndent" function (This checks the whitespace before each line unless it is empty). If it is, then we get it's "type." This is done by passing the line down to our "typeOfLine" function, which returns whether each line is: empty, a comment, a print statement, an if statement, an elif statement, an else statement, a for statement, a while statement, a break statement, and if none of the previous cases hit, a variable. The way we do this is by checking the beginning of each line to see if it starts with those previously mentioned "keywords." If it does, then we return that, and that "type" is assigned to each respective line. Then, we send the line to one of our many "handling" functions, respective to each type.

One final thing that is important to mention about our program's main functionality is that several of our "handling" functions bumps up the indent (which starts at zero). Whenever we find that the next line of the program (so long as there is one) has an indentation that is less than the current indent (unless it has a type that isn't "empty" or "comment"), then we set the current indent to that line.

**Team Members**

Our team includes:
Brett Eckert (bdem7v)
Email: bdem7v@mail.missouri.edu
Joseph Burris (jpb68d)
Email: jpb68d@umsystem.edu
Ian Anderson (TBD)
Email: TBD

**Requirements Met (And How We Met Them)**

- Empty lines-


**How To Use/Run**

Our project can be run in any basic Java compiler. We have already included the test code that was presented on canvas as our "default" file that is to be run with the program. However, it can be easily changed by changing line#(TBD) in the code, in the area surrounded in quotes that says "python_test_code.py" and adding a new file into our "Interpretter_Project" folder.

If you have any questions about running, or the project in general, feel free to reach out to one of the specified emails above! :)
