package ggc.core;

import java.io.Serializable;

public interface Observer extends Serializable{

    long serialVersionUID = 202106542006L;
    
    void adicionaNotificacao(Notificacao notificacao);

    void enviaNotificacao(String tipoDeNotificacao, Produto produto, double preco);

    boolean equals(Object obj);

}
