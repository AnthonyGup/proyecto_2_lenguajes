/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.token;

import lenguajes.final_project.backend.position.Position;

/**
 *
 * @author antho
 */
public class Token {
    
    private TokenType tipo;
    private String lexema;
    private int numCaracter;
    private Position posicion;

    public Token(TokenType tipo, String lexema, int numCaraacter, Position posicion) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.numCaracter = numCaraacter;
        this.posicion = posicion;
    }

    public Token(String lexema, TokenType tipo) {
        this.tipo = tipo;
        this.lexema = lexema;
    }
    
    public TokenType getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public int getNumCaracter() {
        return numCaracter;
    }

    public Position getPosicion() {
        return posicion;
    }
    
}
