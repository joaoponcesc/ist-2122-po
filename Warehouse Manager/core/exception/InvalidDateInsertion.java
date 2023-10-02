package ggc.core.exception;

public class InvalidDateInsertion extends Exception{
    
    private static final long serialVersionUID = 201409301048L;

    private String _dias;
    
    public InvalidDateInsertion(String dias) {
        _dias = dias;
    }

    public String getValue(){
        return _dias;
    }

}
