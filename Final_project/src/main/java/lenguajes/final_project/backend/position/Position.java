/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.position;

/**
 *
 * @author antho
 */
public class Position {
    private final int LINEA;
    private final int COLUMNA;

    public Position(int linea, int columna) {
        this.LINEA = linea;
        this.COLUMNA = columna;
    }

    public int getLINEA() {
        return LINEA;
    }

    public int getCOLUMNA() {
        return COLUMNA;
    }

    @Override
    public String toString() {
        return "Línea " + LINEA + ", Columna " + COLUMNA;
    }
}
