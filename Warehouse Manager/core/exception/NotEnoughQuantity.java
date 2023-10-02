package ggc.core.exception;

public class NotEnoughQuantity extends Exception{
    
    private String _id;
    private int _disponivel;
    private int _pedido;

    public NotEnoughQuantity(String id, int disponivel, int pedido){
        _id = id;
        _disponivel = disponivel;
        _pedido = pedido;
    }

    public String getId(){
        return _id;
    }

    public int getDisponivel(){
        return _disponivel;
    }

    public int getPedido(){
        return _pedido;
    }


}
