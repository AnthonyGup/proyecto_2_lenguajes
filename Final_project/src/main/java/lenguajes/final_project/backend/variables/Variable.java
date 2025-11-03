/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.variables;

import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.backend.token.TokenType;

/**
 *
 * @author antho
 */
public class Variable {
    
    private String nombre;
    private TokenType tipo;
    private boolean libre = true;
    private String contenidoString;
    private int contenidoInt;
    private double contenidoDouble;

    public Variable(Token token, Token tokenTipo) {
        this.nombre = token.getLexema();
        this.tipo = tokenTipo.getTipo2();
    }

    public void setContenido(String contenido) {
        switch (tipo) {
            case CAD:
                contenidoString = contenido;
                break;
            case NUM:
                contenidoDouble = Double.parseDouble(contenido);
                break;
            case ENTERO:
                contenidoInt = Integer.parseInt(contenido);
                break;
            default:
                throw new AssertionError();
        }
        libre = false;
    }
        
    public String getContenidoToString() {
        switch (tipo) {
            case CAD:
                return getContenidoString();
            case NUM:
                return getContenidoDouble() + "";
            default:
            case ENTERO:
                return getContenidoInt() + "";
        }
    }
    
    
    public boolean isLibre() {
        return libre;
    }

    public String getNombre() {
        return nombre;
    }

    public TokenType getTipo() {
        return tipo;
    }

    public String getContenidoString() {
        return contenidoString;
    }

    public int getContenidoInt() {
        return contenidoInt;
    }

    public double getContenidoDouble() {
        return contenidoDouble;
    }
    
    
}
