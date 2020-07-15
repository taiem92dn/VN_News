package com.tngdev.vnnews.network;

public class ApiResource<T> {

    protected T data;
    protected String message;

    public ApiResource(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Success<T> extends ApiResource<T> {
        public Success(T data) {
            super(data, null);
        }
    }

    public static class Loading<T> extends ApiResource<T> {
        public Loading(T data) {
            super(data, null);
        }
    }

    public static class NoInternet<T> extends ApiResource<T> {
        public NoInternet(T data) {
            super(data, null);
        }
    }

    public static class Error<T> extends ApiResource<T> {
        public Error(T data, String message) {
            super(data, message);
        }
    }
}
