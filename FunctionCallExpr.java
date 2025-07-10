import java.util.List;

// src/FunctionCallExpr.java
public record FunctionCallExpr(String name, List<Expr> args) implements Expr { }
