/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.position;

import java.util.ArrayList;

/**
 *
 * @author antho
 */
public class PositionGenerator {

    public Position calcularPosicion(int indice, ArrayList<Integer> saltosDeLinea) {
        int linea = -1;
        int columna = indice;
        
        if (saltosDeLinea == null || saltosDeLinea.isEmpty()) {
            return new Position(0, indice);
        }

        // Buscar la última línea cuyo salto de línea está antes del índice
        for (int i = 0; i < saltosDeLinea.size(); i++) {
            if (indice < saltosDeLinea.get(i)) {
                break;
            }
            if (indice > saltosDeLinea.get(i)) {
                linea = i;
                columna = indice - saltosDeLinea.get(linea);
            }
        }

        return new Position(linea + 1, columna);
    }
}
