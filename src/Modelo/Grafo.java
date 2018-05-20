package Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 * Classe para criação e manipulação de grafos
 *
 * @author SantosI
 */
public class Grafo {

    private List<Aresta> edgeList = new ArrayList();          // Lista de Arestas ou Arcos
    private List<Vertice> vertexList = new ArrayList();      // Lista de Vertices
    private boolean oriented;                               // true = orientado
    private boolean pondered;                              // true  = peso
    private List<ListaAdjacencia> adjacencyList;          // Lista de adjacencia
    private int codigo_vertice;
    public static final int tolerancia_adjacencia_vertice = 80;
    public static final int tamanho_vertice = 60;
    public static final int contorno_vertice = 4;
    public static final int seta_adjacencia = 10;
    public static final int seta_corpo = 7;
    public static final int proximidade_label_aresta = 15;

    public Grafo(boolean oriented, boolean pondered) {
        this.oriented = oriented;
        this.pondered = pondered;
        this.adjacencyList = new ArrayList();
        this.codigo_vertice = 0;
    }

    public Grafo getGrafoResidual() {
        Grafo g = new Grafo();

        List<Vertice> vLista = new ArrayList<>();
        List<Aresta> aLista = new ArrayList<>();
        for (Vertice v : this.vertexList) {
            vLista.add(new Vertice(v.getLabel(), v.getPosX(), v.getPosY()));
        }

        for (Aresta a : this.edgeList) {
            aLista.add(new Aresta(vLista.get(this.vertexList.indexOf(a.getVertex1())), vLista.get(this.vertexList.indexOf(a.getVertex2())), a.getWeight()));
        }

        g.setArestasEVertices(aLista, vLista);
        g.setOriented(true);
        g.setPondered(true);
        return g;
    }

    public void setArestasEVertices(List<Aresta> arestas, List<Vertice> vertice) {
        for (Aresta a : arestas) {
            a.setFluxo(0);
        }
        this.edgeList = arestas;
        this.vertexList = vertice;
    }

    public Grafo() {
        this.adjacencyList = new ArrayList();
        this.oriented = false;
        this.pondered = false;
    }

    public boolean getOriented() {
        return oriented;
    }

    public void setOriented(boolean oriented) {
        this.oriented = oriented;
    }

    public void setPondered(boolean pondered) {
        this.pondered = pondered;
        if (!this.pondered) {
            for (Aresta a : this.edgeList) {
                a.resetWeight();
            }
        }
    }

    public boolean getPondered() {
        return pondered;
    }

    public List<ListaAdjacencia> getAdjacencyList() {
        return adjacencyList;
    }

    public String getNovoLabelVertice() {
        // if (this.vertexList.isEmpty()) {
        //   return "A";
        //}
        String retorno = "";
        char r = 0;
        int size = this.codigo_vertice;
        int iteracoes = 64;
        if (size < 26) {
            r = (char) (65 + size);
        } else {
            do {
                size = size - 26;
                iteracoes++;
            } while (size >= 26);
            r = (char) (iteracoes);
            retorno = retorno + String.valueOf(r);
            r = (char) (65 + size);
        }
        // char r = (char) (this.vertexList.get(this.vertexList.size() - 1).getLabel().charAt(0) + 1);
        retorno = retorno + String.valueOf(r);
        return retorno;
    }

    public boolean addEdge(Aresta edge) {                  // Função para adicionar aresta
        try {
            this.edgeList.add(edge);
            int index1 = getAdjacencyListIndex(edge.getVertex1());
            int index2 = getAdjacencyListIndex(edge.getVertex2());
            adjacencyList.get(index1).addVertex(edge.getVertex2());
            edge.getVertex2().incDegree();
            if (!oriented && index1 != index2) {
                adjacencyList.get(index2).addVertex(edge.getVertex1());
                edge.getVertex1().incDegree();
            }
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir arco ou aresta: " + e);
            return false;
        }
    }

    public int getAdjacencyListIndex(Vertice vertex) {
        for (ListaAdjacencia adj : adjacencyList) {
            if (adj.getVertex().equals(vertex)) {
                return adjacencyList.indexOf(adj);
            }
        }
        return -1;
    }

    public void fluxoAtualizar(boolean ativar) {
        for (Aresta a : this.edgeList) {
            if (ativar) {
                a.setFluxo(0);
            } else {
                a.setFluxo(-1);
            }
        }
    }

    public List<Aresta> getEdgeList() {
        return this.edgeList;
    }

    public boolean addVertex(Vertice vertex) {            // Função para adicionar Vertice
        try {
            this.vertexList.add(vertex);
            this.codigo_vertice++;
            this.adjacencyList.add(new ListaAdjacencia(vertex));
            Collections.sort(this.adjacencyList, ListaAdjacencia.getCompByLabel());
            Collections.sort(this.vertexList, Vertice.getCompByLabel());
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao inserir Vértice: " + e);
            return false;
        }
    }

    public boolean removeEdge(Aresta edge) {
        edgeList.remove(edge);
        int index1 = getAdjacencyListIndex(edge.getVertex1());
        int index2 = getAdjacencyListIndex(edge.getVertex2());
        adjacencyList.get(index2).getVertexList().remove(edge.getVertex1());
        edge.getVertex1().subDegree();
        if (!oriented) {
            adjacencyList.get(index1).getVertexList().remove(edge.getVertex2());
            edge.getVertex2().subDegree();
        }
        return true;
    }

    public boolean removeVertexByLabel(String label) {
        List<Vertice> list = vertexList;
        int index = getAdjacencyListIndex(getVertexByLabel(label));
        if (adjacencyList.get(index).getVertexList().size() > 0) {
            return false;
        }
        for (Vertice vertex : list) {
            if (vertex.getLabel().equals(label)) {
                vertexList.remove(vertex);
                adjacencyList.remove(index);
                return true;
            }
        }
        return false;
    }

    public Vertice getVertexByLabel(String label) {
        for (Vertice vertex : vertexList) {
            if (vertex.getLabel().equals(label)) {
                return vertex;
            }
        }
        return null;
    }

    public List<Vertice> getVertexList() {
        return this.vertexList;
    }

    public Vertice getVerticePos(int x, int y) {
        for (Vertice v : this.vertexList) {
            if (x < v.getPosX() + tamanho_vertice
                    && x > v.getPosX() - tamanho_vertice
                    && y < v.getPosY() + tamanho_vertice
                    && y > v.getPosY() - tamanho_vertice) {
                return v;
            }
        }
        return null;
    }

    public boolean verificarPos(int x, int y) {
        for (Vertice v : this.vertexList) {
            if (x < v.getPosX() + tolerancia_adjacencia_vertice
                    && x > v.getPosX() - tolerancia_adjacencia_vertice
                    && y < v.getPosY() + tolerancia_adjacencia_vertice
                    && y > v.getPosY() - tolerancia_adjacencia_vertice) {
                return false;
            }
        }
        return true;
    }

    public boolean verificarPos(Vertice vertice, int x, int y) {
        for (Vertice v : this.vertexList) {
            if (v.equals(vertice)) {
                continue;
            }
            if (x < v.getPosX() + tolerancia_adjacencia_vertice
                    && x > v.getPosX() - tolerancia_adjacencia_vertice
                    && y < v.getPosY() + tolerancia_adjacencia_vertice
                    && y > v.getPosY() - tolerancia_adjacencia_vertice) {
                return false;
            }
        }
        return true;
    }

    public boolean checkVertex(Vertice vertex) {          // Função para verificar se o grafo contém o Vertice
        return vertexList.contains(vertex);
    }

    public boolean checkEdge(Aresta edge) {                // Função para verificar se o grafo contém a Aresta
        return edgeList.contains(edge);
    }

    public Aresta getEdgeByVertex(Vertice vertex1, Vertice vertex2) {
        for (Aresta edge : edgeList) {
            if (edge.getVertex1().equals(vertex1) && edge.getVertex2().equals(vertex2)) {
                return edge;
            }
        }
        return null;
    }

    public Aresta verficarArestaDupla(Aresta aresta) {
        int quant = 0;
        if (!this.oriented) {
            return null;
        }
        for (Aresta a : this.getEdgeList()) {
            if (a.getVertex1().equals(aresta.getVertex2()) && a.getVertex2().equals(aresta.getVertex1())) {
                return a;
            }
        }
        return null;
    }

    public boolean verticeEstaConectado(Vertice origem, Vertice destino) {
        for (Aresta a : edgeList) {
            if (this.oriented) {
                if (a.getVertex1().equals(origem) && a.getVertex2().equals(destino)) {
                    return true;
                }
            } else {
                if ((a.getVertex1().equals(origem) && a.getVertex2().equals(destino))
                        || (a.getVertex2().equals(origem) && a.getVertex1().equals(destino))) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isVertexConnected(Vertice source, Vertice destination) {
        List<Vertice> vertexList = getAdjacencyList().get(getAdjacencyListIndex(source)).getVertexList();
        for (Vertice vertex : vertexList) {
            if (vertex.equals(destination)) {
                return true;
            }
        }
        return false;
    }

    public Aresta getArestaVertice(Vertice v, Stack<Vertice> pilha, List<Vertice> vertices_rejeitados) {
        ArrayList<Aresta> lista = new ArrayList<>();
        for (Aresta a : this.edgeList) {
            if (a.getVertex1().equals(v) && a.getWeight() > 0 && !pilha.contains(a.getVertex2()) && !vertices_rejeitados.contains(a.getVertex2())) {
                lista.add(a);
            }
        }
        if (lista.size() > 1) {
            Collections.sort(lista, new Comparator<Aresta>() {
                public int compare(Aresta e1, Aresta e2) {
                    return Integer.compare(e2.getWeight(), e1.getWeight());
                }
            });
        }
        if (lista.isEmpty()) {
            return null;
        }
        return lista.get(0);
    }

    public void removerVertice(int x, int y) {
        Vertice v = getVerticePos(x, y);
        if (v != null) {
            String s = "Deseja excluir o vertice " + v.getLabel() + " e suas arestas?";
            int opcao = JOptionPane.showConfirmDialog(null, s, "Exlucir Vertice", JOptionPane.YES_NO_OPTION);
            if (opcao == JOptionPane.YES_OPTION) {
                this.vertexList.remove(v);
                ArrayList<Aresta> remover = new ArrayList<>();
                for (Aresta a : this.getEdgeList()) {
                    if (a.getVertex1().equals(v) || a.getVertex2().equals(v)) {
                        remover.add(a);
                    }
                }

                for (Aresta a : remover) {
                    this.edgeList.remove(a);
                }
            }
        }
    }

}
