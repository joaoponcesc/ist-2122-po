package ggc.core;

import java.io.Serializable;

public class Componente implements Serializable{
    private int _quantidade;
    private Produto _produto;

    public Componente(int quantidade, Produto produto){
        _quantidade = quantidade;
        _produto = produto;
    }

    public int getQuantidade(){
        return _quantidade;
    }

    public Produto getProduto(){
        return _produto;
    }

}
