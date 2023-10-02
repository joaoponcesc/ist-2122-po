package ggc.core;

import java.io.Serializable;

public abstract class Transacao implements Serializable{
    private Integer _id;
    private int _dataPagamento;
    private double _valorBase;
    private int _quantidade;
    private Produto _produto;  
    private Parceiro _parceiro;                 

    public Transacao(Parceiro parceiro, int quantidade, Produto produto, int id){
        _id = id;
        _quantidade = quantidade;
        _produto = produto;
        _parceiro = parceiro;
    }

    public Parceiro getParceiro(){
        return _parceiro;
    }

    public int getId(){
        return _id;
    }

    public int getDataPagamento(){
        return _dataPagamento;
    }

    public double getValorBase(){
        return _valorBase;
    }

    public void setValorBase(double valor){
        _valorBase = valor;
    }

    public int getQuantidade(){
        return _quantidade;
    }

    public Produto getProduto(){
        return _produto;
    }
    
    public void setDataPagamento(Date data){
        _dataPagamento = data.getDays();
    }

    public abstract boolean isPaid();

    @Override
    public String toString(){
        return "|" + _id + "|" + _parceiro.getId() + "|" + _produto.getId() + "|" + _quantidade  + "|";
    }
    
    @Override
    public int hashCode(){
        return _id.hashCode();
    }







}
