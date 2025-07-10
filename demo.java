import lvp.Clerk;
import lvp.skills.Interaction;
import lvp.skills.Text;
import lvp.views.Dot;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class demo {
    // == Marker für Interaction.input ==
    static String expression1 = "5^x+1"; // Input Var1
    static String expression2 = "log(10,x)"; // Input Var2
    static String expression3 = "x^2"; // Input Var3
    static String expression4 = "1"; // Input Var4
    static String expression5 = "-(x+2)"; // Input Var5

    public static void main(String[] args) throws IOException {
        Clerk.clear();

        // ─── 1) Beschreibung + fünf Input-Felder
        Clerk.markdown(Text.fillOut("""
            ### Input
            Hier kannst du fünf Funktionen eingeben:
            ```java
            ${0}
            ${1}
            ${2}
            ${3}
            ${4}
            ```
            Nach Eingabe und Klick auf **Send** werden die markierten Zeilen ersetzt.
            """,
                Text.codeBlock("demo.java", "// Input Var1"),
                Text.codeBlock("demo.java", "// Input Var2"),
                Text.codeBlock("demo.java", "// Input Var3"),
                Text.codeBlock("demo.java", "// Input Var4"),
                Text.codeBlock("demo.java", "// Input Var5")
        ));

        // Input-Felder
        Clerk.write(Interaction.input("demo.java", "// Input Var1", "static String expression1 = \"$\";", expression1));
        Clerk.write(Interaction.input("demo.java", "// Input Var2", "static String expression2 = \"$\";", expression2));
        Clerk.write(Interaction.input("demo.java", "// Input Var3", "static String expression3 = \"$\";", expression3));
        Clerk.write(Interaction.input("demo.java", "// Input Var4", "static String expression4 = \"$\";", expression4));
        Clerk.write(Interaction.input("demo.java", "// Input Var5", "static String expression5 = \"$\";", expression5));

        // 2) Anzeige der aktuellen Funktionen
        Clerk.markdown(
                "**Funktion 1:** `" + expression1 + "`  \n" +
                        "**Funktion 2:** `" + expression2 + "`  \n" +
                        "**Funktion 3:** `" + expression3 + "`  \n" +
                        "**Funktion 4:** `" + expression4 + "`  \n" +
                        "**Funktion 5:** `" + expression5 + "`");

        // Eingabe-Validierung
        if (expression1.isBlank() || expression2.isBlank() || expression3.isBlank() ||
                expression4.isBlank() || expression5.isBlank()) {
            Clerk.markdown("Bitte alle Ausdrücke eingeben.");
            return;
        }

        // ─── 3) Parsen aller Ausdrücke ─────────────────────────────────────────────
        Expr ast1, ast2, ast3, ast4, ast5;
        try {
            ast1 = InfixParser.parse(Tokenizer.tokenize(expression1));
            ast2 = InfixParser.parse(Tokenizer.tokenize(expression2));
            ast3 = InfixParser.parse(Tokenizer.tokenize(expression3));
            ast4 = InfixParser.parse(Tokenizer.tokenize(expression4));
            ast5 = InfixParser.parse(Tokenizer.tokenize(expression5));
        } catch (Exception ex) {
            Clerk.markdown("Parser-Fehler: " + ex.getMessage());
            return;
        }

        // ─── 4) Tokens und Notationen ausgeben
        List<Token> tokens1 = Tokenizer.tokenize(expression1);
        List<Token> tokens2 = Tokenizer.tokenize(expression2);
        List<Token> tokens3 = Tokenizer.tokenize(expression3);
        List<Token> tokens4 = Tokenizer.tokenize(expression4);
        List<Token> tokens5 = Tokenizer.tokenize(expression5);

        Clerk.markdown("**Tokens 1:** " + tokens1.stream()
                .map(t -> t.type() + ":`" + t.lexeme() + "`")
                .collect(Collectors.joining(" · ")));
        Clerk.markdown("**Tokens 2:** " + tokens2.stream()
                .map(t -> t.type() + ":`" + t.lexeme() + "`")
                .collect(Collectors.joining(" · ")));
        Clerk.markdown("**Tokens 3:** " + tokens3.stream()
                .map(t -> t.type() + ":`" + t.lexeme() + "`")
                .collect(Collectors.joining(" · ")));
        Clerk.markdown("**Tokens 4:** " + tokens4.stream()
                .map(t -> t.type() + ":`" + t.lexeme() + "`")
                .collect(Collectors.joining(" · ")));
        Clerk.markdown("**Tokens 5:** " + tokens5.stream()
                .map(t -> t.type() + ":`" + t.lexeme() + "`")
                .collect(Collectors.joining(" · ")));

        Clerk.markdown("- **Infix 1:** `" + InfixPrinter.toInfix(ast1) + "`  |  **UPN 1:** `" + UPNPrinter.toUpn(ast1) + "`");
        Clerk.markdown("- **Infix 2:** `" + InfixPrinter.toInfix(ast2) + "`  |  **UPN 2:** `" + UPNPrinter.toUpn(ast2) + "`");
        Clerk.markdown("- **Infix 3:** `" + InfixPrinter.toInfix(ast3) + "`  |  **UPN 3:** `" + UPNPrinter.toUpn(ast3) + "`");
        Clerk.markdown("- **Infix 4:** `" + InfixPrinter.toInfix(ast4) + "`  |  **UPN 4:** `" + UPNPrinter.toUpn(ast4) + "`");
        Clerk.markdown("- **Infix 5:** `" + InfixPrinter.toInfix(ast5) + "`  |  **UPN 5:** `" + UPNPrinter.toUpn(ast5) + "`");

        // ─── 5) AST-Bäume anzeigen
        String dot1 = new DotExporter().toDot(ast1);
        String dot2 = new DotExporter().toDot(ast2);
        String dot3 = new DotExporter().toDot(ast3);
        String dot4 = new DotExporter().toDot(ast4);
        String dot5 = new DotExporter().toDot(ast5);

        Dot view1 = new Dot(); view1.draw(dot1);
        Dot view2 = new Dot(); view2.draw(dot2);
        Dot view3 = new Dot(); view3.draw(dot3);
        Dot view4 = new Dot(); view4.draw(dot4);
        Dot view5 = new Dot(); view5.draw(dot5);

        // ─── 6) Plotten aller Funktionen im selben Koordinatensystem
        var pts1 = PlotCalculator.compute(ast1, -10, 10, 400);
        var pts2 = PlotCalculator.compute(ast2, -10, 10, 400);
        var pts3 = PlotCalculator.compute(ast3, -10, 10, 400);
        var pts4 = PlotCalculator.compute(ast4, -10, 10, 400);
        var pts5 = PlotCalculator.compute(ast5, -10, 10, 400);

        var sp = new SvgPlotter(1000, 1000, -10, 10, -10, 10, 1.0, 1.0);
        sp.plot(pts1, "ff0000"); // Rot
        sp.plot(pts2, "00ff00"); // Grün
        sp.plot(pts3, "0000ff"); // Blau
        sp.plot(pts4, "ff7f00"); // Orange
        sp.plot(pts5, "8b00ff"); // Lila

        Clerk.markdown(sp.buildSvg());
    }
}
