package ggc.core;

public class ProdutoSimples extends Produto{

    ProdutoSimples(String id){
        super(id);
    }

    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public boolean checkQuantity(int quantidade, Parceiro parceiro){return true;}
}
