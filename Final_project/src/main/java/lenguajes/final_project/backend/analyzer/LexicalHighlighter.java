/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.analyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import lenguajes.final_project.actions.Actions;
import lenguajes.final_project.backend.jflex.Lexer;
import lenguajes.final_project.backend.painter.TextPainter;
import lenguajes.final_project.backend.syntactic.SyntacticAnalyzer;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.exceptions.OperacionException;

public class LexicalHighlighter {

    private final TextPainter PAINTER;
    private ArrayList<Token> tokens;

    public LexicalHighlighter(TextPainter PAINTER) {
        this.PAINTER = PAINTER;
    }

    public void analizarYColorear(String texto) throws IOException {
        String textoNormalizado = texto.replace("\r\n", "\n");

        
        Lexer lexer = new Lexer(new StringReader(textoNormalizado));
        lexer.yylex();
        
        this.tokens = lexer.getTokens();

        PAINTER.pintarTexto(tokens);
    }
    
    public void analizarSintaxis(javax.swing.JTextPane TERMINAL) throws OperacionException {
        if (!tokens.isEmpty()) {
            SyntacticAnalyzer parser = new SyntacticAnalyzer(tokens);
            parser.parseAll();
            Actions acciones = new Actions(TERMINAL);
            acciones.evaluar(parser.getSentenceTokens());
        }
    }
}
