package com.tranhuudat.nuclearshop.exception;

public class NuclearShopException extends RuntimeException{
    public NuclearShopException(String message){
        super(message);
    }
    public NuclearShopException(String message, Exception exception){
        super(message, exception);
    }
}
