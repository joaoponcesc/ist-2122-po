package ggc.core;

import java.util.Comparator;

class ProdutoComparator implements Comparator<Produto>{

    public int compare(Produto p1, Produto p2){
        return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
    }
}