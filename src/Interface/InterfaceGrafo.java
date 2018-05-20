/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Algoritimos.FluxoMaximo;
import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;
import com.sun.java.swing.plaf.windows.WindowsToggleButtonUI;
import java.awt.Color;
import javax.swing.JMenu;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.metal.MetalToggleButtonUI;

/**
 *
 * @author alber
 */
public class InterfaceGrafo extends javax.swing.JFrame {

    /**
     * Creates new form InterfaceGrafo
     */
    private Painel jPanEditor;
    private Grafo grafo;
    private int xInicial;
    private int yInicial;
    private Vertice vertice;
    
    public InterfaceGrafo() {
        initComponents();
        vertice = null;
        grafo = new Grafo();
        jPanEditor = new Painel();
        jPanEditor.setGrafo(grafo);

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            System.out.println(UIManager.getLookAndFeel().getID());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterfaceGrafo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(InterfaceGrafo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InterfaceGrafo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(InterfaceGrafo.class.getName()).log(Level.SEVERE, null, ex);
        }
        criarPainel();

        jBtnInserirVertice.setUI(new MetalToggleButtonUI() {
            @Override
            protected Color getSelectColor() {
                return Color.BLUE;
            }
        });
        jBtnInserirAresta.setUI(new MetalToggleButtonUI() {
            @Override
            protected Color getSelectColor() {
                return Color.BLUE;
            }
        });
        jBtnMover.setUI(new MetalToggleButtonUI() {
            @Override
            protected Color getSelectColor() {
                return Color.BLUE;
            }
        });
        jBtnRemover.setUI(new MetalToggleButtonUI() {
            @Override
            protected Color getSelectColor() {
                return Color.BLUE;
            }
        });
    }

    private void criarPainel() {
        jPanEditor.setBackground(new java.awt.Color(255, 255, 255));
        jPanEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanEditor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                mouseFimDoClick(evt);
            }
        });

        jPanEditor.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                mouseArrasto(evt);
            }
        });

        jPanEditor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                mouseInicioDoClick(evt);
            }
        });

        jPanEditor.setLocation(5, 30);
        jPanEditor.setSize(1060, 550);

        this.add(jPanEditor);
    }

    private void mouseArrasto(MouseEvent evt) {
        if (jBtnMover.isSelected()) {
            if (vertice != null) {
                if (grafo.verificarPos(vertice, evt.getX() - (Grafo.tamanho_vertice / 2), evt.getY() - (Grafo.tamanho_vertice / 2))) {
                    vertice.setPosX(evt.getX() - (Grafo.tamanho_vertice / 2));
                    vertice.setPosY(evt.getY() - (Grafo.tamanho_vertice / 2));
                    jPanEditor.repaint();
                }
            }
        }
        if (jBtnInserirAresta.isSelected()) {
            if (vertice != null) {
                jPanEditor.inserindoAresta(vertice, evt.getPoint());
                jPanEditor.repaint();
            }
        }

    }

    private void mouseInicioDoClick(MouseEvent evt) {
        if (jBtnMover.isSelected()) {
            vertice = grafo.getVerticePos(evt.getX(), evt.getY());
        }
        if (jBtnInserirAresta.isSelected()) {
            vertice = grafo.getVerticePos(evt.getX(), evt.getY());
        }
    }

    private void mouseFimDoClick(MouseEvent evt) {
        Vertice vertice2 = null;
        if (jBtnInserirVertice.isSelected()) {
            if (grafo.verificarPos(evt.getX() - (Grafo.tamanho_vertice / 2), evt.getY() - (Grafo.tamanho_vertice / 2))) {
                Vertice vertex = new Vertice(grafo.getNovoLabelVertice(), evt.getX() - (Grafo.tamanho_vertice / 2), evt.getY() - (Grafo.tamanho_vertice / 2));
                this.grafo.addVertex(vertex);
                jPanEditor.repaint();
            }
        }
        if (jBtnMover.isSelected()) {
            vertice = null;
        }
        if (jBtnInserirAresta.isSelected()) {
            jPanEditor.inserindoAresta(null, null);
            vertice2 = grafo.getVerticePos(evt.getX(), evt.getY());
            if (vertice2 != null) {
                if (!grafo.verticeEstaConectado(vertice, vertice2) && !vertice.equals(vertice2)) {
                    int peso = 1;
                    if (grafo.getPondered()) {
                        boolean erro = false;
                        do {
                            try {
                                String s = JOptionPane.showInputDialog("Insira o peso da aresta");
                                if(s == null){
                                    peso = -1;
                                    break;
                                }
                                peso = Integer.parseInt(s);
                                erro = false;
                            } catch (NumberFormatException e) {
                                erro = true;
                            }
                            
                            if(peso <= 0){
                                erro = true;
                            }
                            
                            if(erro){
                                JOptionPane.showMessageDialog(null,"Valor invalido, informe outro", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } while (erro);
                    }
                    if(peso > 0){
                        Aresta aresta = new Aresta(vertice, vertice2, peso);
                        this.grafo.addEdge(aresta);
                    }
                }
            }
            jPanEditor.repaint();
        }
        if (jBtnRemover.isSelected()) {
            grafo.removerVertice(evt.getX() - (Grafo.tamanho_vertice / 2), evt.getY() - (Grafo.tamanho_vertice / 2));
            jPanEditor.repaint();            
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jBtnInserirVertice = new javax.swing.JToggleButton();
        jBtnMover = new javax.swing.JToggleButton();
        jBtnInserirAresta = new javax.swing.JToggleButton();
        jBtnRemover = new javax.swing.JToggleButton();
        menu = new javax.swing.JMenuBar();
        jMenuConfiguracao = new javax.swing.JMenu();
        JIsPonderado = new javax.swing.JCheckBoxMenuItem();
        JIsOrientado = new javax.swing.JCheckBoxMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jSair = new javax.swing.JMenuItem();
        jMenuAlgoritimos = new javax.swing.JMenu();
        jMenuAGM2 = new javax.swing.JMenu();
        jBFS = new javax.swing.JMenuItem();
        jDFS = new javax.swing.JMenuItem();
        jDjikstra = new javax.swing.JMenuItem();
        jMenuAGM = new javax.swing.JMenu();
        jPrim = new javax.swing.JMenuItem();
        jKruskal = new javax.swing.JMenuItem();
        jPlanaridade = new javax.swing.JMenuItem();
        jMenuAGM1 = new javax.swing.JMenu();
        jFordFukerson = new javax.swing.JMenuItem();
        jMenuColoracao = new javax.swing.JMenu();
        jWelshPowell = new javax.swing.JMenuItem();
        jDSatur = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jBtnInserirVertice.setText("O+");
        jBtnInserirVertice.setToolTipText("");
        jBtnInserirVertice.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jBtnInserirVerticeStateChanged(evt);
            }
        });

        jBtnMover.setSelected(true);
        jBtnMover.setText("->");
        jBtnMover.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jBtnMoverStateChanged(evt);
            }
        });

        jBtnInserirAresta.setText("__+");
        jBtnInserirAresta.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jBtnInserirArestaStateChanged(evt);
            }
        });

        jBtnRemover.setText("X");
        jBtnRemover.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jBtnRemoverStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jBtnInserirVertice)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jBtnMover)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnInserirAresta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnRemover)
                .addGap(0, 858, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnInserirVertice)
                    .addComponent(jBtnMover)
                    .addComponent(jBtnInserirAresta)
                    .addComponent(jBtnRemover)))
        );

        jMenuConfiguracao.setText("Configurações");

        JIsPonderado.setText("Grafo Ponderado");
        JIsPonderado.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                JIsPonderadoStateChanged(evt);
            }
        });
        JIsPonderado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JIsPonderadoActionPerformed(evt);
            }
        });
        jMenuConfiguracao.add(JIsPonderado);

        JIsOrientado.setText("Grafo Orientado");
        JIsOrientado.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                JIsOrientadoStateChanged(evt);
            }
        });
        jMenuConfiguracao.add(JIsOrientado);
        jMenuConfiguracao.add(jSeparator4);

        jSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        jSair.setText("Sair");
        jMenuConfiguracao.add(jSair);

        menu.add(jMenuConfiguracao);

        jMenuAlgoritimos.setText("Algoritimos");

        jMenuAGM2.setText("Algoritimos de Busca");

        jBFS.setText("BFS");
        jMenuAGM2.add(jBFS);

        jDFS.setText("DFS");
        jMenuAGM2.add(jDFS);

        jMenuAlgoritimos.add(jMenuAGM2);

        jDjikstra.setText("Djikstra");
        jMenuAlgoritimos.add(jDjikstra);

        jMenuAGM.setText("Arvore Geradora Minima");

        jPrim.setText("Prim");
        jMenuAGM.add(jPrim);

        jKruskal.setText("Kruskal");
        jMenuAGM.add(jKruskal);

        jMenuAlgoritimos.add(jMenuAGM);

        jPlanaridade.setText("Verificação de Planaridade");
        jMenuAlgoritimos.add(jPlanaridade);

        jMenuAGM1.setText("Fluxo Maximo");

        jFordFukerson.setText("Ford-Fukerson");
        jFordFukerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFordFukersonActionPerformed(evt);
            }
        });
        jMenuAGM1.add(jFordFukerson);

        jMenuAlgoritimos.add(jMenuAGM1);

        menu.add(jMenuAlgoritimos);

        jMenuColoracao.setText("Coloração");

        jWelshPowell.setText("WelshPowell");
        jMenuColoracao.add(jWelshPowell);

        jDSatur.setText("DSatur");
        jMenuColoracao.add(jDSatur);

        menu.add(jMenuColoracao);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 590, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JIsPonderadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JIsPonderadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_JIsPonderadoActionPerformed

    private void jBtnInserirVerticeStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jBtnInserirVerticeStateChanged
        if (jBtnInserirVertice.isSelected()) {
            jBtnInserirAresta.setSelected(false);
            jBtnMover.setSelected(false);
            jBtnRemover.setSelected(false);
        } else if ((!jBtnInserirVertice.isSelected())
                && (!jBtnInserirAresta.isSelected())
                && (!jBtnMover.isSelected())
                && (!jBtnRemover.isSelected())) {

            jBtnInserirVertice.setSelected(true);
        }
    }//GEN-LAST:event_jBtnInserirVerticeStateChanged

    private void jBtnMoverStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jBtnMoverStateChanged
        if (jBtnMover.isSelected()) {
            jBtnInserirVertice.setSelected(false);
            jBtnInserirAresta.setSelected(false);
            jBtnRemover.setSelected(false);
        } else if ((!jBtnInserirVertice.isSelected())
                && (!jBtnInserirAresta.isSelected())
                && (!jBtnMover.isSelected())
                && (!jBtnRemover.isSelected())) {

            jBtnMover.setSelected(true);
        }
    }//GEN-LAST:event_jBtnMoverStateChanged

    private void jBtnInserirArestaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jBtnInserirArestaStateChanged
        if (jBtnInserirAresta.isSelected()) {
            jBtnInserirVertice.setSelected(false);
            jBtnMover.setSelected(false);
            jBtnRemover.setSelected(false);
        } else if ((!jBtnInserirVertice.isSelected())
                && (!jBtnInserirAresta.isSelected())
                && (!jBtnMover.isSelected())
                && (!jBtnRemover.isSelected())) {

            jBtnInserirAresta.setSelected(true);
        }
    }//GEN-LAST:event_jBtnInserirArestaStateChanged

    private void jBtnRemoverStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jBtnRemoverStateChanged
        if (jBtnRemover.isSelected()) {
            jBtnInserirVertice.setSelected(false);
            jBtnInserirAresta.setSelected(false);
            jBtnMover.setSelected(false);
        } else if ((!jBtnInserirVertice.isSelected())
                && (!jBtnInserirAresta.isSelected())
                && (!jBtnMover.isSelected())
                && (!jBtnRemover.isSelected())) {

            jBtnRemover.setSelected(true);
        }
    }//GEN-LAST:event_jBtnRemoverStateChanged

    private void JIsOrientadoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_JIsOrientadoStateChanged
        grafo.setOriented(JIsOrientado.isSelected());
        jPanEditor.repaint();
    }//GEN-LAST:event_JIsOrientadoStateChanged

    private void JIsPonderadoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_JIsPonderadoStateChanged
        grafo.setPondered(JIsPonderado.isSelected());
        jPanEditor.repaint();
    }//GEN-LAST:event_JIsPonderadoStateChanged

    private void jFordFukersonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFordFukersonActionPerformed
        FluxoMaximo f = new FluxoMaximo(this.grafo);
        if(this.grafo.getOriented() && this.grafo.getPondered()){
            f.executar();
            this.jPanEditor.repaint();
        }else{
            JOptionPane.showMessageDialog(null, "Grafo precisa ser orientado e ponderado para executar o metodo Ford-Fukerson","Erro",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jFordFukersonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem JIsOrientado;
    private javax.swing.JCheckBoxMenuItem JIsPonderado;
    private javax.swing.JMenuItem jBFS;
    private javax.swing.JToggleButton jBtnInserirAresta;
    private javax.swing.JToggleButton jBtnInserirVertice;
    private javax.swing.JToggleButton jBtnMover;
    private javax.swing.JToggleButton jBtnRemover;
    private javax.swing.JMenuItem jDFS;
    private javax.swing.JMenuItem jDSatur;
    private javax.swing.JMenuItem jDjikstra;
    private javax.swing.JMenuItem jFordFukerson;
    private javax.swing.JMenuItem jKruskal;
    private javax.swing.JMenu jMenuAGM;
    private javax.swing.JMenu jMenuAGM1;
    private javax.swing.JMenu jMenuAGM2;
    private javax.swing.JMenu jMenuAlgoritimos;
    private javax.swing.JMenu jMenuColoracao;
    private javax.swing.JMenu jMenuConfiguracao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem jPlanaridade;
    private javax.swing.JMenuItem jPrim;
    private javax.swing.JMenuItem jSair;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JMenuItem jWelshPowell;
    private javax.swing.JMenuBar menu;
    // End of variables declaration//GEN-END:variables
}
