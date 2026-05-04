package com.mbahng.lox;

class AstPrinter {
  String print(Expr expr) {
    return printExpr(expr);
  }

  private String printExpr(Expr expr) {
    if (expr instanceof Expr.Binary) {
      Expr.Binary binary = (Expr.Binary) expr;
      return parenthesize(binary.operator.lexeme, binary.left, binary.right);
    }

    if (expr instanceof Expr.Grouping) {
      Expr.Grouping grouping = (Expr.Grouping) expr;
      return parenthesize("group", grouping.expression);
    }

    if (expr instanceof Expr.Literal) {
      Expr.Literal literal = (Expr.Literal) expr;
      if (literal.value == null) return "nil";
      return literal.value.toString();
    }

    if (expr instanceof Expr.Unary) {
      Expr.Unary unary = (Expr.Unary) expr;
      return parenthesize(unary.operator.lexeme, unary.right);
    }

    throw new IllegalArgumentException("Unknown expression type: " + expr.getClass().getName());
  }

  private String parenthesize(String name, Expr... exprs) {
    StringBuilder builder = new StringBuilder();

    builder.append("(").append(name);
    for (Expr expr : exprs) {
      builder.append(" ");
      builder.append(printExpr(expr));
    }
    builder.append(")");

    return builder.toString();
  }

  public static void main(String[] args) {
    Expr expression = new Expr.Binary(
        new Expr.Unary(
            new Token(TokenType.MINUS, "-", null, 1),
            new Expr.Literal(123)),
        new Token(TokenType.STAR, "*", null, 1),
        new Expr.Grouping(
            new Expr.Literal(45.67)));

    System.out.println(new AstPrinter().print(expression)); // should print (* (-123) (group 45.67))
  }
}
