package dev.blackcat.gateways.controller.dto;

public class Response {

    public enum Status {
        OK,
        ERROR
    }

    private Status status;
    private String message;

    public static Response ok() {
        return new Response(Status.OK, "");
    }

    public static Response error(String message) {
        return new Response(Status.ERROR, message);
    }

    public Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
