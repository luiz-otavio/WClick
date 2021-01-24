package io.github.aluria.wclick.composite;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public interface Manager<T> {

    List<T> getCollection();

    void put(T value);

    void remove(T value);

    void putAll(Collection<T> collection);

    void removeAll(Collection<T> collection);

    T searchBy(Predicate<T> predicate);

    List<T> fill(Predicate<T> predicate);

}
