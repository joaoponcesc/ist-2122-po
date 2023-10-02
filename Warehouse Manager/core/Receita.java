package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Iterator;

public class Receita implements Serializable{
    private double _alpha;
    private List<Componente> _componentes = new ArrayList<>();
    

    public Receita(double alpha, List<Componente> componentes){
        _alpha = alpha;
        _componentes = componentes;
    }

    public Receita(double alpha, List<Integer> quantidades , List<Produto> produtos){
        _alpha = alpha;

        for(int i = 0; i < quantidades.size(); i++){
            _componentes.add(new Componente(quantidades.get(i), produtos.get(i)));
        }
    }

    public double getAlpha(){
        return _alpha;
    }

    public List<Componente> getComponentes(){
        return Collections.unmodifiableList(_componentes);
    }

    @Override
    public String toString(){
        String receita =  "";
        Iterator<Componente> iter = _componentes.iterator();

        while(iter.hasNext()){
            Componente c = iter.next();
            receita += c.getProduto().getId() + ":" + c.getQuantidade() + "#";
        }
        receita = receita.substring(0, receita.length()- 1 );
        return receita;
    }
}
