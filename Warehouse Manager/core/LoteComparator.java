package ggc.core;

import java.util.Comparator;

class LoteComparator implements Comparator<Lote>{
    
    public int compare(Lote l1, Lote l2){
        if(l1.getProduto().getId().toLowerCase().equals(l2.getProduto().getId().toLowerCase())){
            if(l1.getParceiro().getId().toLowerCase().equals(l2.getParceiro().getId().toLowerCase())){
                if((int)l1.getPreco() == (int)l2.getPreco()){
                    return l1.getQuantidade() - l2.getQuantidade();
                }
                return (int)l1.getPreco() - (int)l2.getPreco();
            }
            return l1.getParceiro().getId().toLowerCase().compareTo(l2.getParceiro().getId().toLowerCase());
        }
        return l1.getProduto().getId().toLowerCase().compareTo(l2.getProduto().getId().toLowerCase());
    }
}
