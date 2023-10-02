package ggc.core.exception;

public class NonExistentTransaction extends Exception{
    private String _id;

    public NonExistentTransaction(String id){
        _id = id;
    }

    public String getValue(){
        return _id;
    }
}
