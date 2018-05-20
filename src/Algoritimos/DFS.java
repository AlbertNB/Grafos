package Algoritimos;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import Modelo.Grafo;
import Modelo.Vertice;

public class DFS {
    
    private Grafo graph;
    private Stack<Vertice> stack;
    private List<Vertice> visitedList;
    List<Vertice> vertexList;
    Vertice sourceVertex;
    Vertice targetVertex;
    boolean found;
    
    public DFS(Grafo graph){
        this.graph = graph;
        this.sourceVertex = null;
        this.targetVertex = null;
        found = false;
    }
    
    public DFS(Grafo graph, Vertice sourceVertex){
        this.graph = graph;
        this.sourceVertex = sourceVertex;
        this.targetVertex = null;
        found = false;
    }
    
    public DFS(Grafo graph, Vertice sourceVertex, Vertice targetVertex){
        this.graph = graph;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        found = false;
    }

    public void startDFS(){
        // Lista com todos os vértices do Grafo, conectados ou não
        vertexList = graph.getVertexList();
        
        // Lista com todos os vértices já visitados
        visitedList = new ArrayList();
        
        if(this.sourceVertex != null){
            stack = new Stack();
            stack.push(sourceVertex);
            visitedList.add(sourceVertex);
            recursiveStackDepthFirstSearch(stack);
        }
        
        // Para cada vértice do Grafo que ainda não foi visitado roda o algoritmo recursivo
        // Assim garante que mesmo os isolados serão visitados pelo algoritmo
        for(Vertice vertex : vertexList){
            // Se não foi visitado ainda, insere na pilha do DFS e na lista de Visitados, e então inicia o algoritmo
            if(!visitedList.contains(vertex) && !found){
                // Reseta a pilha
                stack = new Stack();
                stack.push(vertex);
                visitedList.add(vertex);
                recursiveStackDepthFirstSearch(stack);
            }
        }
        
        printVisitedList();
    }
    
    public void recursiveStackDepthFirstSearch (Stack<Vertice> stack){
        if(this.targetVertex != null){
            if(this.targetVertex.equals(stack.peek())){
                found = true;
                return;
            }
        }
        // Lista com vertices adjacentes ao Vertice do topo da pilha
        List<Vertice> AdjacencyVertexList = graph.getAdjacencyList().get(graph.getAdjacencyListIndex(stack.peek())).getVertexList();
        
        // Visita o primeiro Vertice adjacente que ainda não foi visitado
        for (Vertice vertex : AdjacencyVertexList) {
            if(!visitedList.contains(vertex) && !found){
                // Empilha, Insere na lista de visitados e chama recursivamente o metodo para todos os métodos adjacentes conectados a este Vertice.
                stack.push(vertex);
                visitedList.add(vertex);
                recursiveStackDepthFirstSearch(stack);
                stack.pop();
            }
        }
    }
    
    public List<Vertice> getVisitedList(){
        return this.visitedList;
    }
    
    public void printVisitedList(){
        System.out.println("\nDFS: IMPRESSÃO DA ORDEM DE VISITADOS: ");
        for(Vertice v : visitedList){
            System.out.print("-> " + v.getLabel() + " ");
        }
        System.out.println("");
    }
    
}
