# Projektabgabe Funktionsplotter
## BItte bewerten Sie den gesamten Projekt und bitte machen Sie alles geltend 

**Hier sind einige Erklärungen zur Projektstruktur**

```
C:.
│   BinaryExpr.java
│   demo.java
│   DotExporter.java
│   Evaluator.java
│   Expr.java
│   FunctionCallExpr.java
│   InfixParser.java
│   InfixPrinter.java
│   lvp-0.5.3.jar
│   Neuer Ordner 3.iml
│   Neuer Ordner.iml
│   NumberExpr.java
│   Operator.java
│   PlotCalculator.java
│   PrefixParser.java
│   SvgPlotter.java
│   Token.java
│   Tokenizer.java
│   TokenType.java
│   UnaryExpr.java
│   UPNPrinter.java
│   VariableExpr.java
```

- Tokenizer soll den Eingabe-String in Tokens zerlegen in Number, Variable, Operator, Paren, Identifier etc.
  - Bemerkung : EOF steht für Ende
  - PLUS für + , MINUS für - , MUL für * , DIV für / , POW für ^,
    LPAREN für (, RPAREN für ).

+ Parser wird hier geteilt in InfixParser für die Konventionelle Notation mit Präsedenz und Klammern, Fehler beim Parsing wird ein Exception geworfen. Nutzt den Shunting -Yard-Algorithmus.
  + -> Damit ist sowohl die Eingabe in Infix-Noation als auch in UPN-Noation möglich für die Funktion (x+2) als Eingabe in Infix-Notation als auch 2 x + , zeichnet der Plotter dieselbe Funktion .
  + Jede Parser gib bei Erfolge einen Expr-AST zurück, bei Fehler 
+ AST-Modell: dafür ist der sealed interface Expr mit seinen records implementierungen wie BinaryExpr, FunctionCallExpr, NumberExpr, UnaryExpr, VariableExpr verantwolich.
+ Infix Printer erstellt aus dem AST einen korrekt geklammerten Infix-String 
+ UPN-Printer: gibt die Postfix-Notation aus 
+ DotExporter.toDot(Expr) -> erzegut die Grraphische Dot Repräsentation 
+ Evaluator macht eine Traversierung(walk) über den AST und wertet rekursiv die Evaluator.eval() und unterstützt  dabei die Funktionen wie sin, cos, log ,pow(^) ,tan abs, log etc. 
+ Der PlotCalculator liefert eine Liste von Punkten der Form (x,f(x))-Paaren zurück 
+ SvgPlotter erzeugt das SVG und wird mitgebunden and das LVP . in einem Intervall zwischen -10 und +10 horizontal und vertikal .

+ Erste Funktion Wird by default mit Rot geplotet , zweite Funktion mit Grün , 3. Funktion mit Blau , 4. Funktion mit Orange und 5. Funktion in Lila .

#### Für jede Funktionseingabe werden die Tokens die Infix-Notation und die UPN-Notation angezeigt.

##### zur Eingabe der Funktionen insbesondere der Logarithmus funktion sollte es so sein log(2,x) oder log(10,x) und ln(x) sein, log(x) alleine wird es nicht funktionieren.

###### zur Eingabe der Betragsfunktion, es kann sowohl |x| oder mit abs(x) gehen 

###### zur Eingabe der Wurzelfunktion mit sqrt(x)

##### Bitte eine Eingabe und dann send und nicht alle gleichzeitig
##### Eingabe ist auch möglich mit einem unären Minus, mathematische Konstanten wie $e$=2.71 oder $\Pi$ =3.14, auch verwendung von einem Ausdruck ohne eine Variable ist möglich.

##### Die Eingabe von bis zu 5 Funktionen die farblich voneinander unterschliedlich sind, ist möglich.
##### Bei einem Parser-Fehler von einer Funktion wird keine Funktion geplottet
#### Bei einer Divsion durch 0 , wird auch auch keine funktion geplottet wie z.B. 1/0 aber der AST Baum wird angezeigt 

falls es aus irgendeinem Grund nicht ganz funktioniert bei einer Eingabe in dem Input Feld nicht funktioniert, dann bitte versuchen, die Eingabe bitte vom Code direkt ändert,falls es nciht funktioniert

***Paar Illustrationen von dem Funktionsplotter***

1.
![Bild1](./Screenshot%202025-07-10%20204208.png)
2.
![Bild2](./Screenshot%202025-07-10%20204251.png)
3.
![Bild3](./Screenshot%202025-07-10%20204320.png)
![Bild4](./Screenshot%202025-07-10%20204337.png)
![Bild5](./Screenshot%202025-07-10%20204400.png)

#### Sie können das Projekt unter Mein GitHub sehen:
[GitHub Repository](https://github.com/omaraljasem7/FunctionPlotterProject.git)

- siehe Live View Programming Repository Als Refernz :
[LiveViewProgramming](https://github.com/denkspuren/LiveViewProgramming/tree/main)

- zum Starten der Server tippe in der cmd :
``` java 
java -jar lvp-0.5.4.jar --log --watch=demo.java
```