package ggc.core;

import java.io.Serializable;

/**
 *  Representacao de um Lote de um Produto
 *  Um Lote é representado pelo Produto que contém, pelo preco
 * unitario do Produto, pela quantidade do Produto existente e 
 * pelo Parceiro fornecedor
 * 
 * @author JoaoCarvalho & AfonsoCarvalho
 * @version 1.0
 */
public class Lote implements Serializable{
    /**
     * Preco unitario do Produto no LOte
     */
    private double _preco;
    /**
     * Quantidade total existente de Produto no Lote
     */
    private int _quantidade;
    /**
     * Produto constituinte do Lote
     */
    private Produto _produtoDoLote;
    /**
     * Parceiro fornecedor do Lote
     */
    private Parceiro _parceiro;

    /**
     * Default Construtcor : classe Lote
     * 
     * @param preco Preco unitario do Lote
     * @param quantidadde Quantidade de Produto existente no Lote
     * @param parceiro Fornecedor do Lote
     * @param produto Produto que existe no Lote
     */
    public Lote(double preco, int quantidade, Parceiro parceiro, Produto produto){
        _preco = preco;
        _quantidade = quantidade;
        _parceiro = parceiro;
        _produtoDoLote = produto;
    }

    /**
     * Devolve o preco unitario do produto do lote
     * 
     * @return Preco unitario do Lote
     */
    public double getPreco(){
        return _preco;
    }

    /**
     * Devolve a quantidade de produto existente no Lote
     * 
     * @return Quantidade de produto no lote
     */
    public int getQuantidade(){
        return _quantidade;
    }

    /**
     * Obtem o Parceiro associado ao Lote
     * 
     * @return Parceiro fornecedor do Lote
     */
    public Parceiro getParceiro(){
        return _parceiro;
    }

    /**
     * Devolvolve o Produto existente no Lote
     * 
     * @return Produto constituinte do Lote
     */
    public Produto getProduto(){
        return _produtoDoLote;
    }

    /**
     * Devolve uma copia do lote
     * 
     * @return Copia do lote 
     */
    Lote makeCopy(){
        Lote lote = new Lote(_preco, _quantidade, _parceiro, _produtoDoLote);
        return lote;
    }

    public void diminuiQuantidade(int quantidade){
        _quantidade -= quantidade;
    }

    /**
     * Override da funcao toString, muda a representacao do Lote no terminal
     * 
     * @return Nova representacao do Lote no terminal
     */
    @Override
    public String toString(){
        return "" + _produtoDoLote.getId() + "|" + _parceiro.getId() + "|" + (int)_preco + "|" + _quantidade;
    }
}
