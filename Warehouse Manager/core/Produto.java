package ggc.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representacao de um produto existente entreposto
 * Um produto é representado por um identificador unico e contem uma 
 * lista de lotes, que contem o produto, e o preco maximo de entre os 
 * lotes
 * 
 * @author JoaoCarvalho & AfonsoCarvalho
 * @version 1.0
 */
public abstract class Produto implements Serializable{
    /**
     * Preco maximo do produto de entre todos os seus lotes
     */
    private double _maxPrice;

    /**
     * Identificador unico do produto
     */
    private String _id;

    /**
     * Lista de lotes que contêm o produto
     */
    private List<Lote> _lotesDoProduto = new ArrayList<>();

    /**
     * Lista de Observers (FOI USADO ARRAYLIST DEVIDO AO FACTO DE HAVER UM BUG NA SERIALIZACAO,
     * SEGUNDO O PROFESSOR DAS TEORICAS, JOAO PEREIRA)
     */
    private List<Observer> _observers = new ArrayList<>();

    /**
     * Default Constructor: classe Produto
     * 
     * @param id Identificador do produto
     */
    public Produto(String id){
        _id = id;
    }

    /**
     * Obtem o identificador do produto
     * 
     * @return Identificador do produto
     */
    public String getId(){
        return _id;
    }
    
    public double getMaxMrice(){
        return _maxPrice;
    }

    public double getMinPrice(){
        double min = 0;
        if(!_lotesDoProduto.isEmpty()){
            min = _lotesDoProduto.get(0).getPreco();
            for(Lote l : _lotesDoProduto){
                if(l.getPreco() < min)
                    min = l.getPreco();
            }
            return min;
        }
        return min;
    }

    /**
     * Obtem o stock total atual do produto
     * 
     * @return Quantidade total existente do produto
     */
    public int getQuantidade(){
        int stock = 0;
        for(Lote l : _lotesDoProduto){
            stock += l.getQuantidade();
        }
        return stock;
    }

    /**
     * Adiciona um lote do produto e atualiza o preco maximo, se necessario
     * 
     * @param l novo lote a adicionar 
     */
    public void addLote(Lote l){
        _lotesDoProduto.add(l);
        if(l.getPreco() > _maxPrice)
            _maxPrice = l.getPreco();
    }

    /**
     * Obtem todos os lotes associados ao produto
     * 
     * @return _lotesProduto lista de lotes que contêm o produto
     */
    public List<Lote> getLotes(){
        return _lotesDoProduto;
    }

    /**
     * Override da funcao toString
     * Muda a representacao da classe Produto no terminal
     * 
     * @return String Representacao do produto
     */
    @Override
    public String toString(){
        return  _id + "|" + Math.round(_maxPrice)+ "|" + getQuantidade();
    }

    /**
     * Verifica a disponibilidade do produto (por realizar, nao necessaria para a entrega intermedia)
     */
    abstract boolean checkQuantity(int quantidade, Parceiro parceiro);


    /**
     * Override da funcao hashCode
     * Redefine o hash code a partir do identificador unico do Produto
     * 
     * @return Novo hash code calculado
     */
    @Override
    public int hashCode(){
        return _id.toLowerCase().hashCode();
    }

    /**
     * Override da funcao equals
     * Redefine o criterio de igualdade entre dois produtos para a 
     * igualdade entre os identificadores de cada um
     * 
     * @return Se dois produtos sao iguais ou nao
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Produto))
            return false;
        Produto p = (Produto)obj;
        return p.getId().toLowerCase().equals(_id.toLowerCase());
    }

    public void ligaDesligaObserver(Observer observer){
        if(_observers.contains(observer))
            _observers.remove(observer);
        else
            _observers.add(observer);
    }
    
    public void notificaObservers(String tipoDeNotificacao, Produto produto, double preco){
        for(Observer observer : _observers){
            observer.enviaNotificacao(tipoDeNotificacao, produto, preco);
        }
    }
}