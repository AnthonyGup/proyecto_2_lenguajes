/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.analyzer;

import javax.swing.JTextPane;
import java.io.StringReader;
import java.util.ArrayList;
//import lenguajes.final_project.backend.jflex.Lexer;
import lenguajes.final_project.backend.painter.TextPainter;
import lenguajes.final_project.backend.token.Token;

public class LexicalHighlighter {

    private JTextPane pane;

    public LexicalHighlighter(JTextPane pane) {
        this.pane = pane;
    }

    public void analizarYColorear() {
        try {
            // 1. Obtener texto del editor
            String texto = pane.getText();

            // 2. Crear Lexer usando ese texto
            //Lexer lexer = new Lexer(new StringReader(texto));

            // 3. Limpiar estilos actuales
            //pane.getStyledDocument().remove(0, texto.length());

            //lexer.yylex();
            
            // 4. Analizar token por token
            TextPainter painter = new TextPainter(pane);
            //painter.pintarTexto(lexer.getTokens());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}