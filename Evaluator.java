// src/Evaluator.java

import java.util.List;
import java.util.Map;

public class Evaluator {
    public static double eval(Expr expr, Map<String, Double> vars) {
        return switch (expr) {
            case NumberExpr n -> n.value();
            case VariableExpr v -> vars.getOrDefault(v.name(), 0.0);
            case UnaryExpr u -> {
                double val = eval(u.expr(), vars);
                yield u.op() == TokenType.MINUS ? -val : val;
            }
            case BinaryExpr b -> {
                double L = eval(b.left(), vars);
                double R = eval(b.right(), vars);
                yield switch (b.op()) {
                    case PLUS  -> L + R;
                    case MINUS -> L - R;
                    case MUL   -> L * R;
                    case DIV   -> L / R;
                    case POW   -> Math.pow(L, R);
                    default    -> throw new RuntimeException("Unbekannter Operator: " + b.op());
                };
            }
            case FunctionCallExpr f -> {
                // Werte aller Argumente in eine Liste sammeln
                List<Double> a = f.args().stream()
                        .map(arg -> eval(arg, vars))
                        .toList();
                String name = f.name().toLowerCase();
                yield switch (name) {
                    case "sin"    -> Math.sin(a.get(0));
                    case "cos"    -> Math.cos(a.get(0));
                    case "tan"    -> Math.tan(a.get(0));
                    case "abs"    -> Math.abs(a.get(0));
                    case "sqrt"   -> Math.sqrt(a.get(0));
                    case "ln"     -> Math.log(a.get(0));                                // natürlicher Log
                    case "log"    -> a.size() == 2
                            ? Math.log(a.get(1)) / Math.log(a.get(0))         // log base a0 (a1)
                            : Math.log10(a.get(0));                            // default Basis 10
                    case "log10"  -> Math.log10(a.get(0));                             // expliziter Basis-10-Log
                    case "exp"    -> Math.exp(a.get(0));                                // e^x
                    case "recip"  -> 1.0 / a.get(0);                                    // alternative 1/x
                    default       -> throw new RuntimeException("Unbekannte Funktion: " + f.name());
                };
            }
            default -> throw new RuntimeException("Unsupported expr: " + expr);
        };
    }
}

