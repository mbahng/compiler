package com.mbahng.lox;

class Interpreter {

  private Object evaluate(Expr expr) {
    if (expr instanceof Expr.Grouping) {
      Expr.Grouping grouping = (Expr.Grouping) expr;
      return evaluate(grouping.expression);
    }

    if (expr instanceof Expr.Literal) {
      Expr.Literal literal = (Expr.Literal) expr;
      return literal.value;
    }

    if (expr instanceof Expr.Unary) {
      return evaluateUnary((Expr.Unary) expr);
    }

    if (expr instanceof Expr.Binary) {
      return evaluateBinary((Expr.Binary) expr);
    }

    throw new IllegalArgumentException("Unknown expression type: " + expr.getClass().getName());
  }

  private boolean isTruthy(Object object) {
    if (object == null) return false;
    if (object instanceof Boolean) return (boolean)object;
    return true;
  }

  private Object evaluateUnary(Expr.Unary expr) {
    Object right = evaluate(expr.right); 
    
    switch (expr.operator.type) {
      case MINUS:
        // we typecast it at runtime to a double 
        // this is the core of what makes a language dynamically typed
        return -(double)(right);
      case BANG:
        return !isTruthy(right); 
      default: 
        break;
    }
    // unreachable
    return null;
  }

  private boolean isEqual(Object a, Object b) {
    if (a == null && b == null) return true;
    if (a == null) return false;

    return a.equals(b);
  }

  private Object evaluateBinary(Expr.Binary expr) {
    Object left = evaluate(expr.left);
    Object right = evaluate(expr.right); 

    switch (expr.operator.type) {
      case MINUS:
        return (double)left - (double)right;
      case SLASH:
        return (double)left / (double)right;
      case STAR:
        return (double)left * (double)right;
      case PLUS:
        if (left instanceof Double && right instanceof Double) {
          return (double)left + (double)right;
        } 

        if (left instanceof String && right instanceof String) {
          return (String)left + (String)right;
        }
      case GREATER:
        return (double)left > (double)right;
      case GREATER_EQUAL:
        return (double)left >= (double)right;
      case LESS:
        return (double)left < (double)right;
      case LESS_EQUAL:
        return (double)left <= (double)right;
      case BANG_EQUAL: 
        return !isEqual(left, right);
      case EQUAL_EQUAL: 
        return isEqual(left, right);
      default: 
        break;
    }

    // Unreachable.
    return null;
  }

}
