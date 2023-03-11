package org.feup.Mutation_Testing_Backend_Final.Dto.Responses;

public class SimpleResponse {
    private boolean sucess;
    private String message;

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAsError(){
        sucess = false;
        message = "An error has occurred.";
    }

    public void setAsError(String message){
        sucess = false;
        this.message = message;
    }

    public void setAsSuccess(){
        sucess = true;
        this.message = "sucess";
    }

    public void setAsSuccess(String message){
        sucess = true;
        this.message = message;
    }
}
