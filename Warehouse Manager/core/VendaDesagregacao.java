package ggc.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendaDesagregacao extends Venda{
    private List<Lote> _lotes = new ArrayList<>();

    VendaDesagregacao(Produto produto, int quantidade, Parceiro parceiro, int id){
        super(produto, quantidade, parceiro, id);
    }

    public void addLoteDesagregacao(Lote l){
        _lotes.add(l);
    }

    public List<Lote> getLotes(){
        return Collections.unmodifiableList(_lotes);
    }

    public boolean isPaid(){
        return true;
    }

    public double getValorBase(){
        return super.getValorBase();
    }

    public void setValorBase(double valor){
        super.setValorBase(valor);
    }

    public void setDataPagamento(Date data){
        super.setDataPagamento(data);
    }

    public void setPrecoAPagar(double valor){
        super.setValorAPagar(valor);
    }

    public double getPrecoAPagar(){
        return super.getValorAPagar();
    }

    public String getResultantesDesagregacao(){
        String resultantes = "";
        for(Lote l : _lotes){
            resultantes += l.getProduto().getId() + ":" + l.getQuantidade() + ":" + Math.round(l.getPreco() * l.getQuantidade()) + "#";
        }
        resultantes = resultantes.substring(0, resultantes.length()- 1 );
        return resultantes;
    }

    @Override
    public String toString(){
        return "DESAGREGAÇÃO" + super.toString() + Math.round(getValorBase()) + "|" + Math.round(getValorAPagar()) + "|" + 
        getDataPagamento() + "|" + getResultantesDesagregacao() ;
    }
}
