package ggc.core;

import java.io.Serializable;

public class EnvioDefault implements MetodoEnvio, Serializable{
    
    private static EnvioDefault _envioDefault;

    private EnvioDefault(){}

    public static EnvioDefault getEnvioDefault(){
        if(_envioDefault == null)
            _envioDefault = new EnvioDefault();
        return _envioDefault;
    }

    @Override
    public void enviaNotificacao(Notificacao notificacao, Observer observer){
        observer.adicionaNotificacao(notificacao);
    }

}
