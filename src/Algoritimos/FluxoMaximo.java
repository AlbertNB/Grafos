/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algoritimos;

import Modelo.Aresta;
import Modelo.Grafo;
import Modelo.Vertice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author alber
 */
public class FluxoMaximo {
    Grafo grafo;
    Grafo residual;
    Vertice fonte;
    Vertice sorvedor;
    int solucao;
    ArrayList<Aresta> caminho ;
    ArrayList<Vertice> vertices_rejeitados ;
    Stack<Vertice> pilha;
    
    
    
    public FluxoMaximo(Grafo g){
        this.grafo = g;
        this.residual = g.getGrafoResidual();
        this.fonte = reconhecerFonte();
        this.sorvedor = reconhecerSorvedor();
        this.solucao = 0;
        this.caminho = new ArrayList<>();
        this.pilha = new Stack<>();
        this.vertices_rejeitados = new ArrayList<>(); 
    }
    
    public void executar(){
        if(this.fonte != null && this.sorvedor != null ){
            System.out.println("Fonte: "+ this.fonte.getLabel()+"\nSorvedor: "+ this.sorvedor.getLabel());
            pegarCaminho();
            popularFluxo();
        }else{
            JOptionPane.showMessageDialog(null, "Fonte ou/e Sorvedor não foram identificados", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void popularFluxo(){
        for(int i=0 ; i<grafo.getEdgeList().size(); i++){
            Aresta a =     grafo.getEdgeList().get(i);
            Aresta a_aux = residual.getEdgeList().get(i);
            a_aux = residual.verficarArestaDupla(a_aux);
                if(a_aux != null){     
                    a.setFluxo(a_aux.getWeight());
                }
        }
        JOptionPane.showMessageDialog(null, "Fluxo Maximo é "+String.valueOf(this.solucao), "Resultado", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void pegarCaminho(){
        caminho.clear();
        this.grafo.fluxoAtualizar(true);
        while(navegar(this.fonte)){
            
            Collections.sort(this.caminho, new Comparator<Aresta>() {
            public int compare(Aresta e1, Aresta e2) {
                return Integer.compare(e1.getWeight(), e2.getWeight());
            }
        });
            
            int fluxo = caminho.get(0).getWeight();
            
            this.solucao += fluxo;
            for(Aresta a : caminho){
                a.setWeight(a.getWeight()-fluxo);
                Aresta contraria = this.residual.verficarArestaDupla(a);
                if(contraria == null){
                    contraria = new Aresta(a.getVertex2(),a.getVertex1(),fluxo);
                    this.residual.addEdge(contraria);
                }else{
                    contraria.setWeight(contraria.getWeight()+fluxo);
                }
            }
            caminho.clear();
            pilha.clear();
            vertices_rejeitados.clear();
        }
    } 
    
    private boolean navegar(Vertice v){
        Aresta aresta_analisada = this.residual.getArestaVertice(v,pilha,vertices_rejeitados);
        
        if(v.equals(this.sorvedor)){
            return true;
        }
        
        if(aresta_analisada != null){
            pilha.push(v);
                if(navegar(aresta_analisada.getVertex2())){
                   caminho.add(aresta_analisada);
                   return true;
                }else{
                    return navegar(pilha.peek());
                } 
        }else{
            vertices_rejeitados.add(v);
            return false;
        }
        
    }
    
    private Vertice reconhecerFonte(){
        for(Vertice v : this.residual.getVertexList()){
           boolean vSolucao = true;
           boolean vIsolado = true;
            for(Aresta a : this.residual.getEdgeList()){
                if(a.getVertex2().equals(v)){
                   vSolucao = false;
                   break;
                }else if(a.getVertex1().equals(v)){
                    vIsolado = false;
                }
            }
            if(vSolucao && !vIsolado){
                return v;
            }
        }
        return null;
    }
    
    private Vertice reconhecerSorvedor(){
         for(Vertice v : this.residual.getVertexList()){
           boolean vSolucao = true;
           boolean vIsolado = true;
            for(Aresta a : this.residual.getEdgeList()){
                if(a.getVertex1().equals(v)){
                   vSolucao = false;
                   break;
                }else if(a.getVertex2().equals(v)){
                    vIsolado = false;
                }
            }
            if(vSolucao && !vIsolado){
                return v;
            }
        }
        return null;
    }
    
}
