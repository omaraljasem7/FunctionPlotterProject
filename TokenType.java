// src/TokenType.java

public enum TokenType {
    NUMBER,
    NAME,    // für Variablen und Funktionsnamen
    VARIABLE,
    PLUS, MINUS, MUL, DIV, POW,
    LPAREN, RPAREN, COMMA,
    PIPE,    // für |
    EOF
}
