package ggc.core;

public class VendaACredito extends Venda{
    private int _dataLimite;
    private boolean _vendaPaga;

    VendaACredito(int dataLimite, Produto produto, int quantidade, Parceiro parceiro, int id){
        super(produto, quantidade, parceiro, id);
        _dataLimite = dataLimite;
        _vendaPaga = false;
    }

    public void pagaVenda(double valor){
        _vendaPaga = true;
        super.setValorAPagar(valor);
    }

    public boolean isPaid(){
        return _vendaPaga;
    }

    public void setValorBase(double valor){
        super.setValorBase(valor);
    }

    public double getValorBase(){
        return super.getValorBase();
    }

    public int getDataLimite(){
        return _dataLimite;
    }

    public void setDataPagamento(Date data){
        super.setDataPagamento(data);
    }

    public void setPrecoAPagar(double valor){
        super.setValorAPagar(valor);
    }

    @Override
    public double getPrecoAPagar(){
        return super.getValorAPagar();
    }

    @Override
    public String toString(){
        String s = "VENDA" +  super.toString() +  Math.round(getValorBase()) + "|" + Math.round(getValorAPagar()) + "|" + _dataLimite;
        if(!isPaid())
            return s;
        return s + "|" + super.getDataPagamento(); 
    }
}
