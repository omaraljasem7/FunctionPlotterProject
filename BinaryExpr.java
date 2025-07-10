// src/BinaryExpr.java

public record BinaryExpr(Expr left, TokenType op, Expr right) implements Expr { }
