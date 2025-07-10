// src/InfixParser.java

import java.util.List;
import java.util.Stack;

// src/InfixParser.java
import java.util.*;
public class InfixParser {
    public static Expr parse(List<Token> tokens) {
        Stack<Token> ops = new Stack<>();
        Stack<Expr>  out = new Stack<>();

        for (int i = 0; i < tokens.size(); i++) {
            Token tk = tokens.get(i);
            switch (tk.type()) {
                case NUMBER -> {
                    out.push(new NumberExpr(Double.parseDouble(tk.lexeme())));
                }

                // InfixParser.java, innerhalb parse(...)
                case NAME -> {
                    String name = tk.lexeme();
                    if (i+1<tokens.size() && tokens.get(i+1).type()==TokenType.LPAREN) {
                        // Funktionserkennung
                        i++; // skip NAME
                        i++; // skip LPAREN
                        List<Expr> args = new ArrayList<>();
                        // solange kein RPAREN, Argumente sammeln
                        while (i<tokens.size() && tokens.get(i).type()!=TokenType.RPAREN) {
                            // parse Sub-Expression bis COMMA oder RPAREN
                            int start = i;
                            int depth = 0;
                            while (i<tokens.size()) {
                                Token t2 = tokens.get(i);
                                if (t2.type()==TokenType.LPAREN) depth++;
                                else if (t2.type()==TokenType.RPAREN && depth==0) break;
                                else if (t2.type()==TokenType.COMMA && depth==0) break;
                                i++;
                            }
                            // Sublist start ... i → tokenize & parse recursively
                            List<Token> sub = tokens.subList(start, i);
                            Expr arg = parse(new ArrayList<>(sub));
                            args.add(arg);
                            if (i<tokens.size() && tokens.get(i).type()==TokenType.COMMA) i++;
                        }
                        // jetzt RPAREN überspringen
                        // tokens.get(i).type()==RPAREN
                        // keine i++ hier, weil die FOR-Schleife i++ macht
                        out.push(new FunctionCallExpr(name, args));
                    } else {
                        // Name ohne Klammer: Variable oder Konstante
                        if (name.equalsIgnoreCase("pi")) out.push(new NumberExpr(Math.PI));
                        else if (name.equalsIgnoreCase("e")) out.push(new NumberExpr(Math.E));
                        else out.push(new VariableExpr(name));
                    }
                }


                case PLUS, MINUS, MUL, DIV, POW -> {
                    Operator o1 = Operator.of(tk.type());
                    while (!ops.isEmpty() && ops.peek().type() != TokenType.LPAREN) {
                        Token top = ops.peek();
                        Operator o2 = Operator.of(top.type());
                        if (o2 != null && o2.precedence >= o1.precedence) {
                            popOp(ops, out);
                        } else break;
                    }
                    ops.push(tk);
                }
                case LPAREN -> {
                    ops.push(tk);
                }
                case RPAREN -> {
                    // bis zur passenden "(" abarbeiten …
                    while (!ops.isEmpty() && ops.peek().type() != TokenType.LPAREN) {
                        popOp(ops, out);
                    }
                    ops.pop();  // "(" wegwerfen

                    // HIER vorher: ops.peek().type() == TokenType.IDENTIFIER
                    // MUSS jetzt lauten:
                    if (!ops.isEmpty() && ops.peek().type() == TokenType.NAME) {
                        Token fn = ops.pop();
                        Expr arg = out.pop();
                        out.push(new FunctionCallExpr(fn.lexeme(), List.of(arg)));
                    }
                }
                case PIPE -> {
                    // Absolutbetrag: "|" öffnet und schließt
                    if (ops.isEmpty() || ops.peek().type() != TokenType.PIPE) {
                        // öffnend
                        ops.push(tk);
                    } else {
                        // schließend
                        ops.pop(); // pipe entfernen
                        Expr inner = out.pop();
                        out.push(new FunctionCallExpr("abs", List.of(inner)));
                    }
                }
                case COMMA -> {
                    // in diesem einfachen Parser: wir unterstützen nur
                    // Ein-Argument-Funktionen, also überschreiten wir COMMA
                    // nicht. Für Log-Basis-2 braucht man einen erweiterten Parser.
                    throw new RuntimeException("Mehrfach-Argumente noch nicht unterstützt");
                }
                case EOF -> { /* ignorieren */ }
            }
        }
        while (!ops.isEmpty()) popOp(ops, out);
        // **Neu:**
        if (out.isEmpty()) {
            throw new RuntimeException("Parse-Error: kein gültiger Ausdruck (leer).");
        }
        return out.pop();
    }

    private static void popOp(Stack<Token> ops, Stack<Expr> out) {
        Token op = ops.pop();
        if (op.type() == TokenType.MINUS && out.size()==1) {
            // unärer Minus
            Expr e = out.pop();
            out.push(new UnaryExpr(TokenType.MINUS, e));
        } else {
            Expr r = out.pop(), l = out.pop();
            out.push(new BinaryExpr(l, op.type(), r));
        }
    }
}
