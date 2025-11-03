/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.actions;

import java.util.ArrayList;
import java.util.List;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.backend.token.TokenType;
import lenguajes.final_project.backend.variables.Variable;
import lenguajes.final_project.exceptions.MultiplesException;
import lenguajes.final_project.exceptions.OperacionException;

/**
 *
 * @author antho
 */
public class Asignador {

    private List<Token> tokens;
    private Variable var;
    private List<Variable> variables;
    
    /**
     * Metodo que asigna valores a variables
     * @param var variable a la cual se le quiere asignar el valor
     * @param tokens lista de tokens que conforman la orden Ejemplo: x = 12 + a;
     * @param variables todas las variables
     */
    public Asignador(Variable var, List<Token> tokens, List<Variable> variables) {
        this.tokens = tokens;
        this.var = var;
        this.variables = variables;
    }
    
    public boolean asignar() throws OperacionException, MultiplesException {
        evaluar();
        return !var.isLibre();
    }
    
    private List<Token> separarTokens() throws OperacionException {
        List<Token> separados = new ArrayList<>();
        for (int i = 2; i < tokens.size() - 1; i++) {
            separados.add(tokens.get(i));
            if (tokens.get(i).getTipo().equals(TokenType.IDENTIFICADOR)) {
                Variable otraVar = isVariableCorrecta(tokens.get(i));
                if (otraVar == null) {
                    throw new OperacionException("Identificador no valido");
                } else {
                    int index = separados.size() - 1;
                    Token token = new Token(otraVar.getContenidoToString(), otraVar.getTipo());
                    token.setTipo2(otraVar.getTipo());
                    separados.subList(index, index + 1).clear();
                    separados.add(index, token);
                }
            }
            //System.out.println("Token: " + tokens.get(i).getLexema());
        }
        return separados;
    }
    
    private void evaluar() throws OperacionException, MultiplesException {
        List<Token> contenidos = separarTokens();
        if (contenidos.get(0).getTipo2().equals(TokenType.CAD)) {
            var.setContenido(contenidos.get(0).getLexema());
        } else {
            if (contenidos.size() > 1) {
                Operator operador = new Operator(contenidos);
                var.setContenido(operador.getResultado());
            } else {
                var.setContenido(contenidos.get(0).getLexema());
            }
        }
        System.out.println("Se asigno: " + var.getContenidoToString() + " a: " + var.getNombre());
    }
    
    private Variable isVariableCorrecta(Token token) {
        Variable otraVar;
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).getNombre().equals(token.getLexema()) && var.getTipo().equals(variables.get(i).getTipo())) {
                otraVar = variables.get(i);
                return otraVar;
            }
        }
        return null;
    }
}
