/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.actions;

import java.util.List;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.backend.token.TokenType;

/**
 *
 * @author antho
 */
public class Operator {

    private final List<Token> TOKENS;
    private String resultado;

    public Operator(List<Token> tokens) {
        this.TOKENS = tokens;
        evaluar();
    }

    private void evaluar() {
        for (TokenType tipo : List.of(TokenType.DIV, TokenType.POR, TokenType.MAS, TokenType.MENOS)) {
            for (int i = 0; i < TOKENS.size(); i++) {
                if (TOKENS.get(i).getTipo2().equals(tipo)) {
                    operar(TOKENS.get(i - 1), TOKENS.get(i), TOKENS.get(i + 1), i - 1);
                    evaluar(); // recursivo
                    return;
                }
            }
        }
        asignarResultadoFinal(); // solo se ejecuta cuando ya no hay operadores
    }

    private void asignarResultadoFinal() {
        if (TOKENS.size() == 1 && TOKENS.get(0).getTipo2() == TokenType.NUM) {
            resultado = TOKENS.get(0).getLexema();
            System.out.println("RESULTADO = " + resultado);
        }
    }

    private void operar(Token t1, Token operador, Token t2, int index) {
        double n1 = Double.parseDouble(t1.getLexema());
        double n2 = Double.parseDouble(t2.getLexema());
        double res;

        if (operador.getTipo2() == TokenType.DIV) {
            res = n1 / n2;
        } else if (operador.getTipo2() == TokenType.POR) {
            res = n1 * n2;
        } else if (operador.getTipo2() == TokenType.MAS) {
            res = n1 + n2;
        } else if (operador.getTipo2() == TokenType.MENOS) {
            res = n1 - n2;
        } else {
            throw new IllegalArgumentException("Operador no soportado: " + operador.getTipo2());
        }

        TOKENS.subList(index, index + 3).clear();
        Token nuevo = new Token(String.valueOf(res), TokenType.NUMERO);
        nuevo.setTipo2(TokenType.NUM);
        TOKENS.add(index, nuevo);
    }

    public String getResultado() {
        return resultado;
    }
}
