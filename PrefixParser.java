import java.util.List;
import java.util.Stack;


// src/PrefixParser.java (idealerweise „PostfixParser.java“)
public class PrefixParser {
    public static Expr parse(List<Token> tokens) {
        Stack<Expr> stack = new Stack<>();
        for (Token tk: tokens) {
            switch (tk.type()) {
                case NUMBER -> stack.push(new NumberExpr(Double.parseDouble(tk.lexeme())));
                case VARIABLE-> stack.push(new VariableExpr(tk.lexeme()));
                case PLUS, MINUS, MUL, DIV, POW -> {
                    Expr r = stack.pop(), l = stack.pop();
                    stack.push(new BinaryExpr(l, tk.type(), r));
                }
                case EOF -> {}
                default -> throw new RuntimeException("UPN-Parse-Error");
            }
        }
        return stack.pop();
    }
}
