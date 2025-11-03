package lenguajes.final_project.backend.syntactic;

import java.util.*;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.backend.token.TokenType;
import static lenguajes.final_project.backend.token.TokenType.*;

/**
 * Analizador sintáctico predictivo LL(1) Solo valida la estructura (sin
 * acciones semánticas) Basado en la gramática del proyecto.
 */
public class SyntacticAnalyzer {

    private final List<Token> tokens;
    private List<List<Token>> sentenceTokens = new ArrayList<>();
    private int index = 0;

    // No terminales
    private final String[] noTerminales = {
        "P", "L", "A", "V", "W", "Type" /*(S)*/, "Arg"/*(O)*/, "E", "E'", "T", "T'", "F"
    };

    // Terminales (tokens esperados)
    private final String[] terminales = {
        "DEFINIR", "COMO", "ESCRIBIR", "ID", "=", "CADENA",
        "NUMERO", "DECIMAL", "entero", "numero", "cadena",
        "(", ")", "+", "-", "*", "/", ";", "$"
    };

    // Tabla LL(1)
    private final String[][] tabla = new String[noTerminales.length][terminales.length];

    public SyntacticAnalyzer(List<Token> tokens) {
        this.tokens = tokens;
        inicializarTabla();
    }

    private void inicializarTabla() {
        for (int i = 0; i < noTerminales.length; i++) {
            Arrays.fill(tabla[i], "ERROR");
        }

        // P -> L
        set("P", "DEFINIR", "L");
        set("P", "ESCRIBIR", "L");
        set("P", "ID", "L");
        set("P", "$", "L");

        // L -> V L | A L | W L | ε
        set("L", "DEFINIR", "V L");
        set("L", "ID", "A L");
        set("L", "ESCRIBIR", "W L");
        set("L", "$", "ε");

        // V -> DEFINIR ID COMO Type ;
        set("V", "DEFINIR", "DEFINIR ID COMO Type ;");

        // A -> ID = E ;
        set("A", "ID", "ID = E ;");

        // W -> ESCRIBIR ( Arg ) ;
        set("W", "ESCRIBIR", "ESCRIBIR ( Arg ) ;");

        // Type -> entero | numero | cadena
        set("Type", "entero", "entero");
        set("Type", "numero", "numero");
        set("Type", "cadena", "cadena");

        // Arg -> CADENA | E
        set("Arg", "CADENA", "CADENA");
        set("Arg", "ID", "E");
        set("Arg", "NUMERO", "E");
        set("Arg", "DECIMAL", "E");
        set("Arg", "(", "E");

        // E -> T E'
        set("E", "ID", "T E'");
        set("E", "NUMERO", "T E'");
        set("E", "numero", "T E'"); //provisional 
        set("E", "DECIMAL", "T E'");
        set("E", "CADENA", "T E'");
        set("E", "(", "T E'");

        // E' -> + T E' | - T E' | ε
        set("E'", "+", "+ T E'");
        set("E'", "-", "- T E'");
        set("E'", ";", "ε");
        set("E'", ")", "ε");

        // T -> F T'
        set("T", "ID", "F T'");
        set("T", "NUMERO", "F T'");
        set("T", "numero", "F T'"); //provisional
        set("T", "DECIMAL", "F T'");
        set("T", "CADENA", "F T'");
        set("T", "(", "F T'");

        // T' -> * F T' | / F T' | ε
        set("T'", "*", "* F T'");
        set("T'", "/", "/ F T'");
        set("T'", "+", "ε");
        set("T'", "-", "ε");
        set("T'", ";", "ε");
        set("T'", ")", "ε");

        // F -> ID | NUMERO | DECIMAL | CADENA | ( E )
        set("F", "ID", "ID");
        set("F", "NUMERO", "NUMERO");
        set("F", "numero", "numero");
        set("F", "DECIMAL", "DECIMAL");
        set("F", "CADENA", "CADENA");
        set("F", "(", "( E )");
    }

    private void set(String noTerminal, String terminal, String produccion) {
        int i = indexOf(noTerminales, noTerminal);
        int j = indexOf(terminales, terminal);
        if (i != -1 && j != -1) {
            tabla[i][j] = produccion;
        }
    }

    private int indexOf(String[] arr, String val) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(val)) {
                return i;
            }
        }
        return -1;
    }

    private String lookahead() {
        if (index < tokens.size()) {
            return mapToken(tokens.get(index).getTipo2());
        }
        return "$";
    }

    private String mapToken(TokenType tipo) {
        return switch (tipo) {
            case IDENTIFICADOR ->
                "ID";
            case IGUAL ->
                "=";
            case NUMERO ->
                "NUMERO";
            case DECIMAL ->
                "DECIMAL";
            case CADENA ->
                "CADENA";
            case MAS ->
                "+";
            case MENOS ->
                "-";
            case POR ->
                "*";
            case DIV ->
                "/";
            case PCOMA ->
                ";";
            case LPAREN ->
                "(";
            case RPAREN ->
                ")";
            case DEFINIR ->
                "DEFINIR";
            case COMO ->
                "COMO";
            case ESCRIBIR ->
                "ESCRIBIR";
            case ENTERO ->
                "entero";
            case NUM ->
                "numero";
            case CAD ->
                "cadena";
            case EOF ->
                "$";
            default ->
                "ERROR";
        };
    }

    public boolean parseStatement() {
        Stack<String> pila = new Stack<>();
        pila.push("$");
        pila.push("P");

        System.out.printf("%-45s %-20s %-30s%n", "PILA", "ENTRADA", "ACCIÓN");
        System.out.println("--------------------------------------------------------------------------------------");

        while (!pila.isEmpty()) {
            String cima = pila.peek();
            String tokenActual = lookahead();

            System.out.printf("%-45s %-20s ", pila.toString(), tokenActual);

            if (esTerminal(cima)) {
                if (cima.equals(tokenActual)) {
                    pila.pop();
                    index++;
                    System.out.println("match(" + tokenActual + ")");

                    // Si llegamos a un punto y coma, termina una sentencia
                    if (tokenActual.equals(";")) {
                        // vaciar pila hasta "$"
                        while (!pila.peek().equals("$")) {
                            pila.pop();
                        }
                        break;
                    }
                } else {
                    System.out.println("Error: se esperaba " + cima + " pero llegó " + tokenActual);
                    return false;
                }
            } else {
                int i = indexOf(noTerminales, cima);
                int j = indexOf(terminales, tokenActual);

                if (i == -1 || j == -1) {
                    System.out.println("Error: símbolo no reconocido");
                    return false;
                }

                String produccion = tabla[i][j];
                if (produccion.equals("ERROR")) {
                    System.out.println("Error: no hay regla para [" + cima + ", " + tokenActual + "]");
                    return false;
                }

                pila.pop();
                if (!produccion.equals("ε")) {
                    String[] simbolos = produccion.trim().split("\\s+");
                    for (int k = simbolos.length - 1; k >= 0; k--) {
                        pila.push(simbolos[k]);
                    }
                }
                System.out.println(cima + " -> " + produccion);
            }
        }

        System.out.println("\nAnálisis sintáctico exitoso");
        return true;
    }

    /*
    public void parseAll() {
        int sentencia = 1;

        while (index < tokens.size()) {
            System.out.println("\nAnalizando sentencia #" + sentencia);
            if (parseStatement()) {
                System.out.println("Sentencia #" + sentencia + " correcta\n");
            } else {
                System.out.println("Sentencia #" + sentencia + " con errores\n");
            }
            sentencia++;
        }
    }
     */
    public void parseAll() {
        int numSentencia = 1;
        this.sentenceTokens = new ArrayList<>();

        while (index < tokens.size()) {
            System.out.println("\nAnalizando sentencia #" + numSentencia);

            int start = index; // inicio de los tokens de la sentencia
            boolean correcta = parseStatement();

            // si fue correcta, guardar los tokens usados
            if (correcta) {
                List<Token> subTokens = new ArrayList<>();
                for (int i = start; i < index && i < tokens.size(); i++) {
                    subTokens.add(tokens.get(i));
                }
                sentenceTokens.add(subTokens);

                System.out.println("Sentencia #" + numSentencia + " correcta, tokens: " + subTokens.size());
            } else {
                System.out.println("Sentencia #" + numSentencia + " incorrecta. No se agregan tokens.");
                break; // opcional: detener análisis al primer error
            }

            numSentencia++;
        }

        System.out.println("\nTotal de sentencias válidas: " + sentenceTokens.size());
    }

    public List<List<Token>> getSentenceTokens() {
        return sentenceTokens;
    }

    private boolean esTerminal(String simbolo) {
        return Arrays.asList(terminales).contains(simbolo);
    }
}
