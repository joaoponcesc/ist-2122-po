package ggc.core;

public class ProdutoDerivado extends Produto{

    private Receita _receita;

    ProdutoDerivado(String id, Receita receita){
        super(id);
        _receita = receita;
    }

    public Receita getReceita(){
        return _receita;
    }

    @Override
    public boolean checkQuantity(int quantidade, Parceiro parceiro){return true;}

    @Override
    public String toString(){
        return super.toString() + "|" + _receita.toString();
    }
}
