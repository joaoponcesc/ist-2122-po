package ggc.core;

public interface Estatuto{

    default double atualizaPontos(Parceiro parceiro, double pontos, double valorFinal, Date dataAtual, int dataLimite){
        double novosPontos;
        if(dataLimite < dataAtual.getDays()){
            novosPontos = retiraPontos(parceiro, pontos, valorFinal, dataAtual, dataLimite);
        }
        else{
            novosPontos = incrementaPontos(parceiro, pontos, valorFinal);
        }
        return novosPontos;
    }

    public double valorVendaFinal(double valorInicial, Date dataAtual, int dataLimite, int N);

    public String getEstatutoString();

    default double incrementaPontos(Parceiro parceiro, double pontos, double valorFinal){
        double  novoPontos = pontos + valorFinal * 10;
        if(novoPontos > 25000){
            parceiro.setEstatuto(ParceiroElite.getEstatuto());
        }
        else if(novoPontos > 2000){
            parceiro.setEstatuto(ParceiroSelection.getEstatuto());
        }
        return novoPontos;
    }

    double retiraPontos(Parceiro parceiro, double pontos, double valorFinal, Date dataAtual, int dataLimite);

}
