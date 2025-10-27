/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author antho
 */
public class FileHandler {

    private File actualFile;
    private String fileContent;

    public void buscarArchivo() {
        try {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setDialogTitle("Selecciona un archivo de texto");

            // Filtro para solo mostrar archivos .txt
            FileNameExtensionFilter filtroTxt = new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt");
            fileChooser.setFileFilter(filtroTxt);

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                actualFile = fileChooser.getSelectedFile();
                fileContent = Files.readString(actualFile.toPath(), StandardCharsets.UTF_8);
            } else {
                fileContent = "";
                actualFile = null;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo:\n" + e.getMessage(), "Error de lectura", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getFileContent() {
        return fileContent;
    }

    public void sobreescribirContenido(String nuevoContenido) {
        if (actualFile != null) {
            try {
                Files.writeString(actualFile.toPath(), nuevoContenido, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(null, "Archivo actualizado con exito", "Exito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo:\n" + e.getMessage(), "Error de escritura", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay archivo seleccionado para guardar.", "Archivo no definido", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void guardarComo(String contenido) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setDialogTitle("Guardar como...");

        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File archivoNuevo = fileChooser.getSelectedFile();

            try {
                Files.writeString(archivoNuevo.toPath(), contenido, StandardCharsets.UTF_8);
                JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo:\n" + e.getMessage(), "Error de escritura", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public File getActualFile() {
        return actualFile;
    }

}
