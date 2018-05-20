/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import model.Edge;
import model.Graph;
import model.Vertex;

/**
 *
 * @author alber
 */
public class Dijkstra {

    private Graph graph;
    private Vertex sourceVertex;
    private Vertex targetVertex;
    private List<Vertex> listVertex;
    private List<Vertex> path;

    public Dijkstra(Graph graph, Vertex sourceVertex, Vertex targetVertex) {
        this.graph = graph;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        path = new ArrayList<>();
        listVertex = graph.getListVertex();
    }

    public List<Vertex> getVisitedList() {
        return this.path;
    }

    private void estimate(Vertex s) {
        List<Edge> arestas = new ArrayList<>();
        Vertex min_v = null;
        int min_d = Integer.MAX_VALUE / 2;
        
        for (Edge e : graph.getListEdge()) {
            if (graph.getOriented()) {
                if (e.getVertex1().getLabel().equals(s.getLabel()) && !e.adjacentVertex(s, graph.getOriented()).isFechado()) {
                    arestas.add(e);
                }

            } else {
                if ((e.getVertex1().getLabel().equals(s.getLabel()) || e.getVertex2().getLabel().equals(s.getLabel())) && !(e.adjacentVertex(s, graph.getOriented()).isFechado())) {
                    arestas.add(e);
                }
            }
        }
        for (Edge e : arestas) {
            if (e.adjacentVertex(s, graph.getOriented()).getWeight() > (s.getWeight() + e.getWeight())) {
                e.adjacentVertex(s, graph.getOriented()).setWeight((s.getWeight() + e.getWeight()));
                e.adjacentVertex(s, graph.getOriented()).setPreview(s);
            }
        }
    }

    private int getIndexSmallestWeight(List<Vertex> listVertex) {
        int index_m = -1;
        for (Vertex v : listVertex) {
            if ((!v.isFechado() && index_m == -1)&& (v.getWeight() != Integer.MAX_VALUE/2)) {
                index_m = listVertex.indexOf(v);
            }
            if ((index_m != -1) && (v.getWeight() < listVertex.get(index_m).getWeight()) && (!(v.isFechado())) && (v.getWeight() != Integer.MAX_VALUE/2)){
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
                listVertex.get(index_m).setFechado(true);
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
    }

    private void buildPath(Vertex v) {
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

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Vertex getSourceVertex() {
        return sourceVertex;
    }

    public void setSourceVertex(Vertex sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    public Vertex getTargetVertex() {
        return targetVertex;
    }

    public void setTargetVertex(Vertex targetVertex) {
        this.targetVertex = targetVertex;
    }

}
