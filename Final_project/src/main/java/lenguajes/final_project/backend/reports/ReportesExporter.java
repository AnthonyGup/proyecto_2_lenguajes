/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.reports;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import lenguajes.final_project.backend.token.Token;

/**
 *
 * @author antho
 */
public class ReportesExporter {

    public void exportarTokensValidos(ArrayList<Token> token) {
        StringBuilder text = new StringBuilder();
        text.append("Tipo,Lexema,Fila,Columna\n"); // encabezado opcional

        for (Token tokens : token) {
            String tipo = tokens.getTipo().name();
            String lexema = tokens.getLexema().replace("\"", "\"\"");
            int fila = tokens.getPosicion().getLINEA();
            int columna = tokens.getPosicion().getCOLUMNA();

            text.append(tipo)
                    .append(",\"").append(lexema).append("\"")
                    .append(",").append(fila)
                    .append(",").append(columna)
                    .append("\n");
        }

        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Guardar CSV");

        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivoNuevo = fileChooser.getSelectedFile();

            // Asegurar extensión .csv
            if (!archivoNuevo.getName().toLowerCase().endsWith(".csv")) {
                archivoNuevo = new File(archivoNuevo.getAbsolutePath() + ".csv");
            }

            try {
                Files.writeString(archivoNuevo.toPath(), text, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(null, "Archivo CSV guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo:\n" + e.getMessage(), "Error de escritura", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
