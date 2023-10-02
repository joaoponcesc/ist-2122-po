package ggc.core.exception;

public class DuplicatePartnerInserted extends Exception{

    private String _erro;
    
    public DuplicatePartnerInserted(String erro) {
        _erro = erro;
    }

    public String getValue(){
        return _erro;
    }
}