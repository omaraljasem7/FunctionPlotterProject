// src/Tokenizer.java

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private final String input;
    private int pos = 0;

    public Tokenizer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char c = input.charAt(pos);
            switch (c) {
                case ' ', '\t', '\r', '\n' -> { pos++; continue; }
                case '+' -> { tokens.add(new Token(TokenType.PLUS, "+")); pos++; }
                case '-' -> { tokens.add(new Token(TokenType.MINUS, "-")); pos++; }
                case '*' -> { tokens.add(new Token(TokenType.MUL, "*")); pos++; }
                case '/' -> { tokens.add(new Token(TokenType.DIV, "/")); pos++; }
                case '^' -> { tokens.add(new Token(TokenType.POW, "^")); pos++; }
                case '(' -> { tokens.add(new Token(TokenType.LPAREN, "(")); pos++; }
                case ')' -> { tokens.add(new Token(TokenType.RPAREN, ")")); pos++; }
                case ',' -> { tokens.add(new Token(TokenType.COMMA, ",")); pos++; }
                case '|' -> {
                    tokens.add(new Token(TokenType.PIPE,"|")); pos++;
                }
                default -> {
                    if (Character.isDigit(c) || c=='.') {
                        int start = pos;
                        while (pos < input.length() &&
                                (Character.isDigit(input.charAt(pos)) || input.charAt(pos)=='.')) {
                            pos++;
                        }
                        tokens.add(new Token(TokenType.NUMBER,
                                input.substring(start, pos)));
                    }
                    else if (Character.isLetter(c)) {
                        int start = pos;
                        while (pos < input.length()
                                && (Character.isLetterOrDigit(input.charAt(pos)) || input.charAt(pos)=='_')) {
                            pos++;
                        }
                        tokens.add(new Token(TokenType.NAME,
                                input.substring(start, pos)));
                    }
                    else {
                        throw new RuntimeException("Unknown char: " + c);
                    }
                }
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    public static List<Token> tokenize(String input) {
        return new Tokenizer(input).tokenize();
    }
}
