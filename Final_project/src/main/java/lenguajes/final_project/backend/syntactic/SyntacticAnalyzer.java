/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.syntactic;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import static javax.swing.text.html.HTML.Attribute.ID;
import static javax.swing.text.html.HTML.Tag.DIV;
import lenguajes.final_project.backend.token.Token;
import static lenguajes.final_project.backend.token.TokenType.DECIMAL;
import static lenguajes.final_project.backend.token.TokenType.NUMERO;

/**
 *
 * @author antho
 */
public class SyntacticAnalyzer {

    private final List<Token> tokens;
    private int index = 0;

    private final String[] noTerminales = {"P", "L", "A", "E", "E'", "T", "T'", "F"};
    private final String[] terminales = {"ID", "=", "NUMERO", "DECIMAL", "+", "-", "*", "/", ";", "$"};

    private final String[][] tabla = new String[noTerminales.length][terminales.length];

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        inicializarTabla();
    }

    private void inicializarTabla() {
        // Inicializar todo con ERROR
        for (int i = 0; i < noTerminales.length; i++) {
            Arrays.fill(tabla[i], "ERROR");
        }

        // P → L
        set("P", "ID", "L");
        set("P", "$", "L");

        // L → A L | ε
        set("L", "ID", "A L");
        set("L", "$", "ε");

        // A → ID = E ;
        set("A", "ID", "ID = E ;");

        // E → T E'
        set("E", "ID", "T E'");
        set("E", "NUMERO", "T E'");
        set("E", "DECIMAL", "T E'");

        // E' → + T E' | - T E' | ε
        set("E'", "+", "+ T E'");
        set("E'", "-", "- T E'");
        set("E'", ";", "ε");

        // T → F T'
        set("T", "ID", "F T'");
        set("T", "NUMERO", "F T'");
        set("T", "DECIMAL", "F T'");

        // T' → * F T' | / F T' | ε
        set("T'", "*", "* F T'");
        set("T'", "/", "/ F T'");
        set("T'", "+", "ε");
        set("T'", "-", "ε");
        set("T'", ";", "ε");

        // F → ID | NUMERO | DECIMAL
        set("F", "ID", "ID");
        set("F", "NUMERO", "NUMERO");
        set("F", "DECIMAL", "DECIMAL");
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
            return mapToken(tokens.get(index).getTipo());
        }
        return "$";
    }

    private String mapToken(TypeToken tipo) {
        return switch (tipo) {
            case ID ->
                "ID";
            case IGUAL ->
                "=";
            case NUMERO ->
                "NUMERO";
            case DECIMAL ->
                "DECIMAL";
            case PCOMA ->
                ";";
            case MAS ->
                "+";
            case MENOS ->
                "-";
            case POR ->
                "*";
            case DIV ->
                "/";
            case EOF ->
                "$";
            default ->
                "ERROR";
        };
    }

    public boolean parse() {
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
                } else {
                    System.out.println("Error: se esperaba " + cima + " pero llegó " + tokenActual);
                    return false;
                }
            } else {
                int i = indexOf(noTerminales, cima);
                int j = indexOf(terminales, tokenActual);
                if (i == -1 || j == -1) {
                    System.out.println(" Error: símbolo no reconocido");
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

        System.out.println("\n Análisis sintáctico exitoso");
        return true;
    }

    private boolean esTerminal(String simbolo) {
        return Arrays.asList("ID", "=", "NUMERO", "DECIMAL", "+", "-", "*", "/", ";", "$").contains(simbolo);
    }

}
