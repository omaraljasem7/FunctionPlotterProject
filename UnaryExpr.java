// src/UnaryExpr.java

public record UnaryExpr(TokenType op, Expr expr) implements Expr { }
