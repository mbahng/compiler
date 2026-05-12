package com.mbahng.lox;

import java.util.ArrayList; 
import java.util.List; 

public class Scanner {
  private final String source; 
  private final List<Token> tokens = new ArrayList<>(); 
  private int i = 0;    // index of the i of this lexeme 
  private int j = 0;  // index of j of this lexeme
  private int line = 1; 


  Scanner(String source) {
    this.source = source;
  } 

  private void addToken(TokenType tt) { 
    this.tokens.add(new Token(tt, source.substring(i, j), null, this.line));
  }

  private void addToken(TokenType tt, Object literal) { 
    this.tokens.add(new Token(tt, source.substring(i, j), literal, this.line));
  }

  public List<Token> scanTokens() { 
    // using a stack is not the mainstream approach since it can be inefficient
    while (j < source.length()) {
      char c = source.charAt(j);    // read and scan next token 
      switch(c) { 
        case '(': addToken(TokenType.RIGHT_PAREN); break; 
        case ')': addToken(TokenType.LEFT_PAREN); break; 
        case '{': addToken(TokenType.LEFT_BRACE); break; 
        case '}': addToken(TokenType.RIGHT_BRACE); break; 
        case ',': addToken(TokenType.COMMA); break; 
        case '.': addToken(TokenType.DOT); break; 
        case '-': addToken(TokenType.MINUS); break; 
        case '+': addToken(TokenType.PLUS); break; 
        case ';': addToken(TokenType.SEMICOLON); break; 
        case '/': addToken(TokenType.SLASH); break; 
        case '*': addToken(TokenType.STAR); break; 
        case ' ': break;
        case '!': 
          if (j + 1 < source.length() && source.charAt(j+1) == '=') {
            j++;
            addToken(TokenType.BANG_EQUAL); 
          }
          else { 
            addToken(TokenType.BANG); 
          }
          break;
        case '=': 
          if (j + 1 < source.length() && source.charAt(j+1) == '=') {
            j++;
            addToken(TokenType.EQUAL_EQUAL); 
          }
          else { 
            addToken(TokenType.EQUAL); 
          }
          break;
        case '>': 
          if (j + 1 < source.length() && source.charAt(j+1) == '=') {
            j++;
            addToken(TokenType.GREATER_EQUAL); 
          }
          else { 
            addToken(TokenType.GREATER); 
          }
          break;
        case '<': 
          if (j + 1 < source.length() && source.charAt(j+1) == '=') {
            j++;
            addToken(TokenType.LESS_EQUAL); 
          }
          else { 
            addToken(TokenType.LESS); 
          }
          break;
        case '"': 
          while (j + 1 < source.length() && source.charAt(++j) != '"') { 
          } 
          j++;
          addToken(TokenType.STRING, this.source.substring(i, j));
          break;
        case '\n': 
          this.line++; 
          break; 
        default: 
          if (Character.isDigit(c)) {  
            // encode an integer
            while (j + 1 < source.length() && Character.isDigit(source.charAt(j + 1))) {
              j++;
            }
            j++;
            addToken(TokenType.NUMBER, Integer.parseInt(source.substring(i, j))); 
            break;
          }
          else if (Character.isLetter(c)){
            // if it doesn't start with a digit, then it is either a keyword or identifier 
            while (j + 1 < source.length() && source.charAt(j + 1) != ' ') {
              j++;
              // make sure that for identifiers or keywords, it should not have invalid characters
              if (!(Character.isLetterOrDigit(source.charAt(j)) || source.charAt(j) == '|')) {
                System.out.println("bruh");
                System.exit(1); 
              }
            } 
            j++;
            String lexeme = source.substring(i, j); 

            switch (lexeme) {
              case "and": 
                addToken(TokenType.AND); 
                break;
              case "class": 
                addToken(TokenType.CLASS); 
                break;
              case "else": 
                addToken(TokenType.ELSE); 
                break;
              case "false": 
                addToken(TokenType.FALSE); 
                break;
              case "fun": 
                addToken(TokenType.FUN); 
                break;
              case "for": 
                addToken(TokenType.FOR); 
                break;
              case "if": 
                addToken(TokenType.IF); 
                break;
              case "nil": 
                addToken(TokenType.NIL); 
                break;
              case "or": 
                addToken(TokenType.OR); 
                break;
              case "print": 
                addToken(TokenType.PRINT); 
                break;
              case "return": 
                addToken(TokenType.RETURN); 
                break;
              case "super": 
                addToken(TokenType.SUPER); 
                break;
              case "this": 
                addToken(TokenType.THIS); 
                break;
              case "true": 
                addToken(TokenType.TRUE); 
                break;
              case "var": 
                addToken(TokenType.VAR);
                break;
              case "while": 
                addToken(TokenType.WHILE); 
                break;
              default: 
                addToken(TokenType.IDENTIFIER);
            }
          } 

          else {
            System.err.println("Error on line " + this.line); 
          }
      } 
      j++; 
      i = j;
    }

    tokens.add(new Token(TokenType.EOF, "", null, this.line));
    return this.tokens;
  }
}
