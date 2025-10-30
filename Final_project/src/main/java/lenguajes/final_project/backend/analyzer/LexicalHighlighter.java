/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.analyzer;

import java.io.IOException;
import java.io.StringReader;
import lenguajes.final_project.backend.jflex.Lexer;
import lenguajes.final_project.backend.painter.TextPainter;

public class LexicalHighlighter {

    private final TextPainter PAINTER;

    public LexicalHighlighter(TextPainter PAINTER) {
        this.PAINTER = PAINTER;
    }

    public void analizarYColorear(String texto) throws IOException {
        String textoNormalizado = texto.replace("\r\n", "\n");

        
        Lexer lexer = new Lexer(new StringReader(textoNormalizado));
        lexer.yylex();

        PAINTER.pintarTexto(lexer.getTokens());
    }
}
