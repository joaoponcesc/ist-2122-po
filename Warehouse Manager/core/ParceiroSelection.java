package ggc.core;

import java.io.Serializable;

public class ParceiroSelection implements Estatuto, Serializable{

    private static ParceiroSelection _parceiroSelection;

    private ParceiroSelection(){}

    public double retiraPontos(Parceiro parceiro, double pontos, double valorFinal, Date dataAtual, int dataLimite){
        if(dataAtual.getDays() - dataLimite > 2)
            pontos = pontos * 0.1;
            parceiro.setEstatuto(ParceiroNormal.getEstatuto());
        return pontos;
    }
    
    public double valorVendaFinal(double valorInicial, Date dataAtual, int dataLimite, int N){   // N=5 prod simples ,  N=3 prod derivado
        int dias = dataLimite - dataAtual.getDays();
        if(dias >= N)
            return valorInicial * 0.9;
        else if(dias >= 0 && dias < N){
            if(dataAtual.getDays() - dataLimite >= 2)
                return valorInicial * 0.95;
            return valorInicial;
        }
        else if(dias > 0 && dias <= N){
            if(dataAtual.getDays() > dataLimite + 1){
                double multa = 0.02 * (dataAtual.getDays() - dataLimite);
                return valorInicial + valorInicial * multa;
            }
            return valorInicial;
        }
        else{
            double multa = 0.05 * (dataAtual.getDays() - dataLimite);
            return valorInicial + valorInicial * multa;
        }
    }
    
    public String getEstatutoString(){
        return "SELECTION";
    }

    public static ParceiroSelection getEstatuto(){
        if(_parceiroSelection == null)
            _parceiroSelection = new ParceiroSelection();
        return _parceiroSelection;
    }
}
