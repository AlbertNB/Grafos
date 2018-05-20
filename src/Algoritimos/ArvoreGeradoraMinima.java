/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritimos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;

public class ArvoreGeradoraMinima {

    private List<Aresta> controle_arestas;
    private List<Vertice> controle_vertices;
    private List<Aresta> solucao;
    private ArrayList<ArrayList<Vertice>> floresta;
    private String type;
    private Grafo graph;
    private boolean finished;
    private int peso;

    public ArvoreGeradoraMinima(Grafo graph, String tipo) {
        this.graph = graph;
        this.type = tipo;
        this.controle_vertices = new ArrayList<>(graph.getVertexList());
        this.controle_arestas = new ArrayList<>(graph.getEdgeList());
        this.solucao = new ArrayList<Aresta>();
        this.finished = false;
        this.peso = 0;

        if (tipo.equals("Prim")) {
            this.floresta = null;
        } else if (tipo.equals("Kruskal")) {
            this.floresta = new ArrayList<>();

            for (Vertice v : this.controle_vertices) {
                ArrayList<Vertice> conjunto = new ArrayList<>();
                conjunto.add(v);
                floresta.add(conjunto);
            }
        }
        Collections.sort(this.controle_arestas, new Comparator<Aresta>() {
            public int compare(Aresta e1, Aresta e2) {
                return Integer.compare(e1.getWeight(), e2.getWeight());
            }
        });
    }

    public void startTreeGeneration() {
        if (!this.finished) {
            switch (this.type) {
                case "Prim":
                    startPrim();
                    System.out.println("Prim started");
                    break;
                case "Kruskal":
                    System.out.println("Kruskal started");
                    startKruskal();
                    break;
            }
            this.finished = true;
        }
    }

    private void startPrim() {
        this.controle_vertices.remove(0);
        while (!this.controle_vertices.isEmpty()) {
            for (Aresta e : controle_arestas) {
                if (estaNoControle(e)) {
                    solucao.add(e);
                    this.controle_vertices.remove(e.getVertex1());
                    this.controle_vertices.remove(e.getVertex2());
                    this.peso = this.peso + e.getWeight();
                    break;
                }
            }
        }
    }

    private boolean estaNoControle(Aresta edge) {
        return ((controle_vertices.contains(edge.getVertex1())
                && !controle_vertices.contains(edge.getVertex2()))
                || (!controle_vertices.contains(edge.getVertex1())
                && controle_vertices.contains(edge.getVertex2())));
    }

    private void startKruskal() {
        for (Aresta e : this.controle_arestas) {
            if (juntarSubArvores(e.getVertex1(), e.getVertex2())) {
                this.solucao.add(e);
                this.peso = this.peso + e.getWeight();
            }
        }
        this.controle_arestas.clear();
    }

    public String escreverSolucao() {
        StringBuilder string = new StringBuilder();
        if (this.finished) {
            for (Aresta e : solucao) {
                string.append(e.getVertex1().getLabel() + e.getVertex2().getLabel() + " - " + e.getWeight() + "\n");
            }
            string.append("Peso total da arvore: " + this.peso + "\n");
        } else {
            string.append("Arvore não executada\n");
        }
        return string.toString();
    }

    private boolean juntarSubArvores(Vertice v1, Vertice v2) {
        ArrayList<Vertice> sub1 = null;
        ArrayList<Vertice> sub2 = null;
        for (ArrayList<Vertice> subarvore : floresta) {
            if (subarvore.contains(v1) && subarvore.contains(v2)) {
                return false;
            } else {
                if (subarvore.contains(v1)) {
                    sub1 = subarvore;
                }
                if (subarvore.contains(v2)) {
                    sub2 = subarvore;
                }
                if (sub1 != null && sub2 != null) {
                    floresta.remove(sub2);
                    for (Vertice v : sub2) {
                        sub1.add(v);
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<Aresta> getSolucao() {
        return this.solucao;
    }

}
