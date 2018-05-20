package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListaAdjacencia {
    private Vertice thisVertex;
    private List<Vertice> vertexList;
    
    public ListaAdjacencia(Vertice vertex) {
        this.thisVertex = vertex;
        vertexList = new ArrayList();
    }
    
    public void addVertex(Vertice vertex){
        this.vertexList.add(vertex);
        Collections.sort(vertexList, Vertice.getCompByLabel());
    }
    
    public Vertice getVertex (){
        return this.thisVertex;
    }
    
    public List<Vertice> getVertexList(){
        return this.vertexList;
    }
    
    public static Comparator<ListaAdjacencia> getCompByLabel() {   
        Comparator comp = (Comparator<ListaAdjacencia>) (ListaAdjacencia al1, ListaAdjacencia al2) ->
                al1.getVertex().getLabel().compareTo(al2.getVertex().getLabel());
        return comp;
    }
}
