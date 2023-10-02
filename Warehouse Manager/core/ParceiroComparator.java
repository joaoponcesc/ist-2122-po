package ggc.core;

import java.util.Comparator;

class ParceiroComparator implements Comparator<Parceiro>{

    public int compare(Parceiro p1, Parceiro p2){
        return p1.getId().toLowerCase().compareTo(p2.getId().toLowerCase());
    }
}
