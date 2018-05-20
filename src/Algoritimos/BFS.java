package Algoritimos;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import Modelo.ListaAdjacencia;
import Modelo.Grafo;
import Modelo.Vertice;

public class BFS {
    
    private Grafo graph;
    private Queue<Vertice> queue;
    private List<Vertice> visitedList;
    private List<Vertice> vertexList;
    Vertice sourceVertex;
    Vertice targetVertex;
    private boolean found;
    
    public BFS(Grafo graph){
        this.graph = graph;
        found = false;
    }
    
    public BFS(Grafo graph, Vertice sourceVertex){
        this.graph = graph;
        this.sourceVertex = sourceVertex;
        this.targetVertex = null;
        found = false;
    }
    
    public BFS(Grafo graph, Vertice sourceVertex, Vertice targetVertex){
        this.graph = graph;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        found = false;
    }

    public void startBFS(){
        // Lista com todos os vértices do Grafo, conectados ou não
        vertexList = graph.getVertexList();
        
        // Lista com todos os vértices já visitados
        visitedList = new ArrayList();

        // Fila do algoritmo BFS
        this.queue = new LinkedList<>();
        
        if(this.sourceVertex != null){
            queue.add(sourceVertex);
            runBFS();
        }
                        
        // Percorre todos os vertices do grafo, garantindo que mesmo aqueles sem conexão sejam visitados pelo algoritmo
        for(Vertice vertex : vertexList){
            // Se não foi visitado ainda, insere na Fila do BFS e então inicia o algoritmo
            if(!visitedList.contains(vertex) && !found){
                queue.add(vertex);
                runBFS();
            }
        }
        
        // Ao final, imprime a ordem em que os grafos foram visitados
        printVisitedList();
    }
    
    public void runBFS(){
        // Remove o vertice da primeira posição da fila e guarda em uma variável, e então registra o mesmo na lista de visitados
        Vertice vertex = this.queue.poll();
        visitedList.add(vertex);
        
        if(this.targetVertex != null){
            if(this.targetVertex.equals(vertex)){
                found = true;
                return;
            }
        }
        
        // Lista dos vértices adjacentes ao vértice que estava na primeira posição da fila
        List<Vertice> adjacencyVertexList = graph.getAdjacencyList().get(graph.getAdjacencyListIndex(vertex)).getVertexList();
        
        // Adiciona todos os vértices adjacentes que ainda não foram visitados à Fila do algoritmo
        for(Vertice v : adjacencyVertexList){
            if(!visitedList.contains(v) && !found && !queue.contains(v)){
                queue.add(v);
            }
        }
        
        // Se ainda houver vértices na fila do algoritmo, chama recursivamente o algoritmo BFS
        if(this.queue.size() > 0 && !found){
            runBFS();
        }
    }
    
    public List<Vertice> getVisitedList(){
        return this.visitedList;
    }
    
    public void printVisitedList(){
        System.out.println("\nBFS: IMPRESSÃO DA ORDEM DE VISITADOS: ");
        
        for(Vertice v : visitedList){
            System.out.print("-> " + v.getLabel() + " ");
        }
        System.out.println("");
    }
}
