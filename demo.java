import lvp.Clerk;
import lvp.skills.Interaction;
import lvp.skills.Text;
import lvp.views.Dot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class demo {
    // == Marker für Interaction.input ==
    static String expression1 = "x"; // Input Var1
    static String expression2 = "x^2"; // Input Var2
    static String expression3 = "log(10,x)"; // Input Var3
    static String expression4 = "e^x"; // Input Var4
    static String expression5 = "1/x"; // Input Var5

    public static void main(String[] args) throws IOException {
        Clerk.clear();

        // --- Input-Felder direkt anzeigen ---
        Clerk.write(Interaction.input("demo.java", "// Input Var1", "static String expression1 = \"$\";", expression1));
        Clerk.write(Interaction.input("demo.java", "// Input Var2", "static String expression2 = \"$\";", expression2));
        Clerk.write(Interaction.input("demo.java", "// Input Var3", "static String expression3 = \"$\";", expression3));
        Clerk.write(Interaction.input("demo.java", "// Input Var4", "static String expression4 = \"$\";", expression4));
        Clerk.write(Interaction.input("demo.java", "// Input Var5", "static String expression5 = \"$\";", expression5));

        // Anzeige der aktuellen Funktionen
        Clerk.markdown(
                "**Funktion 1:** `" + expression1 + "`  \n" +
                        "**Funktion 2:** `" + expression2 + "`  \n" +
                        "**Funktion 3:** `" + expression3 + "`  \n" +
                        "**Funktion 4:** `" + expression4 + "`  \n" +
                        "**Funktion 5:** `" + expression5 + "`");

        // Eingabe-Validierung: mindestens eine Funktion erforderlich
        if (expression1.isBlank() && expression2.isBlank() && expression3.isBlank() &&
                expression4.isBlank() && expression5.isBlank()) {
            Clerk.markdown("Bitte mindestens eine Funktion eingeben.");
            return;
        }

        // --- Dynamisches Parsen aller nicht-leeren Ausdrücke ---
        List<String> expressions = List.of(
                expression1, expression2, expression3, expression4, expression5
        );
        List<String> nonEmpty = expressions.stream()
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());

        List<Expr> asts = new ArrayList<>(nonEmpty.size());
        try {
            for (String expr : nonEmpty) {
                asts.add(InfixParser.parse(Tokenizer.tokenize(expr)));
            }
        } catch (Exception ex) {
            Clerk.markdown("Parser-Fehler: " + ex.getMessage());
            return;
        }

        // --- Tokens, Infix- und UPN-Notation dynamisch ausgeben ---
        for (int i = 0; i < nonEmpty.size(); i++) {
            String expr = nonEmpty.get(i);
            Expr ast = asts.get(i);
            List<Token> tokens = Tokenizer.tokenize(expr);

            Clerk.markdown("**Tokens " + (i+1) + ":** " +
                    tokens.stream()
                            .map(t -> t.type() + ":`" + t.lexeme() + "`")
                            .collect(Collectors.joining(" · "))
            );

            Clerk.markdown("- **Infix " + (i+1) + ":** `" + InfixPrinter.toInfix(ast) + "`  |  " +
                    "**UPN " + (i+1) + ":** `" + UPNPrinter.toUpn(ast) + "`");
        }

        // --- AST-Bäume anzeigen ---
        for (Expr ast : asts) {
            String dot = new DotExporter().toDot(ast);
            new Dot().draw(dot);
        }

        // --- Plotten aller Funktionen im selben Koordinatensystem ---
        String[] colors = {"ff0000", "00ff00", "0000ff", "ff7f00", "8b00ff"};
        SvgPlotter sp = new SvgPlotter(
                1000, 1000,  // Canvas-Größe
                -10, 10,     // x-Bereich
                -10, 10,     // y-Bereich
                1.0, 1.0     // Skalierung
        );

        for (int i = 0; i < asts.size(); i++) {
            var pts = PlotCalculator.compute(asts.get(i), -10, 10, 400);
            sp.plot(pts, colors[i]);
        }

        Clerk.markdown(sp.buildSvg());
    }
}
