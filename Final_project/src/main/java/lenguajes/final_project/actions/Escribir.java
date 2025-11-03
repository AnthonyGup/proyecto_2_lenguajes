/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import lenguajes.final_project.backend.syntactic.SyntacticAnalyzer;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.backend.token.TokenType;
import lenguajes.final_project.backend.variables.Variable;
import lenguajes.final_project.exceptions.MultiplesException;

/**
 *
 * @author antho
 */
public class Escribir {

    private List<Token> tokens;
    private javax.swing.JTextPane panel;
    private List<Variable> variables;

    /**
     *
     * @param variables
     * @param tokens
     * @param panel
     * @throws lenguajes.final_project.exceptions.MultiplesException
     */
    public Escribir(List<Variable> variables, List<Token> tokens, javax.swing.JTextPane panel) throws MultiplesException {
        this.tokens = tokens;
        this.variables = variables;
        this.panel = panel;
        evaluar();
    }

    public Escribir(List<Variable> variables, javax.swing.JTextPane panel) {
        this.variables = variables;
        this.panel = panel;
    }

    private void evaluar() throws MultiplesException {
        switch (tokens.get(0).getTipo2()) {
            case IDENTIFICADOR:
                if (variables.isEmpty()) {
                    panel.setText("Sin variables definidas");
                }
                for (int i = 0; i < variables.size(); i++) {
                    if (tokens.get(0).getLexema().equals(variables.get(i).getNombre())) {
                        if (!variables.get(i).isLibre()) {
                            panel.setText(variables.get(i).getContenidoToString());
                        } else {
                            throw new MultiplesException("Error con el identificador: " + tokens.get(i).getLexema());
                        }
                    }
                }
                break;
            case NUM:
                Operator operador = new Operator(tokens);
                panel.setText(operador.getResultado());
                break;
            case CAD:
                separarCadenas();
                break;
            default:
                throw new MultiplesException("Tipo no encontrado");
        }
    }

    private void separarCadenas() throws MultiplesException {
        List<Token> nuevos = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getTipo2().equals(TokenType.CAD)) {
                nuevos.add(tokens.get(i));
            } else {
                switch (tokens.get(i).getTipo2()) {
                    case LPAREN -> {
                        i++;
                        List<Token> pantesis = new ArrayList<>();
                        for (int j = i; j < tokens.size(); j++) {
                            switch (tokens.get(j).getTipo2()) {
                                case NUM ->
                                    pantesis.add(tokens.get(j));
                                case RPAREN ->
                                    j = tokens.size();
                                default ->
                                    throw new MultiplesException("Eror: no se esperaba esta expresion");
                            }
                        }
                        Operator operador = new Operator(pantesis);
                        String resultado = operador.getResultado();
                        Token token = new Token(resultado, TokenType.CAD);
                        token.setTipo2(TokenType.CAD);
                        nuevos.add(token);
                    }
                    case MAS ->
                        i++;
                    default ->
                        throw new MultiplesException("Caracter invalido");
                }
            }
        }
        String texto = "";
        for (int i = 0; i < nuevos.size(); i++) {
            texto = " " + texto + nuevos.get(i).getLexema();
        }
        panel.setText(texto);
    }

    public void reportarVar() {
        StyledDocument doc = panel.getStyledDocument();
        for (int i = 0; i < variables.size(); i++) {
            try {
                doc.insertString(doc.getLength(), "\n" + "-- Var #" +i + 1+": " + variables.get(i).getNombre() + " || Tipo: " + variables.get(i).getTipo(), null);
                if (!variables.get(i).isLibre()) {
                doc.insertString(doc.getLength(), " || Valor: " + variables.get(i).getContenidoToString(), null);
                    
                }
            } catch (BadLocationException ex) {
                Logger.getLogger(SyntacticAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
