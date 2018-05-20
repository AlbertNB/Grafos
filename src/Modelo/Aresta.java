package Modelo;

// aresta
public class Aresta {
    private Vertice vertex1;
    private Vertice vertex2;
    private int weight;
    private boolean solucao;
    private int fluxo;
    
    public Aresta(Vertice vertex1, Vertice vertex2, int weight){
        this.vertex1 = vertex1;                             // Vertice origem
        this.vertex2 = vertex2;                             // Vertice destino
        this.weight = weight;                               // Peso
        this.fluxo = -1;
    }
    
    public Aresta(Vertice vertex1, Vertice vertex2){
        this.vertex1 = vertex1;                             // Vertice origem
        this.vertex2 = vertex2;                             // Vertice destino
        this.weight = 1;                                    // Peso
    }
    
    public int getWeight() {
        return weight;
    }

    public int getFluxo() {
        return fluxo;
    }
    
    public int getCapacidade() {
        return (this.weight - this.fluxo);
    }
    
    public void setFluxo(int fluxo) {
        this.fluxo = fluxo;
    }
    
    public Vertice getVertex1() {
        return vertex1;
    }
    
    public Vertice getVertex2() {
        return vertex2;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void resetWeight() {
        this.weight = 1;
    }
    
    public void setSolucao(boolean solucao) {
        this.solucao = solucao;
    }

    public boolean isSolucao() {
        return solucao;
    }
    
    // Retorna verdadeiro se a aresta está conectada ao vertice informado
    public boolean isEdgeConnectedToVertex(Vertice vertex, boolean oriented){
        if(oriented){
            return vertex1.equals(vertex);
        } else {
            return vertex1.equals(vertex) || vertex2.equals(vertex);
        }
    }
    
    public boolean isVertexConnectedToVertex(Vertice vertex1, Vertice vertex2, boolean oriented){
        if(oriented){
            return (this.vertex1 == vertex1) && (this.vertex2 == vertex2);
        } else {
            return ((this.vertex1 == vertex1) && (this.vertex2 == vertex2) || (this.vertex1 == vertex2) && (this.vertex2 == vertex1));
        }
    }
    
    public Vertice getAdjacentVertex(Vertice vertex, boolean oriented){
        if(isEdgeConnectedToVertex(vertex, oriented)){
            if(this.vertex1.equals(vertex)){
                return this.vertex2;
            } else if (this.vertex2.equals(vertex)) {
                return this.vertex1;
            }
        }
        return null;
    }
}
