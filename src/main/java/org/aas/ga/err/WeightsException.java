package org.aas.ga.err;

/**
 * Created by Adam on 12/30/2016.
 */
public class WeightsException extends Exception
{
    private String message = "The relative and absolute weights must equal 1.";

    public WeightsException(){

    }
    public WeightsException(String message){
        this.message =message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
