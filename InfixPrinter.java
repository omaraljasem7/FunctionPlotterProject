import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

// ...

public class InfixPrinter {
    public static String toInfix(Expr e) {
        if (e instanceof NumberExpr n)        return Double.toString(n.value());
        if (e instanceof VariableExpr v)      return v.name();
        if (e instanceof UnaryExpr u)         return "(" + u.op() + toInfix(u.expr()) + ")";
        if (e instanceof BinaryExpr b) {
            String sym = Operator.of(b.op()).symbol();
            return "("
                    + toInfix(b.left())
                    + " " + sym + " "
                    + toInfix(b.right())
                    + ")";
        }
        if (e instanceof FunctionCallExpr f) {
            return f.name() + "(" +
                    f.args().stream().map(InfixPrinter::toInfix).collect(joining(",")) +
                    ")";
        }
        return "";
    }
}
