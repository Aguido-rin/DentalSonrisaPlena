package org.model.adapter;

public interface Adapter<F, T> {
    T convert(F from);
}
