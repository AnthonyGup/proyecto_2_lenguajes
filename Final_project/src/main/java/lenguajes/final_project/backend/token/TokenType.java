/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package lenguajes.final_project.backend.token;

/**
 *
 * @author antho
 */
     public enum TokenType {
    //Para el analizador syntactico
    IGUAL,
    PCOMA,
    MAS,
    MENOS,
    POR,
    DIV,
    ESCRIBIR,
    DEFINIR,
    COMO,
    ENTERO,
    LPAREN,
    RPAREN,
    NUM,
    CAD,
    EOF,
    
    //Para el analizador lexico
    NUMERO,
    DECIMAL,
    PUNTUACION,
    AGRUPACION,
    OPERADOR,
    CADENA,
    COMENTARIO_LINEA,
    COMENTARIO_BLOQUE,
    IDENTIFICADOR,
    PALABRAS_RESERVADAS,
    ERROR;
    
}
