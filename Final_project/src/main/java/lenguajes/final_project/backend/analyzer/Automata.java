/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.analyzer;

import java.util.ArrayList;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import lenguajes.final_project.backend.graphics.Transition;
import lenguajes.final_project.backend.painter.TextPainter;
import lenguajes.final_project.backend.position.Position;
import lenguajes.final_project.backend.position.PositionGenerator;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.backend.token.TokenType;

/**
 *
 * @author antho
 */
public class Automata {
    // x = estados y = simbolos
    private final int[][] transiciones = new int[10][8];
    private ArrayList<Transition> grafo = new ArrayList<>();
    private ArrayList<String> terminalText = new ArrayList<>();
    private final javax.swing.JTextPane pane;
    private final StyledDocument doc;
    private int estadoActual = 0;
    private int estadoAnterior = 0;
    private ArrayList<Token> tokens = new ArrayList<>();
    private final String[] palabrasReservadas = {"SI", "si", "ENTONCES", "entonces", "PARA", "para", "ESCRIBIR", "escribir"};
    private final char[] puntuaciones = {'.', ',', ':', ';'};
    private final char[] agrupacion = {'[', ']', '{', '}'};
    private final char[] comentarios = {'"'};
    private final char[] operador = {'+', '-', '*', '/'};

    
    /**
    * Tipos [][x]
    * numeros = 0
    * letras = 1
    * punto = 2
    * operador = 3
    * agrupacion = 4
    * puntuacion = 5
    * comentario = 6
    * espacios = 7
     * @param pane1 El panel de la terminal
     * @param pane2 El panel principal donde se escribe lo necesario
     */
    public Automata(javax.swing.JTextPane pane1, javax.swing.JTextPane pane2) {
        this.pane = pane2;
        this.doc = pane1.getStyledDocument();
        // Estado actual[x][]
        // ESTADO 0
        transiciones[0][0] = 1;
        transiciones[0][1] = 4;
        transiciones[0][2] = -1;
        transiciones[0][3] = 7;
        transiciones[0][4] = 6;
        transiciones[0][5] = 5;
        transiciones[0][6] = 8;
        transiciones[0][7] = -2;
        // ESTADO 1
        transiciones[1][0] = 1;
        transiciones[1][1] = 4;
        transiciones[1][2] = 2;
        transiciones[1][3] = -1;
        transiciones[1][4] = -1;
        transiciones[1][5] = -1;
        transiciones[1][6] = -1;
        transiciones[1][7] = -2;
        // ESTADO 2
        transiciones[2][0] = 3;
        transiciones[2][1] = -1;
        transiciones[2][2] = -1;
        transiciones[2][3] = -1;
        transiciones[2][4] = -1;
        transiciones[2][5] = -1;
        transiciones[2][6] = -1;
        transiciones[2][7] = -2;
        // ESTADO 3
        transiciones[3][0] = 3;
        transiciones[3][1] = -1;
        transiciones[3][2] = -1;
        transiciones[3][3] = -1;
        transiciones[3][4] = -1;
        transiciones[3][5] = -1;
        transiciones[3][6] = -1;
        transiciones[3][7] = -2;
        // ESTADO 4
        transiciones[4][0] = 4;
        transiciones[4][1] = 4;
        transiciones[4][2] = -1;
        transiciones[4][3] = -1;
        transiciones[4][4] = -1;
        transiciones[4][5] = -1;
        transiciones[4][6] = -1;
        transiciones[4][7] = -2;
        // Estado 5
        transiciones[5][0] = -1;
        transiciones[5][1] = -1;
        transiciones[5][2] = -1;
        transiciones[5][3] = -1;
        transiciones[5][4] = -1;
        transiciones[5][5] = -1;
        transiciones[5][6] = -1;
        transiciones[5][7] = -2;
        // Estado 6
        transiciones[6][1] = -1;
        transiciones[6][2] = -1;
        transiciones[6][3] = -1;
        transiciones[6][4] = -1;
        transiciones[6][5] = -1;
        transiciones[6][6] = -1;
        transiciones[6][7] = -2;
        // Estado 7
        transiciones[7][0] = -1;
        transiciones[7][1] = -1;
        transiciones[7][2] = -1;
        transiciones[7][3] = -1;
        transiciones[7][4] = -1;
        transiciones[7][5] = -1;
        transiciones[7][6] = -1;
        transiciones[7][7] = -2;
        // Estado 8
        transiciones[8][0] = 8;
        transiciones[8][1] = 8;
        transiciones[8][2] = 8;
        transiciones[8][3] = 8;
        transiciones[8][4] = 8;
        transiciones[8][5] = 8;
        transiciones[8][6] = 9;
        transiciones[8][7] = 8;
        // Estado 9
        transiciones[9][0] = -1;
        transiciones[9][1] = -1;
        transiciones[9][2] = -1;
        transiciones[9][3] = -1;
        transiciones[9][4] = -1;
        transiciones[9][5] = -1;
        transiciones[9][6] = -1;
        transiciones[9][7] = -1;
    }

    public void analizar(String archivo) {
        tokens = new ArrayList<>();
        terminalText = new ArrayList<>();
        grafo.clear(); //limpia el arreglo del grafo
        
        //Normaliza el texto, pasa los saltos de linea de windows a los otros
        String texto = archivo.replace("\r\n", "\n");

        String lexema = "";
        int indice = 0;
        
        ArrayList<Integer> splits = evaluarSplits(texto);
        PositionGenerator generadorPosicion = new PositionGenerator();

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            int tipo = getTipoCaracter(c);
            indice = i;

            if (estadoActual == 8 || tipo != 7) {
                lexema = lexema + c;
            }

            switch (tipo) {
                case 7 -> {
                    if (estadoActual == 8) {
                        estadoAnterior = estadoActual;
                        estadoActual = transiciones[estadoActual][tipo];
                        grafo.add(new Transition("q" + estadoAnterior, "q" + estadoActual, c));
                    } else {
                        terminalText.add("ES UN ESPACIO HAY QUE REINICIAR EL AUTOMATA\n");
                        reiniciarAutomata(lexema,i - lexema.length(), generadorPosicion.calcularPosicion(i - lexema.length(), splits));
                        lexema = "";
                    }
                }
                case -1 -> {
                    terminalText.add("ERROR EL CARACTER NO ESTA EN EL ALFABETO\n");
                    estadoActual = -1;
                    reiniciarAutomata(lexema, i - lexema.length(), generadorPosicion.calcularPosicion(i - lexema.length(), splits));
                    lexema = "";
                }
                default -> {
                    estadoAnterior = estadoActual;
                    estadoActual = transiciones[estadoActual][tipo];
                    if (estadoActual == -1) {
                        terminalText.add("ERROR: reinicianddo el automata\n");
                        reiniciarAutomata(lexema, i - lexema.length() , generadorPosicion.calcularPosicion(i - lexema.length(), splits));
                        lexema = "";
                    } else {
                        grafo.add(new Transition("q" + estadoAnterior, "q" + estadoActual, c));
                        terminalText.add("- Me movi del estado: " + estadoAnterior + " al estado: " + estadoActual + " con un: " + c + "\n");
                    }
                }
            }
        }
        
        
        reiniciarAutomata(lexema, indice - lexema.length(), generadorPosicion.calcularPosicion(indice - lexema.length(), splits));
        imprimirTokens();
    }

    private ArrayList<Integer> evaluarSplits(String texto) {
        ArrayList<Integer> saltoLinea = new ArrayList<>();
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == '\n') {
                saltoLinea.add(i);
            }
        }
        return saltoLinea;
    }

    public void imprimirTokens() {
        terminalText.add("--- TOKENS ENCONTRADOS ----\n");
        for (Token i : tokens) {
            terminalText.add("Lexema: '" + i.getLexema() + "' || Tipo de Token: "+ i.getTipo() + "\n");
        }
    }

    public void reiniciarAutomata(String lexema, int indice, Position position) {
        terminalText.add("LEXEMA: "+ lexema + "\n");
        if (!lexema.equals("")) {
            Token nuevoToken = new Token(getTipoToken(estadoActual, lexema), lexema, indice, position);
            tokens.add(nuevoToken);
            TextPainter painter = new TextPainter(pane);
            painter.pintarToken(nuevoToken);
            estadoActual = 0;
            estadoAnterior = 0;
        }
    }

    public TokenType getTipoToken(int estadoActual, String lexema) {
        switch (estadoActual) {
            case 1 -> {
                return TokenType.NUMERO;
            }
            case 3 -> {
                return TokenType.DECIMAL;
            }
            case 4 -> {
                return verificarPalabraReservada(lexema);
            }
            case 5 -> {
                return TokenType.PUNTUACION;
            }
            case 6 -> {
                return TokenType.AGRUPACION;
            }
            case 7 -> {
                return TokenType.OPERADOR;
            }
            case 9 -> {
                return TokenType.COMENTARIO_BLOQUE;
            }
            case -1 -> {
                return TokenType.ERROR;
            }
            default -> {
                return TokenType.ERROR;
            }
        }
    }

    public TokenType verificarPalabraReservada(String lexema) {
        for (String tmp : palabrasReservadas) {
            if (lexema.equals(tmp)) {
                return TokenType.PALABRAS_RESERVADAS;
            }
        }
        return TokenType.IDENTIFICADOR;
    }

    // numeros = 0
    // letras = 1
    // punto = 2
    // operador = 3
    // agrupacion = 4
    // puntuacion = 5
    // comentario = 6
    // espacios = 7
    public int getTipoCaracter(char tmp) {
        
        
        int valor = -1;
        if (Character.isDigit(tmp)) {
            return 0;
        }
        if (Character.isLetter(tmp)) {
            return 1;
        }
        if (tmp == '.') {
            return 2;
        }

        for (int i = 0; i < operador.length; i++) {
            if (tmp == operador[i]) {
                return 3;
            }
        }

        for (int i = 0; i < agrupacion.length; i++) {
            if (tmp == agrupacion[i]) {
                return 4;
            }
        }

        for (int i = 0; i < puntuaciones.length; i++) {
            if (tmp == puntuaciones[i]) {
                return 5;
            }
        }

        for (int i = 0; i < comentarios.length; i++) {
            if (tmp == comentarios[i]) {
                return 6;
            }
        }
        if (Character.isWhitespace(tmp)) {
            return 7;
        }
        return valor;
    }
    
    public ArrayList<Transition> getGrafo() {
        return grafo;
    }
    
    public ArrayList<Token> getTOKENS() {
        return tokens;
    }
    
    public void imprimirTerminal() throws BadLocationException {
        for (int i = 0; i < terminalText.size(); i++) {
            doc.insertString(doc.getLength(), terminalText.get(i), null);
        }
    }
}
