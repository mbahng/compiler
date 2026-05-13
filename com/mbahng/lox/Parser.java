package com.mbahng.lox;

import java.util.List;
// import static TokenType.*;

class Parser {
  // expression → equality
  //
  // equality   → comparison ( ( "==" | "!=" ) comparison )*
  // comparison → term       ( ( ">" | ">=" | "<" | "<=" ) term )*
  // term       → factor     ( ( "-" | "+" ) factor )*
  // factor     → unary      ( ( "/" | "*" ) unary )*
  // unary      → ( "!" | "-" ) unary
  //            | primary
  //
  // primary    → NUMBER
  //            | STRING
  //            | "true"
  //            | "false"
  //            | "nil"
  //            | "(" expression ")"
  
  private final List<Token> tokens; 
  private int i = 0;

  Parser(List<Token> tokens) {
    this.tokens = tokens; 
  } 

  Expr parse() {
    // return the Expr tree. 
    return expression();
  }

  private Expr expression() {
    // expression -> equality
    return equality();
  } 

  private Expr equality() {
    // equality   → comparison ( ( "==" | "!=" ) comparison )*
    Expr expr = comparison();

    while (this.tokens.get(i).type == TokenType.BANG_EQUAL || this.tokens.get(i).type == TokenType.EQUAL_EQUAL) {
      Token operator = this.tokens.get(i);
      i++;
      Expr right = comparison(); 
      expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
  }

  private Expr comparison() {
    Expr expr = term();

    while (this.tokens.get(i).type == TokenType.GREATER || this.tokens.get(i).type == TokenType.GREATER_EQUAL || this.tokens.get(i).type == TokenType.LESS || this.tokens.get(i).type == TokenType.LESS_EQUAL) {
      Token operator = this.tokens.get(i); 
      i++;
      Expr right = term();
      expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
  }

  private Expr term() {
    Expr expr = factor();

    while (this.tokens.get(i).type == TokenType.MINUS || this.tokens.get(i).type == TokenType.PLUS) {
      Token operator = this.tokens.get(i); 
      i++;
      Expr right = factor();
      expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
  }

  private Expr factor() {
    Expr expr = unary();

    while (this.tokens.get(i).type == TokenType.SLASH || this.tokens.get(i).type == TokenType.STAR) {
      Token operator = this.tokens.get(i); 
      i++;
      Expr right = unary();
      expr = new Expr.Binary(expr, operator, right);
    }

    return expr;
  }

  private Expr unary() {
    if (this.tokens.get(i).type == TokenType.BANG || this.tokens.get(i).type == TokenType.MINUS) {
      Token operator = this.tokens.get(i);
      i++;
      Expr right = unary();
      return new Expr.Unary(operator, right);
    }

    return primary();
  }

  private Expr primary() {
    if (this.tokens.get(i).type == TokenType.FALSE) { 
      i++;
      return new Expr.Literal(false); 
    }
    if (this.tokens.get(i).type == TokenType.TRUE) { 
      i++;
      return new Expr.Literal(true);
    }
    if (this.tokens.get(i).type == TokenType.NIL) { 
      i++;
      return new Expr.Literal(null);
    }
    if (this.tokens.get(i).type == TokenType.NUMBER || this.tokens.get(i).type == TokenType.STRING) {
      Token token = this.tokens.get(i); 
      i++;
      return new Expr.Literal(token.literal);
    }

    if (this.tokens.get(i).type == TokenType.LEFT_PAREN) {
      i++;
      Expr expr = expression();
      
      if (this.tokens.get(i).type == TokenType.RIGHT_PAREN) {
	i++;
      }
      else {
	throw new RuntimeException("Left parenthesis was not closed.");
      }
      return new Expr.Grouping(expr);
    }
    throw new RuntimeException("Expect expression.");
  }
}
