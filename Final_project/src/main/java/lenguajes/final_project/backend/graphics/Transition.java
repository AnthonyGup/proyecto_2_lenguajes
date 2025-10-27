/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.graphics;

/**
 *
 * @author antho
 */
public class Transition {
    
    public final String origen;
    public final String destino;
    public final char simbolo;

    public Transition(String origen, String destino, char simbolo) {
        this.origen = origen;
        this.destino = destino;
        this.simbolo = simbolo;
    }
}
