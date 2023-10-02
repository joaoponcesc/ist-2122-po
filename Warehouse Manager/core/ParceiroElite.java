package ggc.core;

import java.io.Serializable;

public class ParceiroElite implements Estatuto, Serializable{

    private static ParceiroElite _parceiroElite;

    private ParceiroElite(){}

    public double retiraPontos(Parceiro parceiro, double pontos, double valorFinal, Date dataAtual, int dataLimite){
        if(dataAtual.getDays() - dataLimite > 15)
            pontos = pontos * 0.25;
            parceiro.setEstatuto(ParceiroSelection.getEstatuto());
        return pontos;
    }

    public double valorVendaFinal(double valorInicial, Date dataAtual, int dataLimite, int N){   // N=5 prod simples ,  N=3 prod derivado
        int dias = dataLimite - dataAtual.getDays();
        if(dias >= N)
            return valorInicial * 0.9;
        else if(dias >= 0 && dias < N)
            return valorInicial * 0.9;
        else if(dias > 0 && dias <= N)
            return valorInicial * 0.95;
        else
            return valorInicial;
    }

    public String getEstatutoString(){
        return "ELITE";
    }

    public static ParceiroElite getEstatuto(){
        if(_parceiroElite == null)
            _parceiroElite = new ParceiroElite();
        return _parceiroElite;
    }
}
