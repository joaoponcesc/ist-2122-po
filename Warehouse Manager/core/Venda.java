package ggc.core;

public abstract class Venda extends Transacao{

    private double _valorAPagar;
    
    Venda(Produto produto, int quantidade, Parceiro parceiro, int id){
        super(parceiro, quantidade, produto, id);
    }

    public abstract double getPrecoAPagar();

    public void setValorBase(double valor){
        super.setValorBase(valor);
    }

    public double getValorAPagar(){
        return _valorAPagar;
    }

    public void setValorAPagar(double valor){
        _valorAPagar = valor;
    }

    public abstract boolean isPaid();

    @Override
    public String toString(){
        return super.toString();
    }

    public void setDataPagamento(Date data){
        super.setDataPagamento(data);
    }
}
