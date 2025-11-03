/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lenguajes.final_project.backend.reports;

import java.util.ArrayList;
import lenguajes.final_project.backend.token.Token;
import lenguajes.final_project.frontend.windows.Reporte;

/**
 *
 * @author antho
 */
public class ReportesManager {
    
    private ArrayList<Token> tokensValidos = new ArrayList<>();
    private ArrayList<Token> tokensErrores = new ArrayList<>();
    private ArrayList<Token> todos;
    private javax.swing.JDesktopPane jDesktopPane;
    
    public ReportesManager(ArrayList<Token> tokens, javax.swing.JDesktopPane jDesktopPane) {
        int i = 0;
        this.todos = tokens;
        while (i < tokens.size()) {
            if (tokens.get(i).getTipo().name().equals("ERROR")) {
                tokensErrores.add(tokens.get(i));
            } else {
                tokensValidos.add(tokens.get(i));
            }
            i++;
        }
        this.jDesktopPane = jDesktopPane;
    }
    
    public void abrirReportes() {
        Reporte repo = new Reporte();
        repo.llenarTokens(tokensValidos, tokensErrores, todos);
        jDesktopPane.add(repo);
        repo.setVisible(true);
    }
    
}
