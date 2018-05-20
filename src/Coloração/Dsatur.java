package Coloração;

import java.awt.Color;
import java.util.Collections;
import java.util.List;
import Modelo.Grafo;
import Modelo.Vertice;

public class Dsatur {

    private Color[] color;
    private Grafo graph;
    private int colorless;
    
    public Dsatur(Grafo graph){
        this.graph = graph;
        this.colorless = graph.getVertexList().size();
        this.color = new Color[50];
        this.color = Coloracao.generateVisuallyDistinctColors(50, .8f, .3f);
    }
    
    public void startDsatur(){
        List<Vertice> vertexListByDegree = graph.getVertexList(); Collections.sort(vertexListByDegree, Vertice.DESCENDING_DEGREE_COMPARATOR);
        List<Vertice> vertexListBySatur = graph.getVertexList(); Collections.sort(vertexListBySatur, Vertice.DESCENDING_SATUR_THEN_DEGREE_COMPARATOR);                
        
        vertexListByDegree.stream().map((v) -> {                                // Limpa cores e saturação dos Vertices
            v.setColor(Color.WHITE);
            return v;
        }).forEachOrdered((v) -> {
            v.setSatur(0);
        });
        
        while(colorless > 0){
            for(Vertice v : vertexListBySatur){
                if(v.getColor() != Color.WHITE){
                    continue;
                }
                vertexColoring(v);
            }
        }

        /* APENAS TESTE. REMOVER AO FINALIZAR*/
        System.out.println("Impressão para teste de coloração! (Label -> Grau -> Saturação --> Cor");
        vertexListBySatur.forEach((v) -> {
            System.out.println(v.getLabel() + " -> " + v.getDegree() + " -> " + v.getSatur() + " -> " + v.getColor().toString());
        });
    }
    
    
    private void vertexColoring(Vertice vertex){
        // Recebe um vertex, verifica a primeira cor disponível do vetor de cores que ainda
        // não foi utilizada por um de seus adjacentes
        List<Vertice> adjacencyListVertex = graph.getAdjacencyList().                    // Lista de adjacentes
                            get(graph.getAdjacencyListIndex(vertex)).getVertexList();
        boolean achou = false;                                                          // Boolean para controle
        
        for (int i = 0; i < this.color.length; i++){                                    // Quando achar uma cor utilizada, encerra a iteração e testa a próxima cor
            for(Vertice v : adjacencyListVertex){
                if(v.getColor() == color[i]){
                    achou = true;
                    break;
                }
                achou = false;
            }
            if(achou == false){                                                         // Atribui a primeira cor não utilizada pelos adjacentes ao vertice atual
                vertex.setColor(color[i]);
                for(Vertice v : adjacencyListVertex){
                    v.incSatur();
                }
                this.colorless--;                                                       // Reduz a quantidade de vertices sem cor, para o algoritmo de controle
                break;
            }
        }
    }
}

