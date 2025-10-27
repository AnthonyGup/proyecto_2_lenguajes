/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.painter;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import lenguajes.final_project.backend.token.Token;

/**
 *
 * @author antho
 */
public class TextPainter {
    private final javax.swing.JTextPane TEXT_PANE;
    private final StyledDocument doc;

    /**
     * metodo que crea todos los etstilos para poner en el panel de texto
     *
     * @param TEXT_PANE
     */
    public TextPainter(javax.swing.JTextPane TEXT_PANE) {
        this.TEXT_PANE = TEXT_PANE;
        doc = TEXT_PANE.getStyledDocument();

        javax.swing.text.Style palabraReservada = TEXT_PANE.addStyle("PALABRAS_RESERVADAS", null);
        StyleConstants.setForeground(palabraReservada, Color.BLUE);

        javax.swing.text.Style identificador = TEXT_PANE.addStyle("IDENTIFICADOR", null);
        StyleConstants.setForeground(identificador, new Color(139, 69, 19)); // café

        javax.swing.text.Style numero = TEXT_PANE.addStyle("NUMERO", null);
        StyleConstants.setForeground(numero, Color.GREEN);

        javax.swing.text.Style cadena = TEXT_PANE.addStyle("CADENA", null);
        StyleConstants.setForeground(cadena, Color.ORANGE);

        javax.swing.text.Style decimal = TEXT_PANE.addStyle("DECIMAL", null);
        StyleConstants.setForeground(decimal, Color.BLACK);

        javax.swing.text.Style puntuacion = TEXT_PANE.addStyle("PUNTUACION", null);
        StyleConstants.setForeground(puntuacion, new Color(48, 213, 200));

        javax.swing.text.Style comentarioLinea = TEXT_PANE.addStyle("COMENTARIO_LINEA", null);
        StyleConstants.setForeground(comentarioLinea, new Color(0, 100, 0));

        javax.swing.text.Style comentarioBloque = TEXT_PANE.addStyle("COMENTARIO_BLOQUE", null);
        StyleConstants.setForeground(comentarioBloque, new Color(0, 100, 0));

        javax.swing.text.Style operador = TEXT_PANE.addStyle("OPERADOR", null);
        StyleConstants.setForeground(operador, Color.YELLOW);

        javax.swing.text.Style agrupacion = TEXT_PANE.addStyle("AGRUPACION", null);
        StyleConstants.setForeground(agrupacion, new Color(128, 0, 128));

        javax.swing.text.Style error = TEXT_PANE.addStyle("ERROR", null);
        StyleConstants.setForeground(error, Color.RED);

        javax.swing.text.Style fondo = TEXT_PANE.addStyle("FONDO", null);
        StyleConstants.setBackground(fondo, Color.WHITE);
        StyleConstants.setForeground(fondo, Color.BLACK);

        javax.swing.text.Style sinFondo = TEXT_PANE.addStyle("SinFondo", null);
        StyleConstants.setBackground(sinFondo, TEXT_PANE.getBackground());
    }

    /**
     * Metodo que pinta todo el texto recorriendo los tokens analizados
     *
     * @param tokens
     */
    public void pintarTexto(ArrayList<Token> tokens) {
        if (tokens != null && !tokens.isEmpty()) {
            for (Token token : tokens) {
                Style estilo = TEXT_PANE.getStyle(token.getTipo().name()); // Usa el nombre del tipo como clave
                doc.setCharacterAttributes(token.getNumCaracter(), token.getLexema().length(), estilo, true);
            }
        }
    }

    /**
     * Pinta solo un todken
     *
     * @param token el token guardado
     */
    public void pintarToken(Token token) {
        Style estilo = TEXT_PANE.getStyle(token.getTipo().name());
        doc.setCharacterAttributes(token.getNumCaracter(), token.getLexema().length(), estilo, true);
    }

    public void pintarFondo(int inicio, int longitud) {
        Style style = TEXT_PANE.getStyle("FONDO");
        doc.setCharacterAttributes(inicio, longitud, style, true);
    }
    
    public void limpiarPane() {
        Style limpio = TEXT_PANE.getStyle("SinFondo");
        doc.setCharacterAttributes(0, doc.getLength(), limpio, false);
    }
}
