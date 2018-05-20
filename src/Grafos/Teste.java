package Grafos;

import java.util.ArrayList;
import java.util.List;
import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;

public class Teste {
    
    private Grafo graph;
    
    public Teste() {}
    
    public void fillGraph() {
        // Grafo
        graph = new Grafo(false, true);
        
        // Vertice
        List<Vertice> vertexList = new ArrayList<>();
        
        vertexList.add(new Vertice("A"));
        vertexList.add(new Vertice("B"));
        vertexList.add(new Vertice("C"));
        vertexList.add(new Vertice("D"));
        vertexList.add(new Vertice("E"));
        vertexList.add(new Vertice("F"));
        for(Vertice v : vertexList){
            graph.addVertex(v);
       }  
       
        
        // Aresta
        List<Aresta> edgeList = new ArrayList<>();
       
        
        //Horizontais
        edgeList.add(new Aresta(vertexList.get(0), vertexList.get(2), 7));
        edgeList.add(new Aresta(vertexList.get(3), vertexList.get(5), 4));  
        
        //Centrais
        edgeList.add(new Aresta(vertexList.get(0), vertexList.get(4), 10));   
        edgeList.add(new Aresta(vertexList.get(2), vertexList.get(4), 9));
        edgeList.add(new Aresta(vertexList.get(3), vertexList.get(4), 7));
        edgeList.add(new Aresta(vertexList.get(5), vertexList.get(4), 8));
        
        // Verticais
        edgeList.add(new Aresta(vertexList.get(0), vertexList.get(3), 2));
        edgeList.add(new Aresta(vertexList.get(2), vertexList.get(5), 3));
        
        // Diagonais
        edgeList.add(new Aresta(vertexList.get(2), vertexList.get(1), 3));
        edgeList.add(new Aresta(vertexList.get(5), vertexList.get(1), 2));

        for (Aresta edge : edgeList) {
            graph.addEdge(edge);
        }
    }
    
    public Grafo getGraphInstance() {
        return graph;
    }
}
