/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.actions;

import java.util.ArrayList;
import java.util.List;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.backend.variables.*;
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

    public void evaluar(List<List<Token>> sentencias) throws OperacionException {
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
                        System.out.println("Se escribió algo");
                        // llamar al escribir
                        break;
                    default:
                        break;
                }
            }

        }
    }

    private void definir(List<Token> tokens) {
        Variable var = new Variable(tokens.get(1), tokens.get(3));

        variables.add(var);

        System.out.println("//Variable: " + var.getNombre() + " definida como: " + var.getTipo());
    }

    private void asignar(List<Token> tokens) throws OperacionException {
        Variable var = comprobarExistencia(tokens.get(0).getLexema());
        if (var != null) {
            if (var.isLibre()) {
                Asignador asignador = new Asignador(var, tokens, variables);
                asignador.asignar();
            }
        }
    }

    private Variable comprobarExistencia(String lexema) {
        for (int i = 0; i < variables.size(); i++) {
            Variable nueva = variables.get(i);
            if (nueva.getNombre().equals(lexema)) {
                return nueva;
            }
        }
        System.out.println("Variable: " + lexema + " no ha sido definida");
        return null;
    }
}
