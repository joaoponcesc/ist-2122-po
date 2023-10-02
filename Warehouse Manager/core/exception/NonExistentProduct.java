package ggc.core.exception;

public class NonExistentProduct extends Exception{
    private String _id;

    public NonExistentProduct(String id){
        _id = id;
    }

    public String getValue(){
        return _id;
    }
}
