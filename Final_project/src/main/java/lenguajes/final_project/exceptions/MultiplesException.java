/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.exceptions;

import javax.swing.JOptionPane;

/**
 *
 * @author antho
 */
public class MultiplesException extends Exception {
    public MultiplesException(String message) {
        super(message);
        mostrarJDialog(message);
    }
    
    private void mostrarJDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error al operar", JOptionPane.ERROR_MESSAGE);
    }
}
