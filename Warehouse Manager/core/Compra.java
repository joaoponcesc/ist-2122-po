package ggc.core;

public class Compra extends Transacao{
    
    Compra(Produto produto, int quantidade, Parceiro parceiro, int id){
        super(parceiro, quantidade, produto, id);
    }

    public double getValorBase(){
        return super.getValorBase();
    }

    @Override
    public String toString(){
        return "COMPRA" + super.toString() + Math.round(getValorBase()) + "|" + super.getDataPagamento();
    }

    public void setValorBase(double valor){
        super.setValorBase(valor);
    }

    public void setDataPagamento(Date data){
        super.setDataPagamento(data);
    }

    public boolean isPaid(){
        return true;
    }
}
