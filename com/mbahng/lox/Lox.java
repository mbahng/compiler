package com.mbahng.lox;

import java.io.BufferedReader; 
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset; 
import java.nio.file.Files; 
import java.nio.file.Paths; 
import java.util.List;

public class Lox { 
  public static void main(String args[]) throws IOException { 
    // args = null;
    // args = new String[1]; 
    // args[0] = "tests/integration/example.lox";
    if (args.length > 1) {
      System.err.println("Too many args");
      System.exit(64);
    }
    else if (args.length == 1) {
      runFile(args[0]);
    } 
    else {
      runPrompt();
    }
  } 

  private static void runFile(String path) throws IOException { 
    byte[] bytes = Files.readAllBytes(Paths.get(path)); 
    run(new String(bytes, Charset.defaultCharset())); 
  }

  private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    for (;;) { 
      System.out.print("> ");
      String line = reader.readLine(); 
      if (line == null) { // basically when CTRL-D 
        break; 
      }
      run(line);
    }
  } 

  private static void run(String source) { 
    Scanner scanner = new Scanner(source);  
    List<Token> tokens = scanner.scanTokens(); 
    
    Parser parser = new Parser(tokens); 
    Expr expression = parser.parse();
    System.out.println(new AstPrinter().print(expression));
  } 

  // static void error(int line, String message) {
  //
  // }
}

