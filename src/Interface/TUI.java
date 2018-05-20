package Interface;

import java.io.IOException;
import java.util.Scanner;
import Modelo.ListaAdjacencia;
import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;
import Algoritimos.DFS;
import Algoritimos.BFS;
import Coloração.Dsatur;
import Coloração.WelshPowell;
import java.util.InputMismatchException;
import java.util.List;

/* Text-based User Interface */
public class TUI {

    Grafo graph;
    Scanner scanner;
    
    public TUI() {
        graph = null;
        scanner = new Scanner(System.in);
    }
     
    public void main() {
        System.out.println("Escolha uma opção:\n");
        System.out.println("1. Criar grafo");
        System.out.println("2. Inserir vértice");
        System.out.println("3. Remover vértice");
        System.out.println("4. Inserir aresta / arco");
        System.out.println("5. Remover aresta / arco");
        System.out.println("6. Retornar vértice");
        System.out.println("7. Verificar existência de aresta");
        System.out.println("8. Retornar vertices adjacentes");
        System.out.println("9. Imprimir grafo");
        System.out.println("10. BFS sem destino");
        System.out.println("11. DFS sem destino");
        System.out.println("12. BFS com destino");
        System.out.println("13. DFS com destino");
        System.out.println("\n14. Sair\n");
        
        System.out.print("Escolha uma opção: ");
        int option = 0;
        try {
            option = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException ex) {
            scanner.next();
            main();
            return;
        }
        switch(option) {
            case 1:
                createGraph();
                main();
                break;
            case 2:
                insertVertex();
                main();
                break;
            case 3:
                removeVertex();
                main();
                break;
            case 4:
                insertEdge();
                main();
                break;
            case 5:
                removeEdge();
                main();
                break;
            case 6:
                returnVertex();
                main();
                break;
            case 7:
                returnEdge();
                main();
                break;
            case 8:
                returnAdjacentVertex();
                main();
                break;
            case 9:
                printGraph();
                main();
                break;
            case 10:
                bfsWithoutDestination();
                main();
                break;
            case 11:
                dfsWithoutDestination();
                main();
                break;
            case 12:
                bfsWithDestination();
                main();
                break;
            case 13:
                dfsWithDestination();
                main();
                break;
            case 14:
                return;
            default:
                main();
                break;
        }
    }
    
    public void createGraph() {
        System.out.println("\n\nCRIAR GRAFO");
        System.out.println("\nEscolha as seguintes opções:");
        System.out.print("\nO grafo é orientado? true / false: ");
        boolean oriented = scanner.nextBoolean();
        System.out.print("\nO grafo é ponderado? true / false: ");
        boolean pondered = scanner.nextBoolean();
        graph = new Grafo(oriented, pondered);
        System.out.println("\nO grafo foi criado com sucesso");
        pause();
    }
    
    public void insertVertex() {
        if(isNull()) return;
        System.out.println("\n\nINSERIR VÉRTICE");
        System.out.print("\nDigite o nome para o vértice: ");
        String label = scanner.nextLine();
        if(label.isEmpty()) {
            System.out.println("\nO nome não pode ser vazio");
            pause();
            return;
        }
        if(graph.getVertexByLabel(label) != null) {
            System.out.println("\nJá existe um vértice com este nome");
            pause();
            return;
        }
        Vertice vertex = new Vertice(label);
        graph.addVertex(vertex);
        System.out.println("\nO vértice foi inserido com sucesso");
        pause();
    }
    
    public void removeVertex() {
        if(isNull()) return;
        System.out.println("\n\nREMOVER VÉRTICE");
        System.out.print("\nDigite o nome do vértice a ser removido: ");
        String label = scanner.nextLine();
        boolean result = graph.removeVertexByLabel(label);
        if(result)
            System.out.println("\nO vértice foi removido com sucesso");
        else
            System.out.println("\nO vértice não foi encontrado ou possui conexões");
        pause();
    }
    
    public void insertEdge() {
        if(isNull()) return;
        System.out.println("\n\nINSERIR ARESTA / ARCO");
        System.out.print("\nDigite o nome do vértice de origem: ");
        String label1 = scanner.nextLine();
        Vertice vertex1 = graph.getVertexByLabel(label1);
        if(vertex1 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        System.out.print("\nDigite o nome do vértice de destino: ");
        String label2 = scanner.nextLine();
        Vertice vertex2 = graph.getVertexByLabel(label2);
        if(vertex2 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        Aresta edge = new Aresta(vertex1, vertex2);
        boolean exists = false;
        if(graph.isVertexConnected(vertex1, vertex2)) exists = true;
        if(!graph.getOriented()){
            if(graph.isVertexConnected(vertex2, vertex1)) exists = true;
        }
        if(exists) {
            System.out.println("\n\nJá existe esta aresta / arco");
            pause();
            return;
        }
        graph.addEdge(edge);
        System.out.println("\nA aresta / arco foi inserida com sucesso");
        pause();
    }
    
    public void removeEdge() {
        if(isNull()) return;
        System.out.println("\n\nREMOVER ARESTA / ARCO");
        System.out.print("\nDigite o nome do vértice de origem: ");
        String label1 = scanner.nextLine();
        Vertice vertex1 = graph.getVertexByLabel(label1);
        if(vertex1 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        System.out.print("\nDigite o nome do vértice de destino: ");
        String label2 = scanner.nextLine();
        Vertice vertex2 = graph.getVertexByLabel(label2);
        if(vertex2 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        Aresta edge = null;
        edge = graph.getEdgeByVertex(vertex1, vertex2);
        if(!graph.getOriented()) {
            if(edge == null) {
                edge = graph.getEdgeByVertex(vertex2, vertex1);
            }
        }
        if(edge == null) {
            System.out.println("\n\nA aresta / arco não existe");
            pause();
            return;
        }
        graph.removeEdge(edge);
        System.out.println("\nA aresta / arco foi removida com sucesso");
        pause();
    }
    
    public void returnVertex() {
        if(isNull()) return;
        System.out.println("\nRETORNAR VÉRTICE");
        System.out.print("\nDigite o nome do vértice: ");
        String label = scanner.nextLine();
        if(label.isEmpty()) {
            System.out.println("\nO nome não pode ser vazio");
            pause();
            return;
        }
        if(graph.getVertexByLabel(label) == null) {
            System.out.println("\nNão existe um vértice com este nome");
            pause();
            return;
        } else {
            System.out.println("\nFoi encontrado um vértice com o nome " + label);
            pause();
            return;
        }
    }
    
    public void returnEdge() {
        if(isNull()) return;
        System.out.println("\n\nVERIFICAR EXISTENCIA DE ARESTA / ARCO");
        System.out.print("\nDigite o nome do vértice de origem: ");
        String label1 = scanner.nextLine();
        Vertice vertex1 = graph.getVertexByLabel(label1);
        if(vertex1 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        System.out.print("\nDigite o nome do vértice de destino: ");
        String label2 = scanner.nextLine();
        Vertice vertex2 = graph.getVertexByLabel(label2);
        if(vertex2 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        boolean exists = false;
        if(graph.isVertexConnected(vertex1, vertex2)) exists = true;
        if(!graph.getOriented()){
            if(graph.isVertexConnected(vertex2, vertex1)) exists = true;
        }
        if(exists) {
            System.out.println("\nFoi encontrada a aresta / arco no grafo");
            pause();
            return;
        } else {
            System.out.println("\nNão foi encontrada a aresta / arco no grafo");
            pause();
            return;
        }
    }
    
    public void returnAdjacentVertex() {
        if(isNull()) return;
        System.out.println("\nRETORNAR VÉRTICES ADJACENTES");
        System.out.print("\nDigite o nome do vértice: ");
        String label = scanner.nextLine();
        if(label.isEmpty()) {
            System.out.println("\nO nome não pode ser vazio");
            pause();
            return;
        }
        Vertice vertex = graph.getVertexByLabel(label);
        if(vertex == null) {
            System.out.println("\nNão existe um vértice com este nome");
            pause();
            return;
        } else {
            List<Vertice> vertexList = graph.getAdjacencyList().get(graph.getAdjacencyListIndex(vertex)).getVertexList();
            System.out.print("\nVertices adjacentes ao vertice " + label + ":\n");
            for (Vertice v : vertexList) {
                System.out.print(v.getLabel() + " | ");
            }
            System.out.println("");
            pause();
            return;
        } 
    }
    
    public void printGraph() {
        if(isNull()) return;
        
        // lista de adjacencia
        System.out.println("\nLISTA DE ADJACENCIA");
        for (ListaAdjacencia adjacencyList : graph.getAdjacencyList()) {
            System.out.print("\n" + adjacencyList.getVertex().getLabel() + " -> ");
            for (Vertice vertex : adjacencyList.getVertexList()) {
                System.out.print(vertex.getLabel() + " | ");
            }
        }
        
        // matriz de ajacencia
        System.out.println("\n\nMATRIZ DE ADJACENCIA\n");
        System.out.print("  | ");
        for (Vertice vertex : graph.getVertexList()) {
            System.out.print(vertex.getLabel() + " | ");
        }
        for (Vertice rowVertex : graph.getVertexList()) {
            System.out.print("\n" + rowVertex.getLabel() + " | ");
            for (Vertice columnVertex : graph.getVertexList()) {
                if (graph.isVertexConnected(rowVertex, columnVertex)) {
                    System.out.print("1 | ");
                } else {
                    System.out.print("0 | ");
                }
            }
        }
        System.out.println();
        pause();
    }
    
    public void dfsWithoutDestination() {
        if(isNull()) return;
        
        System.out.println("\n\nDFS SEM DESTINO");
        System.out.print("\nDigite o nome do vértice de origem: ");
        String label = scanner.nextLine();
        Vertice vertex = graph.getVertexByLabel(label);
        if(vertex == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        
        DFS dfs = new DFS(this.graph, vertex);
        dfs.startDFS();
        //WelshPowell color = new WelshPowell(this.graph);
        //color.startWelshPowell();
        Dsatur color = new Dsatur(this.graph);
        color.startDsatur();
        pause();
    }
    
    public void bfsWithoutDestination() {
        if(isNull()) return;
        
        System.out.println("\n\nBFS SEM DESTINO");
        System.out.print("\nDigite o nome do vértice de origem: ");
        String label = scanner.nextLine();
        Vertice vertex = graph.getVertexByLabel(label);
        if(vertex == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        
        BFS bfs = new BFS(this.graph, vertex);
        bfs.startBFS();
        pause();
    }
    
    public void dfsWithDestination() {
        if(isNull()) return;
        
        System.out.println("\n\nDFS COM DESTINO");
        System.out.print("\nDigite o nome do vértice de origem: ");
        String label1 = scanner.nextLine();
        Vertice vertex1 = graph.getVertexByLabel(label1);
        if(vertex1 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        System.out.print("\nDigite o nome do vértice de destino: ");
        String label2 = scanner.nextLine();
        Vertice vertex2 = graph.getVertexByLabel(label2);
        if(vertex2 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        
        DFS dfs = new DFS(this.graph, vertex1, vertex2);
        dfs.startDFS();
        pause();
    }
    
    public void bfsWithDestination() {
        if(isNull()) return;
        
        System.out.println("\n\nBFS COM DESTINO");
        System.out.print("\nDigite o nome do vértice de origem: ");
        String label1 = scanner.nextLine();
        Vertice vertex1 = graph.getVertexByLabel(label1);
        if(vertex1 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        System.out.print("\nDigite o nome do vértice de destino: ");
        String label2 = scanner.nextLine();
        Vertice vertex2 = graph.getVertexByLabel(label2);
        if(vertex2 == null) {
            System.out.println("\n\nO vértice não existe");
            pause();
            return;
        }
        
        BFS bfs = new BFS(this.graph, vertex1, vertex2);
        bfs.startBFS();
        pause();
    }
    
    public void pause() {
        try {
            System.out.println("\nTecle ENTER para continuar...");
            System.in.read();
        } catch (IOException ex) {
            System.out.println("Um erro ocorreu com o programa");
        }
    }
    
    public boolean isNull(){
        if(graph == null) {
            System.out.println("\n--------------------\n");
            System.out.println("O GRAFO AINDA NÃO FOI CRIADO\n");
            System.out.println("--------------------");
            pause();
            return true;
        }
        return false;
    }
}
