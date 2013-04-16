package edu.sl.grabalyze.execution;

public interface Callback<T> {
    void onSuccess(T result);
}
