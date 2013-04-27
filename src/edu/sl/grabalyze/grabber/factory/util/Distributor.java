package edu.sl.grabalyze.grabber.factory.util;

import java.util.ArrayList;
import java.util.List;

public class Distributor <T> {
    
    private List<T> input;
    
    public Distributor(List<T> input) {
        this.input =input;
    }
    
    public List<List<T>> distribute(int count) {
        if (count <= 0)
            throw new IllegalArgumentException("Count cannot be 0 or negative");
        List<List<T>> result = new ArrayList<List<T>>(count);
        int size = input.size() / count;
        int mod = input.size() % count;
        for (int i = 0; i < count; i++) {
            List<T> list = new ArrayList<T>(size + (i < mod ? 1 : 0));
            int start = size * i + Math.min(i, mod);
            int end = start + size + (i < mod ? 1 : 0);
            list.addAll(input.subList(start, end));
            result.add(list);
        }
        return result;
    }
}
