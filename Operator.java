// src/Operator.java

import java.util.Map;
import java.util.HashMap;

public enum Operator {
    ADD(TokenType.PLUS,  1, "+"),
    SUB(TokenType.MINUS, 1, "-"),
    MUL(TokenType.MUL,   2, "*"),
    DIV(TokenType.DIV,   2, "/"),
    POW(TokenType.POW,   3, "^");

    public final TokenType token;
    public final int       precedence;
    private final String   symbol;

    private Operator(TokenType t, int prec, String sym) {
        this.token      = t;
        this.precedence = prec;
        this.symbol     = sym;
    }

    // vorhandene Map bleibt unverändert
    private static final Map<TokenType,Operator> MAP = new HashMap<>();
    static {
        for (var o : values()) MAP.put(o.token, o);
    }

    public static Operator of(TokenType t) {
        return MAP.get(t);
    }

    /** Gibt das Zeichen zurück, z.B. "+" oder "^". */
    public String symbol() {
        return symbol;
    }
}