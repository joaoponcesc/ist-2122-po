package ggc.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Parceiro implements Observer{
    private String _nome;
    private String _morada;
    private final String _id;
    private double _pontos;
    private List<Lote> _lotes = new ArrayList<>();
    private List<Compra> _compras = new ArrayList<>();
    private List<Venda> _vendas = new ArrayList<>();
    private List<Notificacao> _notificacoes = new ArrayList<>();
    private Estatuto _estatuto;
    private MetodoEnvio _metodoEnvio;

    public Parceiro(String nome, String key, String morada){
        _id = key;
        _nome = nome;
        _morada = morada;
        _estatuto = ParceiroNormal.getEstatuto();
        _metodoEnvio = EnvioDefault.getEnvioDefault();
    }

    public MetodoEnvio getMetodoEnvio(){
        return _metodoEnvio;
    }

    public String getNome(){
        
        return _nome;
    }

    public String getMorada(){
        return _morada;
    }

    public String getId(){
        return _id;
    }

    public double getPontos(){
        return _pontos;
    }

    public List<Lote> getLotes(){
        return _lotes;
    }

    public List<Compra> getCompras(){
        return Collections.unmodifiableList(_compras);
    }

    public List<Venda> getVendas(){
        return Collections.unmodifiableList(_vendas);
    }

    public void addLote(Lote lote){
        _lotes.add(lote);
    }

    public void adicionaCompra(Compra compra){
        _compras.add(compra);
    }

    public void adicionaVenda(Venda venda){
        _vendas.add(venda);
    }

    public int getValorCompras(){
        Double total = 0.0;
        for(Compra compra : _compras){
            total += compra.getValorBase();
        }
        return (int) Math.round(total);
    }

    public int getValorVendas(){
        Double total = 0.0;
        for(Venda venda : _vendas){
            if(venda instanceof VendaACredito)
                total += venda.getValorBase();
        }
        return (int) Math.round(total);
    }

    public int getValorVendasPagas(){
        Double total = 0.0;
        for(Venda venda : _vendas){
            if(venda instanceof VendaACredito){
                VendaACredito vendaCredito = (VendaACredito)venda;
                if(vendaCredito.isPaid())
                    total += vendaCredito.getPrecoAPagar();
            }
        }
        return (int) Math.round(total);
    }

    public List<Notificacao> getNotificacoes(){
        return _notificacoes;
    }

    public void setMetodoEnvio(MetodoEnvio metodoEnvio){
        _metodoEnvio = metodoEnvio;
    }

    @Override
    public String toString(){
        return _id + "|" + _nome + "|" + _morada + "|" + _estatuto.getEstatutoString() + "|" + (int)_pontos
        + "|" + getValorCompras() + "|" + getValorVendas() + "|" + getValorVendasPagas();
    }
    
    @Override
    public int hashCode(){
        return _id.toLowerCase().hashCode();
    }
    
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Parceiro))
            return false;
        Parceiro p = (Parceiro)obj;
        return p.getId().toLowerCase().equals(_id.toLowerCase());
    }
    
    public void setEstatuto(Estatuto estatuto){
        _estatuto = estatuto;
    }
    
    public Estatuto getEstatuto(){
        return _estatuto;
    }
    
    public void atualizaPontosParceiro(double valorFinal, Date dataAtual, int dataLimite){
        _pontos = _estatuto.atualizaPontos(this, _pontos, valorFinal, dataAtual, dataLimite);
    }

    public double calculaValorAPagar(double valor, Date dataAtual, int dataLimite, int N){
        return _estatuto.valorVendaFinal(valor, dataAtual, dataLimite, N);
    }

    public void adicionaNotificacao(Notificacao notificacao){
        _notificacoes.add(notificacao);
    }

    public void enviaNotificacao(String tipoNotificacao, Produto produto, double preco){
        Notificacao notificacao = new Notificacao(produto, preco, tipoNotificacao);
        _metodoEnvio.enviaNotificacao(notificacao, this);
    }

    public void turnOnOffNotificacao(Produto produto){
        produto.ligaDesligaObserver(this);
    }
}
