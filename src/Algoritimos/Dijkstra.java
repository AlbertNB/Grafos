/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritimos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;

/**
 *
 * @author alber
 */
public class Dijkstra {

    private Grafo graph;
    private Vertice sourceVertex;
    private Vertice targetVertex;
    private List<Vertice> listVertex;
    private List<Vertice> path;

    public Dijkstra(Grafo graph, Vertice sourceVertex, Vertice targetVertex) {
        this.graph = graph;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        path = new ArrayList<>();
        listVertex = graph.getVertexList();
    }

    public List<Vertice> getVisitedList() {
        return this.path;
    }

    private void estimate(Vertice s) {
        List<Aresta> arestas = new ArrayList<>();
        Vertice min_v = null;
        int min_d = Integer.MAX_VALUE / 2;
        
        for (Aresta e : graph.getEdgeList()) {
            if (graph.getOriented()) {
                if (e.getVertex1().getLabel().equals(s.getLabel()) && !e.getAdjacentVertex(s, graph.getOriented()).isClosed()) {
                    arestas.add(e);
                }

            } else {
                if ((e.getVertex1().getLabel().equals(s.getLabel()) || e.getVertex2().getLabel().equals(s.getLabel())) && (!e.getAdjacentVertex(s, graph.getOriented()).isClosed())) {
                    arestas.add(e);
                }
            }
        }
        for (Aresta e : arestas) {
            if (e.getAdjacentVertex(s, graph.getOriented()).getWeight() > (s.getWeight() + e.getWeight())) {
                e.getAdjacentVertex(s, graph.getOriented()).setWeight((s.getWeight() + e.getWeight()));
                e.getAdjacentVertex(s, graph.getOriented()).setPreview(s);
            }
        }
    }

    private int getIndexSmallestWeight(List<Vertice> listVertex) {
        int index_m = -1;
        for (Vertice v : listVertex) {
            if ((!v.isClosed() && index_m == -1)&& (v.getWeight() != Integer.MAX_VALUE/2)) {
                index_m = listVertex.indexOf(v);
            }
            if ((index_m != -1) && (v.getWeight() < listVertex.get(index_m).getWeight()) && (!(v.isClosed())) && (v.getWeight() != Integer.MAX_VALUE/2)){
                index_m = listVertex.indexOf(v);
            }
        }
        return index_m;
    }

    public void startDijkstra() {
        int index_m;
        boolean first = true;
        boolean finished = false;
        do {
            if (!first) {
                index_m = getIndexSmallestWeight(listVertex);
            } else {
                listVertex.get(listVertex.indexOf(this.sourceVertex)).setWeight(0);
                index_m = listVertex.indexOf(this.sourceVertex);
                first = false;
            }
            if (index_m != -1) {
                listVertex.get(index_m).setClosed(true);
                finished = listVertex.get(index_m).getLabel().equals(targetVertex.getLabel());
                if (finished) {
                    buildPath(listVertex.get(index_m));
                    break;
                } else {
                    estimate(listVertex.get(index_m));
                }
            }else{
                finished = true;
            }
        } while (!finished);
        fixPathBugadoTsiTsi();
        for (Vertice vertex : listVertex) {
            vertex.setPreview(null);
            vertex.setWeight(Integer.MAX_VALUE/2);
            vertex.setClosed(false);
        }
    }

    private void buildPath(Vertice v) {
        if (path != null) {
            do {
                path.add(v);
                v = v.getPreview();
            } while (v != null);
        }
    }
    
    public void fixPathBugadoTsiTsi(){
        Collections.reverse(path);
    }

    public void printpath() {
        System.out.println("\nDijkstra: CAMINHO MAIS EFICIENTE: ");
        for (int i = path.size() - 1; i >= 0; i--) {
            System.out.print("-> " + path.get(i).getLabel() + " ");
        }
        System.out.println("");
    }

    public Grafo getGraph() {
        return graph;
    }

    public void setGraph(Grafo graph) {
        this.graph = graph;
    }

    public Vertice getSourceVertex() {
        return sourceVertex;
    }

    public void setSourceVertex(Vertice sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    public Vertice getTargetVertex() {
        return targetVertex;
    }

    public void setTargetVertex(Vertice targetVertex) {
        this.targetVertex = targetVertex;
    }

}
