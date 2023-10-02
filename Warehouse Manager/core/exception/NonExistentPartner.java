package ggc.core.exception;

public class NonExistentPartner extends Exception{
    
    private String _id;

    public NonExistentPartner(String id){
        _id = id;
    }

    public String getValue(){
        return _id;
    }
}
