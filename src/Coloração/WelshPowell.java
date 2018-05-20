package Coloração;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import Modelo.Grafo;
import Modelo.Vertice;

public class WelshPowell {

    private Color[] color;
    private Grafo graph;
    
    public WelshPowell(Grafo graph){
        this.graph = graph;
        this.color = new Color[50];
        this.color = Coloracao.generateVisuallyDistinctColors(50, .8f, .3f);
    }
    
    public void startWelshPowell(){
        List<Vertice> vertexList = graph.getVertexList();
        Collections.sort(vertexList, Vertice.DESCENDING_DEGREE_COMPARATOR);
                
        for(Vertice v : vertexList){                                             // Limpa cores dos vertices
            v.setColor(Color.WHITE);
        }
        
        for(Color c : color){
            for(Vertice v:  vertexList){
                if(v.getColor() == Color.WHITE){
                    Boolean achou = false;
                    
                    List<Vertice> adjacencyListVertex = graph.getAdjacencyList().
                            get(graph.getAdjacencyListIndex(v)).getVertexList();
                    
                    for(Vertice adj : adjacencyListVertex){
                        if(adj.getColor() == c){
                            achou = true;
                            break;
                        }
                    }
                    if(achou == false){
                        v.setColor(c);
                    }
                }
            }
        }
    }
}
