package Algoritimos;

import java.util.ArrayList;
import java.util.List;
import Modelo.Grafo;
import Modelo.Vertice;

public class Planaridade {
    
    private Grafo graph;
    private List<Vertice> vertexList;
    
    public Planaridade(Grafo graph){
        this.graph = graph;
        this.vertexList = new ArrayList<>(graph.getVertexList());
    }
    
    public boolean startPlanarity(){ 
        return tryPlanarity();
    }
    
    public boolean tryPlanarity(){
        if(this.graph.getVertexList().size() <= 2){
            return true;
        }
        
        int qtyVertex = this.graph.getVertexList().size();
        int qtyEdge = this.graph.getEdgeList().size();
        
        if(qtyVertex >= 3 && qtyEdge <= ((qtyVertex * 3) - 6) && hasAThreeCicle()){
            return true;
        }
        
        if(qtyVertex >= 3 && qtyEdge <= ((qtyVertex * 2) - 4) && !hasAThreeCicle()){
            return true;
        }
        
        return false;
    }
    
    public boolean hasAThreeCicle(){
        for(Vertice v: this.vertexList){
            List<Vertice> adjacencyVertexList = graph.getAdjacencyList().get(graph.getAdjacencyListIndex(v)).getVertexList();
            
            for(Vertice j: adjacencyVertexList){
                List<Vertice> adjacencyVertexList2 = graph.getAdjacencyList().get(graph.getAdjacencyListIndex(j)).getVertexList();
                
                for(Vertice k: adjacencyVertexList2){
                    if(this.graph.isVertexConnected(k, v)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
