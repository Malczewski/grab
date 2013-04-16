package edu.sl.grabalyze.execution;

import java.util.List;

public interface RunnableFactory {

    List<Runnable> create(int count);
    Callback<List<Runnable>> createCallback();
}
