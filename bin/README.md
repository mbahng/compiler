# Compilers 

Compile Time  

   0. Figure out all the token types you want in your language. 

   1. Make a scanner that takes in a string and outputs a sequence of tokens. I thought you might want to implement this using a stack, but you want to just do a sliding window approach. 

   2. Create a parser that takes in this sequence of tokens and outputs an abstract syntax tree (AST), implemented in a tree node class called "Expr". In here, formal grammars are useful. The 4 big classifications are literal, grouping, unary, and binary. But this is an ambiguous grammar, so you want to have precedence. 

   3. Optionally, for debugging, make an AST printer so that you can see the order in which the operations are done in. 

   4. Have some error handling (such as syntax errors in scanning or parsing). Have it print out what line or column the error was on. 


Run Time 

   5. Make the evaluator, which takes in the Expression root node and simply prints the evaluated expression. Actually evaluate the values using an "Interpreter" class. This is where you might want to typecast stuff so that operators are overloaded. 
   
   6. Do some runtime error handling. Have it check whether certain types are okay for certain operations. You can implement RuntimeErrors as a class itself. 

   7. Add statements, which come in 2 types: expressions and print. Statements don't evaluate to a value, so they must do something else that is useful---called a side effect---either by printing something or changing the state of the interpreter (e.g. storing a value). Add expressions to access and assign to variables. 

   8. Add blocks and local scope. 

   9. Add control flow. 

   10. Add functions. 

   11. Resolving and Binding. 

   12. Classes. 

   13. Inheritance. 



# Some Notes 

   Seems like the difference between expressions and statements is really language-dependent. So every language (e.g. Java, C, Python) should have a formal grammar, and you can determine the distinction from looking at it. 
