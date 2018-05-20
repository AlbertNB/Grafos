/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author alber
 */
public class Painel extends JPanel {

    private Grafo grafo;
    private Vertice vertice;
    private Point point;

    public void setGrafo(Grafo g) {
        this.grafo = g;
    }

    public void inserindoAresta(Vertice vertice, Point point) {
        this.vertice = vertice;
        this.point = point;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int contorno = Grafo.contorno_vertice;
        if (this.grafo != null) {
            g.setColor(Color.BLACK);

            if (this.vertice != null && this.point != null) {
                g.drawLine(vertice.getPosX() + (Grafo.tamanho_vertice / 2),
                        vertice.getPosY() + (Grafo.tamanho_vertice / 2),
                        point.x,
                        point.y);
            }
            ArrayList<Aresta> arestasPular = new ArrayList<>();
            for (Aresta a : this.grafo.getEdgeList()) {

                if (arestasPular.contains(a)) {
                    continue;
                }

                if (a.isSolucao()) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.BLACK);
                }

                Aresta aresta_dupla = this.grafo.verficarArestaDupla(a);
                if (aresta_dupla != null) {
                    desenharAresta(g, a, aresta_dupla);
                    arestasPular.add(aresta_dupla);
                } else {
                    desenharAresta(g, a);
                }

                /*if (this.grafo.getOriented()) {

                    Polygon p = new Polygon();
                    p.addPoint(a.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2),
                            a.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2));
                    p.addPoint(a.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) + 5,
                            a.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2));
                    p.addPoint(a.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - 5,
                            a.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - 5);
                    g.fillPolygon(p);
                }*/
            }

            for (Vertice v : this.grafo.getVertexList()) {
                if (v.isSolucao()) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.BLACK);
                }
                g.fillOval(v.getPosX(), v.getPosY(), Grafo.tamanho_vertice, Grafo.tamanho_vertice);
                // g.drawOval(v.getPosX(), v.getPosY(), Grafo.tamanho_vertice, Grafo.tamanho_vertice);
                g.setColor(v.getColor());
                g.fillOval(v.getPosX() + (contorno / 2), v.getPosY() + (contorno / 2), (Grafo.tamanho_vertice - contorno), (Grafo.tamanho_vertice - contorno));
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString(v.getLabel(), v.getPosX() + 26, v.getPosY() + 35);
            }
        }
    }

    private void desenharAresta(Graphics g, Aresta a) {
        double catetoX = ((a.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2)) - (a.getVertex1().getPosX() + (Grafo.tamanho_vertice / 2)));
        double catetoY = ((a.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2)) - (a.getVertex1().getPosY() + (Grafo.tamanho_vertice / 2)));
        double hipotenusa = Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
        double Xh = (catetoX / hipotenusa);
        double Yh = (catetoY / hipotenusa);
        double iniX = a.getVertex1().getPosX() + (Grafo.tamanho_vertice / 2) + (Xh * (Grafo.tamanho_vertice / 2));
        double iniY = a.getVertex1().getPosY() + (Grafo.tamanho_vertice / 2) + (Yh * (Grafo.tamanho_vertice / 2));
        double fimX = a.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * (Grafo.tamanho_vertice / 2));
        double fimY = a.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * (Grafo.tamanho_vertice / 2));

        g.drawLine((int) iniX, (int) iniY, (int) fimX, (int) fimY);

        if (this.grafo.getOriented()) {
            double pt1x = a.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * ((Grafo.tamanho_vertice / 2) + Grafo.seta_adjacencia)) + (Grafo.seta_corpo * Yh);
            double pt1y = a.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * ((Grafo.tamanho_vertice / 2) + Grafo.seta_adjacencia)) - (Grafo.seta_corpo * Xh);
            double pt2x = a.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * ((Grafo.tamanho_vertice / 2) + Grafo.seta_adjacencia)) - (Grafo.seta_corpo * Yh);
            double pt2y = a.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * ((Grafo.tamanho_vertice / 2) + Grafo.seta_adjacencia)) + (Grafo.seta_corpo * Xh);
            Polygon p = new Polygon();

            p.addPoint((int) fimX, (int) fimY);
            p.addPoint((int) pt1x, (int) pt1y);
            p.addPoint((int) pt2x, (int) pt2y);
            g.fillPolygon(p);
        }

        if (this.grafo.getPondered()) {
            hipotenusa = Math.sqrt((Math.pow((fimX - iniX), 2) + Math.pow((fimY - iniY), 2)));
            g.setFont(new Font("Arial", Font.BOLD, 12));
            if (a.getFluxo() != -1) {
                String s = Integer.toString(a.getFluxo()) + "/" + Integer.toString(a.getWeight());
                g.drawString(s, (int) (a.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * ((Grafo.tamanho_vertice / 2) + hipotenusa / 2)) + (Grafo.proximidade_label_aresta * Yh)),
                         (int) (a.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * ((Grafo.tamanho_vertice / 2) + hipotenusa / 2)) - (Grafo.proximidade_label_aresta * Xh)));
            } else {
                g.drawString(String.valueOf(a.getWeight()), (int) (a.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * ((Grafo.tamanho_vertice / 2) + hipotenusa / 2)) + (Grafo.proximidade_label_aresta * Yh)),
                         (int) (a.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * ((Grafo.tamanho_vertice / 2) + hipotenusa / 2)) - (Grafo.proximidade_label_aresta * Xh)));
            }
        }
    }

    private void desenharAresta(Graphics g, Aresta a1, Aresta a2) {

        double catetoX = ((a1.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2)) - (a1.getVertex1().getPosX() + (Grafo.tamanho_vertice / 2)));
        double catetoY = ((a1.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2)) - (a1.getVertex1().getPosY() + (Grafo.tamanho_vertice / 2)));
        double hipotenusa = Math.sqrt((catetoX * catetoX) + (catetoY * catetoY));
        double Xh = (catetoX / hipotenusa);
        double Yh = (catetoY / hipotenusa);
        double iniX = a1.getVertex1().getPosX() + (Grafo.tamanho_vertice / 2) + (Xh * (Grafo.tamanho_vertice / 2)) + (Grafo.tamanho_vertice / 4 * Yh);
        double iniY = a1.getVertex1().getPosY() + (Grafo.tamanho_vertice / 2) + (Yh * (Grafo.tamanho_vertice / 2)) - (Grafo.tamanho_vertice / 4 * Xh);
        double fimX = a1.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * (Grafo.tamanho_vertice / 2)) + (Grafo.tamanho_vertice / 4 * Yh);
        double fimY = a1.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * (Grafo.tamanho_vertice / 2)) - (Grafo.tamanho_vertice / 4 * Xh);

        g.drawLine((int) iniX, (int) iniY, (int) fimX, (int) fimY);

        double pt1x = a1.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * ((Grafo.tamanho_vertice / 4) + Grafo.seta_adjacencia + 13)) + (Grafo.seta_corpo * Yh);
        double pt1y = a1.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * ((Grafo.tamanho_vertice / 4) + Grafo.seta_adjacencia + 13)) - (Grafo.seta_corpo * Xh);
        double pt2x = a1.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * ((Grafo.tamanho_vertice / 4) + Grafo.seta_adjacencia + 13)) - (Grafo.seta_corpo * Yh * -3);
        double pt2y = a1.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * ((Grafo.tamanho_vertice / 4) + Grafo.seta_adjacencia + 13)) + (Grafo.seta_corpo * Xh * -3);

        Polygon p = new Polygon();
        p.addPoint((int) fimX, (int) fimY);
        p.addPoint((int) pt1x, (int) pt1y);
        p.addPoint((int) pt2x, (int) pt2y);
        g.fillPolygon(p);
        p.reset();

        if (this.grafo.getPondered()) {
            hipotenusa = Math.sqrt((Math.pow((fimX - iniX), 2) + Math.pow((fimY - iniY), 2)));
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString(String.valueOf(a1.getWeight()), (int) (a1.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * ((Grafo.tamanho_vertice / 4) + hipotenusa / 2)) + (Grafo.proximidade_label_aresta * Yh)),
                     (int) (a1.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * ((Grafo.tamanho_vertice / 4) + hipotenusa / 2)) - (Grafo.proximidade_label_aresta * Xh)));
        }

        iniX = a2.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) + (Xh * (Grafo.tamanho_vertice / 2)) + ((Grafo.tamanho_vertice / 4 - Grafo.tamanho_vertice / 2) * Yh) - 3;
        iniY = a2.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) + (Yh * (Grafo.tamanho_vertice / 2)) - ((Grafo.tamanho_vertice / 4 - Grafo.tamanho_vertice / 2) * Xh) - 3;
        fimX = a2.getVertex1().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * (Grafo.tamanho_vertice / 2)) + ((Grafo.tamanho_vertice / 4 - Grafo.tamanho_vertice / 2) * Yh) + 3;
        fimY = a2.getVertex1().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * (Grafo.tamanho_vertice / 2)) - ((Grafo.tamanho_vertice / 4 - Grafo.tamanho_vertice / 2) * Xh) + 3;

        g.drawLine((int) iniX, (int) iniY, (int) fimX, (int) fimY);

        pt1x = a2.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * ((-Grafo.tamanho_vertice / 4 - Grafo.tamanho_vertice / 2) + Grafo.seta_adjacencia - 5)) - (Grafo.seta_corpo * Yh);
        pt1y = a2.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * ((-Grafo.tamanho_vertice / 4 - Grafo.tamanho_vertice / 2) + Grafo.seta_adjacencia - 5)) + (Grafo.seta_corpo * Xh);
        pt2x = a2.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) - (Xh * ((-Grafo.tamanho_vertice / 4 - Grafo.tamanho_vertice / 2) + Grafo.seta_adjacencia - 5)) + (Grafo.seta_corpo * Yh * -3);
        pt2y = a2.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) - (Yh * ((-Grafo.tamanho_vertice / 4 - Grafo.tamanho_vertice / 2) + Grafo.seta_adjacencia - 5)) - (Grafo.seta_corpo * Xh * -3);

        p.addPoint((int) iniX, (int) iniY);
        p.addPoint((int) pt1x, (int) pt1y);
        p.addPoint((int) pt2x, (int) pt2y);
        g.fillPolygon(p);

        if (this.grafo.getPondered()) {
            hipotenusa = Math.sqrt((Math.pow((iniX - fimX), 2) + Math.pow((iniY - fimY), 2)));
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString(String.valueOf(a2.getWeight()), (int) (a2.getVertex2().getPosX() + (Grafo.tamanho_vertice / 2) + (Xh * ((Grafo.tamanho_vertice / 2) + hipotenusa / 2)) + (-Grafo.proximidade_label_aresta * Yh)),
                     (int) (a2.getVertex2().getPosY() + (Grafo.tamanho_vertice / 2) + (Yh * ((Grafo.tamanho_vertice / 2) + hipotenusa / 2)) - (-Grafo.proximidade_label_aresta * Xh)));
        }

    }

}
