package ggc.core;

import java.io.Serializable;

public class ParceiroNormal implements Estatuto, Serializable{

    private static ParceiroNormal _parceiroNormal;

    private ParceiroNormal(){}

    public double retiraPontos(Parceiro parceiro, double pontos, double valorFinal, Date dataAtual, int dataLimite){
        return 0;
    }

    public double valorVendaFinal(double valorInicial, Date dataAtual, int dataLimite, int N){   // N=5 prod simples ,  N=3 prod derivado
        int dias = dataLimite - dataAtual.getDays();
        if(dias >= N)
            return valorInicial * 0.9;
        else if(dias >= 0 && dias < N)
            return valorInicial;
        else if(dias > 0 && dias <= N){
            double multa = 0.05 * (dataAtual.getDays() - dataLimite);
            return valorInicial + valorInicial * multa;
        }
        else{
            double multa = 0.1 * (dataAtual.getDays() - dataLimite);
            return valorInicial + valorInicial * multa;
        }
    }

    public String getEstatutoString(){
        return "NORMAL";
    }

    public static ParceiroNormal getEstatuto(){
        if(_parceiroNormal == null)
            _parceiroNormal = new ParceiroNormal();
        return _parceiroNormal;
    }
}
