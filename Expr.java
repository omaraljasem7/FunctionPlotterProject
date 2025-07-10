// src/Expr.java

public sealed interface Expr
        permits BinaryExpr, FunctionCallExpr, NumberExpr, UnaryExpr, VariableExpr
{ }
