/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.actions;

import java.util.ArrayList;
import java.util.List;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.backend.variables.*;
import lenguajes.final_project.exceptions.MultiplesException;
import lenguajes.final_project.exceptions.OperacionException;

/**
 *
 * @author antho
 */
public class Actions {

    private ArrayList<Variable> variables = new ArrayList<>();
    private final javax.swing.JTextPane TERMINAL;

    public Actions(javax.swing.JTextPane TERMINAL) {
        this.TERMINAL = TERMINAL;
    }

    public void evaluar(List<List<Token>> sentencias) throws OperacionException, MultiplesException {
        variables = new ArrayList<>();
        for (int i = 0; i < sentencias.size(); i++) {
            List<Token> linea = sentencias.get(i);
            if (!linea.isEmpty()) {
                Token t = linea.get(0); // Solo el primer token de cada sentencia
                switch (t.getTipo2()) {
                    case DEFINIR:
                        definir(sentencias.get(i));
                        break;
                    case IDENTIFICADOR:
                        asignar(sentencias.get(i));
                        break;
                    case ESCRIBIR:
                        escribir(sentencias.get(i));
                        break;
                    default:
                        throw new MultiplesException("Error: no se encuentra el tipo");
                }
            }

        }
    }
    
    private void escribir(List<Token> tokens) throws MultiplesException {
        List<Token> nuevos = new ArrayList<>();
        for (int i = 2; i < tokens.size() - 2; i++) {
            nuevos.add(tokens.get(i));
        }
        Escribir es = new Escribir(variables, nuevos, TERMINAL);
    }

    private void definir(List<Token> tokens) {
        Variable var = new Variable(tokens.get(1), tokens.get(3));

        variables.add(var);

        System.out.println("//Variable: " + var.getNombre() + " definida como: " + var.getTipo());
    }

    private void asignar(List<Token> tokens) throws OperacionException, MultiplesException {
        Variable var = comprobarExistencia(tokens.get(0).getLexema());
        if (var != null) {
            Asignador asignador = new Asignador(var, tokens, variables);
            asignador.asignar();
        }
    }

    private Variable comprobarExistencia(String lexema) throws MultiplesException {
        for (int i = 0; i < variables.size(); i++) {
            Variable nueva = variables.get(i);
            if (nueva.getNombre().equals(lexema)) {
                return nueva;
            }
        }
        throw new MultiplesException("Variable: " + lexema + " no ha sido definida");
    }
    
    public void reportarVariables() {
        Escribir escritor = new Escribir(variables, TERMINAL);
        escritor.reportarVar();
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }
}
