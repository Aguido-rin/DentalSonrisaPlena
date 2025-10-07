package org.patterns.adapter;

public interface Adapter<F, T> {
    T convert(F from);
}
