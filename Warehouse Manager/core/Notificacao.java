package ggc.core;

import java.io.Serializable;

public class Notificacao implements Serializable{
    private final Produto _produto;
    private final double _precoProduto;
    private final String _tipoNotificacao;

    public Notificacao(Produto produto, double preco, String tipoNotificacao){
        _precoProduto = preco;
        _produto = produto;
        _tipoNotificacao = tipoNotificacao;
    }

    public Produto getProduto(){
        return _produto;
    }

    public double getPrecoProduto(){
        return _precoProduto;
    }

    public String getTipoNotificacao(){
        return _tipoNotificacao;
    }

    @Override
    public String toString(){
        return "" + _tipoNotificacao + "|" + _produto.getId() + "|" + Math.round(_precoProduto);
    }
}
