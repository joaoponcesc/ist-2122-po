package ggc.core;

import java.util.Comparator;

public class LotePorPrecoComparator implements Comparator<Lote>{
    public int compare(Lote l1, Lote l2){
        return (int)l1.getPreco() - (int)l2.getPreco();
    }
}
