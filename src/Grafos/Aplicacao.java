package Grafos;

import Algoritimos.ArvoreGeradoraMinima;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import Modelo.Grafo;
import Interface.GUI;
import Interface.InterfaceGrafo;
import Interface.TUI;

public class Aplicacao {

    public static void main(String[] args) {
        /* Text-based User Interface */
        //UI tui = new TUI();
        //tui.main();
        
        /* Graphical User Interface */
     /*
        GUI gui = new GUI();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | 
                InstantiationException | 
                IllegalAccessException | 
                UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        gui.setVisible(true);
   */
     
     /* Graphical User Interface */
        InterfaceGrafo gui = new InterfaceGrafo();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | 
                InstantiationException | 
                IllegalAccessException | 
                UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        gui.setVisible(true);
        /* Test */
        //Test t = new Test();
        //t.fillGraph();
        //Graph g = t.getGraphInstance();
        //ArvoreGeradoraMinima a = new ArvoreGeradoraMinima(g,"Kruskal");
        //a.startTreeGeneration();
        //a.escreverSolucao();
    }
}
