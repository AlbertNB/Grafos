package Modelo;

// Vertice

import java.awt.Color;
import java.awt.Point;
import java.util.Comparator;

public class Vertice {
    private String label;
    private int degree;
    private int satur;                          // Saturation
    private Color color;
    private int posX;
    private int posY;
    private Vertice preview;
    private int weight;
    private boolean closed;
    private boolean solucao;


    public Vertice getPreview() {
        return preview;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
    
    public void setPreview(Vertice preview) {
        this.preview = preview;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public void setSolucao(boolean solucao) {
        this.solucao = solucao;
    }

    public boolean isSolucao() {
        return solucao;
    }
       
    public Vertice(String label){
        this.label = label;
        this.degree = 0;
        this.satur = 0;
        this.color = Color.WHITE;
        this.posX = 0;
        this.posY = 0;
        this.preview = null;
        this.weight = Integer.MAX_VALUE/2;
        this.closed = false;
        this.solucao = false;
    }
    
    public Vertice(String label, int posx, int posy){
        this.label = label;
        this.degree = 0;
        this.satur = 0;
        this.color = Color.PINK;
        this.posX = posx;
        this.posY = posy;
        this.preview = null;
        this.weight = Integer.MAX_VALUE/2;
        this.closed = false;
    }
    
    public void incDegree(){
        this.degree++;
    }
    
    public void subDegree(){
        this.degree--;
    }
    
    public String getLabel() {
        return label;
    }
    
    public int getDegree(){
        return this.degree;
    }
    
    public Color getColor(){
        return this.color;
    }
    
    public void setColor(Color color){
        this.color = color;
    }
    
    public int getSatur(){
        return this.satur;
    }
    
    public void setSatur(int satur){
        this.satur = satur;
    }
    
    public void incSatur(){
        this.satur++;
    }
    
    public void subSatur(){
        this.satur--;
    }
    
    public int getPosX(){
        return this.posX;
    }
    
    public void setPosX(int posX){
        this.posX = posX;
    }
    
    public int getPosY(){
        return this.posY;
    }
    
    public Point getCentroVertice(){
        return (new Point(this.posX+30,this.posY+30));
    }
    
    public void setPosY(int posY){
        this.posY = posY;
    }
    
    public static Comparator<Vertice> getCompByLabel() {   
        Comparator comp = (Comparator<Vertice>) (Vertice vertex1, Vertice vertex2) ->
                vertex1.getLabel().compareTo(vertex2.getLabel());
        return comp;
    }

    public static final Comparator<Vertice> DESCENDING_DEGREE_COMPARATOR = (Vertice vertex1, Vertice vertex2) 
            -> vertex2.getDegree() - vertex1.getDegree();
    
    public static final Comparator<Vertice> DESCENDING_SATUR_COMPARATOR = (Vertice vertex1, Vertice vertex2) 
        -> vertex2.getSatur() - vertex1.getSatur();    

    public static final Comparator<Vertice> DESCENDING_SATUR_THEN_DEGREE_COMPARATOR = (Vertice vertex1, Vertice vertex2) ->  {
        int comp = vertex2.getSatur() - vertex1.getSatur();
        if(comp == 0){
            comp = vertex2.getDegree() - vertex1.getDegree();
        }
        return comp;
        };
}
